package com.mycompany.webcrawler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * crawls a domain
 * @author xxx
 */
public class Crawler {
    String domain;
    HttpUtil httpUtil;
    public Crawler(String domain, HttpUtil httpUtil) {
        this.domain = domain;
        this.httpUtil = httpUtil; 
    }
    public Map<String, List<String>> start() {
        Map<String, List<String>> links = new HashMap<>();
        links.put(domain, crawl(links, domain));
        return links;
    }
    
    private List<String> crawl(Map<String, List<String>> links, String pageUrl) {
        List<String> pageLinks = httpUtil.getPageLinks(pageUrl);
        if (pageLinks != null) {
            for(String pageLink : pageLinks) {
                if (pageLink.startsWith(domain) && !links.containsKey(pageLink)) {
                    //this is to stop processing parent link again when child link referring parent link
                    links.put(pageLink, null);
                    links.put(pageLink, crawl(links, pageLink)); 
                }
            }
        }
        return pageLinks;
    }

}

