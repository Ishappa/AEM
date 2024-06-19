//package com.adobe.aem.guides.wknd.core.servlets;
//
//import com.day.cq.tagging.Tag;
//import com.day.cq.tagging.TagManager;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import org.apache.sling.api.SlingHttpServletRequest;
//import org.apache.sling.api.SlingHttpServletResponse;
//import org.apache.sling.api.resource.Resource;
//import org.apache.sling.api.resource.ResourceResolver;
//import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
//import org.apache.sling.api.servlets.HttpConstants;
//import org.apache.sling.servlets.annotations.SlingServletPaths;
//import org.osgi.service.component.annotations.Component;
//import org.osgi.framework.Constants;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.Servlet;
//import javax.servlet.annotation.HttpMethodConstraint;
//import java.io.IOException;
//
//@Component(
////        service = {Servlet.class},
////        property = {
////                Constants.SERVICE_DESCRIPTION + "=Tags Data Source Servlet",
////                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
////                "sling.servlet.paths=" + "/bin/tagsdatasource"
////        }
//service = Servlet.class)
//@HttpMethodConstraint(HttpConstants.METHOD_GET)
//@SlingServletPaths(value = "/bin/tagsdatasource")
//
//public class TaskOne_Jira extends SlingSafeMethodsServlet {
//
//    private static final Logger log = LoggerFactory.getLogger(TaskOne_Jira.class);
//
//    @Override
//    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
//
//        ResourceResolver resourceResolver = request.getResourceResolver();
//        TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
//        Resource resource = resourceResolver.getResource("/content/cq:tags/properties");
//
//        if (tagManager == null) {
//            log.error("TagManager is null");
//            response.getWriter().write("[]");
//        }
//
//        if (resource == null) {
//            log.error("Resource is null for path: /content/cq:tags/properties");
//            response.getWriter().write("[]");
//        }
//
//        Tag[] tags = tagManager.getTags(resource);
//
//        if (tags == null) {
//            log.error("Tags are null for resource: {}", resource.getPath());
//            response.getWriter().write("[]");
//        }
//        if (tags.length == 0) {
//            log.info("No tags found for resource: {}", resource.getPath());
//        }
//        if (tags != null) {
//            JsonArray tagsArray = new JsonArray();
//            for (Tag tag : tags) {
//                JsonObject tagObject = new JsonObject();
////                tagObject.addProperty("value", tag.getTagID());
//                tagObject.addProperty("text", tag.getTitle());
//                tagsArray.add(tagObject);
//            }
//
//            log.info("Tags fetched: {}", tagsArray.toString());
//            response.getWriter().write(tagsArray.toString());
//        } else {
//            log.error("Tags are null for resource: {}", resource.getPath());
//            response.getWriter().write("[]");
//        }
//    }
//}

package com.adobe.aem.guides.wknd.core.servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import java.io.IOException;

@Component(
        service = {Servlet.class},
        property = {
                Constants.SERVICE_DESCRIPTION + "=Tags Data Source Servlet",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.paths=" + "/bin/tagsdatasource"
        }
)
public class TaskOne_Jira_TagsFetching extends SlingSafeMethodsServlet {

    private static final Logger log = LoggerFactory.getLogger(TaskOne_Jira_TagsFetching.class);

    @Reference
    private SlingRepository repository;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        Session session = null;
        try {
            session = repository.loginService("data-reader", null);
            Node tagNode = session.getNode("/content/cq:tags/demo");

            if (tagNode == null) {
                log.error("Node is null for path: /content/cq:tags/demo");
                response.getWriter().write("[]");
                return;
            }

            log.info("Node found for path: {}", tagNode.getPath());

            JsonArray tagsArray = new JsonArray();
            NodeIterator nodeIterator = tagNode.getNodes();
            while (nodeIterator.hasNext()) {
                Node tag = nodeIterator.nextNode();
                JsonObject tagObject = new JsonObject();
                tagObject.addProperty("value", tag.getIdentifier());
                tagObject.addProperty("text", tag.getName());
                tagsArray.add(tagObject);
            }

            log.info("Tags fetched: {}", tagsArray.toString());
            response.getWriter().write(tagsArray.toString());

        } catch (RepositoryException e) {
            log.error("RepositoryException: ", e);
            response.getWriter().write("[]");
        } finally {
            if (session != null) {
                session.logout();
            }
        }
    }
}
