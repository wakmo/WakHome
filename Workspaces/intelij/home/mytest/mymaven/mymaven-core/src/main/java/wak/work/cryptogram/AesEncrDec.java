/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.work.cryptogram;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import sun.misc.BASE64Encoder;

public class AesEncrDec
{

    public static void main(String args[])
    {
        new AesEncrDec().encrypt();
    }

    void encrypt()
    {
        try
        {
            String plainData = "hello";
            byte[] byteDataToEncrypt = plainData.getBytes();
                         
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();
            
            System.out.println("Key:"+secretKey.getFormat());
            
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, secretKey); 
            
            byte[] byteCipherText = aesCipher.doFinal(byteDataToEncrypt); //Encrypted Data
            
            System.out.println("sdfsd:"+aesCipher.getParameters());
                                   
            aesCipher.init(Cipher.DECRYPT_MODE, secretKey, aesCipher.getParameters());
            byte[] byteDecryptedText = aesCipher.doFinal(byteCipherText); //Decrypted Data
            String decryptedText = new String(byteDecryptedText);
            
            String cipherText = new BASE64Encoder().encode(byteCipherText);
            System.out.println("\n Plain Data : " + plainData + " \n Cipher Data : " + cipherText + " \n Decrypted Data : " + decryptedText);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
