package com.adobe.aem.guides.wknd.core.servlets;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Iterator;

@Component(service = { Servlet.class })
@SlingServletPaths("/bin/tags")
@ServiceDescription("Tag Data Source Servlet")
public class TagDataFeching extends SlingSafeMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(TagDataFeching.class);

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            ResourceResolver resourceResolver = request.getResourceResolver();
            TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
            if (tagManager != null) {
                Tag rootTag = tagManager.resolve("/content/cq:tags/demo");
                Iterator<Tag> tags = rootTag.listChildren();
                response.setContentType("application/json");
                response.getWriter().write("[");
                boolean first = true;
                while (tags.hasNext()) {
                    Tag tag = tags.next();
                    if (!first) {
                        response.getWriter().write(",");
                    } else {
                        first = false;
                    }
                    response.getWriter().write("{\"value\":\"" + tag.getTagID() + "\", \"text\":\"" + tag.getTitle() + "\"}");
                }
                response.getWriter().write("]");
            }
        } catch (Exception e) {
            LOG.error("Error fetching tags", e);
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error fetching tags: " + e.getMessage());
        }
    }
}
