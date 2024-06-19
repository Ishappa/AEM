package com.adobe.aem.guides.wknd.core.servlets;

import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
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

@Component(service = Servlet.class)
@SlingServletPaths(value = "/bin/page1")
@HttpMethodConstraint(value = "{GET,POST,PUT,DELET}")
public class PageApi_one extends SlingAllMethodsServlet {

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        ResourceResolver resolver=request.getResourceResolver();
//        Resource resource=resolver.getResource("/content/wknd/us/en/page10/newpage3/jcr:content");
//        Map<String,Object> prop=new HashMap<>();
//        resolver.create(resource,"newpage3",prop);

        //if we want to new property for specific node
//        ModifiableValueMap modifiableValueMap=resource.adaptTo(ModifiableValueMap.class);
//        modifiableValueMap.put("newpagetitle","Newtitle");

        //this is for new page creation(Res resolver and pagemanger )
        PageManager pageManager=resolver.adaptTo(PageManager.class);
        try {
            pageManager.create(request.getParameter("parentpath"),request.getParameter("title"),
                    request.getParameter("template"),request.getParameter("name"));

        } catch (WCMException e) {
            throw new RuntimeException(e);
        }

        response.getWriter().write("new page is created");
        resolver.commit();
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        ResourceResolver resolver=request.getResourceResolver();
        Resource resource=resolver.getResource("/content/wknd/us/en/page10/newpage3/jcr:content");
        ValueMap valueMap=resource.getValueMap();
        String title=valueMap.get("jcr:title" ,toString());
        String name = valueMap.get("pageTitle",String.class);
        String resourcetype= valueMap.get("sling:resourceType",toString());

        response.getWriter().write("Title: "+title+"\nName: "+name+"\nresourceType: "+resourcetype);
    }

    @Override
    protected void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        ResourceResolver resolver=request.getResourceResolver();
        Resource resource=resolver.getResource("");
        ModifiableValueMap modifiableValueMap=null;
        if(resource!=null){
            modifiableValueMap=resolver.adaptTo(ModifiableValueMap.class);
            modifiableValueMap.put("jcr:title","modifiedname");

        }
        response.getWriter().write("updated the property");
        resolver.commit();
    }

    @Override
    protected void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        ResourceResolver resolver=request.getResourceResolver();
        Resource resource=resolver.getResource("pass the exact path of page or node");

        if (resource!=null){
            resolver.delete(resource);
        }
        response.getWriter().write("pages are deleted");
        resolver.commit();
    }
}
