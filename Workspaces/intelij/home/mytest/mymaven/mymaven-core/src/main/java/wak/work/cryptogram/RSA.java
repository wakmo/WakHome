/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.work.cryptogram;

import java.math.BigInteger;
import wak.work.cryptogram.helper.ByteArrayUtilities;
import wak.work.cryptogram.helper.SecurityDataTranslationException;

/**
 *
 * @author wakkir.muzammil
 */
public class RSA
{
    public void myRSADecryption() throws SecurityDataTranslationException
    {
        byte[] caPublicExponentByte = ByteArrayUtilities.byteify_nospaces("010001");
        byte[] caPrivateExponentByte = ByteArrayUtilities.byteify_nospaces("82B67A8209E25A022A41F10DCAED28DFE147C955ADCFE9CB47D984C785266856991C2B4C625FA1634734B40A7A3B33F71C60907EA9E54CB5721547FA9528C8E6B4DFA2F88554F6B1D5F4C4701EC94E5B4E823F9D4E55CB4E03CB4595C506E70D99DA9FFC0E3FF3FF3244648C75936F8C40D8CFF256DD80B50908B451AA0EF961");
        
        byte[] caModulusByte = ByteArrayUtilities.byteify_nospaces("B2F52A91225A11B9CDF4642D0D0440234ECF86398CC2BA075B5A2CBF0F4A1248DB737567F228DE1AB5F27738EB61CC7298E54279D96B7B05BB5B9D9E680904574AD358C5B4C7A1B1A8B0E807FA5571801AAC35DC336CB2497760C82CFD16EC8D5BD2D69BF64388F242528A66CA178C9ABEA81BC82F5D742CAD2C77C80DF48563");
        
        byte[] encryptedDataByte = ByteArrayUtilities.byteify_nospaces("29b05e21196875e48b133c0eb2f7de79170361a3d1e46d4acf911979127e6f51cb12da8b40fd56b27b7bb723707a65b5b07f78816d1b9f104b902b17d4b9c77d118c3e8c6cb2335ecd24081c07d0cb2ae9782a7fde607a816bb891129555af8f8d9babf66b3a3f4e96bdb4dc63e942a227ee060d39d55b10be179423fc91db94");
//        byte[] clearDataByte = ByteArrayUtilities.byteify_nospaces(ByteArrayUtilities.convertStringToHex("wakkir"));
        byte[] clearDataByte = "wakkir".getBytes();
        //byte[] clearDataByte = ByteArrayUtilities.byteify_nospaces("1234");
        
        
        
        BigInteger caPrivateExponent = new BigInteger(1, caPrivateExponentByte);
        BigInteger caPublicExponent = new BigInteger(1, caPublicExponentByte);
        
        BigInteger caModulus = new BigInteger(1, caModulusByte);
        
        BigInteger encryptedData = new BigInteger(1, encryptedDataByte);
        
        BigInteger clearData = new BigInteger(1, clearDataByte);
        
        //Now do the decryption!
        BigInteger decryptedData = encryptedData.modPow(caPublicExponent, caModulus);
        byte[] decryptedDataBytes = decryptedData.toByteArray();        
        //System.out.println("decrypted Data = " + ByteArrayUtilities.stringify_nospaces(decryptedDataBytes));
        System.out.println("decrypted Data = " + new String(decryptedDataBytes));
        
        
        //Now do the decryption!
        BigInteger encryptedData2 = clearData.modPow(caPrivateExponent, caModulus);
        byte[] encryptedData2Bytes = encryptedData2.toByteArray();
        System.out.println("encrypted Data 2 = " + ByteArrayUtilities.stringify_nospaces(encryptedData2Bytes));
        
       
        //return decryptedIssuerCertBytes;
    }

