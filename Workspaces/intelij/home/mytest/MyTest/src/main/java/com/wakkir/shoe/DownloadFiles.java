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
        //Its recommended to specify a user agent in Jsoup, to avoid HTTP 403 error messages.
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
        String destPath="\\\\fs01\\WakSpace\\Business\\00.Pictures\\01.Selling\\Shoes\\99.CH-China\\xxx";

        try
        {
            Map<String,String> map=new HashMap<String,String>();
            /*
            map.put("1381400352_140785201",	"http://g03.s.alicdn.com/kf/HT1Rf5hFDBkXXagOFbXf/220781329/HT1Rf5hFDBkXXagOFbXf.jpg?size=237262&height=1400&width=750&hash=46f122a907fd521399a9ba0f2e1fedbb");
            map.put("1381400352_140785202",	"http://g04.s.alicdn.com/kf/HT1hxWCFuJfXXagOFbXp/220781329/HT1hxWCFuJfXXagOFbXp.jpg?size=207279&height=1772&width=750&hash=cf337eb028b6ce2c1c01bfc854839add");
            map.put("1381400352_140785203",	"http://g01.s.alicdn.com/kf/HT1YHKfFqdmXXagOFbXu/220781329/HT1YHKfFqdmXXagOFbXu.jpg?size=195241&height=1015&width=1000&hash=b504c81cc58f339684cf033df31b8635");
            map.put("1381400352_140785204",	"http://g02.s.alicdn.com/kf/HT1N9CIFy4kXXagOFbXt/220781329/HT1N9CIFy4kXXagOFbXt.jpg?size=260824&height=2228&width=750&hash=f0cb666574d4d2aefba23b4b296aed94");
            map.put("1381400352_140785205",	"http://g03.s.alicdn.com/kf/HT1n.ojFNRdXXagOFbXS/220781329/HT1n.ojFNRdXXagOFbXS.jpg?size=198181&height=1011&width=1000&hash=35fc50518376bd801221fe8f0f501208");
            map.put("1381400352_140785206",	"http://g02.s.alicdn.com/kf/HT1l8N6FtBxXXagOFbXu/220781329/HT1l8N6FtBxXXagOFbXu.jpg?size=205978&height=1626&width=750&hash=382eddd77e7e4c8b94e98dde77e27046");
            map.put("1381400352_140785207",	"http://g01.s.alicdn.com/kf/HT1gOtfFTplXXagOFbXG/220781329/HT1gOtfFTplXXagOFbXG.jpg?size=139217&height=924&width=1000&hash=8e7c0501acd023164e7b1c974c6bd7a6");
            map.put("1381400352_140785208",	"http://g03.s.alicdn.com/kf/HT1BsuXFrllXXagOFbXc/220781329/HT1BsuXFrllXXagOFbXc.jpg?size=271723&height=2052&width=750&hash=9ffa7ecc86ee71f401be6aa3f3a621eb");
            map.put("1381400352_140785209",	"http://g03.s.alicdn.com/kf/HT1ZRZlFTJcXXagOFbX1/220781329/HT1ZRZlFTJcXXagOFbX1.jpg?size=122867&height=891&width=1000&hash=1a9b5f3ac452e90e66ae68c680816eed" 				         );
            map.put("1381400353_140785210",	"http://g03.s.alicdn.com/kf/HT16rBjFFpfXXagOFbXY/220781329/HT16rBjFFpfXXagOFbXY.jpg?size=139344&height=892&width=1000&hash=057476bc929fca48bba62753ebe124af" 				         );
            map.put("1381400355_140785203",	"http://g01.s.alicdn.com/kf/HT1MDCgFRJeXXagOFbXT/220781329/HT1MDCgFRJeXXagOFbXT.jpg?size=140068&height=882&width=1000&hash=319fe1b6a74753cf6ec4ee854ce4fd83" 				         );
            map.put("1381406110_140785203",	"http://g01.s.alicdn.com/kf/HT1E4ebFpVyXXagOFbXD/220781329/HT1E4ebFpVyXXagOFbXD.jpg?size=138205&height=862&width=1000&hash=6be6ad177e223b07e5aeae22c3410aaa" 				         );
            map.put("1381406111_140785203",	"http://g01.s.alicdn.com/kf/HT1R35mFw0dXXagOFbXY/220781329/HT1R35mFw0dXXagOFbXY.jpg?size=138874&height=893&width=1000&hash=809a34c1e104d782b27cec1a876b4f10" 				         );
            map.put("1381406113_140785203",	"http://g03.s.alicdn.com/kf/HT1u4KdFzllXXagOFbXM/220781329/HT1u4KdFzllXXagOFbXM.jpg?size=136333&height=885&width=1000&hash=534f26190fb81651bcc2678f2c0b783e" 				         );
            map.put("1381406108_140785203",	"http://g02.s.alicdn.com/kf/HT1Wod.FxRhXXagOFbXH/220781329/HT1Wod.FxRhXXagOFbXH.jpg?size=132772&height=863&width=1000&hash=9db47e2c31a43164098fbbe984904070" 				         );
            map.put("1312190881_140785203",	"http://g03.s.alicdn.com/kf/HT1imCeFq0lXXagOFbXI/220781329/HT1imCeFq0lXXagOFbXI.jpg?size=110558&height=816&width=1000&hash=9d753209d2a89b355d6b673fd04db18a" 				         );
            map.put("1312190890_140785203",	"http://g03.s.alicdn.com/kf/HT1cyZpFP4XXXagOFbXG/220781329/HT1cyZpFP4XXXagOFbXG.jpg?size=197663&height=1038&width=1000&hash=2decf283b9eeb9e43c29d5540f3e88c2"				         );
            map.put("1312190907_140785203",	"http://g03.s.alicdn.com/kf/HT1yX2oFtdjXXagOFbXP/220781329/HT1yX2oFtdjXXagOFbXP.jpg?size=162258&height=903&width=1000&hash=763b55a77cf5a17f5fe319ada5d261e5" 				         );
            map.put("1312190861_140785203",	"http://g03.s.alicdn.com/kf/HT1AcZkFQldXXagOFbXH/220781329/HT1AcZkFQldXXagOFbXH.jpg?size=166136&height=898&width=1000&hash=742448b52068de9808b07656b0413899" 				         );
            map.put("1321106214_140785203",	"http://g03.s.alicdn.com/kf/HT1DjQkFOXdXXagOFbXO/220781329/HT1DjQkFOXdXXagOFbXO.jpg?size=122405&height=788&width=1000&hash=d619cc6d01d9a4e70ae3a2f78104e0ca" 				         );
            map.put("1321106208_140785203",	"http://g04.s.alicdn.com/kf/HT17dmdFpVjXXagOFbXm/220781329/HT17dmdFpVjXXagOFbXm.jpg?size=108941&height=799&width=1000&hash=7bc4a74a4b7b68fbc620fe329edd1825" 				         );
            map.put("1321106210_140785203",	"http://g02.s.alicdn.com/kf/HT1P4MlFF0dXXagOFbXa/220781329/HT1P4MlFF0dXXagOFbXa.jpg?size=109092&height=801&width=1000&hash=1dbe77853950528208d30f435bbf7333" 				         );
            map.put("1321106211_140785203",	"http://g01.s.alicdn.com/kf/HT1eKzTFoBjXXagOFbXz/220781329/HT1eKzTFoBjXXagOFbXz.jpg?size=114397&height=841&width=1000&hash=f28367232e77cda9f729184e5d5ba353" 				         );
            map.put("HT1BsuXFrllXXagOFbXc",	"http://g01.s.alicdn.com/kf/HT1NXmdFExhXXagOFbXm/220781329/HT1NXmdFExhXXagOFbXm.jpg?size=113714&height=854&width=1000&hash=124bfd8e72b8fe759e4303f7cf22c6b5" 				         );
            map.put("1340860673_1272797912",    "http://g02.s.alicdn.com/kf/HTB1TXgXFpXXXXcxaVXXq6xXFXXXx/220781329/HTB1TXgXFpXXXXcxaVXXq6xXFXXXx.jpg?size=230967&height=1231&width=750&hash=299c9d674b0e50d9d237070ea5ec33d7"       );
            map.put("1342488510_1272797912",    "http://g01.s.alicdn.com/kf/HT1NVWfFDpkXXagOFbXE/220781329/HT1NVWfFDpkXXagOFbXE.jpg?size=97903&height=640&width=640&hash=3690be1c6295c553f40a3d8c9c35f574" 					         );
            map.put("1321106154_140785203",	"http://g03.s.alicdn.com/kf/HT1AimyFxNdXXagOFbX3/220781329/HT1AimyFxNdXXagOFbX3.jpg?size=107034&height=640&width=640&hash=99c9b90c9ef631224de7f62da74a4f84" 				         );
            map.put("1321106156_140785203",	"http://g01.s.alicdn.com/kf/HT1gPy6FKxeXXagOFbXc/220781329/HT1gPy6FKxeXXagOFbXc.jpg?size=118879&height=885&width=1000&hash=181530ddc543841f9d55b939abb90374"				         );
            map.put("1321106157_140785203",	"http://g01.s.alicdn.com/kf/HT1h.ibFqlrXXagOFbX0/220781329/HT1h.ibFqlrXXagOFbX0.jpg?size=112638&height=875&width=1000&hash=072861342a2056900f966c95c886f22f"				         );
            map.put("IMG_20140611_164249",	"http://g01.s.alicdn.com/kf/HT1OzxZFvJpXXagOFbXb/220781329/HT1OzxZFvJpXXagOFbXb.jpg?size=111976&height=941&width=1000&hash=db35197fbd959d0ce260ebb43244164f"				         );
            map.put("3B5A3874",				"http://g02.s.alicdn.com/kf/HTB11F_KFFXXXXbrapXXq6xXFXXX2/220781329/HTB11F_KFFXXXXbrapXXq6xXFXXX2.jpg?size=312714&height=689&width=1000&hash=3495bb3baca5e9149fb9b17a7c43cb42"       );
            map.put("3B5A3873",				"http://g01.s.alicdn.com/kf/HTB1xyhbFVXXXXbcXVXXq6xXFXXXY/220781329/HTB1xyhbFVXXXXbcXVXXq6xXFXXXY.jpg?size=71918&height=758&width=1000&hash=b017b814092cc941a92a1819a82f6d68"        );
            map.put("3B5A3875",				"http://g02.s.alicdn.com/kf/HTB1WGJeFVXXXXX6XpXXq6xXFXXXp/220781329/HTB1WGJeFVXXXXX6XpXXq6xXFXXXp.jpg?size=79528&height=865&width=1000&hash=8821d617436934f21a6be8aebc156c17"        );
            map.put("3B5A3877",				"http://g03.s.alicdn.com/kf/HTB1f5tcFVXXXXbpXFXXq6xXFXXXq/220781329/HTB1f5tcFVXXXXbpXFXXq6xXFXXXq.jpg?size=79455&height=780&width=1000&hash=e8d191b58894d822423462dd83a62f1b"        );
            map.put("3B5A3880",				"http://g03.s.alicdn.com/kf/HTB1mYRcFVXXXXafXVXXq6xXFXXXY/220781329/HTB1mYRcFVXXXXafXVXXq6xXFXXXY.jpg?size=91677&height=830&width=1000&hash=bbd02b27de2880fc92837b933d6ca3d9"        );
            map.put("3B5A3881",				"http://g04.s.alicdn.com/kf/HTB1XIBcFVXXXXXPXVXXq6xXFXXXy/220781329/HTB1XIBcFVXXXXXPXVXXq6xXFXXXy.jpg?size=91612&height=859&width=1000&hash=13511f108fd13a9577075a2fdc6e5f2c"        );
            map.put("3B5A3883",				"http://g02.s.alicdn.com/kf/HTB1iAVcFVXXXXbeXFXXq6xXFXXXh/220781329/HTB1iAVcFVXXXXbeXFXXq6xXFXXXh.jpg?size=96631&height=861&width=1000&hash=eaa8e9c904152b121702a2d3996bbd0a"        );
            map.put("3B5A3879",				"http://g01.s.alicdn.com/kf/HTB17QhdFVXXXXbbXpXXq6xXFXXXo/220781329/HTB17QhdFVXXXXbbXpXXq6xXFXXXo.jpg?size=104162&height=865&width=1000&hash=620ece32c2d5606a5384f256e99dfd95"       );
            map.put("3B5A3886",				"http://g03.s.alicdn.com/kf/HTB1cKpbFVXXXXbiXVXXq6xXFXXXS/220781329/HTB1cKpbFVXXXXbiXVXXq6xXFXXXS.jpg?size=87544&height=872&width=1000&hash=f2efebf2e94cdc9da65eb6ffd6a4e34e"        );
            map.put("3B5A3887",				"http://g04.s.alicdn.com/kf/HTB1uy0cFVXXXXXiXVXXq6xXFXXXY/220781329/HTB1uy0cFVXXXXXiXVXXq6xXFXXXY.jpg?size=90394&height=842&width=1000&hash=5979965bfb5d27b64cf0b7be631d710c"        );
            map.put("3B5A3889",				"http://g02.s.alicdn.com/kf/HTB1OOlbFVXXXXbaXVXXq6xXFXXXX/220781329/HTB1OOlbFVXXXXbaXVXXq6xXFXXXX.jpg?size=88558&height=792&width=1000&hash=b6ba4191335ac568fed90339bc551f52"        );
            map.put("3B5A3885",				"http://g02.s.alicdn.com/kf/HTB1Wl0XFVXXXXX4aXXXq6xXFXXXJ/220781329/HTB1Wl0XFVXXXXX4aXXXq6xXFXXXJ.jpg?size=99677&height=844&width=1000&hash=dba9151d2648aaeb903de538d61df67b"        );
            map.put("IMG_0665",				"http://g01.s.alicdn.com/kf/HTB1_DRcFVXXXXctXpXXq6xXFXXXQ/220781329/HTB1_DRcFVXXXXctXpXXq6xXFXXXQ.jpg?size=83414&height=877&width=1000&hash=af8f6b73d18b11f7b556de24524ffc12"        );
            map.put("IMG_0661",				"http://g01.s.alicdn.com/kf/HTB184xcFVXXXXaaXVXXq6xXFXXXO/220781329/HTB184xcFVXXXXaaXVXXq6xXFXXXO.jpg?size=207128&height=844&width=1000&hash=20dadb8595c66bec3be6d199e003e20b"       );
            map.put("IMG_0663",				"http://g02.s.alicdn.com/kf/HTB1YLJcFVXXXXajXFXXq6xXFXXXS/220781329/HTB1YLJcFVXXXXajXFXXq6xXFXXXS.jpg?size=77129&height=831&width=1000&hash=9b0f59193d553b4e3e4c9bf37ada5db6"        );
            map.put("IMG_0664",				"http://g01.s.alicdn.com/kf/HTB1zA0bFVXXXXaOXVXXq6xXFXXXt/220781329/HTB1zA0bFVXXXXaOXVXXq6xXFXXXt.jpg?size=119511&height=682&width=1000&hash=a4abff89dd93e312f6aa3656ab8eb18c"       );
            map.put("IMG_0651",				"http://g04.s.alicdn.com/kf/HTB1Y2VaFVXXXXczXpXXq6xXFXXXV/220781329/HTB1Y2VaFVXXXXczXpXXq6xXFXXXV.jpg?size=64094&height=718&width=1000&hash=762a8ee44f00d1650fb7ee38aeb7f276"        );
            map.put("IMG_0647",				"http://g01.s.alicdn.com/kf/HTB1QkRdFVXXXXaHXpXXq6xXFXXXr/220781329/HTB1QkRdFVXXXXaHXpXXq6xXFXXXr.jpg?size=209127&height=828&width=1000&hash=301b6f74e7abebe6bebab1b2ceded728"       );
            map.put("IMG_0649",				"http://g03.s.alicdn.com/kf/HTB1.JZ_FFXXXXbuXFXXq6xXFXXX9/220781329/HTB1.JZ_FFXXXXbuXFXXq6xXFXXX9.jpg?size=86091&height=864&width=1000&hash=52920b0bd7ee061dfa803b37b6106793"        );
            map.put("IMG_0650",				"http://g01.s.alicdn.com/kf/HTB1ESBbFVXXXXabXVXXq6xXFXXXL/220781329/HTB1ESBbFVXXXXabXVXXq6xXFXXXL.jpg?size=124312&height=686&width=1000&hash=6cb87048df71f3a91b0bb96acb1adcd2"       );
            map.put("IMG_0678",				"http://g02.s.alicdn.com/kf/HTB1eldeFVXXXXaGXpXXq6xXFXXXH/220781329/HTB1eldeFVXXXXaGXpXXq6xXFXXXH.jpg?size=66313&height=718&width=1000&hash=c6a8e1bf1fc8e9a9d2bfc1adeea13ab2"        );
            map.put("IMG_0674",				"http://g02.s.alicdn.com/kf/HTB1284cFVXXXXawXFXXq6xXFXXX1/220781329/HTB1284cFVXXXXawXFXXq6xXFXXX1.jpg?size=223759&height=854&width=1000&hash=7ad0f1edd07487cc636d16a8629efe41"       );
            map.put("IMG_0676",				"http://g01.s.alicdn.com/kf/HTB1J0wMFFXXXXXpapXXq6xXFXXXg/220781329/HTB1J0wMFFXXXXXpapXXq6xXFXXXg.jpg?size=85378&height=858&width=1000&hash=a282dd779d91653cf8ee4a46b30d15c7"        );
            map.put("IMG_0677",				"http://g01.s.alicdn.com/kf/HTB17RFeFVXXXXcLXXXXq6xXFXXXd/220781329/HTB17RFeFVXXXXcLXXXXq6xXFXXXd.jpg?size=124909&height=731&width=1000&hash=ce59d6658dcad0b013deb809a27c3f5f"       );
            map.put("IMG_0678",				"http://g01.s.alicdn.com/kf/HTB1yyhfFVXXXXXoXXXXq6xXFXXXS/220781329/HTB1yyhfFVXXXXXoXXXXq6xXFXXXS.jpg?size=70129&height=732&width=1000&hash=fce7651296ce0aeeb7163af7e265c74b"        );
            */
            /*
            map.put("BWN0BTDFRSN00-01","http://g02.a.alicdn.com/kf/HTB1tO2yGXXXXXaVXFXXq6xXFXXXf/221200747/HTB1tO2yGXXXXXaVXFXXq6xXFXXXf.jpg");
            map.put("BWN0BTDFRSN00-02","http://g03.a.alicdn.com/kf/HTB1m7jBGXXXXXbcXpXXq6xXFXXXo/221200747/HTB1m7jBGXXXXXbcXpXXq6xXFXXXo.jpg");
            map.put("BWN0BTDFRSN00-03","http://g02.a.alicdn.com/kf/HTB1Vq6AGXXXXXcAXXXXq6xXFXXX8/221200747/HTB1Vq6AGXXXXXcAXXXXq6xXFXXX8.jpg");
            map.put("BWN0BTDFRSN00-04","http://g01.a.alicdn.com/kf/HTB1nnDEGXXXXXbsXXXXq6xXFXXXN/221200747/HTB1nnDEGXXXXXbsXXXXq6xXFXXXN.jpg");
            */
            map.put("01","http://g04.s.alicdn.com/kf/HTB1HKecFVXXXXXTXXXXq6xXFXXXO/202576724/HTB1HKecFVXXXXXTXXXXq6xXFXXXO.jpg?size=362449&height=4324&width=640&hash=4bb9590ce17f375781b225f1d2a9b23d"  );
            map.put("02","http://g02.s.alicdn.com/kf/HTB1QcicFVXXXXXsXXXXq6xXFXXXU/202576724/HTB1QcicFVXXXXXsXXXXq6xXFXXXU.jpg?size=279791&height=3708&width=640&hash=0a66c983ab5add5242f16d0e9d463f83"  );
            map.put("03","http://g02.a.alicdn.com/kf/HTB1kWodGXXXXXa9XXXXq6xXFXXXq/202576724/HTB1kWodGXXXXXa9XXXXq6xXFXXXq.jpg?size=397094&height=3768&width=640&hash=4e8b7745328f7e619a5693ca1eff1869"  );
            map.put("04","http://g02.a.alicdn.com/kf/HTB1BPT_GXXXXXbHXpXXq6xXFXXXR/202576724/HTB1BPT_GXXXXXbHXpXXq6xXFXXXR.jpg?size=489432&height=4120&width=640&hash=320366593193ef6a9540544750ef6be7"  );
            map.put("05","http://g01.a.alicdn.com/kf/HTB1_X_8GXXXXXa4XFXXq6xXFXXXE/202576724/HTB1_X_8GXXXXXa4XFXXq6xXFXXXE.jpg?size=493295&height=3132&width=640&hash=540755a59acf4075711a6832ee56368d"  );
            map.put("06","http://g01.a.alicdn.com/kf/HTB1xLAcGXXXXXbQXXXXq6xXFXXXJ/202576724/HTB1xLAcGXXXXXbQXXXXq6xXFXXXJ.jpg?size=366849&height=3276&width=640&hash=246e9ced2c8b36eba567b20348b75840"  );
            map.put("07","http://g02.a.alicdn.com/kf/HTB110.XGXXXXXX.XpXXq6xXFXXX0/202576724/HTB110.XGXXXXXX.XpXXq6xXFXXX0.jpg?size=43146&height=640&width=640&hash=8809828a3051187c6c5202aa204293b8"    );
            map.put("08","http://g02.a.alicdn.com/kf/HTB1Qu7bGXXXXXcOXXXXq6xXFXXXG/202576724/HTB1Qu7bGXXXXXcOXXXXq6xXFXXXG.jpg?size=44763&height=640&width=640&hash=7dfb0cf0b09a20b2d603644bd9e1d006"    );
            map.put("09","http://g02.a.alicdn.com/kf/HTB1P8v6GXXXXXcfXFXXq6xXFXXXA/202576724/HTB1P8v6GXXXXXcfXFXXq6xXFXXXA.jpg?size=38183&height=640&width=640&hash=d03f2bba94b72f6623dc6d3fabf060c3"    );
            map.put("10","http://g03.a.alicdn.com/kf/HTB1HSD3GXXXXXXGXVXXq6xXFXXXm/202576724/HTB1HSD3GXXXXXXGXVXXq6xXFXXXm.jpg?size=42030&height=640&width=640&hash=0817d751e437d1fa6b9416e092332c96"    );
            map.put("11","http://g02.a.alicdn.com/kf/HTB1cx24GXXXXXXRXVXXq6xXFXXXy/202576724/HTB1cx24GXXXXXXRXVXXq6xXFXXXy.jpg?size=78900&height=640&width=640&hash=b80a36e3f9ed5cc0a828360b45d30c18"    );
            map.put("12","http://g02.a.alicdn.com/kf/HTB1id7XGXXXXXaHXpXXq6xXFXXXc/202576724/HTB1id7XGXXXXXaHXpXXq6xXFXXXc.jpg?size=74043&height=640&width=640&hash=e0850a648e4acd9ce6b1bd0ea344947c"    );
            map.put("13","http://g01.a.alicdn.com/kf/HTB1VpIdGXXXXXa5XXXXq6xXFXXXT/202576724/HTB1VpIdGXXXXXa5XXXXq6xXFXXXT.jpg?size=78168&height=640&width=640&hash=db72143a9725ac3cae36d36762da1d00"    );
            map.put("14","http://g02.a.alicdn.com/kf/HTB1n6n.GXXXXXbbXpXXq6xXFXXXb/202576724/HTB1n6n.GXXXXXbbXpXXq6xXFXXXb.jpg?size=80367&height=640&width=640&hash=3fb6a64cc32d8a39dc901044c1599cb1"    );
            map.put("15","http://g01.a.alicdn.com/kf/HTB1vJf4GXXXXXXIXVXXq6xXFXXXK/202576724/HTB1vJf4GXXXXXXIXVXXq6xXFXXXK.jpg?size=62851&height=640&width=640&hash=062c5b642baba6277c3af0756ee3223a"    );
            map.put("16","http://g02.a.alicdn.com/kf/HTB1HYY6GXXXXXc4XFXXq6xXFXXXs/202576724/HTB1HYY6GXXXXXc4XFXXq6xXFXXXs.jpg?size=72699&height=640&width=640&hash=bd0b22b88e3e5f368b8126aa979849ad"    );
            map.put("17","http://g02.a.alicdn.com/kf/HTB1kuAdGXXXXXaVXXXXq6xXFXXX7/202576724/HTB1kuAdGXXXXXaVXXXXq6xXFXXX7.jpg?size=589935&height=4153&width=640&hash=5e47f306e8c4f0788a4f7df7932ead0d"  );
            map.put("18","http://g03.a.alicdn.com/kf/HTB1b67bGXXXXXcKXXXXq6xXFXXXc/202576724/HTB1b67bGXXXXXcKXXXXq6xXFXXXc.jpg?size=263192&height=3328&width=640&hash=b2ccc96dd1dd37b76d3361c138dd5d1f"  );
            map.put("19","http://g02.a.alicdn.com/kf/HTB1KGMcGXXXXXccXXXXq6xXFXXXS/202576724/HTB1KGMcGXXXXXccXXXXq6xXFXXXS.jpg?size=539311&height=5000&width=647&hash=ffde78a75efdfc53bd62dbb21858b0a4"  );
            map.put("20","http://g02.a.alicdn.com/kf/HTB1xU65GXXXXXcNXFXXq6xXFXXXh/202576724/HTB1xU65GXXXXXcNXFXXq6xXFXXXh.jpg?size=505599&height=5000&width=648&hash=685f60206e988de4049e4cbb7b4b3d82"  );
            map.put("21","http://g02.a.alicdn.com/kf/HTB1ZEUcGXXXXXaUXXXXq6xXFXXX5/202576724/HTB1ZEUcGXXXXXaUXXXXq6xXFXXX5.jpg?size=312525&height=4225&width=640&hash=b1628c98270321f53574d4966e3e4c25"  );
            map.put("22","http://g03.a.alicdn.com/kf/HTB1nbn8GXXXXXajXFXXq6xXFXXXq/202576724/HTB1nbn8GXXXXXajXFXXq6xXFXXXq.jpg?size=246459&height=4458&width=640&hash=82db4997885d60ef365c1143d3801be4"  );
            map.put("23","http://g04.a.alicdn.com/kf/HTB1sCT6GXXXXXciXFXXq6xXFXXXL/202576724/HTB1sCT6GXXXXXciXFXXq6xXFXXXL.jpg?size=544077&height=4683&width=640&hash=ebb6e845cdaccb9fbdc3be40a198e2ff"  );
            map.put("24","http://g02.a.alicdn.com/kf/HTB1isH6GXXXXXcRXFXXq6xXFXXX6/202576724/HTB1isH6GXXXXXcRXFXXq6xXFXXX6.jpg?size=482562&height=4629&width=640&hash=ef8238a4eb2a7285503e9d5b8c17520f"  );
            map.put("25","http://g04.a.alicdn.com/kf/HTB1DKUeGXXXXXXPXXXXq6xXFXXXq/202576724/HTB1DKUeGXXXXXXPXXXXq6xXFXXXq.jpg?size=315874&height=4004&width=640&hash=460696083ccae310b04984b23d594c19"  );
            map.put("26","http://g01.a.alicdn.com/kf/HTB1ZRH4GXXXXXXJXVXXq6xXFXXXQ/202576724/HTB1ZRH4GXXXXXXJXVXXq6xXFXXXQ.jpg?size=296887&height=3724&width=640&hash=b6588bc415c9973fba88f16a5835dc57"  );

            
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

            //boolean doneIt=FileUtils.saveFile(productDetails,destPath,shoeId+".txt");

        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void mainx(String[] args)
    {
        DownloadFiles df=new DownloadFiles();


        boolean keepSameFolderStructure=false;

        //String destPath="\\\\fs01\\WakSpace\\Business\\08.eBay\\Pictures\\Shoes\\06.MS-Mark&Spencer\\Boots";
        String destPath="\\\\fs01\\WakSpace\\Business\\08.eBay\\Pictures\\Shoes\\07.DB-Debenhams\\xxx";

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
            String shoeId="050010229960";
            map.put(shoeId+"-00","http://debenhams.scene7.com/is/image/Debenhams/"+shoeId+"?wid=1250&hei=1250&qlt=95");
            map.put(shoeId+"-01","http://debenhams.scene7.com/is/image/Debenhams/"+shoeId+"_1?wid=1250&hei=1250&qlt=95");
            map.put(shoeId+"-02","http://debenhams.scene7.com/is/image/Debenhams/"+shoeId+"_2?wid=1250&hei=1250&qlt=95");
            map.put(shoeId+"-03","http://debenhams.scene7.com/is/image/Debenhams/"+shoeId+"_3?wid=1250&hei=1250&qlt=95");
            map.put(shoeId+"-04","http://debenhams.scene7.com/is/image/Debenhams/"+shoeId+"_4?wid=1250&hei=1250&qlt=95");
            String productDetails=df.getProductDetails("http://www.debenhams.com/webapp/wcs/stores/servlet/prod_10701_10001_"+shoeId+"_-1","span[class=breadcrumb_current]","div[id=product-item-no]","div[id=view-more-details-parent]","div[id=info1]","p","p","li");

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

    public String getProductDetails(String baseUrl,String nameElement,String itemNoElement,String descElement,String detailElement,String childItemNoElement,String childDescElement,String childDetailElement)   throws IOException
    {
        Document doc = Jsoup.connect(baseUrl).userAgent("Mozilla").get();
        Elements name = doc.select(nameElement);    //element : a[href]
        StringBuffer sb2=new StringBuffer();
        sb2.delete(0,sb2.length());
        sb2.append("\n");
        sb2.append(name.text());
        sb2.append("\n");
        
        for (Element div : doc.select(itemNoElement))
        {
            for (Element ul : div.children())
            {
                if(ul.getElementsByTag(childItemNoElement).size()>0)
                {
                    //System.out.println(ul.getElementsByTag("li"));
                    sb2.append(ul.getElementsByTag(childItemNoElement));
                    sb2.append("\n");
                }
            }
        }
        
        for (Element div : doc.select(descElement))
        {
            for (Element ul : div.children())
            {
                if(ul.getElementsByTag(childDescElement).size()>0)
                {
                    //System.out.println(ul.getElementsByTag("li"));
                    sb2.append(ul.getElementsByTag(childDescElement));
                    sb2.append("\n");
                }
            }
        }

        for (Element div : doc.select(detailElement))
        {
            for (Element ul : div.children())
            {
                if(ul.getElementsByTag(childDetailElement).size()>0)
                {
                    //System.out.println(ul.getElementsByTag("li"));
                    sb2.append(ul.getElementsByTag(childDetailElement));
                    sb2.append("\n");
                }
            }
        }
        //System.out.println(sb2.toString());
        return  sb2.toString();

    }
}
