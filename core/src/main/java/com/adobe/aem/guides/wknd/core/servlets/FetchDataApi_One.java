package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Component(service = Servlet.class)
@SlingServletPaths("/bin/custom/fetchdatacreatenode")
public class FetchDataApi_One extends SlingAllMethodsServlet {
    private static final String API_URL = "https://newsapi.org/v2/everything?apiKey=9513009648e849d49dc50f01878e981d&q=sport&language=de&from=2024-06-10";
    private static final String INDEX_PROPERTY = "currentIndex";
    private StringBuilder responseString;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
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
            doPost(request, response);
        } else {
            out.println("Status code: " + statusCode + "\"}");
        }
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        nodeCreate(request, response, responseString.toString());
    }

    protected void nodeCreate(SlingHttpServletRequest request, SlingHttpServletResponse response, String data) throws ServletException, IOException {
        ResourceResolver resolver = request.getResourceResolver();
        Resource resource = resolver.getResource("/content/wknd/us/category-page/jcr:content/de");

        if (resource != null) {
            // Retrieve all child nodes
            Iterator<Resource> children = resource.listChildren();
            List<Resource> childNodes = new ArrayList<>();
            while (children.hasNext()) {
                childNodes.add(children.next());
            }

            // Sort child nodes by creation date
            childNodes.sort(Comparator.comparing(Resource::getName));

            // Check if we have more than 6 nodes, then delete the oldest one
            if (childNodes.size() >= 7) {
                Resource oldestNode = childNodes.get(0);
                resolver.delete(oldestNode);
                resolver.commit();
            }

            // Retrieve the current index
            ModifiableValueMap parentProps = resource.adaptTo(ModifiableValueMap.class);
            int currentIndex = parentProps != null && parentProps.containsKey(INDEX_PROPERTY)
                    ? parentProps.get(INDEX_PROPERTY, Integer.class)
                    : 0;

            // Parse the JSON data
            JsonObject jsonData = JsonParser.parseString(data).getAsJsonObject();
            JsonArray articles = jsonData.getAsJsonArray("articles");

            if (currentIndex >= articles.size()) {
                currentIndex = 0;  // Reset index if it exceeds the number of articles
            }

            JsonObject article = articles.get(currentIndex).getAsJsonObject();

            String author = article.has("author") && !article.get("author").isJsonNull() ? article.get("author").getAsString() : "No Author";
            String title = article.has("title") && !article.get("title").isJsonNull() ? article.get("title").getAsString() : "No title";
            String description = article.has("description") && !article.get("description").isJsonNull() ? article.get("description").getAsString() : "No description";

            // Create a new node
            Map<String, Object> prop = new HashMap<>();
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String dateString = formatter.format(date);

            String nodeName = "nodePage_" + dateString + "_" + currentIndex;
            Resource newNode = resolver.create(resource, nodeName, prop);

            // Adding properties to the node
            ModifiableValueMap nodeProps = newNode.adaptTo(ModifiableValueMap.class);
            if (nodeProps != null) {
                nodeProps.put("author", author);
                nodeProps.put("title", title);
                nodeProps.put("description", description);
            }

            // Increment the current index and update the property
            parentProps.put(INDEX_PROPERTY, currentIndex + 1);
            resolver.commit();

            response.getWriter().write("New node created: " + nodeName);
        } else {
            response.getWriter().write("Resource not found");
        }
    }
}
