package com.adobe.aem.guides.wknd.core.models;

import com.adobe.cq.wcm.core.components.models.Image;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.factory.ModelFactory;


import javax.annotation.PostConstruct;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Test_one {

    @ValueMapValue
    private String name;

    @ValueMapValue
    private String occupation;

    @ValueMapValue
    private String isEmpty;

    com.adobe.cq.wcm.core.components.models.Image image;

    ModelFactory modelFactory;

    SlingHttpServletRequest request;
//    SlingHttpServletResponse response;

    public String getName() {
        return name;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getIsEmpty() {
        return isEmpty;
    }
    @PostConstruct
    private void init() {
        image = modelFactory.getModelFromWrappedRequest(request, request.getResource(), Image.class);
    }

}
