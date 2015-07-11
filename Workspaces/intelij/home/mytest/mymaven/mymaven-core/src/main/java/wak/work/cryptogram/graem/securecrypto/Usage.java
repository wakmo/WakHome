package wak.work.cryptogram.graem.securecrypto;

/** Provides all the acceptable types of Usage.
 * @author Richard Izzard
 * @version 1.0
 */

import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyUsages;

public class Usage implements java.io.Serializable
{
    private static final String[] description = {"BDK", "ECK", "DCK", "KDK", "TRK", "VEK"};

    private static final int MAX_INDEX = SCIKeyUsages.VEK;

    /**
     * Usage type for Bi-directional keys.
     */
    public static final Usage BDK = new Usage(SCIKeyUsages.BDK);

    /**
     * Usage type for Encryption keys.
     */
    public static final Usage ECK = new Usage(SCIKeyUsages.ECK);

    /**
     * Usage type for Decryption keys.
     */
    public static final Usage DCK = new Usage(SCIKeyUsages.DCK);

    /**
     * Usage type for Key Derivation keys.
     */
    public static final Usage KDK = new Usage(SCIKeyUsages.KDK);

    /**
     * Usage type for Transport keys.
     */
    public static final Usage TRK = new Usage(SCIKeyUsages.TRK);

    /**
     * Usage type for Verification keys.
     */
    public static final Usage VEK = new Usage(SCIKeyUsages.VEK);


    private int usage;

    private Usage(int usage)
    {
        this.usage = usage;
    }

    /**
     * Gets a description of the Usage.
     *
     * @return A string representation of Usage.
     */
    public String toString()
    {
        return description[usage];
    }

    int value()
    {
        return usage;
    }

    public static Usage getUsage(String usage) throws SecureCryptoException
    {
        int index = -1;
        for (int f = 0; f <= MAX_INDEX && index == -1; f++)
        {
            if (description[f].equals(usage))
            {
                index = f;
            }
        }
        return getUsage(index);
    }

    public static Usage getUsage(int index) throws SecureCryptoException
    {
        Usage usage;

        switch (index)
        {
            case SCIKeyUsages.BDK:
                usage = BDK;
                break;
            case SCIKeyUsages.ECK:
                usage = ECK;
                break;
            case SCIKeyUsages.DCK:
                usage = DCK;
                break;
            case SCIKeyUsages.KDK:
                usage = KDK;
                break;
            case SCIKeyUsages.TRK:
                usage = TRK;
                break;
            case SCIKeyUsages.VEK:
                usage = VEK;
                break;
            default:
                throw new SecureCryptoException("Undefined usage specified: " + index);
        }
        return usage;
    }

    public int toInt()
    {
        return usage;
    }
}
