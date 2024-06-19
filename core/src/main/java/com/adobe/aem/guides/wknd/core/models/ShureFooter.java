package com.adobe.aem.guides.wknd.core.models;

import lombok.Getter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Getter
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ShureFooter {

    @ChildResource
    List<ShureFooterHeadings> fields1;

    @ChildResource
    List<ShureFooterIcons> fields3;

    @ValueMapValue
    private String stitle;


    @ValueMapValue
    private String fileReference;

    @ValueMapValue
    private String mailIcon;

    @ValueMapValue
    private String mailPath;

    @ValueMapValue
    private String cr;

    @ValueMapValue
    private String contact;

    @ValueMapValue
    private String path;

    @ValueMapValue
    private String email;


}
