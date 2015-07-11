/*
 *
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SCIHashAlgorithm.java %
 *  Created:	 Thu Nov 13 21:51:08 2003
 *  Created By:	 %created_by:  jakatir %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  SCIHashAlgorithm.java~INPMA#4:java:UKPMA#1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */

/*
 * HashAlgorithm.java
 *
 * Created on 02 November 2001, 09:39
 */

package wak.work.cryptogram.graem.securecrypto.generickeyvalue;

/**
 * Enumerated type that defines all the accceptable hash algorithms.
 *
 * @author Richard Izzard
 * @version 1.0
 */
public class SCIHashAlgorithm
{

    private static final String CBC_HEADER = "CBC_";
    private static final String[] hashAlgs = new String[]{"NO_HASH",
            "SHA1",
            "AHASH",
            "MD5",
            "RIPEMD160",
            "ISO_10118-2",
            "MULTOS4"};
    /*,
                                                              "X9.9_M1MAC4",
                                                              "X9.9_M2MAC4",
                                                              "X9.9_M1MAC8",
                                                              "X9.9_M2MAC8"};*/

    private static final int NO_HASH_IDX = 0;
    private static final int SHA1_IDX = 1;
    private static final int AHASH_IDX = 2;
    private static final int MD5_IDX = 3;
    private static final int RIPEMD160_IDX = 4;
    private static final int ISO_10118_2_IDX = 5;
    private static final int MULTOS4_IDX = 6;

/*
	private static final int X9_9_M1MAC4_IDX  = 6;
	private static final int X9_9_M2MAC4_IDX  = 7;
	private static final int X9_9_M1MAC8_IDX  = 8;
	private static final int X9_9_M2MAC8_IDX  = 9;
*/

    /**
     * Represents NO HASH hashing algorithm.
     */
    public static final SCIHashAlgorithm NO_HASH = new SCIHashAlgorithm(NO_HASH_IDX);
    /**
     * Represents SHA 1 hashing algorithm.
     */
    public static final SCIHashAlgorithm SHA1 = new SCIHashAlgorithm(SHA1_IDX);
    /**
     * Represents AHASH hashing algorithm.
     */
    public static final SCIHashAlgorithm AHASH = new SCIHashAlgorithm(AHASH_IDX);
    /**
     * Represents MD5 hashing algorithm.
     */
    public static final SCIHashAlgorithm MD5 = new SCIHashAlgorithm(MD5_IDX);
    /**
     * Represents RIPEMD160 hashing algorithm.
     */
    public static final SCIHashAlgorithm RIPEMD160 = new SCIHashAlgorithm(RIPEMD160_IDX);
    /**
     * Represents ISO 10118-2 hashing algorithm.
     */
    public static final SCIHashAlgorithm ISO10118_2 = new SCIHashAlgorithm(ISO_10118_2_IDX);
    /**
     * Represents MULTOS4 hashing algorithm.
     */
    public static final SCIHashAlgorithm MULTOS4 = new SCIHashAlgorithm(MULTOS4_IDX);

    /** Represents X9.9M1MAC4 hashing algorithm.
     */
//	  public static final SCIHashAlgorithm X9_9M1MAC4 = new SCIHashAlgorithm(X9_9_M1MAC4_IDX);
    /** Represents X9.9M2MAC4 hashing algorithm.
     */
//	  public static final SCIHashAlgorithm X9_9M2MAC4 = new SCIHashAlgorithm(X9_9_M2MAC4_IDX);
    /** Represents X9.9M1MAC8 hashing algorithm.
     */
//	  public static final SCIHashAlgorithm X9_9M1MAC8 = new SCIHashAlgorithm(X9_9_M1MAC8_IDX);
    /**
     * Represents X9.9M2MAC8 hashing algorithm.
     */
//	  public static final SCIHashAlgorithm X9_9M2MAC8 = new SCIHashAlgorithm(X9_9_M2MAC8_IDX);

    public static final int CBC_NO_PADDING = 0;
    public static final int CBC_PREFIX = 1;
    public static final int CBC_POSTFIX = 2;
    public static final int CBC_POSTFIX_COUNT = 3;

