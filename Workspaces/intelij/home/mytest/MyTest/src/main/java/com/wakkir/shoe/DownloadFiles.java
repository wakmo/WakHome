package com.wakkir.shoe;


import com.wakkir.utils.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * User: wakkir
 * Date: 27/11/13
 * Time: 20:47
 */
public class DownloadFiles
{
    public void downloadFileFromMultipleUrls(String baseUrl,String destinationFilePath,boolean keepSameFolderStructure)  throws IOException
    {
        Map<String,String> urlMap=getValidHtmlUrlsFromMainWebsite(baseUrl);

        Set keys=urlMap.keySet();
        Iterator<String> it=keys.iterator();
        StringBuffer sb=new StringBuffer();
        while(it.hasNext())
        {
            String key=it.next();
            String url=urlMap.get(key);
            grabAllImages(url,destinationFilePath+ File.separator+key,keepSameFolderStructure);
            boolean doneIt=FileUtils.saveFile(url,destinationFilePath+ File.separator+key,key+".txt");
            sb.append(key+" : "+url);
            sb.append("\n");
        }
        boolean doneIt=FileUtils.saveFile(sb.toString(),destinationFilePath,"summary.txt");
    }

    //===================================================================================

    public Map<String,String> getValidHtmlUrlsFromMainWebsite(String baseUrl)   throws IOException
    {
        Map<String,String> urlMap=new HashMap<String,String>();

        Document doc = Jsoup.connect(baseUrl).userAgent("Mozilla").get();
        // get page title
        String title = doc.title();
        System.out.println("title : " + title);

        String[] strBaseUrl=baseUrl.split("/");
        String hostName="";
        if(strBaseUrl.length>2)
        {
            hostName= strBaseUrl[0]+"//"+strBaseUrl[2];
        }
        // get all links
        Elements links = doc.select("a[href]");
        StringBuffer sb2=new StringBuffer();
        sb2.delete(0,sb2.length());
        for (Element link : links)
        {
            // get the value from href attribute
            String hrefAttributeValue=link.attr("href");
            //System.out.println("link : " + hrefAttributeValue);
            String[] strHref=hrefAttributeValue.split("/");
            if(strHref.length>2 && "product".equalsIgnoreCase(strHref[1]))
            {
                urlMap.put(strHref[2],hostName+hrefAttributeValue);
                sb2.append(strHref[2]+" : "+hostName+hrefAttributeValue);
                sb2.append("\n");
            }
        }
        System.out.println(sb2.toString());

        return urlMap;
    }

    //===================================================================================

    public void grabAllImages(String webUrl,String destinationImagePath,boolean keepSameFolderStructure)  throws IOException
    {
        //It’s recommended to specify a “userAgent” in Jsoup, to avoid HTTP 403 error messages.
        Document doc = Jsoup.connect(webUrl).userAgent("Mozilla").get();
        Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
        System.out.println("Number of elements found is "+images.size());
        int count=0;
        for (Element image : images)
        {
            //System.out.println("src : " + image.attr("src"));
            boolean doneIt=downloadOneFile(image.attr("src"),destinationImagePath,keepSameFolderStructure);
            if(doneIt)
            {
              count++;
            }
        }
        System.out.println("Number of elements downloaded is "+count);

    }

    //===================================================================================
    public boolean downloadOneFile(String imageUrl,String destinationImagePath,boolean keepSameFolderStructure) throws IOException
    {
        return downloadOneFile(imageUrl,destinationImagePath,keepSameFolderStructure,null);
    }

