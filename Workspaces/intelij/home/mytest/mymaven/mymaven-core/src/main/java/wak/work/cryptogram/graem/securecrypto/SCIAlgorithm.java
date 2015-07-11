/*
 *
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SCIAlgorithm.java %
 *  Created:	 Tue May 28 14:02:20 2002
 *  Created By:	 %created_by:  perkinsd %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  SCIAlgorithm.java~5:java:UKPMA#2 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *  1.1			9 Apr 2008	Dick Perkins	 Updated to remove IAIK libraries
 *
 */

  /** THIS CLASS IS A DIRECT COPY OF Roger Aingers SCEAlgorithm */
  /** This class should be removed when there is a version of ca.jar available */

package wak.work.cryptogram.graem.securecrypto;


/**
 * Used to represent an SCI signing algorithm.
 */
class SCIAlgorithm
{
    private String algString;
    
    public static SCIAlgorithm sha1 = new SCIAlgorithm("SHA-1");
    public static SCIAlgorithm md5 = new SCIAlgorithm("MD5");

	
    public SCIAlgorithm (String algString)
    {
        this.algString = algString;
    }

    /**
     * Returns an implementation for this algorithm.
     * This is always an SCESign object.
     */
/*    public Object getInstance() throws NoSuchAlgorithmException
    {
          Object inst = null;
          try
          {
            inst = new SCISign(getIAIKAlgorithm(), sci, keyLength);
          }
          catch (Throwable e)
          {
            throw new NoSuchAlgorithmException("Cannot instantiate engine signing helper");
          }
          return inst;
    } */

    /**
     * Provides IAIK with the ASN1 OID which identifies
     * this signing algorithm.
     */
/*    public ASN1Object toASN1Object() {
        return getIAIKAlgorithm().toASN1Object();
    } */

    /**
     * Returns the IAIK signature algorithm.
     */
/*    AlgorithmID getIAIKAlgorithm () {
      // The default signing algorithm is always SHA1
      return AlgorithmID.sha1WithRSAEncryption_; */
      /*
      return new AlgorithmID("1.2.840.113549.1.1.5",
                             "sha1WithRSAEncryption",
                             "SHA/RSA");

    } */

    /**
    * Returns the IAIK ObjectID for the underlying algorithm.
    */
/*    public ObjectID getAlgorithm () {
      return getIAIKAlgorithm().getAlgorithm();
    }

    public String getName() {
        return getIAIKAlgorithm().getAlgorithm().getName();
    }

    public String getImplementationName() {
        return "sha1WithRSAEncryption";
    }

    public Signature getSignatureInstance() throws NoSuchAlgorithmException
    {
        return (Signature)getInstance();
    }

    public Signature getSignatureInstance(String provider) throws NoSuchAlgorithmException
    {
        return (Signature)getInstance();
    } */

    public String toString() {
        return algString;
    }
}
