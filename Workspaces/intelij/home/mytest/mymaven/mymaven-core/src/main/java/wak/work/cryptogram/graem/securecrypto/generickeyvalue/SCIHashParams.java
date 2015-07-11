/*
 *
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SCIHashParams.java %
 *  Created:	 Thu Nov 13 21:47:56 2003
 *  Created By:	 %created_by:  jakatir %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  SCIHashParams.java~INPMA#3:java:UKPMA#1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */
/*
 *
 *  � 2001 Datacard Platform Seven Limited.  All rights reserved.
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SCIHashParams.java %
 *  Created:	 08 February 2002 11:20:01
 *  Created By:	 %created_by:  jakatir %
 *  Last modified:	 %date_modified:  15 February 2002 10:12:07 %
 *  CI Idenitifier:	 %full_filespec:  SCIHashParams.java~INPMA#3:java:UKPMA#1 %
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

package wak.work.cryptogram.graem.securecrypto.generickeyvalue;

/**
  * Encapsulates the information necessary to hash some data
  *
  * @author         %derived_by: jakatir %
  *
  *
  * @version 		%version:  INPMA#3 %
  * @since
  * @see
  *
  */

public class SCIHashParams {

	private SCIHashAlgorithm alg;
	private SCIKey hashKey;

	/**
	 * Specifies No hash. This does not contain any key information.
	 */
	public static final SCIHashParams NO_HASH    = new SCIHashParams(SCIHashAlgorithm.NO_HASH);
	/**
	 * Specifies SHA1. This does not contain any key information.
	 */
	public static final SCIHashParams SHA1       = new SCIHashParams(SCIHashAlgorithm.SHA1);
	/**
	 * Specifies MD5. This does not contain any key information.
	 */
	public static final SCIHashParams MD5        = new SCIHashParams(SCIHashAlgorithm.MD5);
	/**
	 * Specifies RIPEMD160. This does not contain any key information.
	 */
	public static final SCIHashParams RIPEMD160  = new SCIHashParams(SCIHashAlgorithm.RIPEMD160);
	/**
	 * Specifies ISO10118-2. This does not contain any key information.
	 */
	public static final SCIHashParams ISO10118_2 = new SCIHashParams(SCIHashAlgorithm.ISO10118_2);
	/**
	 * Specifies MULTOS4. This does not contain any key information.
	 */
    public static final SCIHashParams MULTOS4 = new SCIHashParams(SCIHashAlgorithm.MULTOS4);

	public SCIHashParams() { }

	/** Creates new SCIHashParams */
	private SCIHashParams(SCIHashAlgorithm alg) {
		this.alg = alg;
	}

	/**
	 * Creates new SCIHashParams. This constructor is only to be used when constructing HashParam
	 * objects which require hashing keys. A valid non null key must be supplied, SCIHashParams
	 * with no key data are predefined.
	 */
	public SCIHashParams(SCIHashAlgorithm alg, SCIKey key) throws SCIKeyException {
	  if (key==null) {
		throw new SCIKeyException("A valid key must be supplied", null);
	  }
	  if (SCIHashAlgorithm.NO_HASH==alg) {
		throw new SCIKeyException("NO hash doesn't require a key", null);
	  }
	  if (SCIHashAlgorithm.AHASH==alg && !(key.getKeyType() == SCIKeyTypes.AUTHENTIC)){
		throw new SCIKeyException("AHASH must have an authentic key", null);
	  }
	  if (alg.isCBC() && !(key.getKeyType() == SCIKeyTypes.PROTECTED)){
		if (SCIKeyUsages.ECK!=key.getEncodingUsage()){
		  throw new SCIKeyException("CBC hash algorithms must have an enciphering key", null);
		}
		if (SCIHashAlgorithm.CBC_SINGLE_DES_KEY==alg.getKeySize() && SCIKeyAlgorithms.DES1E!=key.getEncodingAlgorithm()){
		  throw new SCIKeyException("Single length key must be supplied", null);
		}
		if (SCIHashAlgorithm.CBC_DOUBLE_DES_KEY==alg.getKeySize() && SCIKeyAlgorithms.DES2EDE!=key.getEncodingAlgorithm()){
		  throw new SCIKeyException("Double length key must be supplied", null);
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

	/**
	 * Retreive the hash algorithm.
	 */
	public SCIHashAlgorithm getHashAlgorithm() {
		return alg;
	}

	/**
	 * Retreive the hashing key if any.
	 */
	public SCIKey getKey() {
	   return hashKey;
	}
}
