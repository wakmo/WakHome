package wak.work.cryptogram.graem.securecrypto;

/**
 * Created with IntelliJ IDEA.
 * User: graeme.rowles
 * Date: 14/05/13
 * Time: 13:31
 */
public class ThalesNativePublicKey
{
    private static final int THALES_PUBLIC_KEY_MAC_LEN = 4;


    private final byte[] derPublicKeyBytes;
    private final byte[] mac;
    private final int keySize;


    public ThalesNativePublicKey(byte[] nativeKey) throws SecureCryptoException
    {
        if (nativeKey.length < THALES_PUBLIC_KEY_MAC_LEN)
        {
            throw new SecureCryptoException("Public key is too short");
        }

        derPublicKeyBytes = new byte[nativeKey.length - THALES_PUBLIC_KEY_MAC_LEN];
        System.arraycopy(nativeKey, 0, derPublicKeyBytes, 0, derPublicKeyBytes.length);

        try
        {
            DerEncodedPublicKey derKey = new DerEncodedPublicKey(derPublicKeyBytes);
            keySize = derKey.getKeySize();
        }
        catch (SecureCryptoException x)
        {
            throw new SecureCryptoException("Invalid public key entered:\n" + x.getMessage());
        }

        mac = new byte[THALES_PUBLIC_KEY_MAC_LEN];
        System.arraycopy(nativeKey, nativeKey.length - THALES_PUBLIC_KEY_MAC_LEN, mac, 0, THALES_PUBLIC_KEY_MAC_LEN);
    }

    public ThalesNativePublicKey(byte[] derPublicKeyBytes, byte[] mac) throws SecureCryptoException
    {
        this.derPublicKeyBytes = derPublicKeyBytes;
        this.mac = mac;

        DerEncodedPublicKey derKey = new DerEncodedPublicKey(derPublicKeyBytes);
        keySize = derKey.getKeySize();
    }

    public int getKeySize()
    {
        return keySize;
    }

    public byte[] getDerPublicKeyBytes()
    {
        return derPublicKeyBytes;
    }

    public byte[] getMac()
    {
        return mac;
    }

    public byte[] getNativeKeyBytes()
    {
        byte[] retVal = new byte[derPublicKeyBytes.length + mac.length];
        System.arraycopy(derPublicKeyBytes, 0, retVal, 0, derPublicKeyBytes.length);
        System.arraycopy(mac, 0, retVal, derPublicKeyBytes.length, mac.length);

        return retVal;
    }
}
