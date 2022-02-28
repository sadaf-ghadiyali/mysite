package com.mysite.core.config;

import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;

@Configuration(label="MySite - Context Aware Configuration", description = "Context Aware Configuration for My Site")
public  @interface GeekCAConfig {

    @Property(label = "Country Site",
                description = "Site Name")
    String siteCountry() default "us";

    @Property(label = "Site Locale",
            description = "Site for a different Language")
    String siteLocale() default "en";

    @Property(label = "Site Admin",
            description = "Admin for updating Country   ")
    String siteAdmin() default "my-site";

    @Property(label = "Site Section",
            description = "Site Section for Aem   ")
    String siteSection() default "mysite";


}
