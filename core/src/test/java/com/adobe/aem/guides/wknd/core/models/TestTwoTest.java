package com.adobe.aem.guides.wknd.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(AemContextExtension.class)
public class TestTwoTest {
    private final AemContext context = new AemContext();
    private TestTwo testTwo;

    @BeforeEach
    void test(){

        Map<String, Object> props = new HashMap<>() ;
        props.put("name","Name is Ish");
        props.put("location", "Location is Bengaluru");
        Resource resource = context.create().resource("/content/test", props);
        testTwo = resource.adaptTo(TestTwo.class);
//        context.currentResource(resource);
//        context.create().resource("/content/test", "location","banglore");


    }

    @Test
    void testProperties(){
//        assertNotNull(testTwo, "Model should not be null");
//        Resource resource = context.resourceResolver().getResource("/content/test");
//        testTwo = resource.adaptTo(TestTwo.class);
        assertEquals("Name is Ish", testTwo.getName());
        assertEquals("Location is Bengaluru", testTwo.getLocation());

    }
}
