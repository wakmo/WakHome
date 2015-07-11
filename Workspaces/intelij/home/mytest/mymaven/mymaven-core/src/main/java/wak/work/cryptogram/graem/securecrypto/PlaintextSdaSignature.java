package wak.work.cryptogram.graem.securecrypto;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import wak.work.cryptogram.helper.ByteArrayUtilities;

/**
 * Created with IntelliJ IDEA.
 * User: graeme.rowles
 * Date: 17/01/13
 * Time: 11:53
 */
public class PlaintextSdaSignature
{
    private static final Logger log = Logger.getLogger(PlaintextSdaSignature.class);


    private static final byte HEADER = (byte)0x6A;
    private static final byte SIGNED_DATA_FORMAT = (byte)0x03;
    private static final byte HASH_ALG_INDICATOR = (byte)0x01;
    private static final byte TRAILER = (byte)0xBC;


    private final byte[] staticAuthenticationData;
    private final byte[] dac;
    private final int lengthInBytes;

    private final byte[] padding;

    private byte[] hash;


    public PlaintextSdaSignature(byte[] staticAuthenticationData, byte[] dac, int lengthInBytes) throws SecureCryptoException
    {
        this.staticAuthenticationData = staticAuthenticationData;
        this.dac = dac;
        this.lengthInBytes = lengthInBytes;

        checkNotNull("Static Authentication Data", staticAuthenticationData);
        checkNotNull("DAC", dac);

        if (dac.length != 2)
        {
            throw new SecureCryptoException("Cannot create Plaintext SDA Signature because DAC is not 2 bytes in length: " +
                    ByteArrayUtilities.stringify_nospaces(dac));
        }

        if (lengthInBytes < 26)
        {
            throw new SecureCryptoException("Cannot create Plaintext SDA Signature because lengthInBytes is invalid: " + lengthInBytes);
        }

        padding = new byte[lengthInBytes - 26];
        Arrays.fill(padding, (byte)0xBB);

        hash = null;
    }

    public byte[] getBytesToHash()
    {
        ByteArrayOutputStream hashData = new ByteArrayOutputStream(padding.length + 5 + staticAuthenticationData.length);

        hashData.write(new byte[] {SIGNED_DATA_FORMAT, HASH_ALG_INDICATOR}, 0, 2);
        hashData.write(dac, 0, dac.length);
        hashData.write(padding, 0, padding.length);
        hashData.write(staticAuthenticationData, 0, staticAuthenticationData.length);

        return hashData.toByteArray();
    }

    public void setHash(byte[] hash) throws SecureCryptoException
    {
        if (hash != null && hash.length != 20)
        {
            String errMsg = "Hash was not 20 bytes in length: " + ByteArrayUtilities.stringify_nospaces(hash);
            log.warn(errMsg);
            throw new SecureCryptoException(errMsg);
        }

        this.hash = hash;
    }

    public byte[] toByteArray() throws SecureCryptoException
    {
        if (hash == null)
        {
            String errMsg = "Cannot create Plaintext SDA Signature because hash has not been set";
            log.warn(errMsg);
            throw new SecureCryptoException(errMsg);
        }

        ByteArrayOutputStream sigBytes = new ByteArrayOutputStream(padding.length + 5 + staticAuthenticationData.length);

        sigBytes.write(new byte[]{HEADER, SIGNED_DATA_FORMAT, HASH_ALG_INDICATOR}, 0, 3);
        sigBytes.write(dac, 0, dac.length);
        sigBytes.write(padding, 0, padding.length);
        sigBytes.write(hash, 0, hash.length);
        sigBytes.write(new byte[] {TRAILER}, 0, 1);

        byte[] plaintextSig = sigBytes.toByteArray();

        if (plaintextSig.length != lengthInBytes)
        {
            String errMsg = "After producing plaintext signature its length was " + plaintextSig.length +
                    " bytes in length, rather than " + lengthInBytes + " bytes in length";
            log.error(errMsg);
            log.warn("Produced signature=" + ByteArrayUtilities.stringify_nospaces(plaintextSig));
            throw new SecureCryptoException("Internal error: " + errMsg);
        }

        return plaintextSig;
    }

    private static void checkNotNull(String dataItemName, Object data) throws SecureCryptoException
    {
        if (data == null)
        {
            String errMsg = "Cannot create Plaintext SDA Signature because " + dataItemName + " is null";
            log.warn(errMsg);
            throw new SecureCryptoException(errMsg);
        }
    }
}
