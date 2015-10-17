package com.wakkir.photo;

import com.drew.imaging.ImageProcessingException;

import java.io.IOException;

/**
 * User: wakkir
 * Date: 27/11/13
 * Time: 20:47
 */
public class RenameMediaFileNames
{
    private boolean needRename;
    private boolean keepSameFolderStructure;
    private String inputPath;
    private String extension;
    private String outputPath;

            
    AccessFileMetaData fmd = new AccessFileMetaData();
    
    public RenameMediaFileNames()
    {        
    
    }
    
    public void doRename()  throws ImageProcessingException, IOException
    {
       fmd.readDirectory(inputPath, extension, outputPath,needRename,keepSameFolderStructure,0,15,"");  
             
    }

    //public void renameVideoFiles(boolean needRename,boolean keepSameFolderStructure)  throws ImageProcessingException, IOException
    //{
    //String inputPath = "\\\\Fs01\\mymedia\\MyVideos\\STREAM";
    //String extension = ".mts";
    //String outputPath = "\\\\Fs01\\mymedia\\MyVideos\\STREAM";
    //fmd.readDirectory(inputPath, extension, outputPath,needRename,keepSameFolderStructure,0,15,"");
    //}
    //public void renamePictureFiles(boolean needRename,boolean keepSameFolderStructure) throws ImageProcessingException, IOException
    //{
    //String inputPath = "E:\\MyMedia\\MyPictures\\x";
    //String extension = ".jpg";
    //String outputPath = "E:\\MyMedia\\MyPictures\\x";
    //fmd.readDirectory(inputPath, extension, outputPath,needRename,keepSameFolderStructure,0,15,"");
    //}
    public boolean isNeedRename()
    {
        return needRename;
    }

    public void setNeedRename(boolean needRename)
    {
        this.needRename = needRename;
    }

    public boolean isKeepSameFolderStructure()
    {
        return keepSameFolderStructure;
    }

    public void setKeepSameFolderStructure(boolean keepSameFolderStructure)
    {
        this.keepSameFolderStructure = keepSameFolderStructure;
    }

    public String getInputPath()
    {
        return inputPath;
    }

    public void setInputPath(String inputPath)
    {
        this.inputPath = inputPath;
    }

    public String getExtension()
    {
        return extension;
    }

    public void setExtension(String extension)
    {
        this.extension = extension;
    }

    public String getOutputPath()
    {
        return outputPath;
    }

    public void setOutputPath(String outputPath)
    {
        this.outputPath = outputPath;
    }
    
    
    
    
}
