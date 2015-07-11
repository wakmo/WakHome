package wak.work.cryptogram.graem.securecrypto.generickeyvalue;


import java.io.Serializable;
import wak.work.cryptogram.helper.ByteArrayUtilities;

/**
 * These object are stored in the Affina database
 */
public class SCIKeyValue implements SCIKey, Serializable
{
    static final long serialVersionUID = 6202639849030947154L;

    private final byte[] mkIdentifier;
    private final String smType;

    private final SCIKeyUsage[] keyUsage;
    private final int component;
    private final int keyType;
    private final int keySize;
    private final int cryptoEngineVersion;
    private final byte[] smExtension;
    private final byte[] keyValue;


    public SCIKeyValue(byte[] mkIdentifier,
                       String smType,
                       SCIKeyUsage[] keyUsage,
                       int component,
                       int keyType,
                       int keySize,
                       int cryptoEngineVersion,
                       byte[] smExtension,
                       byte[] keyValue)
    {
        this.mkIdentifier = mkIdentifier;
        this.smType = smType;
        this.keyUsage = keyUsage;
        this.component = component;
        this.keyType = keyType;
        this.keySize = keySize;
        this.cryptoEngineVersion = cryptoEngineVersion;
        this.smExtension = smExtension;
        this.keyValue = keyValue;
    }

    public boolean isValid()
    {
        // check the validity of the key value
        boolean valid = SCISecureModules.isValid(smType);

        SCIKeyUsage[] usages = getKeyUsage();
        valid &= (usages.length > 0);
        
        for (SCIKeyUsage usage : usages)
        {
            valid &= SCIKeyAlgorithms.isValid(usage.getAlgorithm());
            valid &= SCIKeyUsages.isValid(usage.getUsage());
        }

        valid &= SCIKeyTypes.isValid(keyType);
        valid &= SCIKeyComponents.isValid(component);
        valid &= (keyValue.length > 0);

        return valid;
    }

    public byte[] getMKIdentifier()
    {
        return mkIdentifier;
    }

    public String getSMType()
    {
        return smType;
    }

    public final SCIKeyUsage[] getKeyUsage()
    {
        // returns a copy of it do don't risk client modifying the array
        SCIKeyUsage[] retVal = new SCIKeyUsage[keyUsage.length];
        System.arraycopy(keyUsage, 0, retVal, 0, keyUsage.length);
        return retVal;
    }

    public int getAlgorithm()
    {
        return keyUsage[0].getAlgorithm();
    }

    public int getEncodingAlgorithm()
    {
        return getAlgorithm();
    }

    public int getEncodingUsage()
    {
        return keyUsage[0].getUsage();
    }

    public int getComponent()
    {
        return component;
    }

    public int getKeyType()
    {
        return keyType;
    }

    public int getKeySize()
    {
        return keySize;
    }

    public int getCryptoEngineVersion()
    {
        return cryptoEngineVersion;
    }

    public byte[] getSMExtension()
    {
        return smExtension;
    }

    public byte[] getKeyValue()
    {
        return keyValue;
    }

    public String toString()
    {
        StringBuffer ret = new StringBuffer();
        ret.append("SCI Key Value description:\n");
        ret.append("\tSecure Module Type: ").append(smType).append("\n");
        ret.append("\tMk Identifier: ").append(ByteArrayUtilities.stringify_nospaces(mkIdentifier)).append("\n");
        ret.append("\tKey Algorithm: ").append(SCIKeyAlgorithms.translate(getAlgorithm())).append("\n");
        ret.append("\tComponent: ").append(SCIKeyComponents.translate(getComponent())).append("\n");
        ret.append("\tKey Type: ").append(SCIKeyTypes.translate(keyType)).append("\n");
        ret.append("\tKey Size: ").append(keySize).append("\n");
        ret.append("\tKey Value : ").append(ByteArrayUtilities.stringify_nospaces(keyValue)).append("\n");
        ret.append("\tKey Secure Module Extension : ").append(ByteArrayUtilities.stringify_nospaces(smExtension)).append("\n");
        for (int x = 0; x < getKeyUsage().length; x++)
        {
            ret.append("\tUsage ").append(x).append(":");
            ret.append(" Algorithm: ").append(SCIKeyAlgorithms.translate(getKeyUsage()[x].getAlgorithm()));
            ret.append(" Usage: ").append(SCIKeyUsages.translate(getKeyUsage()[x].getUsage())).append("\n");
        }
        return ret.toString();
    }
}
