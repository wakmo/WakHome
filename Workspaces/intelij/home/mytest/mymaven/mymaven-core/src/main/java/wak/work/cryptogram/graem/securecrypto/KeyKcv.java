package wak.work.cryptogram.graem.securecrypto;


import java.io.Serializable;
import wak.work.cryptogram.helper.ByteArrayUtilities;

/**
 * @author andreas.heilemann
 *         As SciKey, Key, etc. do NOT care about key check values:
 *         Here is a new data structure to hold key data and corresponding KCV
 */
public class KeyKcv implements Serializable
{
    private static final long serialVersionUID = -2974228993280342993L;

    private final byte[] keyData;
    private final byte[] kcv;

    public KeyKcv(byte[] keyData, byte[] kcv)
    {
        this.keyData = keyData;
        this.kcv = kcv;
    }

    public byte[] getKeyData()
    {
        return keyData;
    }

    public byte[] getKcv()
    {
        return kcv;
    }

    public String toString()
    {
        StringBuilder buff = new StringBuilder();
        buff.append(keyData == null ? "null" : ByteArrayUtilities.stringify_nospaces(keyData));
        buff.append("[");
        buff.append(kcv == null ? "null" : ByteArrayUtilities.stringify_nospaces(kcv));
        buff.append("]");

        return buff.toString();
    }
}
