package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@Component(service = Servlet.class)
@SlingServletPaths("/bin/custom/fetchdataFilter")
public class FetchDataApi_Filter extends SlingAllMethodsServlet {

    private static final String API_KEY = "9513009648e849d49dc50f01878e981d";
    private static final String API_URL = "https://newsapi.org/v2/everything?q=bitcoin&apiKey=" + API_KEY; // Update this with your GraphQL endpoint

    @Override
    protected void doGet(org.apache.sling.api.SlingHttpServletRequest request, org.apache.sling.api.SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        // Build your GraphQL query
        String graphqlQuery = "{\"query\":\"query { articles { author title description } }\"}";

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost postRequest = new HttpPost(API_URL);

        // Setting GraphQL query for the request body
        postRequest.setEntity(new StringEntity(graphqlQuery));
        postRequest.setHeader("Content-Type", "application/json");

        HttpResponse apiResponse = httpClient.execute(postRequest);
        int statusCode = apiResponse.getStatusLine().getStatusCode();

        PrintWriter out = response.getWriter();
        if (statusCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(apiResponse.getEntity().getContent()));
            StringBuilder responseString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseString.append(line);
            }
            out.println(responseString);
        } else {
            out.println("{\"error\":\"Status code: " + statusCode + "\"}");
        }
    }
}
