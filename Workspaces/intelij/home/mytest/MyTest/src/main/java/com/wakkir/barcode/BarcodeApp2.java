package com.wakkir.barcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;


/**
 * User: wakkir
 * Date: 18/01/14
 * Time: 20:25
 */
public class BarcodeApp2
{

    /**
     * Decode method used to read image or barcode itself, and recognize the barcode,
     * get the encoded contents and returns it.
     *
     * @param file   image that need to be read.
     * @param hints configuration used when reading the barcode.
     * @return decoded results from barcode.
     */
    public static String decode(File file, Map<DecodeHintType, Object> hints) throws Exception
    {
        // check the required parameters
        if (file == null || file.getName().trim().isEmpty())
        {
            throw new IllegalArgumentException("File not found, or invalid file name.");
        }
        BufferedImage image;
        try
        {
            image = null;//read(file);
        }
        catch (Exception ioe)
        {
            throw new Exception(ioe.getMessage());
        }
        if (image == null)
        {
            throw new IllegalArgumentException("Could not decode image.");
        }
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        MultiFormatReader barcodeReader = new MultiFormatReader();
        Result result;
        String finalResult=null;
        try
        {
            if (hints != null && !hints.isEmpty())
            {
                result = barcodeReader.decode(bitmap, hints);
            }
            else
            {
                result = barcodeReader.decode(bitmap);
            }
            // setting results.
            finalResult = String.valueOf(result.getText());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //throw new BarcodeEngine().new BarcodeEngineException(e.getMessage());
        }
        return finalResult;
    }
}
