package com.mycompany.webcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author xxx
 */
public class HttpUtil {
    private static final String USER_AGENT = "Mozilla/4.0 (Windows 8; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    public List<String> getPageLinks(String pageURL) {
        List<String> pageLinks = null;
        Connection connection = Jsoup.connect(pageURL).userAgent(USER_AGENT);
        
        try {
            Document document = connection.get();
            //OK
            if(connection.response().statusCode() == 200 && connection.response().contentType().contains("text/html")) {
                Elements linksInPage = document.select("a[href]");
                pageLinks = new ArrayList<>();
                for(Element link : linksInPage) {
                    pageLinks.add(link.absUrl("href"));
                }
            }
        } catch (IOException ex) {
            System.out.println("\nunable to fetch page " + pageURL);
        }
            
        
        
        return pageLinks;
    }
}
