package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = SlingHttpServletRequest.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class VideoComponent {

    @ValueMapValue
    private String desktopPath;

    @ValueMapValue
    private String mobilePath;

    @ValueMapValue
    private boolean autoplay;

    public boolean isAutoplay() {
        return autoplay;
    }

    public boolean isShowcontrol() {
        return showcontrol;
    }

    public boolean isLoop() {
        return loop;
    }

    @ValueMapValue
    private boolean showcontrol;

    @ValueMapValue
    private String link;

    public String getLink() {
        return link;
    }

    @ValueMapValue
    private boolean loop;


    public String getDesktopPath() {
        return desktopPath;
    }

    public String getMobilePath() {
        return mobilePath;
    }


}
