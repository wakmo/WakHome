package com.wakkir.shoe;

import com.drew.imaging.ImageProcessingException;
import com.wakkir.utils.CommonUtils;
import com.wakkir.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;


/**
 * User: wakkir
 * Date: 22/10/13
 * Time: 00:50
 */
public class RenameAsFolder
{


    public void readDirectory(String inputPath, String extension, String outputPath, boolean needRename,boolean keepSameFolderStructure,int primaryFileNameStartIndex,int primaryFileNameEndIndex,String requiredFormat) throws ImageProcessingException, IOException
    {
        File inputFilePath = new File(inputPath);
        File[] files = inputFilePath.listFiles();
        int count = 0;
        assert files != null;
        for (File file : files)
        {
            if (file.isDirectory())
            {
                System.out.println("Source : "+file.getAbsolutePath());
                if(keepSameFolderStructure)
                {
                    System.out.println("Destination : "+outputPath+File.separator+file.getName());
                    readDirectory(file.getPath(), extension, outputPath+File.separator+file.getName(),needRename,keepSameFolderStructure,primaryFileNameStartIndex,primaryFileNameEndIndex,requiredFormat);
                }
                else
                {
                    System.out.println("Destination : "+outputPath);
                    readDirectory(file.getPath(), extension, outputPath,needRename,keepSameFolderStructure,primaryFileNameStartIndex,primaryFileNameEndIndex,requiredFormat);
                }
            }
            else
            {
                //System.out.println(file.getAbsolutePath());
                if (readFileDetails(file, extension, outputPath, needRename, count + 1,primaryFileNameStartIndex,primaryFileNameEndIndex,requiredFormat))
                {
                    count++;
                }
            }
        }
        if(needRename)
        {
            System.out.println("Total Renamed : " + count);
        }
        else
        {
            System.out.println("Total Retrieved : " + count);
        }
    }

    public boolean readFileDetails(File inputFilePath, String extension, String newFilePath, boolean needRename, int index,int primaryFileNameStartIndex,int primaryFileNameEndIndex,String requiredFormat) throws ImageProcessingException, IOException
    {
        boolean doneIt = false;
        try
        {
            String oldFileName = inputFilePath.getName();
            String oldFilePath = inputFilePath.getParent();

            //if(oldFileName.endsWith(".MTS"))
            if (".jpg".equalsIgnoreCase(extension) && oldFileName.toLowerCase().endsWith(extension))
            {
                doneIt= useBasicFilename4Rename(inputFilePath, extension, newFilePath, needRename, index,primaryFileNameStartIndex,primaryFileNameEndIndex,requiredFormat);
            }
            
            return doneIt;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return doneIt;
        }

    }


    private boolean useBasicFilename4Rename(File inputFilePath, String extension, String newFilePath, boolean needRename, int index,int primaryFileNameStartIndex,int primaryFileNameEndIndex,String requiredFormat) throws IOException
    {
        boolean  doneIt;
        String oldFileName = inputFilePath.getName();
        String oldFilePath = inputFilePath.getParent();
        Path path = Paths.get(oldFilePath, oldFileName);
        
        String newFileName=requiredFormat+inputFilePath.getParentFile().getName();
        CommonUtils.printInfo(index, oldFileName, newFileName);
        if(needRename)
        {
            doneIt = FileUtils.renameFile(oldFilePath, oldFileName, newFileName + extension, newFilePath,primaryFileNameStartIndex,newFileName.length(),2);
        }
        else
        {
            doneIt=true;
        }

        return doneIt;
    }

    public void renamePictureFiles(boolean needRename,boolean keepSameFolderStructure) throws ImageProcessingException, IOException
    {
        String inputPath = "\\\\fs01\\WakSpace\\Business\\08.eBay\\Pictures\\Shoes\\102_FUJI\\T028696W";
        String extension = ".jpg";
        String outputPath = "\\\\fs01\\WakSpace\\Business\\08.eBay\\Pictures\\Shoes\\102_FUJI\\T028696W";

        readDirectory(inputPath, extension, outputPath,needRename,keepSameFolderStructure,0,11,"");
    }

    public static void main(String[] args)
    {
        
        try
        {
            RenameAsFolder rename=new RenameAsFolder();
            boolean needRename=true;
            boolean keepSameFolderStructure=true;
            rename.renamePictureFiles(needRename,keepSameFolderStructure);            

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}