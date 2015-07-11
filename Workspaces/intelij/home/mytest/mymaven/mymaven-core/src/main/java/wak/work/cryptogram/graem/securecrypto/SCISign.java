/*
 *

 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SCISign.java %
 *  Created:	 Tue May 28 14:01:28 2002
 *  Created By:	 %created_by:  build %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  SCISign.java~3:java:UKPMA#2 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *  1.1			9 Apr 2008	Dick Perkins	 Updated to remove IAIK libraries
 *
 */

  /** THIS CLASS IS A DIRECT COPY OF Roger Aingers SCESign */
  /** This class should be removed when there is a version of ca.jar available */

package wak.work.cryptogram.graem.securecrypto;



import cryptix.asn1.lang.ObjectIdentifier;
import cryptix.asn1.lang.OctetString;
import java.io.IOException;
import java.security.*;
import java.util.ArrayList;

/**
 * Uses the SCE to generate signatures for both certificate requests.
 * The digest an PKCS#1 padding are both provided
 * by this class. The SCE need only perform the actual encryption.
 */
class SCISign extends java.security.Signature {
  /**
   * The name of the signature algorithm.
   */
  private String algorithm;

  /**
   * The digest created prior to signing the object.
   */
  private MessageDigest	digest;

  /**
   * The private key used to sign the data.
   */
  private PrivateKey privateKey;

  private PublicKey publicKey;

  /**
   * The signature algorithm.
   */
  private SCIAlgorithm algorithmID;

  /**
   * The length of the RSA modulus.
   */
  private int keyLength;

  /**
   * Reference to the SCI.
   */
  private SecureCrypto sci;

  /**
   * Creates an SCESign object. This is used to create a
   * signature for some data. The default hashing algorithm
   * is SHA-1.
   */
  public SCISign (SCIAlgorithm alg, SecureCrypto sci, int len) throws NoSuchAlgorithmException,NoSuchProviderException {
      super ("SCI");

      algorithmID = alg;
      this.sci = sci;

      // len is the bit length - we need the byte length
      keyLength = len/8;

      // Determine the signing algorithm to use.
      if (algorithmID == SCIAlgorithm.sha1) {
    	  digest = MessageDigest.getInstance("SHA-1");
      } else if (algorithmID == SCIAlgorithm.md5) {
    	  digest = MessageDigest.getInstance("MD5");
      } else {
    	  throw new NoSuchAlgorithmException("Digest algorithm, " + algorithmID.toString() + ", not supported");
      }
  }

  protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
      this.publicKey = publicKey;
      digest.reset();
  }

  /**
   * Used to initialise this object for signature generation.
   */
  protected void engineInitSign(PrivateKey pk) throws InvalidKeyException {
      privateKey = pk;
      digest.reset();
  }

  /**
   * Used to add a byte of data to be signed.
   */
  protected void engineUpdate(byte b) throws SignatureException {
    digest.update (b);
  }

  /**
   * Used to add a bytes to the data to be signed.
   */
  protected void engineUpdate(byte b[], int off, int len) throws SignatureException {
    digest.update(b, off, len);
  }

  /**
   * Generates a signature. The digest is completed and placed
   * in a DigestInfo structure. This is then padded with a PKCS#1
   * padding with a block type of 01. The resultant byte array is
   * passed to the SCE to be encrypted using the secret key.
   */
  protected byte[] engineSign() throws SignatureException {
    byte[] signature = null;
    try {
      /*
       * We construct a PKCS#1 V1.5 signature. This consists of
       *
       *  00 || 01 || paddingString || 00 || digestInfo
       *
       * The paddingString consists of k-3-len(digestInfo) bytes
       * set to 0xFF. k is the key modulus length in bytes.
       *
       * digestInfo ::= SEQUENCE {
       *    digestAlgorithm OID
       *    digest OCTECT STRING }
       *
       * The whole thing is then enciphered using the RSA key.
       */

      // Create the digestInfo structure.
      ObjectIdentifier digestAlg = null;
      if (algorithmID == SCIAlgorithm.md5) {
    	  digestAlg = new ObjectIdentifier("MD5", "1.2.840.113549.1.1.4"); // OID for MD5 with RSA Encryption
      } else if (algorithmID == SCIAlgorithm.sha1) {
    	  digestAlg = new ObjectIdentifier("SHA1", "1.2.840.113549.1.1.5"); // OID for SHA-1 with RSA Encryption
      } else {
    	  throw new SignatureException("Signature algorithm ID, "+ algorithmID.toString() + ", not supported");
      }
      
      OctetString digestValue = new OctetString("OCTS", digest.digest());
      DigestInfo digestInfo = new DigestInfo(digestAlg, digestValue);

      // Encode the DigestInfo as a byte array
      cryptix.asn1.io.ASNWriter writer = null;
      try {
    	  writer = cryptix.asn1.encoding.Factory.getEncoder("DER");
      } catch (Exception e) {
    	  e.printStackTrace();
    	  System.exit(1);
      }
      
      java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
      writer.open(bos);
      try {
    	  writer.encodeStructure(digestInfo);
      } catch (IOException e) {
    	  e.printStackTrace();
    	  System.exit(1);
      }
		
      byte[] digestBytes = bos.toByteArray();

      // Construct the paddingString
      int digestLength = digestBytes.length;
      int psLength = keyLength - 3 - digestLength;
      byte[] paddingString = new byte [psLength];
      for (int i=0; i<psLength; i++) {
      	paddingString[i] = (byte)0xFF;
      }

      // Put together the block to be encrypted.
      byte[] encryptionBlock = new byte [keyLength];
      encryptionBlock[0] = 0;
      encryptionBlock[1] = 1;
      System.arraycopy (paddingString, 0, encryptionBlock, 2, psLength);
      encryptionBlock [psLength+2] = 0;
      System.arraycopy (digestBytes, 0, encryptionBlock, psLength+3, digestLength);


      // Now build the SCE cipher request
      signature = sci.signMessage(encryptionBlock,
                                  HashParams.NO_HASH,
                                  PadMode.NoPadding,
                                  ((RSAPrivateKeyWrapper)privateKey).getKey());

      // Fix padding on signature
      byte[] fixedSignature = new byte[keyLength];

      if (signature.length > keyLength) {
        // remove zero's from the front
        int siglength = signature.length - keyLength;
        System.arraycopy(signature, siglength, fixedSignature, 0, keyLength);
        signature = fixedSignature;
      }
      else if (signature.length < keyLength) {
        // add zero's to the front
        int siglength = keyLength - signature.length;
        for (int x = 0; x < siglength; x ++) {
          fixedSignature[x] = 0x00;
        }
        System.arraycopy(signature, 0, fixedSignature, siglength, signature.length);
        signature = fixedSignature;
      }
    }
    catch (Exception e) {
      String logMessage = "Error signing data: "+e.toString();

      throw new SignatureException (logMessage);
    }
    return signature;
  }

  protected boolean engineVerify(byte sigBytes[]) throws SignatureException {
    return true;
  }

  protected void engineSetParameter(String param, Object value) throws InvalidParameterException {
  }

  protected Object engineGetParameter(String param) throws InvalidParameterException {
    return null;
  }
	
	private class DigestInfo extends cryptix.asn1.lang.Sequence {

		public DigestInfo(ObjectIdentifier oid, OctetString os) {
			super("", new cryptix.asn1.lang.Tag(0x10));

			components = new ArrayList<Object>(2);
			components.add(0, oid);
			components.add(1, os);
		}
	}
}