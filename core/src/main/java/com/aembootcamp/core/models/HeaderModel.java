package com.aembootcamp.core.models;

import com.day.cq.wcm.api.Page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = SlingHttpServletRequest.class, adapters = HeaderModel.class, resourceType = HeaderModel.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class HeaderModel {
    protected static final String RESOURCE_TYPE = "aem-bootcamp/components/header";

    @ValueMapValue(name = "file")
    private String file;

    @ValueMapValue(name = "altText")
    private String altText;

    @ValueMapValue(name = "logoLink")
    private String logoLink;

    @ChildResource(injectionStrategy = InjectionStrategy.DEFAULT)
    Resource navigations;

    @SlingObject
    private ResourceResolver resourceResolver;

    List<Page> headerNavigationItemsList;

    @PostConstruct
    protected void init() {
        headerNavigationItemsList = new ArrayList<>();
        Iterator<Resource> children = navigations.listChildren();
        while (children.hasNext()) {
            Resource childResource = children.next();
//            ValueMap vm = childResource.adaptTo(ValueMap.class);
//            String path =  childResource.getValueMap().get("rootPath", String.class);
//            Page navPage = resourceResolver.getResource(path).adaptTo(Page.class);
//            headerNavigationItemsList.add(navPage);
            ValueMap properties = childResource.adaptTo(ValueMap.class);
            String rootPath = properties.get("rootPath", String.class);
            Page navPage = resourceResolver.getResource(rootPath).adaptTo(Page.class);
            headerNavigationItemsList.add(navPage);

        }
    }

    public String getFile() {
        return file;
    }

    public String getAltText() {
        return altText;
    }

    public String getLogoLink() {
        return logoLink;
    }

    public List<Page> getHeaderNavigationItemsList() {
        return headerNavigationItemsList;
    }
}
