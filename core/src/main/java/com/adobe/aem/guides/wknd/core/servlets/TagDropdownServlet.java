package com.adobe.aem.guides.wknd.core.servlets;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.tagging.Tag;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.annotation.HttpMethodConstraint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@Component(service = Servlet.class)
@HttpMethodConstraint(HttpConstants.METHOD_GET)
@SlingServletPaths(value = "/bin/TagLists")
public class TagDropdownServlet extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagDropdownServlet.class);

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        ResourceResolver resourceResolver = request.getResourceResolver();
        Resource pathResource = request.getResource();
        List<Resource> resourceList = new ArrayList<>();

        String tagsPath = Objects.requireNonNull(pathResource.getChild("datasource")).getValueMap().get("tagsPath", String.class);
        assert tagsPath != null;

        Resource tagsResource = resourceResolver.getResource(tagsPath);
        assert tagsResource != null;

        for (Resource childTags : tagsResource.getChildren()) {
            ValueMap valueMap = new ValueMapDecorator(new HashMap<>());

            Tag tag = childTags.adaptTo(Tag.class);
            assert tag != null;

            String tagFullName = tag.getTagID();
            String tagName = tagFullName.substring(tagFullName.lastIndexOf("/") + 1);
            String tagTitle = tag.getTitle();

            valueMap.put("value", tagName);
            valueMap.put("text", tagTitle);

            resourceList.add(new ValueMapResource(resourceResolver, new ResourceMetadata(), "nt:unstructured", valueMap));
        }
        DataSource dataSource = new SimpleDataSource(resourceList.iterator());
        request.setAttribute(DataSource.class.getName(), dataSource);

        LOGGER.info("Tags successfully exported using DataSource...");
    }
}
