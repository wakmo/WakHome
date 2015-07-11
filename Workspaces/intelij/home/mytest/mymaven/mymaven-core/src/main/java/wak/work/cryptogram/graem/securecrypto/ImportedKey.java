package wak.work.cryptogram.graem.securecrypto;

/**
 * User: growles
 * Date: 27-Oct-2011
 * Time: 13:34:59
 */
public class ImportedKey
{
    private Key key;
    private String keyCV;

    public ImportedKey(Key key, String keyCV)
    {
        this.key = key;
        this.keyCV = keyCV;
    }

    public Key getKey()
    {
        return key;
    }

    public String getKeyCV()
    {
        return keyCV;
    }
}
