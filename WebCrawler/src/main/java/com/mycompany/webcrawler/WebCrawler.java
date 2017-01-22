package com.mycompany.webcrawler;

import java.util.List;
import java.util.Map;

/**
 * Web Crawler app main class
 * @author xxx
 */
public class WebCrawler {
    
    public static void main(String[] args) {
        if (args.length > 0 && args[0] != null && !args[0].isEmpty()) {
            Crawler crawler = new Crawler(args[0], new HttpUtil());
            Map<String, List<String>> links = crawler.start();
            for(String parentLink : links.keySet()) {
                System.out.println("Page:" + parentLink);
                for(String childLink : links.get(parentLink)) {
                    System.out.println("----Link:" + childLink);
                }
                    
             }
        } else {
            System.out.println("parameter <domain name> required");
        }
    }
}