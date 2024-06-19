package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.jcr.*;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TagManager_one {

    @ValueMapValue
    private String tags;

    private String tagging;

    @SlingObject
    private ResourceResolver resourceResolver;

    @PostConstruct
    public void init() {
        System.out.println("init method executing");
        try {
            Session session = resourceResolver.adaptTo(Session.class);
            if (session == null) {
                System.err.println("Failed to adapt ResourceResolver to Session.");
                return;
            }
            System.out.println("Session: " + session);
            String queryString = "SELECT * FROM [cq:PageContent] AS pageContent "
                    + "WHERE ISDESCENDANTNODE(pageContent, '/content') "
                    + "AND pageContent.[cq:tags] = '" + tags + "'";

            QueryManager queryManager = session.getWorkspace().getQueryManager();
            Query query = queryManager.createQuery(queryString, Query.JCR_SQL2);
            QueryResult result = query.execute();

            NodeIterator nodeIterator = result.getNodes();
            while (nodeIterator.hasNext()) {
                Node node = nodeIterator.nextNode();
                try {
                    if (node.hasProperty("jcr:title")) {
                        tagging = node.getProperty("jcr:title").getString();
                        System.out.println("Node title: " + tagging);
                    } else {
                        System.out.println("Node does not have jcr:title property. Path: " + node.getPath());
                    }
                } catch (RepositoryException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTags() {
        return tagging;
    }

//    public String getTagging() {
//        return tagging;
//    }
}
