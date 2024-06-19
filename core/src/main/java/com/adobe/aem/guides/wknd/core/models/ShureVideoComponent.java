package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;


@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ShureVideoComponent {



    private String title;
    private String description;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

































    @ValueMapValue
    private String path;

    public String getPath() {
        return path;
    }

    @ValueMapValue
    private String mobilepath;

    public String getMobilepath() {
        return mobilepath;
    }

    @ValueMapValue
    private String laptoppath;

    public String getLaptoppath() {
        return laptoppath;
    }

    //    @ValueMapValue
//    private String videourl;
//
//    public String getVideourl(){
//        return videourl;
//    }


}
