package com.mysite.core.models;


import com.day.cq.wcm.api.Page;
import com.mysite.core.config.GeekCAConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters=CAConfig.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class CAConfigImpl implements CAConfig {
    @SlingObject
    ResourceResolver resourceResolver;

    @ScriptVariable
    Page currentPage;

    private String siteCountry;
    private String siteLocale;
    private String siteAdmin;
    private String siteSection;
    private GeekCAConfig geekCAConfig;


    @Override
    public String getSiteCountry() {
        return siteCountry;
    }

    @Override
    public String getSiteLocale() {
        return siteLocale;
    }

    @Override
    public String getSiteAdmin() {
        return siteAdmin;
    }

    @Override
    public String getSiteSection() {
        return siteSection;
    }

    @PostConstruct
    public void postConstruct(){
        GeekCAConfig caConfig=getContextAwareConfig(currentPage.getPath(),resourceResolver);
        siteCountry= caConfig.siteCountry();
        siteAdmin=caConfig.siteAdmin();
        siteLocale=caConfig.siteLocale();
        siteSection=caConfig.siteLocale();
    }

    public GeekCAConfig getContextAwareConfig(String currentPage,ResourceResolver resourceResolver){
        String currentPath= StringUtils.isNotBlank(currentPage)? currentPage:StringUtils.EMPTY;
        Resource contentResource = resourceResolver.getResource(currentPath);
        if(contentResource!=null){
            ConfigurationBuilder configurationBuilder=contentResource.adaptTo(ConfigurationBuilder.class);
            if(configurationBuilder!=null){
                return configurationBuilder.as(GeekCAConfig.class);
            }
        }
        return null;
    }
}
