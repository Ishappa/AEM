package com.adobe.aem.guides.wknd.core.models;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NodeData_Fetch {

    @Inject
    private Resource resource;
    private List<ContentNode> nodes;

    @PostConstruct
    protected void init() {
        nodes = new ArrayList<>();
        if (resource != null) {
            // Get child resources under /content/wknd/en
            Resource contentResource = resource.getResourceResolver().getResource("/content/wknd/us/category-page/jcr:content/en");
            if (contentResource != null) {
                Iterator<Resource> children = contentResource.listChildren();
                while (children.hasNext()) {
                    Resource child = children.next();
                    ContentNode node = new ContentNode(child.getName(), child.getValueMap());
                    nodes.add(node);
                }
            }
        }
    }

    public List<ContentNode> getNodes() {
        return nodes;
    }

    // Inner class to represent a content node
    public static class ContentNode {
        private String name;
        private ValueMap properties;

        public ContentNode(String name, ValueMap properties) {
            this.name = name;
            this.properties = properties;
        }

        public String getName() {
            return name;
        }

        public ValueMap getProperties() {
            return properties;
        }
    }
}
