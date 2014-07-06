package com.wakkir.shoe;

import com.drew.imaging.ImageProcessingException;

import java.io.IOException;
/**
 * User: wakkir
 * Date: 27/11/13
 * Time: 20:47
 */
public class ReformatFileNames
{
    AccessFileDetails fmd = new AccessFileDetails();


    public void renamePictureFiles(boolean needRename,boolean keepSameFolderStructure) throws ImageProcessingException, IOException
    {
        String inputPath = "\\\\Fs01\\WakSpace\\Business\\08.eBay\\Pictures\\Shoes\\01.PL-Priceless";
        String extension = ".jpg";
        String outputPath = "\\\\Fs01\\WakSpace\\Business\\08.eBay\\Pictures\\Shoes\\99.ALL";

        fmd.readDirectory(inputPath, extension, outputPath,needRename,keepSameFolderStructure,0,11,"PL-");
    }

    public static void main(String[] args)
    {
        /*
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

        context.registerShutdownHook();

        VelocityEngine velocityEngine = (VelocityEngine) context.getBean("velocityEngine");

        Map<String, Object> props = new HashMap<String, Object>();
        props.put("firstName", "Joe");
        props.put("lastName", "Smith");

        props.put("responseType", "CardSetup Response");
        props.put("trackId", "ASD234234");
        //props.put("status", "SUCCESS");
        props.put("status", "ERROR");
        props.put("errorData", "errorData errorData");
        props.put("errorDesc", "errorDesc errorDesc");
        props.put("errorCode", "errorCode2323");
        props.put("lastName", "Smith");
        props.put("lastName", "Smith");

        String body2 = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/priceless.vm", props);

        System.out.println(body2);
        */
        try
        {
            ReformatFileNames rename=new ReformatFileNames();
            boolean needRename=false;
            boolean keepSameFolderStructure=true;
            rename.renamePictureFiles(needRename,keepSameFolderStructure);
            //rename.renameVideoFiles(needRename,keepSameFolderStructure);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
