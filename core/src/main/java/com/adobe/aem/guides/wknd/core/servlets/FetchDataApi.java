package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@Component(service = Servlet.class)
@SlingServletPaths("/bin/custom/fetchdata")
@HttpMethodConstraint(HttpConstants.METHOD_GET)
public class FetchDataApi extends SlingAllMethodsServlet {

    private static final String API_KEY = "9513009648e849d49dc50f01878e981d";
    private static final String API_URL = "https://newsapi.org/v2/everything?q=bitcoin&apiKey=" + API_KEY;


    public static StringBuilder responseString;

    @Override
    protected void doGet(org.apache.sling.api.SlingHttpServletRequest request, org.apache.sling.api.SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        HttpClient httpClient = HttpClients.createDefault();
        HttpGet getRequest = new HttpGet(API_URL);

        HttpResponse apiResponse = httpClient.execute(getRequest);
        int statusCode = apiResponse.getStatusLine().getStatusCode();

        PrintWriter out = response.getWriter();

        if (statusCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(apiResponse.getEntity().getContent()));
            responseString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseString.append(line);
            }
            out.println(responseString);
        } else {
            out.println("Status code: " + statusCode + "\"}");
        }
    }
}




