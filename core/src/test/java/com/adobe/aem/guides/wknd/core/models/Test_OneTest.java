package com.adobe.aem.guides.wknd.core.models;

import com.adobe.cq.wcm.core.components.models.Image;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.factory.ModelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class Test_OneTest {
//   private final AemContext context = new AemContext();
//
//    @Mock
//    private Image image;
//
//    @Mock
//    private ModelFactory modelFactory;
//    @BeforeEach
//    void setUp() throws Exception{
//    context.addModelsForClasses(Test_one.class);
//    context.load().json("/com/adobe/aem/guides/wknd/core/models/Test_OneTest.json", "/content");
//
//    lenient().when(modelFactory.getModelFromWrappedRequest(eq(context.request()), any(Resource.class), eq(Image.class)))
//                .thenReturn(image);
//
//    context.registerService(ModelFactory.class, modelFactory, org.osgi.framework.Constants.SERVICE_RANKING,
//                Integer.MAX_VALUE);
//    }
//    @Test
//    public void testGetName() {
//        final String expected = "Jane Doe";
//        context.currentResource("/content/test_Junit");
//        Test_one one = context.request().adaptTo(Test_one.class);
//        String actual = one.getName();
//        assertEquals(expected, actual);
//
//
//    }
//
////    @Test
////    void testGetOccupations() {
////        fail("Not yet implemented");
////    }
////
////    @Test
////    void testIsEmpty() {
////        fail("Not yet implemented");
////    }
//
}
