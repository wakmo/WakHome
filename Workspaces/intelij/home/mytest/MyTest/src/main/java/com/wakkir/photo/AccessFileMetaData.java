package com.wakkir.photo;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.wakkir.utils.CommonUtils;
import com.wakkir.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;


/**
 * User: wakkir
 * Date: 22/10/13
 * Time: 00:50
 */
public class AccessFileMetaData
{
    private String[] videos={".mts",".mov",".avi"};

    //private final int primaryFileNameStartIndex=0;
    //private final int primaryFileNameEndIndex=15;

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
                Metadata metadata = ImageMetadataReader.readMetadata(inputFilePath);
                for (Directory directory : metadata.getDirectories())
                {
                    //System.out.println(directory.getClass().getName());
                    if ("com.drew.metadata.exif.GpsDirectory".equalsIgnoreCase(directory.getClass().getName()))
                    {
                        System.out.println("GpsDirectory found");
                    }
                    if ("com.drew.metadata.exif.ExifSubIFDDirectory".equalsIgnoreCase(directory.getClass().getName()))
                    {
                        for (Tag tag : directory.getTags())
                        {
                            if (36867 == tag.getTagType())
                            {
                                String newFileName=FileUtils.generateNewFileName(tag.getDescription());
                                //String newFileName=FileUtils.generateNewFileNameWithOffsetDateTime(tag.getDescription(),0,5,10,-4,30);
                                CommonUtils.printInfo(index,oldFileName,newFileName);
                                if(needRename)
                                {
                                    doneIt = FileUtils.renameFile(oldFilePath, oldFileName, newFileName + extension, newFilePath,primaryFileNameStartIndex,primaryFileNameEndIndex);
                                }
                                else
                                {
                                    doneIt=true;
                                }
                                break;
                            }
                        }
                        break;
                    }
                    if(!doneIt)
                    {
                        //doneIt=useBasicFileAttribute4Rename(inputFilePath, extension, newFilePath,needRename,index);
                        //break;
                    }
                }
            }
            else if (".mts".equalsIgnoreCase(extension) && oldFileName.toLowerCase().endsWith(extension))
            {
                doneIt=useBasicFileAttribute4Rename(inputFilePath, extension, newFilePath,needRename,index,primaryFileNameStartIndex,primaryFileNameEndIndex,requiredFormat);
            }
            else if (".mov".equalsIgnoreCase(extension) && oldFileName.toLowerCase().endsWith(extension))
            {
                doneIt=useBasicFileAttribute4Rename(inputFilePath, extension, newFilePath,needRename,index,primaryFileNameStartIndex,primaryFileNameEndIndex,requiredFormat);
            }
            else if (".avi".equalsIgnoreCase(extension) && oldFileName.toLowerCase().endsWith(extension))
            {
                doneIt=useBasicFileAttribute4Rename(inputFilePath, extension, newFilePath,needRename,index,primaryFileNameStartIndex,primaryFileNameEndIndex,requiredFormat);
            }
            return doneIt;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return doneIt;
        }

    }


    private boolean useBasicFileAttribute4Rename(File inputFilePath, String extension, String newFilePath, boolean needRename, int index,int primaryFileNameStartIndex,int primaryFileNameEndIndex,String requiredFormat) throws IOException
    {
        boolean  doneIt;
        String oldFileName = inputFilePath.getName();
        String oldFilePath = inputFilePath.getParent();
        Path path = Paths.get(oldFilePath, oldFileName);
        BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
        //FileTime fileTime = attributes.lastModifiedTime();
        FileTime fileTime = attributes.creationTime();
        String newFileName=FileUtils.generateNewFileName(fileTime.toMillis());
        CommonUtils.printInfo(index, oldFileName, newFileName);
        if(needRename)
        {
            doneIt = FileUtils.renameFile(oldFilePath, oldFileName, newFileName + extension, newFilePath,primaryFileNameStartIndex,primaryFileNameEndIndex);
        }
        else
        {
            doneIt=true;
        }

        return doneIt;
    }



}