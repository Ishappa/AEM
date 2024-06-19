package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.List;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class})
public class TagDropdownModel {

    String filterTag;

    @ValueMapValue
    private String selectedTag;

    public String getSelectedTag() {
        return filterTag;
    }

    @PostConstruct
    void init(){

       filterTag = selectedTag.substring(selectedTag.lastIndexOf(":")+1);
    }
}
