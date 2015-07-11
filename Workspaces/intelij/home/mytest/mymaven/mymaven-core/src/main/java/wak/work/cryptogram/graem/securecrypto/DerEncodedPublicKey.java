package wak.work.cryptogram.graem.securecrypto;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.math.BigInteger;
import wak.work.cryptogram.helper.ByteArrayUtilities;

/**
 * Encodes an RSA Public key into the following format:
 *
 * 30 [length]
 *   02 [length] [public modulus]
 *   02 [length] [public exponent]
 *
 * where [length] = LL where length < 0x80
 *       [length] = 81LL where length >= 0x80 and length <= 0xFF
 *       [length] = 82LLLL where length >= 0x0100 and length <= 0xFFFF
 *
 * e.g.: 3081860281809FD19C1E33911C842FDC68E91A88A3192CD6552B4BA5B2222B132927CA4558B4E65604F9797CA257B5324E36186684F35E372C30313CE40BE34D4F8C64F7F963C5EFBBC46B5A5167C79A02B9CF4476DDC69F25D406FBAF1FE75D55C5F772419CBFBA72CB014723C4CA7F7431B702EC259D193CB02F6C138A67D2795E3666D4A3020103
 * 
 * User: growles
 * Date: 28-Jul-2010
 * Time: 11:54:57
 */
public class DerEncodedPublicKey implements Serializable
{
    private static final Logger log = Logger.getLogger(DerEncodedPublicKey.class);

    private static final byte STRUCTURED_TAG = 0x30;
    private static final byte INT_TAG = 2;


    private final BigInteger exponent;
    private final BigInteger modulus;

    //DER Encoded public key as a byte array
    private final byte[] encodedPublicKeyBytes;

    private final int numBytesRead;

    //Two properties for For parsing a byte array
    private byte[] suppliedPublicKeyBytes;
    private int pos;


    public DerEncodedPublicKey(BigInteger exponent, BigInteger modulus) throws SecureCryptoException
    {
        this.exponent = exponent;
        this.modulus = modulus;

        byte[] exponentBytes = exponent.toByteArray();
        byte[] modulusBytes = modulus.toByteArray();

        byte[] modulusPart = ByteArrayUtilities.addByteArrays(new byte[] {INT_TAG},
                makeLengthField(modulusBytes.length),
                modulusBytes);

        byte[] exponentPart = ByteArrayUtilities.addByteArrays(new byte[] {INT_TAG},
                makeLengthField(exponentBytes.length),
                exponentBytes);

        encodedPublicKeyBytes = ByteArrayUtilities.addByteArrays(new byte[] {STRUCTURED_TAG},
                makeLengthField(modulusPart.length + exponentPart.length),
                modulusPart,
                exponentPart);

        numBytesRead = 0;
    }

    public DerEncodedPublicKey(byte[] derEncodedPublicKey) throws SecureCryptoException
    {
        this(derEncodedPublicKey, 0, true);
    }

    public DerEncodedPublicKey(byte[] derEncodedPublicKey, int startPos) throws SecureCryptoException
    {
        this(derEncodedPublicKey, startPos, false);
    }

    public int getKeySize()
    {
        return modulus.bitLength();
    }

    private DerEncodedPublicKey(byte[] derEncodedPublicKey, int startPos, boolean checkLength) throws SecureCryptoException
    {
        suppliedPublicKeyBytes = derEncodedPublicKey;

        pos = startPos;

        if (nextByte() != STRUCTURED_TAG)
        {
            throwException("DER Encoded public key does not start with a byte of value " + STRUCTURED_TAG, pos);
        }

        int wholeLength = readLength();
        int expectedEndPos = pos + wholeLength;

        if (nextByte() != INT_TAG)
        {
            throwException("Modulus field in DER Encoded public key does not start with a byte of value " + INT_TAG, pos);
        }
        int modulusLength = readLength();
        byte[] modulusBytes = nextBytes(modulusLength);
        modulus = new BigInteger(1, modulusBytes);

        if (nextByte() != INT_TAG)
        {
            throwException("Exponent field in DER Encoded public key does not start with a byte of value " + INT_TAG, pos);
        }
        int exponentLength = readLength();
        byte[] exponentBytes = nextBytes(exponentLength);
        exponent = new BigInteger(1, exponentBytes);

        if (checkLength && (pos != expectedEndPos))
        {
            throwException("Length field of DER Encoded public key does not match the actual length of the modulus and exponent fields", 1);
        }

        numBytesRead = pos - startPos;

        encodedPublicKeyBytes = new byte[numBytesRead];
        System.arraycopy(derEncodedPublicKey, startPos, encodedPublicKeyBytes, 0, numBytesRead);
    }