    //public byte[] getDecryptedIssuerCertSignature(byte[] issuerCert,BigInteger caModulus,BigInteger caPublicExponent) throws SecurityDataTranslationException
    public void getDecryptedIssuerCertSignature() throws SecurityDataTranslationException
    {
        byte[] caPublicExponentByte = ByteArrayUtilities.byteify_nospaces("03");
        byte[] caModulusByte = ByteArrayUtilities.byteify_nospaces("8CF2A75E3C45FB4E72C2775111BC0D0C81A420C2C652A11D6AA6292D70112A16871BC616A0D1C30E1E0097A3D4EA344B1842D7EBCE96D89CC873A2843D545A4C8FF3723D9480723CD3960BBBEE32EED57D1D4EA81815CA39F27F65B537A33499C5548F58B7C96DF0F6606EBA5CDEAFD11BB48A6691CA2DC0CC8F3631A59255CB");
        byte[] issuerCert = ByteArrayUtilities.byteify_nospaces("3038A5F154AE44C683CF68DF1333FACA0AB50E3FBF967878E8413F59502B736E298F53834582FC1A5B2CFD301870BCB271F758F4670326B0968C7D16935BDCCDF1BA72F41DACF94CF4A4718F68559EF85B731C37C66F98AFA8875C478EF252993BC162658D1787E0EFD2C3D5408D0B9AA457895BAD1AAB02989A68EA4752883C");


        caModulusByte = ByteArrayUtilities.byteify_nospaces("996AF56F569187D09293C14810450ED8EE3357397B18A2458EFAA92DA3B6DF6514EC060195318FD43BE9B8F0CC669E3F844057CBDDF8BDA191BB64473BC8DC9A730DB8F6B4EDE3924186FFD9B8C7735789C23A36BA0B8AF65372EB57EA5D89E7D14E9C7B6B557460F10885DA16AC923F15AF3758F0F03EBD3C5C2C949CBA306DB44E6A2C076C5F67E281D7EF56785DC4D75945E491F01918800A9E2DC66F60080566CE0DAF8D17EAD46AD8E30A247C9F");
        issuerCert = ByteArrayUtilities.byteify_nospaces("3827EC9EF3EA9F3F238684E4F6C0F1DF8708BF197AB56EB09AE4ED41720DEFE7EF00DFB6F167BC2CCC26204EB2520E9591421BAF70786E6B8988C334C3A6F80193B320A390058DEF5096A3015F14B984D0AB63BEFF0466D19319494BE39CD140A89EE2BBB9A2F6613DD87D7B56DBCA4EF60C6C652EE33D3705A6326F8D144AB97E6D9888798ECFE19469722168C2ED8F5044F1F8D5F39070BEA25330CDB4D48CEFFE9029DA6B63E3F6FE9E8EA45DFD63");

        if (issuerCert.length != caModulusByte.length)
        {
            throw new SecurityDataTranslationException("CA Modulus cannot be used with this issuer certificate, (issuerCert.length != caModulus.length)");
        }

        BigInteger caModulus = new BigInteger(1, caModulusByte);
        BigInteger caPublicExponent = new BigInteger(1, caPublicExponentByte);

        if (issuerCert == null)
        {
            throw new SecurityDataTranslationException("Issuer Certificate cannot be null");
        }

        if (caModulus == null)
        {
            throw new SecurityDataTranslationException("CA Modulus cannot be null");
        }

        if (caPublicExponent == null)
        {
            throw new SecurityDataTranslationException("CA Public Exponent cannot be null");
        }


        BigInteger issuerCertBI = new BigInteger(1, issuerCert);

        //Now do the decryption!
        BigInteger decryptedIssuerCertBI = issuerCertBI.modPow(caPublicExponent, caModulus);

        byte[] decryptedIssuerCertBytes = decryptedIssuerCertBI.toByteArray();
        //if(logger.isDebugEnabled())
        {
            System.out.println("Decrypted cert = " + ByteArrayUtilities.stringify_nospaces(decryptedIssuerCertBytes));
        }

        if (decryptedIssuerCertBytes.length < issuerCert.length)
        {
            //Prepad with (issuerCert.length - decryptedIssuerCertBytes.length) 0x00 bytes
            byte[] temp = new byte[issuerCert.length];
            System.arraycopy(decryptedIssuerCertBytes, 0, temp, issuerCert.length - decryptedIssuerCertBytes.length, decryptedIssuerCertBytes.length);
            decryptedIssuerCertBytes = temp;
        }
        else if (decryptedIssuerCertBytes.length > issuerCert.length)
        {
            //Remove some trailing zeros
            //Number of trailing zeros to remove is: (decryptedIssuerCertBytes.length - issuerCert.length)
            byte[] temp = new byte[issuerCert.length];
            System.arraycopy(decryptedIssuerCertBytes, (decryptedIssuerCertBytes.length - issuerCert.length), temp, 0, issuerCert.length);
            decryptedIssuerCertBytes = temp;
        }

        System.out.println("decryptedIssuerCertBytes :" + ByteArrayUtilities.stringify_nospaces(decryptedIssuerCertBytes));

        //return decryptedIssuerCertBytes;
    }
    
    public static void main(String[] args) throws SecurityDataTranslationException 
    {
        System.out.println("Starting...");
        //RSA_MODULUSEXPOENET
        //RSA_CRT(P,Q,DP,DQ,N)
        RSA ru = new RSA();
        ru.myRSADecryption();
      
    }
    
}
