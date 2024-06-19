package com.adobe.aem.guides.wknd.core.models;


import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import javax.inject.Named;

@Model(adaptables = {Resource.class,SlingHttpServletRequest.class},defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class PrctcModel {
    @Inject
   private String pageTitle;

    @ScriptVariable
    Page currentPage;

    public String getPageTitle() {
        return currentPage.getTitle();
    }

    @ValueMapValue
    @Named("sling:resourceType")
    private String slingresourceType;

    public String getSlingresourceType() {
        return slingresourceType;
    }

    @ScriptVariable
    Resource resource;

    @ValueMapValue
    @Named("jcr:title")
    private String compoTitle;

    public String getCompoTitle() {
        return compoTitle;
    }
}
