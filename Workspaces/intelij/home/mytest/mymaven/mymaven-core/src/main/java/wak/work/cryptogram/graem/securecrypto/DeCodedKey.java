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
 *  1.1			9 Apr 2008	Dick Perkins	Updated to remove IAIK libraries
 */

import java.util.ArrayList;

public class DeCodedKey {
  private Component component;
  private String keyType;
  private Integer keySize;
  private byte [] keyData;

  private Algorithm currentKeyUsageAlg;
  private Usage currentUsage;
  private ArrayList keyUsageList = new ArrayList();
  private Integer masterKey;

  public DeCodedKey(byte[] asn1Seq) throws SecureCryptoException
  {
	  // This class has not had the ASN.1 parts converted.
	  // It is believed only to be used by the KMS, for which it must be updated.
	  // Until then, it throws an exception.
    throw new SecureCryptoException("DeCodedKey class not implemented");

//    try
//    {
//      iaik.asn1.ASN1Object innerSeq = iaik.asn1.DerCoder.decode(asn1Seq);
//      if (iaik.asn1.ASN.SEQUENCE == innerSeq.getAsnType())
//      {
//        decode((SEQUENCE)innerSeq, 1);
//      }
//      else
//      {
//        throw new SecureCryptoException("Not a valid ASN1 sequenece");
//      }
//    }
//    catch (Throwable ex)
//    {
//      throw new SecureCryptoException("ASN1 decode error", ex);
//    }
  }

  public int getMasterKey()
  {
      //todo This method has been 'nullified' (as discussed with DP).
      //It was previously commented out, but this caused a compile error as it is called

    //return masterKey.intValue();
      return 0;
  }

//  public Component getComponent()
//  {
//    return component;
//  }

//  public String getKeyType ()
//  {
//    return keyType;
//  }

//  public int getKeySize()
//  {
//    return keySize.intValue();
//  }

  public byte [] getKeyData()
  {
      //todo This method has been 'nullified' (as discussed with DP).
      //It was previously commented out, but this caused a compile error as it is called
    //return keyData;
      return null;
  }

//  public KeyUsage[] getKeyUsage ()
//  {
//    KeyUsage usage[] = new KeyUsage[keyUsageList.size()];
//
//    int counter = 0;
//
//    for(Iterator it=keyUsageList.iterator(); it.hasNext();){
//      usage[counter++] = (KeyUsage)it.next();
//    }
//
//    return usage;
//  }

//  private void decode(SEQUENCE asn1Seq, int level) throws java.io.IOException, SecureCryptoException
//  {
//    ASN1Object innerASN1 = null;
//    for (int f=0; f<asn1Seq.countComponents(); f++) {
//      innerASN1 = asn1Seq.getComponentAt(f);
//
//      if (iaik.asn1.ASN.SEQUENCE == innerASN1.getAsnType()) {
//        decode((SEQUENCE)innerASN1, level+1);
//      }
//      else {
//        switch (level) {
//          case 1:
//            if (iaik.asn1.ASN.OCTET_STRING == innerASN1.getAsnType()) {
//              keyData = ((OCTET_STRING)innerASN1).getWholeValue();
//            }
//          break;
//          case 2:
//            if (iaik.asn1.ASN.INTEGER == innerASN1.getAsnType()) {
//              if (masterKey==null) {
//                Object obj = innerASN1.getValue();
//                if (obj instanceof java.math.BigInteger) {
//                  masterKey = new Integer(((java.math.BigInteger)obj).intValue());
//                }
//              }
//              else if (component == null) {
//                Object obj = innerASN1.getValue();
//                if (obj instanceof java.math.BigInteger) {
//                  component = Component.getComponent(((java.math.BigInteger)obj).intValue());
//                }
//              }
//              else if (keySize == null) {
//                Object obj = innerASN1.getValue();
//                if (obj instanceof java.math.BigInteger) {
//                  keySize = new Integer(((java.math.BigInteger)obj).intValue());
//                }
//              }
//            }
//            else if (iaik.asn1.ASN.IA5String == innerASN1.getAsnType()) {
//              keyType = innerASN1.getValue().toString();
//            }
//          break;
//          case 3:
//          break;
//          case 4:
//            if (iaik.asn1.ASN.IA5String == innerASN1.getAsnType()) {
//              if (currentKeyUsageAlg == null) {
//                currentKeyUsageAlg = Algorithm.getAlgorithm(innerASN1.getValue().toString());
//              }
//              else {
//                throw new SecureCryptoException ("Invalid key usage found");
//              }
//            }
//            else if (iaik.asn1.ASN.INTEGER == innerASN1.getAsnType()) {
//              Object obj = innerASN1.getValue();
//              if (obj instanceof java.math.BigInteger) {
//                currentUsage = Usage.getUsage(((java.math.BigInteger)obj).intValue());
//              }
//              if (currentKeyUsageAlg != null) {
//                keyUsageList.add(new KeyUsage(currentKeyUsageAlg, currentUsage));
//                currentKeyUsageAlg = null;
//              }
//              else {
//                throw new SecureCryptoException ("Invalid key usage found");
//              }
//            }
//          break;
//          default:
//          break;
//        }
//      }// end else
//    }// end for
//  }
}