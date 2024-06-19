package com.adobe.aem.guides.wknd.core.servlets;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Component(service = Servlet.class , immediate = true)
@SlingServletPaths( value = "/bin/test")
public class QueryBuilderServlet extends SlingAllMethodsServlet {

    @Reference
    QueryBuilder queryBuilder;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        HashMap<String, String> predicates = new HashMap<>();

        predicates.put("type","cq:Page");
        predicates.put("path","/content/wknd/us/en");
        predicates.put("orderby","@jcr:content/jcr:created");
        predicates.put("orderby.sort","desc");
        predicates.put("p.limit","-1");
        Query query = queryBuilder.createQuery(PredicateGroup.create(predicates), request.getResourceResolver().adaptTo(Session.class));
        SearchResult result = query.getResult();
        List<Hit> hits = result.getHits();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for(Hit hit:hits)
        {

            try {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                Resource resource = hit.getResource();
                Resource content = request.getResourceResolver().getResource(resource.getPath() + "/jcr:content");
                objectBuilder.add("title",content.getValueMap().get("jcr:title",String.class));
                objectBuilder.add("path",resource.getPath());
                arrayBuilder.add(objectBuilder);
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        }
        response.getWriter().write(arrayBuilder.build().toString());
    }
}
