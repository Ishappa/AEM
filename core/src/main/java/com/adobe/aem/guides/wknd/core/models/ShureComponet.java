package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import java.util.List;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface ShureComponet {
    @ValueMapValue
    String getCategorytitle();

    @ValueMapValue
    String getProductstitle();

    @Inject
    List<Categories> getCategories();


    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    interface Categories{
        @ValueMapValue
        String getFileReference();

        @ValueMapValue
        String getLinklabel();

        @ValueMapValue
        String getLinkpath();

    }

}
