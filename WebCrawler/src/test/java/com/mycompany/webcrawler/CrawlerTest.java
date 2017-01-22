package com.mycompany.webcrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author xxx
 */
@RunWith(MockitoJUnitRunner.class)
public class CrawlerTest {
    private static final String DOMAIN = "http://www.test.com/";
    private static final String PAGE_ONE = "page1.html";
    private static final String PAGE_TWO = "page2.html";
    private static final String PAGE_THREE = "page3.html";
    
    @Mock  
    HttpUtil httpUtil;
    
    Crawler crawler;
    
    @Before
    public void setUp(){
        crawler = new Crawler(DOMAIN, httpUtil);
    
    }
    
    @Test
    public void testSimpleSiteWithNoLinks() {
        List<String> pageLinks = new ArrayList<>();
        when(httpUtil.getPageLinks(DOMAIN)).thenReturn(pageLinks);
        Map <String, List<String>> links = crawler.start();
        Assert.assertEquals("Should be only one page", 1, links.size());
        Assert.assertEquals("Page should be domain url", DOMAIN, links.keySet().iterator().next());
    }

    @Test
    public void testExternalSites() {
        List<String> pageLinks = new ArrayList<>();
        pageLinks.add("http://www.twitter.com");
        pageLinks.add("http://www.facebook.com");
        when(httpUtil.getPageLinks(DOMAIN)).thenReturn(pageLinks);
        Map <String, List<String>> links = crawler.start();
        Assert.assertEquals("Should be only one page", 1, links.size());
        Assert.assertEquals("Page should be domain url", DOMAIN, links.keySet().iterator().next());
        Assert.assertEquals("Page links are same as mocked", pageLinks, links.get(DOMAIN));
        
    }
    
    @Test
    public void testNestedLinks() {
        List<String> pageLinks = new ArrayList<>();
        pageLinks.add(DOMAIN + PAGE_ONE);
        pageLinks.add(DOMAIN + PAGE_TWO);       
        when(httpUtil.getPageLinks(DOMAIN)).thenReturn(pageLinks);
        List<String> pageLinksOne = new ArrayList<>();
        pageLinksOne.add(DOMAIN + PAGE_THREE);
        when(httpUtil.getPageLinks(DOMAIN + PAGE_ONE)).thenReturn(pageLinksOne);
        List<String> pageLinksNone = new ArrayList<>();
        when(httpUtil.getPageLinks(DOMAIN + PAGE_TWO)).thenReturn(pageLinksNone);
        when(httpUtil.getPageLinks(DOMAIN + PAGE_THREE)).thenReturn(pageLinksNone);
        Map <String, List<String>> links = crawler.start();
        Assert.assertEquals("Should be 4 pages", 4, links.size());
        Assert.assertEquals("Number of links in DOMAIN", 2, links.get(DOMAIN).size());
        Assert.assertEquals("Number of links in page one", 1, links.get(DOMAIN + PAGE_ONE).size());
        Assert.assertEquals("Number of links in page two", 0, links.get(DOMAIN + PAGE_TWO).size());
        Assert.assertEquals("Number of links in page three", 0, links.get(DOMAIN + PAGE_THREE).size());
    }
    
    @Test
    public void testChildPageReferringParentPage() {
        List<String> pageLinks = new ArrayList<>();
        pageLinks.add(DOMAIN + PAGE_ONE);
        pageLinks.add(DOMAIN + PAGE_TWO);
        pageLinks.add(DOMAIN);
        when(httpUtil.getPageLinks(DOMAIN)).thenReturn(pageLinks);
        List<String> pageLinksOne = new ArrayList<>();
        pageLinksOne.add(DOMAIN + PAGE_THREE);
        when(httpUtil.getPageLinks(DOMAIN + PAGE_ONE)).thenReturn(pageLinksOne);
        List<String> pageLinksTwo = new ArrayList<>();
        pageLinksTwo.add(DOMAIN);
        when(httpUtil.getPageLinks(DOMAIN + PAGE_TWO)).thenReturn(pageLinksTwo);
        when(httpUtil.getPageLinks(DOMAIN + PAGE_THREE)).thenReturn(new ArrayList<String>());
        Map <String, List<String>> links = crawler.start();
        Assert.assertEquals("Should be 4 pages", 4, links.size());
        Assert.assertEquals("Number of links in DOMAIN", 3, links.get(DOMAIN).size());
        Assert.assertEquals("Number of links in page one", 1, links.get(DOMAIN + PAGE_ONE).size());
        Assert.assertEquals("Number of links in page two", 1, links.get(DOMAIN + PAGE_TWO).size());
        Assert.assertEquals("Number of links in page three", 0, links.get(DOMAIN + PAGE_THREE).size());
    }
    
}
