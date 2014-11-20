package wak.work.cryptogram.helper;

/**
 * Provides all the acceptable cipher modes.
 *
 * @author Richard Izzard
 * @version 1.0
 */

public class CipherMode implements java.io.Serializable
{
    private static final String ECB_MODE = "ECB";
    private static final String CBC_MODE = "CBC";

    /**
     * ECB cipher mode object
     */
    public static final CipherMode ECB = new CipherMode(ECB_MODE);
    /**
     * CBC cipher mode object
     */
    public static final CipherMode CBC = new CipherMode(CBC_MODE);


    private String mode;

    private CipherMode(String mode)
    {
        this.mode = mode;
    }

    /**
     * Retrieve String representation of the cipher mode.
     *
     * @return Cipher mode as string.
     */
    public String toString()
    {
        return mode;
    }

    public static CipherMode getCipherMode(String mode)
    {
        CipherMode cm = null;

        if (mode.equals(ECB_MODE))
        {
            cm = ECB;
        }
        else if (mode.equals(CBC_MODE))
        {
            cm = CBC;
        }

        return cm;
    }

    public boolean equals(CipherMode cipherMode)
    {
        return mode.equals(cipherMode.mode);
    }
}