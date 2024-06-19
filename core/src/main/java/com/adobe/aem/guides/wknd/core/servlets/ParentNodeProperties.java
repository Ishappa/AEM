package com.adobe.aem.guides.wknd.core.servlets;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletPaths("/bin/parentNode")
public class ParentNodeProperties extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        ResourceResolver resolver = request.getResourceResolver();
        Resource resource=resolver.getResource("/content/wknd/us/en/new");
        PageManager pageManager = resolver.adaptTo(PageManager.class);

        String path = resource.getPath();
        Page page= pageManager.getPage(path);
        Page parentName = page.getAbsoluteParent(2);
        String title = page.getPageTitle();
        String url = page.getVanityUrl();
//        ValueMap valueMap = resource.getValueMap();
//        String title = valueMap.get("jcr:title",toString());
//        response.getWriter().write("Title is : "+title);
        response.getWriter().write("Name: "+parentName.getName()+" Path: "+path+" Title: "+title + " vanityUrl: "+url);


    }
}
