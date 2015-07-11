package wak.work.cryptogram.graem.securecrypto;

import org.apache.log4j.Logger;

import java.io.*;
import wak.work.cryptogram.helper.ByteArrayUtilities;

/**
 * User: growles
 * Date: 17-Apr-2012
 * Time: 09:27:12
 */
public class RsaKeyGenOptions implements Externalizable
{
    private transient static final Logger log = Logger.getLogger(RsaKeyGenOptions.class);


    private KeyUsage keyUsage;
    private int keySize;
    private byte[] publicExponent;

    //Is the key being generated for a key cache?
    //We need to know this because only certain HSM connections should be used for key caching
    private boolean forKeyCache;

    //Optional
    private PKIKeyType pkiKeyType;

    //Use strong or standard primes when generating a key, or the SCI default?
    private PrimeStrength primeStrength;

    //Optional.
    //The SMType of the key that must be produced.  If null then the default key type will be used.
    private String smType;

    //Optional.
    //The MkIdentifier of the HSM that must be used to produce the key pair.  If null then an HSM with
    //any MkIdentifier will be used.
    private byte[] mkIdentifier;


    //For Externalizable interface
    public RsaKeyGenOptions()
    {
    }

    public RsaKeyGenOptions(KeyUsage keyUsage, int keySize, byte[] publicExponent) throws SecureCryptoException
    {
        this.keyUsage = keyUsage;
        this.keySize = keySize;
        this.publicExponent = publicExponent;

        if (keyUsage == null)
        {
            log.warn("keyUsage is null");
            throw new SecureCryptoException("keyUsage is null");
        }

        Algorithm alg = keyUsage.getAlgorithm();
        if (alg == null)
        {
            log.warn("algorithm (inside keyUsage) is null");
            throw new SecureCryptoException("algorithm (inside keyUsage) is null");
        }

        if (alg.equals(Algorithm.RSA))
        {
            pkiKeyType = PKIKeyType.Signature_and_Key_Management;
        }
        else if (alg.equals(Algorithm.RSA_ALL) || alg.equals(Algorithm.RSA_CRT) || alg.equals(Algorithm.RSA_MODEXP))
        {
            pkiKeyType = PKIKeyType.Type3;
        }
        else
        {
            String errMsg = "algorithm (inside keyUsage) is not valid: " + alg;
            log.warn(errMsg);
            throw new SecureCryptoException(errMsg);
        }

        primeStrength = PrimeStrength.DEFAULT;
        smType = null;
        mkIdentifier = null;
        forKeyCache = false;
    }

    public KeyUsage getKeyUsage()
    {
        return keyUsage;
    }

    public int getKeySize()
    {
        return keySize;
    }

    public byte[] getPublicExponent()
    {
        return publicExponent;
    }

    public boolean isForKeyCache()
    {
        return forKeyCache;
    }

    public void setForKeyCache(boolean forKeyCache)
    {
        this.forKeyCache = forKeyCache;
    }

    public PKIKeyType getPkiKeyType()
    {
        return pkiKeyType;
    }

    public void setPkiKeyType(PKIKeyType pkiKeyType)
    {
        this.pkiKeyType = pkiKeyType;
    }

    public PrimeStrength getPrimeStrength()
    {
        return primeStrength;
    }

    public void setPrimeStrength(PrimeStrength primeStrength)
    {
        this.primeStrength = primeStrength;
    }

    public String getSmType()
    {
        return smType;
    }

    public void setSmType(String smType)
    {
        this.smType = smType;
    }

    public byte[] getMkIdentifier()
    {
        return mkIdentifier;
    }

    public void setSmTypeAndMkIdentifier(String smType, byte[] mkIdentifier)
    {
        this.smType = smType;
        this.mkIdentifier = mkIdentifier;
    }

