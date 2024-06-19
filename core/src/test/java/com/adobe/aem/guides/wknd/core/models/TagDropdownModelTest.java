package com.adobe.aem.guides.wknd.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(AemContextExtension.class)
public class TagDropdownModelTest {

    private final AemContext context = new AemContext();

    private static final String RESOURCE_PATH = "/content/test";
    private static final String SELECTED_TAG = "namespace:tagValue";

    @BeforeEach
    void setUp() {
        // Create a mock resource with the required properties
        context.build()
                .resource(RESOURCE_PATH, "selectedTag", SELECTED_TAG)
                .commit();
    }

    @Test
    void testGetSelectedTag() {
        // Get the resource and adapt it to the model
        Resource resource = context.resourceResolver().getResource(RESOURCE_PATH);
        TagDropdownModel model = resource.adaptTo(TagDropdownModel.class);

        // Ensure the model is not null
        assertNotNull(model);

        // Verify the behavior of the model
        assertEquals("tagValue", model.getSelectedTag());
    }
}
