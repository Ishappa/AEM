package com.adobe.aem.guides.wknd.core.models;

import lombok.Getter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ParsysCompo {

    @ValueMapValue
    private int columns;

    @Getter
    private List<Integer> a;

    @PostConstruct
    protected void init(){
        a=new ArrayList<>();
        for(int i=0;i<columns;i++){
            a.add(i);
        }
    }
}



