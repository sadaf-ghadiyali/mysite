package com.mysite.core.models.Impl;

import com.day.cq.wcm.api.Page;
import com.mysite.core.models.Author;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.Resource;
import javax.inject.Inject;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = Author.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AuthorImpl implements Author{

    @SlingObject
    ResourceResolver resourceResolver;

    @ScriptVariable
    Page currentPage;

    @RequestAttribute(name = "myAttribute")
    String myAttribute;

    @ValueMapValue
    @Default(values="MY")
    String fname;

    @Inject
    @Via("resource")
    @Default(values="SITE")
    String lname;

    @Override
    public String getFirstName(){
        return fname;
    }
    @Override
    public String getLastName(){
        return lname;
    }

    public String getCurrentPage() {
        return currentPage.getName();
    }

    public String getMyAttribute() {
        return myAttribute;
    }

}
