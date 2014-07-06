package com.wakkir.barcode;

/**
 * User: wakkir
 * Date: 18/01/14
 * Time: 18:52
 */

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import eg.com.tm.barcode.processor.BarcodeEngine;
import eg.com.tm.barcode.processor.config.DecodeConfig;
import eg.com.tm.barcode.processor.config.EncodeConfig;

import java.io.File;
import java.util.Map;

/**
 * @author mohamed_taman
 */
public class BarcodeApplication
{

    public static void main(String[] args)
    {
        String path="E:\\Wakkir\\workspaces\\Github\\W3Repo\\Workspaces\\intelij\\output";
        // File will be used for creating the QRCode barcode type.
        File qrCodeFile = new File(path+"\\barcode\\QRCode.png");

        // File will be used for creating the QRCode barcode type.
        File PDF417File = new File(path+"\\barcode\\PDF_417.png");

        // Building the encoding configurations - using builder battern
        EncodeConfig encodeConfig =
                new EncodeConfig.Builder().createDirectories(Boolean.TRUE)
                        .isQRCodeFormat(Boolean.TRUE)
                        .withErrorCorrLevel(ErrorCorrectionLevel.M).build();

        // Generating the QRCode barcode

        String content = "This is the contents of the barcode. 1234567 (QRCode)";

        BarcodeEngine.encode(qrCodeFile, content, BarcodeFormat.QR_CODE, 200, 200, encodeConfig);

        encodeConfig =
                new EncodeConfig.Builder().createDirectories(Boolean.TRUE).
                        withCharactersMode(Mode.ALPHANUMERIC).build();

        // Generating the PDF_417 barcode

        content = "1234567891056 (PDF_417)";

        //BarcodeEngine.encode(PDF417File, content, BarcodeFormat.PDF_417, 10, 40, encodeConfig);

        BarcodeEngine.encode(PDF417File, "00000000000", BarcodeFormat.UPC_A, 10, 40, encodeConfig);

        System.out.println("------------------- Begins Writing barcodes -------------------\n");
        System.out.println("Is QRCode Created? " + (qrCodeFile.exists() ? "Yes " : "Not not ") + "Created");
        System.out.println("Is PDF_417 barcode Created? " + (PDF417File.exists() ? "Yes " : "Not not ") + "Created");
        System.out.println("\n------------------- Finished Writing barcodes -------------------");

        // Now we are going to decode (read) back contents of created barcodes

        // Building the decoding configurations - using builder battern
        DecodeConfig decodeConfig =
                new DecodeConfig.Builder()
                        .withHumanBarcodes(Boolean.TRUE)
                        .build();


        Map<BarcodeEngine.DecodeResults, Object> results = BarcodeEngine.decode(qrCodeFile, decodeConfig);

        String decodeText = (String) results.get(BarcodeEngine.DecodeResults.RESULT);
        String barcodeType = ((BarcodeFormat) results.get(BarcodeEngine.DecodeResults.BARCODE_FORMATE)).name();

        System.out.println("\n------------------- Begins reading barcodes -------------------\n");
        System.out.println("The decoded contents is: \"" + decodeText + "\", Barcode type is: " + barcodeType);

        // Configuration for product barcodes
        decodeConfig =
                new DecodeConfig.Builder()
                        .withHumanBarcodes(true)
                        .build();

        results = BarcodeEngine.decode(PDF417File, decodeConfig);

        decodeText = (String) results.get(BarcodeEngine.DecodeResults.RESULT);
        barcodeType = ((BarcodeFormat) results.get(BarcodeEngine.DecodeResults.BARCODE_FORMATE)).name();


        System.out.println("The decoded contents is: \"" + decodeText + "\", Barcode type is: " + barcodeType);

        System.out.println("\n------------------- Finished reading barcodes -------------------");
    }
}