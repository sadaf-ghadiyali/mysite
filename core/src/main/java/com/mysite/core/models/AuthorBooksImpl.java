package com.mysite.core.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.mysite.core.helper.MultifieldHelper;
import com.mysite.core.helper.NestedHelper;
import com.mysite.core.models.Author;
import com.mysite.core.models.AuthorBooks;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.inject.Inject;
import java.util.*;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = AuthorBooks.class,
        //resourceType = AuthorBooksImpl.RESOURCE_TYPE,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name="jackson",extensions = "json",
        options = {
                @ExporterOption(name = "MapperFeature.SORT_PROPERTIES_ALPHABETICALLY", value="true"),
                @ExporterOption(name= "SerializationFeature.WRAP_ROOT_VALUE",value="true")

        }
)
public class AuthorBooksImpl implements AuthorBooks {
    private static final Logger LOG = LoggerFactory.getLogger(AuthorBooksImpl.class);
    //static final String RESOURCE_TYPE="mysite/components/content/author-books";

    @Inject
    Resource componentResource;

    @ValueMapValue
    @Default(values = "AEM")
    private String authorname;

    @ValueMapValue
    private List<String> books;

//    @JsonProperty(value="resourceName")
//    public String resourceName(){
//        return componentResource.getName();
//    }


    @Override
    public String getAuthorName() {
        return authorname;
    }

    @Override
    public List<String> getAuthorBooks() {
        if(books!=null){
            return new ArrayList<String>(books);

        }else{
            return Collections.emptyList();
        }
    }

    @ChildResource(name = "bookdetailswithmap")
    Resource bookDetail;


    @Override
    public List<Map<String, String>> getBookDetailsWithMap() {
        List<Map<String, String>> bookDetailsMap=new ArrayList<>();
        try {
            //Resource bookDetail=componentResource.getChild("bookdetailswithmap");

            if(bookDetail!=null){
                for (Resource book : bookDetail.getChildren()) {
                    Map<String,String> bookMap=new HashMap<>();
                    bookMap.put("bookname",book.getValueMap().get("bookname",String.class));
                    bookMap.put("bookgenre",book.getValueMap().get("bookgenre",String.class));
                    bookMap.put("bookpublishyear",book.getValueMap().get("bookpublishyear",String.class));
                    bookDetailsMap.add(bookMap);
                }
            }
        }catch (Exception e){
            LOG.info("\n ERROR while getting Book Details {} ",e.getMessage());
        }
        LOG.info("\n SIZE {} ",bookDetailsMap.size());
        return bookDetailsMap;
    }

    @ChildResource(name = "bookdetailswithbean")
    Resource bookDetailBean;

    @Override
    public List<MultifieldHelper> getBookDetailsWithBean(){
        List<MultifieldHelper> bookDetailsBean=new ArrayList<>();
        try {

            //Resource bookDetailBean=componentResource.getChild("bookdetailswithbean");
            if(bookDetailBean!=null){
                for (Resource bookBean : bookDetailBean.getChildren()) {
                    LOG.info("\n PATH Bean {} ",bookBean.getPath());
                    LOG.info("\n BEAN PRO {} ",bookBean.getValueMap().get("bookname",String.class));

                    bookDetailsBean.add(new MultifieldHelper(bookBean));
                }
            }
        }catch (Exception e){
            LOG.info("\n ERROR while getting Book Details With Bean {} ",e.getMessage());
        }
        return bookDetailsBean;
    }

    @ChildResource(name = "bookdetailswithnestedmultifield")
    Resource bookDetailNested;



    @Override
    public List<MultifieldHelper> getBookDetailsWithNestedMultifield() {
        List<MultifieldHelper> bookDetailsNested=new ArrayList<>();
        try {
            //Resource bookDetailNested=componentResource.getChild("bookdetailswithnastedmultifield");
            if(bookDetailNested!=null){
                for (Resource bookNasted : bookDetailNested.getChildren()) {
                    MultifieldHelper multifieldHelper=new MultifieldHelper(bookNasted);
                    if(bookNasted.hasChildren()){
                        List<NestedHelper> bookNastedList=new ArrayList<>();
                        Resource nastedResource=bookNasted.getChild("bookeditions");
                        for(Resource nasted : nastedResource.getChildren()){
                            bookNastedList.add(new NestedHelper(nasted));
                        }
                        multifieldHelper.setBookEditions(bookNastedList);
                    }
                    bookDetailsNested.add(multifieldHelper);
                }
            }
        }catch (Exception e){
            LOG.info("\n ERROR while getting Book Details With Nasted Multifield {} ",e.getMessage());
        }
        LOG.info("\n SIZE Multifield {} ",bookDetailsNested.size());
        return bookDetailsNested;
    }

}
