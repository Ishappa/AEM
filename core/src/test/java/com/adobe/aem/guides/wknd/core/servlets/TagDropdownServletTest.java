package com.adobe.aem.guides.wknd.core.servlets;

import com.adobe.granite.ui.components.ds.DataSource;
import com.day.cq.tagging.Tag;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagDropdownServletTest {

    @Mock
    SlingHttpServletRequest request;

    @Mock
    SlingHttpServletResponse response;

    @Mock
    ResourceResolver resourceResolver;

    @Mock
    Resource pathResource;

    @InjectMocks
    TagDropdownServlet servlet;

    @BeforeEach
    public void setUp() {
        when(request.getResourceResolver()).thenReturn(resourceResolver);
        when(request.getResource()).thenReturn(pathResource);
    }

    @Test
    public void testDoGet() throws IOException {
        Resource mockDataSourceResource = mockDataSourceResource();
        when(pathResource.getChild("datasource")).thenReturn(mockDataSourceResource);

        Resource mockTagsResource = mockTagsResource();
        when(resourceResolver.getResource(any(String.class))).thenReturn(mockTagsResource);

        Iterable<Resource> mockTagChildren = mockTagChildren();
        when(mockTagsResource.getChildren()).thenReturn(mockTagChildren);

        servlet.doGet(request, response);

//        verify(request).setAttribute(eq(DataSource.class.getName()), any(DataSource.class));
    }

    private Resource mockDataSourceResource() {
        Resource mockResource = Mockito.mock(Resource.class);
        ValueMap valueMap = Mockito.mock(ValueMap.class);
        when(valueMap.get("tagsPath", String.class)).thenReturn("/content/cq:tags");
        when(mockResource.getValueMap()).thenReturn(valueMap);
        return mockResource;
    }

    private Resource mockTagsResource() {
        Resource mockResource = Mockito.mock(Resource.class);
        return mockResource;
    }

    private List<Resource> mockTagChildren() {
        List<Resource> mockChildren = new ArrayList<>();

        Resource mockChild = Mockito.mock(Resource.class);
        Tag mockTag = Mockito.mock(Tag.class);
        when(mockTag.getTagID()).thenReturn("tag/fullName");
        when(mockTag.getTitle()).thenReturn("Tag Title");
        when(mockChild.adaptTo(Tag.class)).thenReturn(mockTag);

        mockChildren.add(mockChild);

        return mockChildren;
    }

}
