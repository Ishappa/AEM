package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@Component(service = Servlet.class)
@HttpMethodConstraint(HttpConstants.METHOD_GET)
@SlingServletPaths(value = "/bin/shuregraphql")
public class ShureDataServlet extends SlingAllMethodsServlet {

   @Override
    protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws IOException {
        String endPoint = "https://shureshop.in/graphql";
        String graphqlQuery = "{\n" +
                "  \"query\": \"query getProducts { products( filter: { category_id: { eq: \\\"9\\\" } custom_filter: [] } sort: { custom_sort: [ { attribute: \\\"position\\\", direction: ASC } ] } pageSize: 29 ) { total_count items { id sku name type_id stock_status special_price only_x_left_in_stock price { minimalPrice { amount { value currency } } regularPrice{ amount{ value currency } } } thumbnail { url path label } } } }\"\n" +
                "}";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(endPoint);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("authority", "shureshop.in");
        httpPost.setHeader("scheme", "https");
        httpPost.setEntity(new StringEntity(graphqlQuery));
        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity entity = httpResponse.getEntity();

        if (entity != null) {
//            System.out.println("printing Entity:"+entity);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"))) {
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                resp.getWriter().write(String.valueOf(response));
            } finally {
                // Ensure the entity content is fully consumed/closed
                EntityUtils.consume(entity);
            }
        }


    }
}