package com.wakkir.jsoup;

/**
 * User: wakkir
 * Date: 04/01/14
 * Time: 23:44
 */
//http://www.mkyong.com/java/jsoup-html-parser-hello-world-examples/
//Grabs All Hyperlinks

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HTMLParserExample1
{
    public void grabAllHyperLinks()
    {
        Document doc;
        try
        {

            // need http protocol
            //doc = Jsoup.connect("http://google.com").get();

            //Note
            //It’s recommended to specify a “userAgent” in Jsoup, to avoid HTTP 403 error messages.

            doc = Jsoup.connect("http://aiselin.en.alibaba.com/productgrouplist-219592382/Big_size_shoes.html")
                    .userAgent("Mozilla")
                    .get();

            // get page title
            String title = doc.title();
            System.out.println("title : " + title);

            // get all links
            Elements links = doc.select("a[href]");
            for (Element link : links)
            {

                // get the value from href attribute
                System.out.println("\nlink : " + link.attr("href"));
                System.out.println("text : " + link.text());

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void grabAllImages()
    {
        Document doc;
        try
        {

            //get all images
            doc = Jsoup.connect("http://yahoo.com").get();
            Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
            for (Element image : images)
            {

                System.out.println("\nsrc : " + image.attr("src"));
                System.out.println("height : " + image.attr("height"));
                System.out.println("width : " + image.attr("width"));
                System.out.println("alt : " + image.attr("alt"));

            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void getMetaElements()
    {
        StringBuffer html = new StringBuffer();

        html.append("<!DOCTYPE html>");
        html.append("<html lang=\"en\">");
        html.append("<head>");
        html.append("<meta charset=\"UTF-8\" />");
        html.append("<title>Hollywood Life</title>");
        html.append("<meta name=\"description\" content=\"The latest entertainment news\" />");
        html.append("<meta name=\"keywords\" content=\"hollywood gossip, hollywood news\" />");
        html.append("</head>");
        html.append("<body>");
        html.append("<div id='color'>This is red</div> />");
        html.append("</body>");
        html.append("</html>");

        Document doc = Jsoup.parse(html.toString());

        //get meta description content
        String description = doc.select("meta[name=description]").get(0).attr("content");
        System.out.println("Meta description : " + description);

        //get meta keyword content
        String keywords = doc.select("meta[name=keywords]").first().attr("content");
        System.out.println("Meta keyword : " + keywords);

        String color1 = doc.getElementById("color").text();
        String color2 = doc.select("div#color").get(0).text();

        System.out.println(color1);
        System.out.println(color2);
    }

    public void getFormParams(String html)
    {
        //http://www.mkyong.com/java/how-to-automate-login-a-website-java-example/

        Document doc = Jsoup.parse(html);

        //HTML form id
        Element loginform = doc.getElementById("your_form_id");

        Elements inputElements = loginform.getElementsByTag("input");

        List<String> paramList = new ArrayList<String>();
        for (Element inputElement : inputElements)
        {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");
        }

    }

    public void getFavIcon()
    {
        StringBuffer html = new StringBuffer();

        html.append("<html lang=\"en\">");
        html.append("<head>");
        html.append("<link rel=\"icon\" href=\"http://example.com/image.ico\" />");
        //html.append("<meta content=\"/images/google_favicon_128.png\" itemprop=\"image\">");
        html.append("</head>");
        html.append("<body>");
        html.append("something");
        html.append("</body>");
        html.append("</html>");

        Document doc = Jsoup.parse(html.toString());

        String fav = "";

        Element element = doc.head().select("link[href~=.*\\.(ico|png)]").first();
        if (element == null)
        {

            element = doc.head().select("meta[itemprop=image]").first();
            if (element != null)
            {
                fav = element.attr("content");
            }
        }
        else
        {
            fav = element.attr("href");
        }
        System.out.println(fav);
    }

    public static void main(String[] args)
    {
        HTMLParserExample1 htmlParser = new HTMLParserExample1();

        htmlParser.grabAllHyperLinks();
        //htmlParser.grabAllImages();
        //htmlParser.getMetaElements();
        //htmlParser.getFormParams("");
        //htmlParser.getFavIcon();

    }
}