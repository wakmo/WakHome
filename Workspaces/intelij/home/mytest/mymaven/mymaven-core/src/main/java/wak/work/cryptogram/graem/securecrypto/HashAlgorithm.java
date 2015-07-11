/*
 * HashAlgorithm.java
 *
 * Created on 02 November 2001, 09:39
 */

package wak.work.cryptogram.graem.securecrypto;

import java.io.Serializable;

/**
 * Enumerated type that defines all the acceptable hash algorithms.
 * // TODO redesign this whole class! Make enum!
 *
 * @author Richard Izzard
 * @version 1.0
 */
public final class HashAlgorithm implements Serializable
{
    private static final long serialVersionUID = 9024969441615094417L;

    private static final String[] hashAlgs = new String[]{"NO_HASH",
            "SHA1",
            "AHASH",
            "MD5",
            "RIPEMD160",
            "ISO_10118-2",
            "MULTOS4",
            "CBC_000108",
            "CBC_000208"
    };

    private static final int NO_HASH_IDX = 0;
    private static final int SHA1_IDX = 1;
    private static final int AHASH_IDX = 2;
    private static final int MD5_IDX = 3;
    private static final int RIPEMD160_IDX = 4;
    private static final int ISO_10118_2_IDX = 5;
    private static final int MULTOS4_IDX = 6;
    private static final int CBC_P0K1M8_IDX = 7;
    private static final int CBC_P0K2M8_IDX = 8;

    public static final int CBC_NO_PADDING = 0;
    public static final int CBC_PREFIX = 1;
    public static final int CBC_POSTFIX = 2;
    public static final int CBC_POSTFIX_COUNT = 3;

    public static final int CBC_SINGLE_DES_KEY = 1;
    public static final int CBC_DOUBLE_DES_KEY = 2;

    public static final int CBC_MAC_MS4BYTES = 4;
    public static final int CBC_MAC_LASTBLOCK = 8;

    private final int index;
    private final int padding;
    private final int keySize;
    private final int mac;

    /**
     * Represents NO HASH hashing algorithm.
     */
    public static final HashAlgorithm NO_HASH = new HashAlgorithm(NO_HASH_IDX);
    /**
     * Represents SHA 1 hashing algorithm.
     */
    public static final HashAlgorithm SHA1 = new HashAlgorithm(SHA1_IDX);
    /**
     * Represents AHASH hashing algorithm.
     */
    public static final HashAlgorithm AHASH = new HashAlgorithm(AHASH_IDX);
    /**
     * Represents MD5 hashing algorithm.
     */
    public static final HashAlgorithm MD5 = new HashAlgorithm(MD5_IDX);
    /**
     * Represents RIPEMD160 hashing algorithm.
     */
    public static final HashAlgorithm RIPEMD160 = new HashAlgorithm(RIPEMD160_IDX);
    /**
     * Represents ISO 10118-2 hashing algorithm.
     */
    public static final HashAlgorithm ISO10118_2 = new HashAlgorithm(ISO_10118_2_IDX);
    /**
     * Represents MULTOS4 hashing algorithm.
     */
    public static final HashAlgorithm MULTOS4 = new HashAlgorithm(MULTOS4_IDX);

    //Wholesale MAC
    public static final HashAlgorithm CBC_P0K1M8 = new HashAlgorithm(CBC_NO_PADDING, CBC_SINGLE_DES_KEY, CBC_MAC_LASTBLOCK);

    //Retail MAC
    public static final HashAlgorithm CBC_P0K2M8 = new HashAlgorithm(CBC_NO_PADDING, CBC_DOUBLE_DES_KEY, CBC_MAC_LASTBLOCK);


    //Constructor for non-CBC algorithms
    private HashAlgorithm(int index)
    {
        this.index = index;

        padding = -1;
        keySize = -1;
        mac = -1;
    }

    //Constructor for CBC algorithms
    private HashAlgorithm(int padding, int keySize, int mac)
    {
        index = -1;

        this.padding = padding;
        this.keySize = keySize;
        this.mac = mac;
    }

    /**
     * Get a string representation of the hashing algorithm.
     *
     * @return Hashing algorithm.
     */
    @Override
    public String toString()
    {
        String hAlg;
        if (isCBC())
        {
            hAlg = "CBC_0" + padding + "0" + keySize + "0" + mac;
        }
        else
        {
            hAlg = hashAlgs[index];
        }

        return hAlg;
    }


    public boolean isCBC()
    {
        return (index == -1);
    }

    public static HashAlgorithm getHashAlgorithm(String alg)
    {
        HashAlgorithm hAlg = null;
        for (int f = 0; (f < hashAlgs.length) && (hAlg == null); f++)
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
                    case CBC_P0K1M8_IDX:
                        hAlg = CBC_P0K1M8;
                        break;
                    case CBC_P0K2M8_IDX:
                        hAlg = CBC_P0K2M8;
                        break;
                }
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

    @Override
    public int hashCode()
    {
        return 31 + index;
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean equals = false;

        if (obj != null && obj instanceof HashAlgorithm)
        {
            HashAlgorithm thatHashAlg = (HashAlgorithm)obj;

            return thatHashAlg.index == index && thatHashAlg.padding == padding &&
                    thatHashAlg.keySize == keySize && thatHashAlg.mac == mac;
        }

        return equals;
    }
}
