package com.adobe.aem.guides.wknd.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.List;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ShureHeaderProd {

    @ValueMapValue
    private String prod;

    @ValueMapValue
    public String link;

    @ChildResource
    private List<ShureHeaderProdItems> field2;

    public String getProd() {
        return prod;
    }
    public String getLink() {
//        System.out.println("second child bas:"+baseUrl);
        return link;
    }
    public List<ShureHeaderProdItems> getField2() {
        return field2;
    }


}