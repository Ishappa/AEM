package com.adobe.aem.guides.wknd.core.servlets;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes = "wknd/components/carosel_compo" ,
        methods = "GET" ,
        extensions = "json"
)
public class RetriveCurrentPageServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
//        Page currentPage = Optional.ofNullable(request.getResourceResolver().adaptTo(PageManager.class))
//                .map( p-> p.getContainingPage(request.getResource()))
//                .orElse(null);
//
//        if(currentPage != null) {
//            response.getWriter().write("Gotcha! Current page is: " + currentPage.getPath());
//        } else {
//            response.getWriter().write("Oops, couldn't find the current page.");
//        }


        PageManager pageManager = request.getResourceResolver().adaptTo(PageManager.class);
        ResourceResolver resolver = request.getResourceResolver();
        Resource resource = resolver.getResource("/content/wknd/us/en/new/jcr:content/root/container/container/training");
        Page currentPage = null;
        if (pageManager != null) {
            currentPage = pageManager.getContainingPage(resource);
        }

        if (currentPage != null) {
            response.getWriter().write("Current page is: " + currentPage.getPath());

        }
        else {
            response.getWriter().write("Oops, couldn't find the resource in the current page.");
        }

    }
}
