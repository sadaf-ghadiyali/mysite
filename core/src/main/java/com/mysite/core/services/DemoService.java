package com.mysite.core.services;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.LoginException;

import java.util.Iterator;

public interface DemoService {

    public Iterator<Page> getPages() ;
}
