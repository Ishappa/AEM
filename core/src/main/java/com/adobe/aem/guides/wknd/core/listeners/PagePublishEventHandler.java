package com.adobe.aem.guides.wknd.core.listeners;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationEvent;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.HashMap;
import java.util.Map;

@Component(immediate = true,
        property = {
                EventConstants.EVENT_TOPIC + "=" + ReplicationEvent.EVENT_TOPIC
        },
        service = EventHandler.class)
public class PagePublishEventHandler implements EventHandler {

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Override
    public void handleEvent(Event event) {
        ReplicationAction replicationAction = ReplicationEvent.fromEvent(event).getReplicationAction();
        if (ReplicationActionType.ACTIVATE.equals(replicationAction.getType())) {
            String pagePath = replicationAction.getPath();
            createNode(pagePath);
        }
    }

    private void createNode(String path) {
        Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, "aemgeeks-service-user");
        try (ResourceResolver resolver = resolverFactory.getServiceResourceResolver(param)) {
            Resource resource = resolver.getResource(path);
            if (resource != null) {
                Node node = resource.adaptTo(Node.class);
                if (node != null) {
                    // Logic to create a node
                    node.addNode("newNode", "nt:unstructured");
                    resolver.commit();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
