package com.mysite.core.services.impl;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mysite.core.services.DemoService;
import com.mysite.core.utils.ResolverUtil;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;

@Component(service = DemoService.class, immediate = true)
public class DemoServiceAImpl implements DemoService{

    private static final Logger LOG= LoggerFactory.getLogger(DemoServiceAImpl.class);

    @Activate
    public void activate(ComponentContext componentContext){
        LOG.info("=================INSIDE ACTIVATE==============");
        LOG.info("\n {} = {}",componentContext.getBundleContext().getBundle().getBundleId(),componentContext.getBundleContext().getBundle().getSymbolicName());


    }

    @Deactivate
    public void deactivate(){
        LOG.info("=================INSIDE DEACTIVATE==============");
    }

    @Modified
    public void modified(){
        LOG.info("=================INSIDE MODIFIED==============");
    }

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @ScriptVariable
    Page currentPage;



    @Override
    public Iterator<Page>  getPages(){
        try {
            ResourceResolver resourceResolver= ResolverUtil.newResolver(resourceResolverFactory);

            PageManager pageManager=resourceResolver.adaptTo(PageManager.class);
            Page page=pageManager.getPage("/content/mysite/us/en");

            Iterator<Page> pages= page.listChildren();
            return pages;
        } catch (LoginException e) {
            LOG.info("\n Login Exception {} ",e.getMessage());
        }
        return null;
    }


}