    public boolean downloadOneFile(String imageUrl,String destinationImagePath,boolean keepSameFolderStructure,String destinationFileName) throws IOException
    {
        URL url = new URL(imageUrl);
        InputStream in = new BufferedInputStream(url.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1!=(n=in.read(buf)))
        {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();
        //------------------------------------------------
        String[] str=  url.getPath().split("/") ;
        if((str.length-1)<0)
        {
            throw new IOException("There are no file to download!");
        }
        String fileName=str[str.length-1];
        if(destinationFileName!=null && destinationFileName.trim().length()>0)
        {
            fileName=destinationFileName;
        }

        String savingFilePath;
        if(keepSameFolderStructure)
        {
            savingFilePath=(destinationImagePath+url.getPath()).replace(fileName,"");
            System.out.println("dest: " + savingFilePath + fileName);
        }
        else
        {
            savingFilePath=destinationImagePath;
            System.out.println("dest: " + savingFilePath +File.separator+ fileName);
        }

        //------------------------------------------------
        boolean doneIt=FileUtils.saveFile(response,savingFilePath,fileName);

        return doneIt;

    }

    public void downloadImagesFromAlibaba()
    {
        boolean keepSameFolderStructure=false;

        //String hostName="aiselin.en.alibaba.com";
        String hostName="eino.en.alibaba.com";

        String destPath="E:\\WakSpace\\Business\\08.eBay\\Suppliers\\test\\"+hostName;

        //String baseUrl="http://aiselin.en.alibaba.com/productgrouplist-219592382/Big_size_shoes.html";
        //String baseUrl="http://aiselin.en.alibaba.com/productgrouplist-219592382-2/Big_size_shoes.html?isGallery=Y";
        //String baseUrl="http://eino.en.alibaba.com/productgrouplist-215880603-1/New_styles_Wedding_shoes.html?isGallery=N";

        try
        {
            for(int i=1;i<=10;i++)
            {
                String baseUrl="http://aiselin.en.alibaba.com/productgrouplist-219592375-"+i+"/Hot_sales.html?isGallery=N";
                System.out.println("baseUrl :: " + baseUrl);
                downloadFileFromMultipleUrls(baseUrl,destPath,keepSameFolderStructure);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    //===================================================================================
    //===================================================================================

    public static void main(String[] args)
    {
        DownloadFiles df=new DownloadFiles();


        boolean keepSameFolderStructure=false;

        //String destPath="\\\\fs01\\WakSpace\\Business\\08.eBay\\Pictures\\Shoes\\06.MS-Mark&Spencer\\Boots";
        String destPath="F:\\NAS-BACKUP\\WakSpace\\Business\\08.eBay\\Pictures\\Shoes\\07.DB-Debenhams\\temp";

        try
        {
            Map<String,String> map=new HashMap<String,String>();
            //M&S
            //map.put("T027065-01","http://ecx.images-amazon.com/images/I/91hVjKzfQoL._SX1400_SY1820_.jpg");
            //map.put("T027065-02","http://ecx.images-amazon.com/images/I/81DD7-EfBcL._SX1400_SY1820_.jpg");
            //map.put("T027065-03","http://ecx.images-amazon.com/images/I/A1OJIFB55xL._SX1400_SY1820_.jpg");
            //map.put("T027065-04","http://ecx.images-amazon.com/images/I/91hVjKzfQoL._SX1400_SY1820_.jpg");

            //map.put("T028698W-01","http://ecx.images-amazon.com/images/I/81ApqmXLlQL._SX1400_SY1820_.jpg");
            //map.put("T028698W-02","http://ecx.images-amazon.com/images/I/910aLCgo34L._SX1400_SY1820_.jpg");
            //map.put("T028698W-03","http://ecx.images-amazon.com/images/I/81rLnu9jTSL._SX1400_SY1820_.jpg");
            //map.put("T028698W-04","http://ecx.images-amazon.com/images/I/81rLnu9jTSL._SX1400_SY1820_.jpg");

            //map.put("T026404W-01","http://ecx.images-amazon.com/images/I/817FrdV08aL._SX1400_SY1820_.jpg");
            //map.put("T026404W-02","http://ecx.images-amazon.com/images/I/81wnBHvmk7L._SX1400_SY1820_.jpg");
            //map.put("T026404W-03","http://ecx.images-amazon.com/images/I/91-mG6UZjTL._SX1400_SY1820_.jpg");
            //map.put("T026404W-04","http://ecx.images-amazon.com/images/I/81wnBHvmk7L._SX1400_SY1820_.jpg");
            //map.put("T026404W-05","http://ecx.images-amazon.com/images/I/81wnBHvmk7L._SX1400_SY1820_.jpg");

//            map.put("T026404W-01","http://ecx.images-amazon.com/images/I/817FrdV08aL._SX1000_SY1300_.jpg");
//            map.put("T026404W-02","http://ecx.images-amazon.com/images/I/81wnBHvmk7L._SX1000_SY1300_.jpg");
//            map.put("T026404W-03","http://ecx.images-amazon.com/images/I/91-mG6UZjTL._SX1000_SY1300_.jpg");
//            map.put("T026404W-04","http://ecx.images-amazon.com/images/I/81wnBHvmk7L._SX1000_SY1300_.jpg");

            //Debenhams
            String shoeId="61252_264996";
            map.put(shoeId+"-00","http://debenhams.scene7.com/is/image/Debenhams/"+shoeId+"?wid=1250&hei=1250&qlt=95");
            map.put(shoeId+"-01","http://debenhams.scene7.com/is/image/Debenhams/"+shoeId+"_1?wid=1250&hei=1250&qlt=95");
            map.put(shoeId+"-02","http://debenhams.scene7.com/is/image/Debenhams/"+shoeId+"_2?wid=1250&hei=1250&qlt=95");
            map.put(shoeId+"-03","http://debenhams.scene7.com/is/image/Debenhams/"+shoeId+"_3?wid=1250&hei=1250&qlt=95");
            String productDetails=df.getProductDetails("http://www.debenhams.com/webapp/wcs/stores/servlet/prod_10701_10001_"+shoeId+"_-1","span[class=breadcrumb_current]","div[id=info1]","li");

            Set keys=map.keySet();
            Iterator<String> it=keys.iterator();
            StringBuffer sb=new StringBuffer();
            while(it.hasNext())
            {
                String key=it.next();
                String url=map.get(key);
                System.out.println("imgUrl :: " + url);
                df.downloadOneFile(url, destPath,keepSameFolderStructure,key+".jpg");
                sb.append(key+" : "+url);
                sb.append("\n");
            }

            boolean doneIt=FileUtils.saveFile(productDetails,destPath,shoeId+".txt");

        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    //===================================================================================
    //===================================================================================

    public String getProductDetails(String baseUrl,String nameElement,String detailElement,String childElement)   throws IOException
    {
        Document doc = Jsoup.connect(baseUrl).userAgent("Mozilla").get();
        Elements name = doc.select(nameElement);    //element : a[href]
        StringBuffer sb2=new StringBuffer();
        sb2.delete(0,sb2.length());
        sb2.append("\n");
        sb2.append(name.text());
        sb2.append("\n");

        for (Element div : doc.select(detailElement))
        {
            for (Element ul : div.children())
            {
                if(ul.getElementsByTag(childElement).size()>0)
                {
                    //System.out.println(ul.getElementsByTag("li"));
                    sb2.append(ul.getElementsByTag(childElement));
                    sb2.append("\n");
                }
            }
        }
        //System.out.println(sb2.toString());
        return  sb2.toString();

    }
}
