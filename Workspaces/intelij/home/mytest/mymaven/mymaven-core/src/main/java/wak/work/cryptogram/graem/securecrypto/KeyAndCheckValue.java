package wak.work.cryptogram.graem.securecrypto;

import java.io.Serializable;

/**
 * User: growles
 * Date: 28-Oct-2011
 * Time: 17:02:07
 */
public class KeyAndCheckValue implements Serializable
{
    private final String keyValue;
    private final String checkValue;

    public KeyAndCheckValue(String keyValue, String checkValue)
    {
        this.keyValue = keyValue;
        this.checkValue = checkValue;
    }

    public String getKeyValue()
    {
        return keyValue;
    }

    public String getCheckValue()
    {
        return checkValue;
    }
}
