/*

 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   HashParams.java %
 *  Created:	 08 February 2002 11:20:01
 *  Created By:	 %created_by:  jakatir %
 *  Created By:	 %created_by:  jakatir %
 *  Last modified:	 %date_modified:  15 February 2002 10:12:07 %
 *  CI Idenitifier:	 %full_filespec:  HashParams.java~INPMA#7:java:UKPMA#1 %
 *  CI Idenitifier:	 %full_filespec:  HashParams.java~INPMA#7:java:UKPMA#1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */

/*
 * HashParams.java
 *
 * Created on 06 February 2002, 13:01
 */

package wak.work.cryptogram.graem.securecrypto;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
/**
  * Encapsulates the information necessary to hash some data
  *
  * @author         %derived_by: jakatir %
  * @author         %derived_by: jakatir %
  *
  *
  * @version 		%version:  INPMA#7 %
  * @version 		%version:  INPMA#7 %
  * @since
  * @see
  *
  */

public class HashParams implements Externalizable {

    private HashAlgorithm alg;
    private Key hashKey;

    /**
     * Specifies No hash. This does not contain any key information.
     */
    public static final HashParams NO_HASH    = new HashParams(HashAlgorithm.NO_HASH);
    /**
     * Specifies SHA1. This does not contain any key information.
     */
    public static final HashParams SHA1       = new HashParams(HashAlgorithm.SHA1);
    /**
     * Specifies MD5. This does not contain any key information.
     */
    public static final HashParams MD5        = new HashParams(HashAlgorithm.MD5);
    /**
     * Specifies RIPEMD160. This does not contain any key information.
     */
    public static final HashParams RIPEMD160  = new HashParams(HashAlgorithm.RIPEMD160);
    /**
     * Specifies ISO10118-2. This does not contain any key information.
     */
    public static final HashParams ISO10118_2 = new HashParams(HashAlgorithm.ISO10118_2);
	/**
	     * Specifies MULTOS4. This does not contain any key information.
	     */
    public static final HashParams MULTOS4 = new HashParams(HashAlgorithm.MULTOS4);

    public HashParams() { }

    /** Creates new HashParams */
    public HashParams(HashAlgorithm alg) {
        this.alg = alg;
    }

    /**
     * Creates new HashParams. This constructor is only to be used when constructing HashParam
     * objects which require hashing keys. A valid non null key must be supplied, HashParams
     * with no key data are predefined.
     */
    public HashParams(HashAlgorithm alg, Key key) throws SecureCryptoException {
      if (key==null) {
        throw new SecureCryptoException("A valid key must be supplied");
      }
      if (HashAlgorithm.NO_HASH==alg) {
        throw new SecureCryptoException("Key cannot be used with no-hash algorithm");
      }
      if ((HashAlgorithm.AHASH==alg) && !(key instanceof AuthenticKey)){
        throw new SecureCryptoException("AHASH must have an authentic key");
      }
      if (alg.isCBC() && !(key instanceof ProtectedKey)){
        if (Usage.ECK!=key.getEncodingUsage()){
          throw new SecureCryptoException("CBC hash algorithms must have an enciphering key");
        }
        if ((HashAlgorithm.CBC_SINGLE_DES_KEY==alg.getKeySize()) && (Algorithm.DES1E!=key.getEncodingAlgorithm())){
          throw new SecureCryptoException("Single length key must be supplied");
        }
        if ((HashAlgorithm.CBC_DOUBLE_DES_KEY==alg.getKeySize()) && (Algorithm.DES2EDE!=key.getEncodingAlgorithm())){
          throw new SecureCryptoException("Double length key must be supplied");
        }
      }
      /*
      if (alg.isX9_9() && !(key instanceof ProtectedKey)){
        throw new SecureCryptoException("X9.9 must have a protected key");
      }
      */
      this.alg = alg;
      hashKey = key;
    }

	public static HashParams getHashParams(HashAlgorithm algo) throws SecureCryptoException {
			HashParams ret;
			if (algo == HashAlgorithm.NO_HASH) {
	            ret = NO_HASH;
            } else if (algo == HashAlgorithm.SHA1) {
	            ret = SHA1;
            } else if (algo == HashAlgorithm.MD5) {
	            ret = MD5;
            } else if (algo == HashAlgorithm.RIPEMD160) {
	            ret = RIPEMD160;
            } else if (algo == HashAlgorithm.ISO10118_2) {
	            ret = ISO10118_2;
            } else if (algo == HashAlgorithm.MULTOS4) {
	            ret = MULTOS4;
            } else {
	            throw new SecureCryptoException("Invalid algorithm: " + algo.toString());
            }
			return ret;
	}

    /**
     * Retreive the hash algorithm.
     */
    public HashAlgorithm getHashAlgorithm() {
        return alg;
    }

    /**
     * Retreive the hashing key if any.
     */
    public Key getKey() {
       return hashKey;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
      out.writeUTF(alg.toString());
      out.writeObject(hashKey);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      alg = HashAlgorithm.getHashAlgorithm(in.readUTF());
      hashKey = (Key) in.readObject();
    }
}
