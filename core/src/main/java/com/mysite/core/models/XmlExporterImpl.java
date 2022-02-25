package com.mysite.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mysite.core.models.XmlExporter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.resource.Resource;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import javax.xml.namespace.QName;


import java.util.*;
@Exporters({
        @Exporter(name = "jackson", extensions = "json", selector = "mysitejson",options = {
                @ExporterOption(name= "SerializationFeature.WRAP_ROOT_VALUE",value="true")
        }),
        @Exporter(name = "mySiteExporter", extensions = "xml", selector = "mysitexml")
})
@Model(
        adaptables = SlingHttpServletRequest.class,
        adapters = XmlExporter.class,
        resourceType = XmlExporterImpl.RESOURCE_TYPE,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@XmlRootElement(name = "mysite-exporter")
@JsonRootName("mysite-json-exporter")
public class XmlExporterImpl implements XmlExporter {
    static final String RESOURCE_TYPE="mysite/components/content/xmlexporter";
    private static final Logger LOG = LoggerFactory.getLogger(XmlExporterImpl.class);

    @Inject
    Resource componentResource;

    @ValueMapValue
    String xmltitle;

    @ValueMapValue
    String xmldescription;

    @ValueMapValue
    Calendar xmldate;

    @ValueMapValue
    private List<String> books;

    List<Map<String, String>> bookDetailsMap;

    @Override
    @JsonProperty(value = "author-title")
    @XmlElement(name = "author-title")
    public String getTitle() {
        return xmltitle;
    }

    @Override
    @JsonProperty(value = "author-description")
    @XmlElement(name = "author-description")
    public String getDescription() {
        return xmldescription;
    }

    @Override
    @JsonProperty(value = "books")
    @XmlElementWrapper(name = "books")
    @XmlElement(name="book")
    public List<String> getBooks() {
        if (books != null) {
            return new ArrayList<String>(books);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    @XmlElement(name = "publish-date")
    public Calendar getDate() {
        return xmldate;
    }
    @XmlElement(name = "author-name")
    public String getAuthorName(){
        return "AEM GEEKS";
    }



}
