package wak.work.cryptogram.graem.securecrypto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: graeme.rowles
 * Date: 04/02/13
 * Time: 09:55
 */
public class MacParams implements Serializable
{
    public enum MacType
    {
        //ISO 9797 MAC algorithm 1 (= ANSI X9.9 when used with a single length key)
        //This MAC single-DES encrypts all the blocks, and triple-DES encrypts the last
        RETAIL_MAC,

        //ISO 9797 MAC algorithm 3 (= ANSI X9.9 when used with a double length key)
        //This MAC triple-DES encrypts all the blocks
        WHOLESALE_MAC
    }

    private final Key macKey;
    private final MacType macType;
    private final byte[] iv;

    public MacParams(Key macKey, MacType macType, byte[] iv)
    {
        this.macKey = macKey;
        this.macType = macType;
        this.iv = iv;
    }

    public MacParams(Key macKey, MacType macType)
    {
        this(macKey, macType, null);
    }

    public Key getMacKey()
    {
        return macKey;
    }

    public MacType getMacType()
    {
        return macType;
    }

    public byte[] getIv()
    {
        return iv;
    }

    public boolean containsIv()
    {
        boolean ivPresent = false;
        if (iv != null)
        {
            for (byte b : iv)
            {
                ivPresent |= (b != 0);
            }
        }

        return ivPresent;
    }
}
