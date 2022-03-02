package com.mysite.core.models;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.LoginException;

import java.util.Iterator;
import java.util.List;

public interface ServiceDemo {

    Iterator<Page> getPagesList();
    List<String> getPageTitleList();

    public String getNameFromService();
    public String getNameFromServiceB();
}
