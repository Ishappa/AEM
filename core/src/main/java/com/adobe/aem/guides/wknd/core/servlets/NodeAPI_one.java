package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(service = Servlet.class)
@SlingServletPaths(value = "/bin/new1")
@HttpMethodConstraint(value = "{PUT,GET,POST}")

public class NodeAPI_one extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        ResourceResolver resolver=request.getResourceResolver();
        Resource resource=resolver.getResource("/content/wknd/us/en/page10/jcr:content");
        ValueMap valueMap=resource.getValueMap();
        String title=valueMap.get("jcr:title",toString());
        String page_title=valueMap.get("pageTitle", String.class);
        String resourcetype=valueMap.get("sling:resourceType",toString());
        response.getWriter().write("Title: "+title+"\nPage-Title: "+page_title+"\nResourceType: "+resourcetype);

    }

    @Override
    protected void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        ResourceResolver resolver= request.getResourceResolver();
        Resource resource = resolver.getResource("/content/wknd/us/en/page10/jcr:content");
        ModifiableValueMap modifiableValueMap=null;
        if (resource!= null){
            modifiableValueMap=resource.adaptTo(ModifiableValueMap.class);
            modifiableValueMap.put("pageTitle","modified");

//            modifiableValueMap.remove("feature");
        }
        response.getWriter().write("Properties Value");
        resolver.commit();


    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        ResourceResolver resolver = request.getResourceResolver();
        Resource resource=resolver.getResource("/content/wknd/us/en/page10/jcr:content");
        Map<String,Object> prop =new HashMap<>();

        if(resource!=null){
            resolver.create(resource,"nodePage1",prop);
            resolver.create(resource,"nodePage2",prop);
        }
        response.getWriter().write("New Nodes created");
        resolver.commit();
    }

    @Override
    protected void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        ResourceResolver resolver=request.getResourceResolver();
        Resource resource=resolver.getResource("/content/wknd/us/en/page10/jcr:content/nodePage2");

        if (resource!=null){
            resolver.delete(resource);
        }
        response.getWriter().write("node is deleted");
        resolver.commit();
    }
}
