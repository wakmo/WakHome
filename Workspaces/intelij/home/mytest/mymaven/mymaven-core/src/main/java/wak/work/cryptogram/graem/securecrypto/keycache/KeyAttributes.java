package wak.work.cryptogram.graem.securecrypto.keycache;

import wak.work.cryptogram.graem.securecrypto.*;

import java.util.Arrays;
import wak.work.cryptogram.helper.ByteArrayUtilities;


/**
 * User: growles
 * Date: 08-Feb-2012
 * Time: 10:26:57
 */
public class KeyAttributes
{
    //The public key exponent as a byte array (big endian)
    private final byte[] publicExp;

    //Size of the modulus in bits
    private final int keySize;

    //Key Usage
    private final Algorithm algorithm;
    private final Usage usage;

    private final String smType;
    private final String mkId;
    private final String additionalData;


    private final String shortDescription;


    //additional = HSM specific, e.g. PKIKeyType for Thales 9000
    public KeyAttributes(byte[] publicExp, int keySize, KeyUsage keyUsage,
                         String smType, String mkId, String additionalData) throws SecureCryptoException
    {
        checkNotNull("Public Exponent", publicExp);
        checkNotNull("keyUsage", keyUsage);

        this.publicExp = publicExp;
        this.keySize = keySize;
        this.algorithm = keyUsage.getAlgorithm();
        this.usage = keyUsage.getUsage();

        checkNotNull("keyUsage.algorithm", this.algorithm);
        checkNotNull("keyUsage.usage", this.usage);

        this.smType = (smType == null)? "" : smType;
        this.mkId = (mkId == null)? "" : mkId;
        this.additionalData = (additionalData == null)? "" : additionalData;

        String publicExpStr = ByteArrayUtilities.stringify_nospaces(publicExp);

        shortDescription = String.format("%d:%s:%s:%s:%s:%s:%s", keySize, algorithm, usage, publicExpStr,
                this.smType, this.mkId, this.additionalData).toUpperCase();
    }

    public boolean matches(KeyAttributes otherAttribs)
    {
        return Arrays.equals(publicExp, otherAttribs.publicExp)
                && keySize == otherAttribs.keySize
                && algorithm.equals(otherAttribs.algorithm)
                && usage.equals(otherAttribs.usage)
                && equalsIfNotNull(smType, otherAttribs.smType)
                && equalsIfNotNull(mkId, otherAttribs.mkId)
                && equalsIfNotNull(additionalData, otherAttribs.additionalData);
    }

    public byte[] getPublicExp()
    {
        return publicExp;
    }

    public int getKeySize()
    {
        return keySize;
    }

    public Algorithm getAlgorithm()
    {
        return algorithm;
    }

    public Usage getUsage()
    {
        return usage;
    }

    public String getSmType()
    {
        return smType;
    }

    public String getMkId()
    {
        return mkId;
    }

    public String getAdditionalData()
    {
        return additionalData;
    }

    public String getShortString()
    {
        return shortDescription;
    }

    public KeyUsage getKeyUsage()
    {
        return new KeyUsage(algorithm, usage);
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("keySize: ").append(keySize);
        sb.append(", keyUsage: alg=").append(algorithm).append(", usage=").append(usage);
        sb.append(", publicExp: ").append(ByteArrayUtilities.stringify_nospaces(publicExp));
        sb.append(", smType=").append(smType);
        sb.append(", mkId=").append(mkId);
        sb.append(", additionalData=").append(additionalData);

        return sb.toString();
    }

    private static boolean equalsIfNotNull(String myValue, String theirValue)
    {
        return (theirValue == null) || theirValue.equals(myValue);
    }

    private static void checkNotNull(String paramName, Object value) throws SecureCryptoException
    {
        if (value == null)
        {
            throw new SecureCryptoException(paramName + " is null");
        }
    }
}
