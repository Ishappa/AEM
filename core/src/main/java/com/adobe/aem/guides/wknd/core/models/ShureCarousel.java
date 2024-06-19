package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;



@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ShureCarousel {

//    private static final Logger log= LoggerFactory.getLogger(ShureCarousel.class);

    @ValueMapValue
    private String link;

    @ValueMapValue
    private String file;

    public String getFile() {
        return file;
    }

    public String getLink() {
        return link;
    }

}