    public void validate() throws SecureCryptoException
    {
        if (!keyUsage.getAlgorithm().isRSA())
        {
            throw new SecureCryptoException("Algorithm must be either RSA, RSA CRT or RSA NON CRT");
        }

        if ((Usage.BDK != keyUsage.getUsage()) &&
                (Usage.DCK != keyUsage.getUsage()) &&
                (Usage.ECK != keyUsage.getUsage()))
        {
            throw new SecureCryptoException("Invalid usage specified");
        }

        if ((keySize & 7) != 0)
        {
            //throw new SecureCryptoException("Key size, must be a multiple 8 bytes long", null);
            // fix for ncr UKPMA#1508
            throw new SecureCryptoException("Key size must be a multiple of 8 bits");
        }
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        String publicExponentHex = publicExponent == null? "null" : ByteArrayUtilities.stringify_nospaces(publicExponent);
        String mkIdHex = mkIdentifier == null? "null" : ByteArrayUtilities.stringify_nospaces(mkIdentifier);

        sb.append("\nkeyUsage       : ").append(keyUsage);
        sb.append("\nkeySize        : ").append(keySize);
        sb.append("\npublicExponent : ").append(publicExponentHex);
        sb.append("\nforKeyCache    : ").append(forKeyCache);
        sb.append("\npkiKeyType     : ").append(pkiKeyType);
        sb.append("\nprimeStrength  : ").append(primeStrength);
        sb.append("\nsmType         : ").append(smType);
        sb.append("\nmkIdentifier   : ").append(mkIdHex);

        return sb.toString();
    }


    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeBoolean(false);
        out.writeUTF(keyUsage.getAlgorithm().toString());
        out.writeUTF(keyUsage.getUsage().toString());
        out.writeInt(keySize);
        out.writeBoolean(forKeyCache);

        out.writeBoolean(publicExponent != null);
        if (publicExponent != null)
        {
            out.writeInt(publicExponent.length);
            out.write(publicExponent, 0, publicExponent.length);
        }

        out.writeBoolean(pkiKeyType != null);
        if (pkiKeyType != null)
        {
            out.writeUTF(pkiKeyType.toString());
        }

        out.writeBoolean(primeStrength != null);
        if (primeStrength != null)
        {
            out.writeUTF(primeStrength.toString());
        }

        out.writeBoolean(smType != null);
        if (smType != null)
        {
            out.writeUTF(smType);
        }

        out.writeBoolean(mkIdentifier != null);
        if (mkIdentifier != null)
        {
            out.writeInt(mkIdentifier.length);
            out.write(mkIdentifier, 0, mkIdentifier.length);
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        try
        {
            Algorithm alg = Algorithm.getAlgorithm(in.readUTF());
            Usage usage = Usage.getUsage(in.readUTF());
            keyUsage = new KeyUsage(alg, usage);
            keySize = in.readInt();
            forKeyCache = in.readBoolean();

            publicExponent = null;
            if (in.readBoolean())
            {
                int publicExpLen = in.readInt();
                if (publicExpLen > 4096) throw new IOException("Public Exponent length too big to read: " + publicExpLen);
                publicExponent = new byte[publicExpLen];
                in.readFully(publicExponent);
            }

            pkiKeyType = null;
            if (in.readBoolean())
            {
                String pkiKeyTypeStr = in.readUTF();
                pkiKeyType = PKIKeyType.getFrom(pkiKeyTypeStr);
            }

            primeStrength = null;
            if (in.readBoolean())
            {
                String primeStrengthStr = in.readUTF();
                primeStrength = PrimeStrength.valueOf(primeStrengthStr);
            }

            smType = null;
            if (in.readBoolean())
            {
                smType = in.readUTF();
            }

            mkIdentifier = null;
            if (in.readBoolean())
            {
                int mkIdLen = in.readInt();
                if (mkIdLen > 128) throw new IOException("MK Identifier length too big to read: " + mkIdLen);
                mkIdentifier = new byte[mkIdLen];
                in.readFully(mkIdentifier);
            }
        }
        catch (SecureCryptoException x)
        {
            log.warn("Cannot read externalized GenerateKeyPair data", x);
            throw new IOException("Cannot read externalized GenerateKeyPair data: " + x);
        }
    }
}
