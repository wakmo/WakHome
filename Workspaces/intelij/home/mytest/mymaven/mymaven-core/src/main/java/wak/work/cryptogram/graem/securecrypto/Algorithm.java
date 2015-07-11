package wak.work.cryptogram.graem.securecrypto;

/** This class provides all the acceptable key algorithms that may be used.
 * @author Richard Izzard
 * @version 1.0
 */

import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyAlgorithms;

import java.io.Serializable;

public class Algorithm implements Serializable
{
    private static final int START_DES = SCIKeyAlgorithms.DES1E;
    private static final int END_DES = SCIKeyAlgorithms.DES3EEE;

    //private static final int MAX_INDEX = SCIKeyAlgorithms.RSA_NONCRT;

    /**
     * Single length DES encryption.
     */
    public static final Algorithm DES1E = new Algorithm(SCIKeyAlgorithms.DES1E);

    /**
     * Single length DES decryption.
     */
    public static final Algorithm DES1D = new Algorithm(SCIKeyAlgorithms.DES1D);

    /**
     * Double length DES encryption, decryption, encryption.
     */
    public static final Algorithm DES2EDE = new Algorithm(SCIKeyAlgorithms.DES2EDE);

    /**
     * Double length DES encryption, encryption, encryption.
     */
    public static final Algorithm DES2EEE = new Algorithm(SCIKeyAlgorithms.DES2EEE);

    /**
     * Double length DES decryption, decryption, decryption.
     */
    public static final Algorithm DES2DDD = new Algorithm(SCIKeyAlgorithms.DES2DDD);

    /**
     * Triple length DES encryption, decryption, encryption.
     */
    public static final Algorithm DES3EDE = new Algorithm(SCIKeyAlgorithms.DES3EDE);

    /**
     * Triple length DES encryption, encryption, encryption.
     */
    public static final Algorithm DES3EEE = new Algorithm(SCIKeyAlgorithms.DES3EEE);

    /**
     * RSA.
     */
    public static final Algorithm RSA = new Algorithm(SCIKeyAlgorithms.RSA);//added to aid asymmetric key generation using software sce
    public static final Algorithm RSA_ALL = new Algorithm(SCIKeyAlgorithms.RSA_ALL);

    /**
     * RSA CRT.
     */
    public static final Algorithm RSA_CRT = new Algorithm(SCIKeyAlgorithms.RSA_CRT);

    /**
     * RSA NON CRT.
     */
    public static final Algorithm RSA_MODEXP = new Algorithm(SCIKeyAlgorithms.RSA_MODEXP);

    
    private int alg;

    private Algorithm(int alg)
    {
        this.alg = alg;
    }

    /**
     * Gets a description of the algorithm.
     *
     * @return String representation of the algorithm.
     */
    public String toString()
    {
        return SCIKeyAlgorithms.names[alg];
    }

    public boolean isRSA()
    {
        return (alg >= SCIKeyAlgorithms.RSA && alg <= SCIKeyAlgorithms.RSA_MODEXP);//added to aid asymmetric key generation using software sce
    }

    /**
     * Identify if the algorithm is any type of DES.
     *
     * @return True if the algorithm is one of the following types:
     *         DES1E
     *         DES1D
     *         DES2EDE
     *         DES2EEE
     *         DES2DDD
     *         DES3EDE
     *         DES3EEE
     */
    public boolean isDES()
    {
        return (alg >= START_DES && alg <= END_DES);
    }

    /**
     * Identify if the algorithm is any type of symmetric algorithm.
     * Initially this method returns true under the same circumstances as the isDES method
     * however this situation may not be true in future extensions.
     *
     * @return True if the algorithm is one of the following types:
     *         DES1E
     *         DES1D
     *         DES2EDE
     *         DES2EEE
     *         DES2DDD
     *         DES3EDE
     *         DES3EEE
     */
    public boolean isSymmetric()
    {
        return isDES();
    }

    /**
     * Identify if the algorithm is any single length DES.
     *
     * @return True if the algorithm is one of the following types:
     *         DES1E
     *         DES1D
     */
    public boolean isSingleDES()
    {
        return (alg >= SCIKeyAlgorithms.DES1E && alg <= SCIKeyAlgorithms.DES1D);
    }

    /**
     * Identify if the algorithm is any double length DES.
     *
     * @return True if the algorithm is one of the following types:
     *         DES2EDE
     *         DES2EEE
     *         DES2DDD
     */
    public boolean isDoubleDES()
    {
        return (alg >= SCIKeyAlgorithms.DES2EDE && alg <= SCIKeyAlgorithms.DES2DDD);
    }

    /**
     * Identify if the algorithm is any triple length DES.
     *
     * @return True if the algorithm is one of the following types:
     *         DES3EDE
     *         DES3EEE
     */
    public boolean isTripleDES()
    {
        return (alg >= SCIKeyAlgorithms.DES3EDE && alg <= SCIKeyAlgorithms.DES3EEE);
    }

    public static Algorithm getAlgorithm(String alg) throws SecureCryptoException
    {
        int index = -1;
        for (int f = 0; f < SCIKeyAlgorithms.names.length && index == -1; f++)
        {
            if (SCIKeyAlgorithms.names[f].equals(alg))
            {
                index = f;
            }
        }
        return getAlgorithm(index);
    }

    public static Algorithm getAlgorithm(int index) throws SecureCryptoException
    {
        Algorithm alg = null;
        switch (index)
        {
            case SCIKeyAlgorithms.DES1E:
                alg = DES1E;
                break;
            case SCIKeyAlgorithms.DES1D:
                alg = DES1D;
                break;
            case SCIKeyAlgorithms.DES2EDE:
                alg = DES2EDE;
                break;
            case SCIKeyAlgorithms.DES2EEE:
                alg = DES2EEE;
                break;
            case SCIKeyAlgorithms.DES2DDD:
                alg = DES2DDD;
                break;
            case SCIKeyAlgorithms.DES3EDE:
                alg = DES3EDE;
                break;
            case SCIKeyAlgorithms.DES3EEE:
                alg = DES3EEE;
                break;
            case SCIKeyAlgorithms.RSA://added to aid asymmetric key generation using software sce
                alg = RSA;
                break;
            case SCIKeyAlgorithms.RSA_ALL:
                alg = RSA_ALL;
                break;
            case SCIKeyAlgorithms.RSA_CRT:
                alg = RSA_CRT;
                break;
            case SCIKeyAlgorithms.RSA_MODEXP:
                alg = RSA_MODEXP;
                break;
            default:
                throw new SecureCryptoException("Undefined algorithm specified: " + index);
        }
        return alg;
    }

    public int toInt()
    {
        return alg;
    }

    public boolean equals(Object alg)
    {
        boolean equal = false;
        if (alg != null && alg instanceof Algorithm)
        {
            Algorithm thatAlg = (Algorithm)alg;
            equal = (thatAlg.alg == this.alg);
        }

        return equal;
    }
}
