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
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.List;

@Model(adaptables = {SlingHttpServletRequest.class,Resource.class},defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ShureHeader {

    //    @SlingObject
//    SlingHttpServletRequest request;
//    @SlingObject
//    ResourceResolver resolver;
    @ScriptVariable
    private ValueMap inheritedPageProperties;

    @ScriptVariable
    private ValueMap pageProperties;

    private String baseUrl;
    @ValueMapValue
    private String fileReference;

    @ValueMapValue
    private String searchbar;

    @ValueMapValue
    private String searchBtn;

    @ValueMapValue
    private String addToCart;

    @ValueMapValue
    private String wishlist;

    @ValueMapValue
    private String signInPath;

    @ValueMapValue
    private String signIn;

    @ValueMapValue
    private String mobileMenu;

    @ValueMapValue
    private String mobileSearchBtn;

    @ValueMapValue
    private String mobileSideShureImg;

    @ValueMapValue
    private String mobileMenuClose;

    @ChildResource
    private List<ShureHeaderProd> field1;

    public String getFileReference() {
        return fileReference;
    }

    public String getSearchbar() {
        return searchbar;
    }

    public String getSearchBtn() {
        return searchBtn;
    }

    public String getAddToCart() {
        return addToCart;
    }

    public String getWishlist() {
        return wishlist;
    }

    public String getSignInPath() {
        return signInPath;
    }

    public String getSignIn() {
        return signIn;
    }


    public String getMobileMenu() {
        return mobileMenu;
    }

    public String getMobileSearchBtn() {
        return mobileSearchBtn;
    }

    public String getMobileSideShureImg() {
        return mobileSideShureImg;
    }

    public String getMobileMenuClose() {
        return mobileMenuClose;
    }

    public List<ShureHeaderProd> getField1() {
        for (ShureHeaderProd s:field1){
            s.link=baseUrl+s.getLink();
            List<ShureHeaderProdItems> field2 = s.getField2();
            if(field2!=null) {
                for (ShureHeaderProdItems spi : field2) {
                    spi.prodLink = baseUrl + spi.getProdLink();
                }
            }
        }
        return field1;
    }

    @PostConstruct
    public void init() {
        if (inheritedPageProperties != null) {
            baseUrl = inheritedPageProperties.get("baseUrl", String.class);
        } else if (pageProperties != null) {
            baseUrl = pageProperties.get("baseUrl", String.class);
        }

    }
}
//        String currentPagePath = request.getRequestURI().replace(".html","");
//        PageManager pageManager = resolver.adaptTo(PageManager.class);
//        Page page = pageManager.getPage(currentPagePath);
//       ValueMap properties = page.getProperties();
//        baseUrl = properties.get("baseUrl", String.class);
//        System.out.println("basss:"+baseUrl);