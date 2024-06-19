package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class})
public class TagDropdownList {

    private List<String> filterTags;

    @ValueMapValue(name = "selectedTag")
    private String[] selectedTags;

    public List<String> getSelectedTags() {
        return filterTags;
    }

    @PostConstruct
    void init() {
        filterTags = new ArrayList<>();
        if (selectedTags != null) {
            for (String tag : selectedTags) {
                String filteredTag = tag.substring(tag.lastIndexOf(":") + 1);
                filterTags.add(filteredTag);
            }
        }
    }


}
