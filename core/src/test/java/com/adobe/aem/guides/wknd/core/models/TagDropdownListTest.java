package com.adobe.aem.guides.wknd.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(AemContextExtension.class)
public class TagDropdownListTest {

    private final AemContext context = new AemContext();

    private static final String RESOURCE_PATH = "/content/test";
    private static final String[] SELECTED_TAGS = {"namespace:tagValue1", "namespace:tagValue2"};

    @BeforeEach
    void setUp() {
        context.build()
                .resource(RESOURCE_PATH, "selectedTag", (Object[]) SELECTED_TAGS)
                .commit();
    }

    @Test
    void testGetSelectedTags() {
        Resource resource = context.resourceResolver().getResource(RESOURCE_PATH);
        TagDropdownList model = resource.adaptTo(TagDropdownList.class);

        assertNotNull(model);

        List<String> expectedTags = Arrays.asList("tagValue1", "tagValue2");
        assertEquals(expectedTags, model.getSelectedTags());

        // Arrays.asList or List.of()  --> is use for converting Array to List
        // example:   List<String> expectedTags = Arrays.asList("tagValue1", "tagValue2");
    }
}
