package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import java.util.List;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface PopularProducts {

    @ValueMapValue
    String getProductstitle();


    @Inject
    List<Products> getProducts();

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    interface Products{
        @ValueMapValue
        String getFileReference();

        @ValueMapValue
        String getButtonlabe();

        @ValueMapValue
        String getButtonlink();

        @ValueMapValue
        String getText();

        @ValueMapValue
        String getCheck();

    }
}
