/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.work.cryptogram;

import cryptix.jce.provider.key.RawSecretKey;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import wak.work.cryptogram.helper.ByteArrayUtilities;

/**
 *
 * @author wakkir.muzammil
 */
public class MyDESTest
{
    private static final String DES3_CBC_INIT = "DESede/CBC/NoPadding";
    private static final String DES3_ECB_INIT = "DESede/ECB/NoPadding";
    private static final String DES3_ALG = "DESede";
    
    public MyDESTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }
    
    
     @Test
     public void testDoCipher() throws Exception     
     {
         try
         {
            String hexStr=ByteArrayUtilities.convertStringToHex("Wakkir!!");
            byte[] plaintext = ByteArrayUtilities.byteify_nospaces(hexStr);//Wakkir!!");
            //byte[] plaintext = ByteArrayUtilities.byteify_nospaces("57616B6B69722121");//Wakkir!!");
            byte[] key = ByteArrayUtilities.byteify_nospaces("0505050505050505FCFCFCFCFCFCFCFC");

            byte[] iv = new byte[8]; //8 zeros

            byte[] ciphertext = des(DES3_CBC_INIT, DES3_ALG, Cipher.ENCRYPT_MODE, plaintext, key, iv);

            System.out.println("ciphertext = " + ByteArrayUtilities.stringify_nospaces(ciphertext));
         }
         catch (Exception x)
         {
             x.printStackTrace();
         }
     }
     
     @Test
     public void testDoDeCipher() throws Exception     
     {
         try
         {
            byte[] ciphertext = ByteArrayUtilities.byteify_nospaces("505aa4e53e1b9e19");
            byte[] key = ByteArrayUtilities.byteify_nospaces("0505050505050505FCFCFCFCFCFCFCFC");

            byte[] iv = new byte[8]; //8 zeros

            byte[] plaintext = des(DES3_CBC_INIT, DES3_ALG, Cipher.DECRYPT_MODE, ciphertext, key, iv);

            System.out.println("plaintext in Hex = " + ByteArrayUtilities.stringify_nospaces(plaintext));
            System.out.println("plaintext ASCII= " + ByteArrayUtilities.convertHexToString(ByteArrayUtilities.stringify_nospaces(plaintext)));
         }
         catch (Exception x)
         {
             x.printStackTrace();
         }
     }
    
    private static byte[] des(String init, String alg, int mode, byte[] data, byte[] keyBytes, byte[] iv) throws Exception
    {
        keyBytes = toTripleDesKey(keyBytes);

        try
        {
            Cipher desCipher = Cipher.getInstance(init);

            Key key = new RawSecretKey(alg, keyBytes);

            if (init.contains("CBC"))
            {
                desCipher.init(mode, key, new IvParameterSpec(iv));
            }
            else
            {
                desCipher.init(mode, key);
            }

            return desCipher.doFinal(data);
        }
        catch (Exception x)
        {
            x.printStackTrace();
            throw new Exception("Encryption failed", x);
        }
    }

    private static byte[] toTripleDesKey(byte[] data)
    {
        if (data.length == 16)
        {
            byte[] keyL = ByteArrayUtilities.partOfByteArray(data, 0, 8);
            byte[] keyR = ByteArrayUtilities.partOfByteArray(data, 8, 16);
            data = ByteArrayUtilities.addByteArrays(keyL, keyR, keyL);
        }

        return data;
    }
    
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     //GPSCP02I55MockCrypto mock = new GPSCP02I55MockCrypto();
    /*
     @Test
     public void testDoGenerateDerivedKey() throws Exception     
     {
         try
         {
            byte[] plaintext = "Wakkir!!".getBytes();


           SCIKeyValue sciKeyValue = new SCIKeyValue(new byte[1],
                   "Fake",
                   new SCIKeyUsage[] {
                           new SCIKeyUsage(Algorithm.DES2EDE.toInt(), Usage.ECK.toInt())
                   },
                   Component.Complete.toInt(),
                   KeyType.PROTECTED.toInt(),
                   128,
                   1,
                   new byte[] {0x00, 0x00},
                   ByteArrayUtilities.byteify_nospaces("0505050505050505FCFCFCFCFCFCFCFC")
           );

           ProtectedKey pKey = SCIKeyFactory.getInstance().createSCIProtectedKey(sciKeyValue);

           byte[] iv = new byte[8]; //8 zeros

            byte[] ciphertext = mock.cipherImage(plaintext, EncDec.encrypt, pKey, CipherMode.CBC, iv);

            System.out.println("Ciphertext = " + ByteArrayUtilities.stringify_nospaces(ciphertext));
         }
         catch (Exception x)
         {
             x.printStackTrace();
         }
     }

     @Test
     public void testDoGenerateDerivedKey2() throws Exception     
     {
         try
         {
            byte[] plaintext = ByteArrayUtilities.byteify_nospaces("505aa4e53e1b9e19");


           SCIKeyValue sciKeyValue = new SCIKeyValue(new byte[1],
                   "Fake",
                   new SCIKeyUsage[] {
                           new SCIKeyUsage(Algorithm.DES2EDE.toInt(), Usage.DCK.toInt())
                   },
                   Component.Complete.toInt(),
                   KeyType.PROTECTED.toInt(),
                   128,
                   1,
                   new byte[] {0x00, 0x00},
                   ByteArrayUtilities.byteify_nospaces("0505050505050505FCFCFCFCFCFCFCFC")
           );

           ProtectedKey pKey = SCIKeyFactory.getInstance().createSCIProtectedKey(sciKeyValue);

           byte[] iv = new byte[8]; //8 zeros

            byte[] ciphertext = mock.cipherImage(plaintext, EncDec.decrypt, pKey, CipherMode.CBC, iv);

            System.out.println("Ciphertext = " + ByteArrayUtilities.stringify_nospaces(ciphertext));
         }
         catch (Exception x)
         {
             x.printStackTrace();
         }
     }
     */
    
}
