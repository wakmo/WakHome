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
    AccessFileMetaData fmd = new AccessFileMetaData();

    public void renameVideoFiles(boolean needRename,boolean keepSameFolderStructure)  throws ImageProcessingException, IOException
    {
        String inputPath = "\\\\Fs01\\mymedia\\MyVideos\\STREAM";
        String extension = ".mts";
        String outputPath = "\\\\Fs01\\mymedia\\MyVideos\\STREAM";

        fmd.readDirectory(inputPath, extension, outputPath,needRename,keepSameFolderStructure,0,15,"");
    }

    public void renamePictureFiles(boolean needRename,boolean keepSameFolderStructure) throws ImageProcessingException, IOException
    {
        String inputPath = "E:\\MyMedia\\MyPictures\\x";
        String extension = ".jpg";
        String outputPath = "E:\\MyMedia\\MyPictures\\x";

        fmd.readDirectory(inputPath, extension, outputPath,needRename,keepSameFolderStructure,0,15,"");
    }

    public static void main(String[] args)
    {

        try
        {
            RenameMediaFileNames rename=new RenameMediaFileNames();
            boolean needRename=true;
            boolean keepSameFolderStructure=false;
            //rename.renamePictureFiles(needRename,keepSameFolderStructure);
            rename.renameVideoFiles(needRename,keepSameFolderStructure);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
