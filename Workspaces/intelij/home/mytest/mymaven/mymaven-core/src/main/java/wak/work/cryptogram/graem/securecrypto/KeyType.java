package wak.work.cryptogram.graem.securecrypto;

/**
 * Note that this class replaces "Format" from SCI-R1.0.5 onwards.
 */

import java.io.Serializable;

import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyTypes;

public class KeyType implements Serializable
{
    private static final long serialVersionUID = -955874238051089649L;

	private static final String[] formatDescriptions = {"PROTECTED", "AUTHENTIC"};

    //Private key
    public static final KeyType PROTECTED = new KeyType(SCIKeyTypes.PROTECTED);

    //Public key
    public static final KeyType AUTHENTIC = new KeyType(SCIKeyTypes.AUTHENTIC);

    private final int formatIndex; 

    protected KeyType(int index)
    {
        this.formatIndex = index;
    }

    @Override
    public String toString()
    {
        return formatDescriptions[formatIndex];
    }

    public static KeyType getKeyType(String format) throws SecureCryptoException
    {
        int index = -1;
        for (int f = 0; (f < formatDescriptions.length) && (index == -1); f++)
        {
            if (formatDescriptions[f].equals(format))
            {
                index = f;
            }
        }
        return getKeyType(index);
    }

    public static KeyType getKeyType(int index) throws SecureCryptoException
    {
        KeyType keyType;
        
        switch (index)
        {
            case SCIKeyTypes.PROTECTED:
                keyType = PROTECTED;
                break;

            case SCIKeyTypes.AUTHENTIC:
                keyType = AUTHENTIC;
                break;
            
            default:
                throw new SecureCryptoException("Undefined format specified: " + index);
        }
        return keyType;
    }

    public int toInt()
    {
        return formatIndex;
    }
}
