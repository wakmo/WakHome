package wak.work.cryptogram.graem.securecrypto;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *  1.1			9 Apr 2008	Dick Perkins	 Updated to remove IAIK libraries
 *
 */

import java.math.BigInteger;

public final class SecureCryptoUtils
{
    private SecureCryptoUtils()
    {
    }

    public static String toHexString(byte[] data)
    {
        return toHexString(data, true);
    }

    public static String toHexString(byte[] data, boolean addSpace)
    {
        StringBuilder sb = new StringBuilder(data.length * 3);
        for (byte b : data)
        {
            String hexStr = Integer.toHexString(b & 0xff);

            if (hexStr.length() == 1)
            {
                sb.append("0");
            }
            sb.append(hexStr);

            if (addSpace)
            {
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    /*
      static byte [] translateAccountNumber (byte [] data) throws SecureCryptoException {
        byte [] translatedAccountNum = null;
        if(data==null || data.length!=8) {
          throw new SecureCryptoException("Invalid account number");
        }

        byte[] wholeAccountNum = HsmUtilities.convertSCDGKeytoVOPKey(data);

        translatedAccountNum = new byte[ACCOUNT_NUMBER_SIZE];

        System.arraycopy(wholeAccountNum, 4, translatedAccountNum, 0, ACCOUNT_NUMBER_SIZE);

        return translatedAccountNum;
      }
    */
    public static byte[] modulusToBytes(BigInteger bi)
    {
        byte[] value;
        byte[] res = bi.toByteArray();

        if (res[0] == 0)
        {
            value = new byte[res.length - 1];
            System.arraycopy(res, 1, value, 0, value.length);
        }
        else
        {
            value = res;
        }

        return value;
    }

    static BigInteger ensurePositive(BigInteger bi)
    {
        BigInteger newBI = null;
        if (bi.doubleValue() < 0)
        {
            byte[] biBytes = bi.toByteArray();
            byte[] newBIBytes = new byte[biBytes.length + 1];
            newBIBytes[0] = 0x00;
            System.arraycopy(biBytes, 0, newBIBytes, 1, biBytes.length);
            newBI = new BigInteger(newBIBytes);
        }
        else
        {
            newBI = bi;
        }
        return newBI;
    }

//    public static boolean signPKCS10CertificateRequest(SecureCrypto sci, int keyLength,
//                            iaik.pkcs.pkcs10.CertificateRequest req, RSAPrivateKeyWrapper secKey)
//            throws SignatureException, InvalidKeyException, NoSuchAlgorithmException
//    {
//        // 1. Create a signing algorithm
//        iaik.asn1.structures.AlgorithmID signingAlgorithm = new SCIAlgorithm (sci, keyLength);
//
//        // 2. Sign the certificate request
//        req.sign (signingAlgorithm, secKey);
//
//        // 3. Verify
//        return req.verify();
//    }

/*
  static String dump(byte[] encodedKey) {
    StringBuffer result = new StringBuffer(1024);
    try {
      iaik.asn1.ASN1Object asn1 = iaik.asn1.DerCoder.decode(encodedKey);
      if (iaik.asn1.ASN.SEQUENCE == asn1.getAsnType()){
        result.append("Sequence 1").append(lineSep).append(unpackSequence(asn1, 1)).append("End sequence1");
      }
    }
    catch (iaik.asn1.CodingException codeX) {}
    return result.toString();
  }

  static String unpackSequence(ASN1Object asn1, int indent) {
    StringBuffer diagnostic = new StringBuffer(1024);
    try{
      ASN1Object innerASN1 = null;

      for (int f=0; f<asn1.countComponents(); f++) {
        innerASN1 = asn1.getComponentAt(f);

        for (int i=0; i<indent; i++) {
          diagnostic.append("  ");
        }
        if (ASN.SEQUENCE == innerASN1.getAsnType()) {
          diagnostic.append("Sequence "+(indent+1)+lineSep);
          diagnostic.append(unpackSequence(innerASN1, indent+1));
          for (int j=0; j<indent; j++) {
            diagnostic.append("  ");
          }
          diagnostic.append("End sequence"+(indent+1)+lineSep);
        }
        else{
           if (ASN.INTEGER == innerASN1.getAsnType()){
             diagnostic.append("INTEGER ");
           }
           else if (ASN.IA5String == innerASN1.getAsnType()){
             diagnostic.append("IA5STRING ");
           }
           else if (ASN.OCTET_STRING == innerASN1.getAsnType()){
             diagnostic.append("OCTET STRING ");
           }
           else if(ASN.BIT_STRING ==innerASN1.getAsnType()){
            diagnostic.append("BIT_STRING ");
           }
           else if(ASN.ObjectID==innerASN1.getAsnType()){
            diagnostic.append("Object_ID ");
           }
           else if(ASN.NULL==innerASN1.getAsnType()){
           }
           else {
            diagnostic.append("Unrecognised type "+innerASN1.getAsnType());
           }

           if (ASN.OCTET_STRING == innerASN1.getAsnType()){
             try {
               byte [] keyData = ((OCTET_STRING)innerASN1).getWholeValue();
               if (keyData.length >2 &&
                   keyData[0] == 0x30 &&
                   (((int)keyData[1]&0x000000ff) == 0x81 || ((int)keyData[1]&0x000000ff) == 0x82) ){
                    diagnostic.append("Sequence "+(indent+1)+lineSep);
                    diagnostic.append(unpackSequence(DerCoder.decode(keyData), indent+1));
                    for (int j=0; j<indent; j++) {
                      diagnostic.append("  ");
                    }
                    diagnostic.append("End sequence"+(indent+1)+lineSep);
               }
               else {
//                 diagnostic.append("OCTET STRING ");
                 diagnostic.append(toHexString(keyData));
               }
               diagnostic.append(lineSep);
             }
             catch (java.io.IOException ioX) {};
           }
           else if(ASN.BIT_STRING ==innerASN1.getAsnType()){
            BigInteger bi = new BigInteger(((BIT_STRING)innerASN1).getBinaryString(), 2);

            byte[] bitBytes = convertASCIIBytesToBinary(bi.toString(16));
             if (bitBytes.length >2 &&
                 bitBytes[0] == 0x30 &&
                 (((int)bitBytes[1]&0x000000ff) == 0x81 || ((int)bitBytes[1]&0x000000ff) == 0x82) ){
                  diagnostic.append("Sequence "+(indent+1)+lineSep);
                  diagnostic.append(unpackSequence(DerCoder.decode(bitBytes), indent+1));
                  for (int j=0; j<indent; j++) {
                    diagnostic.append("  ");
                  }
                  diagnostic.append("End sequence"+(indent+1)+lineSep);
             }
             else {
               diagnostic.append(bi.toString(16));
             }
             diagnostic.append(lineSep);

//            diagnostic.append(bi.toString(16)).append(lineSep);
//            diagnostic.append(((BIT_STRING)innerASN1).getBinaryString()).append(lineSep);
           }
           else if (ASN.INTEGER == innerASN1.getAsnType()){
            BigInteger bi = null;
            Object obj = innerASN1.getValue();
            if (obj instanceof java.math.BigInteger) {
              bi = (java.math.BigInteger)obj;
              diagnostic.append(bi.toString(16));
            }
            else {
              diagnostic.append(obj.toString());
            }
            diagnostic.append(lineSep);
           }

           else {
             String value = null;
             if (innerASN1.getValue() != null) {
               value = innerASN1.getValue().toString();
             }
             else {
               value = innerASN1.toString();
             }
             diagnostic.append(value).append(lineSep);
           }
        }
      }
    }
    catch (iaik.asn1.CodingException codeX) {}
    return diagnostic.toString();
  }

  private static byte [] convertASCIIBytesToBinary (String data) {
      byte [] binaryBytes = null;
      if (data!=null) {
          binaryBytes = new byte [data.length()/2];

          for (int f=0; f<binaryBytes.length; f++) {
              int pos = f*2;
              binaryBytes[f] = (byte)Integer.parseInt(data.substring(pos, pos+2), 16);
          }
      }
      return binaryBytes;
  } */

}