    public static final int CBC_SINGLE_DES_KEY = 1;
    public static final int CBC_DOUBLE_DES_KEY = 2;

    public static final int CBC_MAC_MS4BYTES = 4;
    public static final int CBC_MAC_LASTBLOCK = 8;

    private int index = -1;
    private int padding = -1;
    private int keySize = -1;
    private int mac = -1;

    /**
     * Creates new SCIHashAlgorithm
     */
    private SCIHashAlgorithm(int index)
    {
        this.index = index;
    }

    public SCIHashAlgorithm(int padding, int keySize, int mac) throws SCIKeyException
    {
        if (padding < CBC_NO_PADDING || padding > CBC_POSTFIX_COUNT)
        {
            throw new SCIKeyException("Invalid CBC padding mode specified", null);
        }
        if (keySize != CBC_SINGLE_DES_KEY && keySize != CBC_DOUBLE_DES_KEY)
        {
            throw new SCIKeyException("Invalid CBC key size specified", null);
        }
        if (mac != CBC_MAC_MS4BYTES && mac != CBC_MAC_LASTBLOCK)
        {
            throw new SCIKeyException("Invalid CBC key size specified", null);
        }
        this.padding = padding;
        this.keySize = keySize;
        this.mac = mac;
    }

    /**
     * Get a string representation of the hashing algorithm.
     *
     * @return Hashing algorithm.
     */
    public String toString()
    {
        String hAlg = null;
        if (index != -1)
        {
            hAlg = hashAlgs[index];
        }
        else
        {
            hAlg = CBC_HEADER + "0" + padding + "0" + keySize + "0" + mac;
        }
        return hAlg;
    }

    /*
        public boolean isX9_9 () {
            return toString().indexOf("X9.9")>-1;
        }
    */
    public boolean isCBC()
    {
        return toString().indexOf(CBC_HEADER) > -1;
    }

    public static SCIHashAlgorithm getHashAlgorithm(String alg)
    {
        SCIHashAlgorithm hAlg = null;
        for (int f = 0; f < hashAlgs.length && hAlg == null; f++)
        {
            if (alg.equals(hashAlgs[f]))
            {
                switch (f)
                {
                    case NO_HASH_IDX:
                        hAlg = NO_HASH;
                        break;
                    case SHA1_IDX:
                        hAlg = SHA1;
                        break;
                    case AHASH_IDX:
                        hAlg = AHASH;
                        break;
                    case MD5_IDX:
                        hAlg = MD5;
                        break;
                    case RIPEMD160_IDX:
                        hAlg = RIPEMD160;
                        break;
                    case MULTOS4_IDX:
                        hAlg = MULTOS4;
                        break;
/*
			case ISO_10118_2_IDX:
			  hAlg = ISO10118_2;
			break;
			case X9_9_M1MAC4_IDX:
			  hAlg = X9_9M1MAC4;
			break;
			case X9_9_M2MAC4_IDX:
			  hAlg = X9_9M2MAC4;
			break;
			case X9_9_M1MAC8_IDX:
			  hAlg = X9_9M1MAC8;
			break;
			case X9_9_M2MAC8_IDX:
			  hAlg = X9_9M2MAC8;
			break;
*/
                }
            }
        }
        if (hAlg == null && alg.startsWith(CBC_HEADER) && alg.length() == CBC_HEADER.length() + 6)
        {
            String cbcProperties = alg.substring(CBC_HEADER.length());

            int p = Integer.parseInt(cbcProperties.substring(1, 1));
            int k = Integer.parseInt(cbcProperties.substring(3, 3));
            int m = Integer.parseInt(cbcProperties.substring(5, 5));

            try
            {
                hAlg = new SCIHashAlgorithm(p, k, m);
            }
            catch (SCIKeyException scX)
            {
            }
        }

        return hAlg;
    }

    public int getPadding()
    {
        return padding;
    }

    public int getKeySize()
    {
        return keySize;
    }

    public int getMac()
    {
        return mac;
    }
}
