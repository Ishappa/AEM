package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component(service = Servlet.class)
@SlingServletPaths(value = "/bin/nodeCreate")
@HttpMethodConstraint(value = "{POST}")
public class NodeCreation extends SlingAllMethodsServlet {

    @Reference
    FetchDataApi fetchDataApi;



    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        ResourceResolver resolver = request.getResourceResolver();
        Resource resource = resolver.getResource("/content/wknd/us/category-page/jcr:content/news");

        if (resource != null) {
            Map<String, Object> prop = new HashMap<>();

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String dateString = formatter.format(date);

            String nodeName = "nodePage_" + dateString;
            resolver.create(resource, nodeName, prop);
            response.getWriter().write(String.valueOf(fetchDataApi.responseString));
            
            resolver.commit();

            response.getWriter().write("New node created : " + nodeName);
        } else {
            response.getWriter().write("Resource not found");
        }
    }
}