    public byte[] getPublicKey()
    {
        return encodedPublicKeyBytes;
    }

    public BigInteger getExponent()
    {
        return exponent;
    }

    public BigInteger getModulus()
    {
        return modulus;
    }

    public int getNumBytesRead()
    {
        return numBytesRead;
    }

    public byte[] getModulusAsBytes()
    {
        return bigintToBytes(modulus);
    }

    public byte[] getExponentAsBytes()
    {
        return bigintToBytes(exponent);
    }

    private static byte[] bigintToBytes(BigInteger bi)
    {
        byte[] bytes = bi.toByteArray();
        if (bytes[0] == 0)
        {
            byte[] temp = new byte[bytes.length - 1];
            System.arraycopy(bytes, 1, temp, 0, temp.length);
            bytes = temp;
        }

        return bytes;
    }


    private int readLength() throws SecureCryptoException
    {
        int retVal = -1;

        int b = nextByte() & 0xFF;
        if (b < 0x80)
        {
            retVal = b;
        }
        else if (b == 0x81)
        {
            retVal = nextByte() & 0xFF;
        }
        else if (b == 0x82)
        {
            retVal = nextByte() & 0xFF;
            retVal <<= 8;
            retVal |= nextByte() & 0xFF;
        }
        else
        {
            throwException("Invalid length field in DER encoded public key", null);
        }

        return retVal;
    }

    private byte nextByte() throws SecureCryptoException
    {
        if (pos >= suppliedPublicKeyBytes.length)
        {
            throwException("Ran out of data whilst parsing DER encoded public key.  DER encoded public key is corrupt.", null);
        }

        return suppliedPublicKeyBytes[pos++];
    }

    private byte[] nextBytes(int numOfBytes) throws SecureCryptoException
    {
        if (pos + numOfBytes > suppliedPublicKeyBytes.length)
        {
            throwException("Ran out of data whilst parsing DER encoded public key.  DER encoded public key is corrupt.", null);
        }

        byte[] retVal = new byte[numOfBytes];
        System.arraycopy(suppliedPublicKeyBytes, pos, retVal, 0, numOfBytes);
        pos += numOfBytes;

        return retVal;
    }


    private void throwException(String errMsg, Integer startOffset) throws SecureCryptoException
    {
        String logMsg = errMsg + "... ";
        if (startOffset != null)
        {
            logMsg += "startOffset=" + startOffset + ", ";
        }
        logMsg += "encodedPublicKeyBytes=" + ByteArrayUtilities.stringify_nospaces(suppliedPublicKeyBytes);
        logMsg += ", offsetOfError=" + pos;

        log.warn(logMsg);
        throw new SecureCryptoException(errMsg);
    }

    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append("exponent=0x").append(exponent.toString(16));
        buff.append(", modulus=0x").append(modulus.toString(16));

        return buff.toString();
    }

    private static byte[] makeLengthField(int length) throws SecureCryptoException
    {
        byte[] lengthField;

        if (length < 0)
        {
            throw new SecureCryptoException("Cannot construct a length field for a negative length of " + length);
        }
        
        if (length <= 0x7F)
        {
            lengthField = new byte[] {(byte)length};
        }
        else if (length <= 0xFF)
        {
            lengthField = new byte[] {(byte)0x81, (byte)length};
        }
        else if (length <= 0xFFFF)
        {
            lengthField = new byte[] {(byte)0x82, (byte)(length >> 8), (byte)length};
        }
        else
        {
            throw new SecureCryptoException("Cannot construct a length field for a length of " + length);
        }

        return lengthField;
    }
}
