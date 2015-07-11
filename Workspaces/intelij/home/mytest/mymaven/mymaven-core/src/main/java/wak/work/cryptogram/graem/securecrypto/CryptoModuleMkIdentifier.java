package wak.work.cryptogram.graem.securecrypto;

import org.apache.log4j.Logger;
import wak.work.cryptogram.helper.ByteArrayUtilities;

/**
 * Stores an Master Key Identifier (e.g. LMK Check digit) of a Crypto Module.
 *
 * Allows the MK Identifiers fields of keys to be compared with it in order to tell whether the
 * CryptoModule is capable of using that key
 *
 * User: growles
 * Date: 03-Feb-2012
 * Time: 11:30:23
 */
public class CryptoModuleMkIdentifier
{
    private static final Logger log = Logger.getLogger(CryptoModuleMkIdentifier.class);


    public enum EncodingType {
        //e.g. 0x26, 0x86, 0x04, ..., 0x22
        BINARY,

        //e.g. "268604...22"  -- 0x32, 0x36, 0x38, 0x36, ..., 0x32, 0x32
        HEX,

        //e.g. "323638363034...3232" -- 0x33, 0x32, 0x33, 0x36, 0x33, 0x38, ..., 0x33, 0x32, 0x33, 0x32
        DOUBLE_HEX
    }


    //The MK Identifier - stored in 'keyMkIdentType' format
    //Will be size zero if there's no MK Identifier for the Crypto Module
    private final byte[] cmMkIdentifier;

    //The minimum number of bytes of mkIdentifier which must match those
    //of Key objects being compared to assume the MK Identifiers are the same
    //Bytes means bytes in cmMkIdentifier... so if encoding type of keys is binary then this minSignificantBytes=3
    //means 6 hex digits (=3 binary bytes) of the MK Identifier
    private final int minSignificantBytes;

    /**
     * Encoding type that keys of this CryptoModule are encrypted in
     */
    private final EncodingType keyMkIdentType;


    //Constructor can be used for HEX or DOUBLE_HEX
    public CryptoModuleMkIdentifier(String cmMkIdent, EncodingType cmMkIdentType,
                                    int minSignificantBytes, EncodingType keyMkIdentType) throws SecureCryptoException
    {
        this((cmMkIdent == null)? null : cmMkIdent.getBytes(),
                cmMkIdentType, minSignificantBytes, keyMkIdentType);
    }

    /**
     * Example: The following are equivalent:
     *   CryptoModuleMkIdentifier(new byte[] {0x26, 0x86, 0x04}, EncodingType.BINARY,     3, EncodingType.BINARY)
     *   CryptoModuleMkIdentifier("268604".getBytes(),           EncodingType.HEX,        3, EncodingType.BINARY)
     *   CryptoModuleMkIdentifier("323638363034".getBytes(),     EncodingType.DOUBLE_HEX, 3, EncodingType.BINARY)
     *
     * The following both look for a "268604" prefix to a MK Identifier in a key for keys who
     * represent their MK Identifiers as BINARY, HEX, or DOUBLE_HEX respectively
     *   CryptoModuleMkIdentifier(new byte[] {0x26, 0x86, 0x04}, EncodingType.BINARY, 3, EncodingType.BINARY)
     *   CryptoModuleMkIdentifier(new byte[] {0x26, 0x86, 0x04}, EncodingType.BINARY, 6, EncodingType.HEX)
     *   CryptoModuleMkIdentifier(new byte[] {0x26, 0x86, 0x04}, EncodingType.BINARY, 12, EncodingType.DOUBLE_HEX)
     *
     * @param cmMkIdent The Crypto Module's MK Identifier (a.k.a. LMK Check Value) which is in the format
     *                  signified in cmMkIdentType
     * @param cmMkIdentType BINARY, HEX or DOUBLE_HEX
     * @param minSignificantBytes The minimum number of bytes or ASCII characters in cmMkIdent, when
     *                            represented in format 'keyMkIdentType', that are important
     *                            in checking an MK Identifier
     * @param keyMkIdentType The format of the MK Identifier in keys that is to be checked against cmMkIdent -
     *                       one of: BINARY, HEX, DOUBLE_HEX
     * @throws SecureCryptoException Incorrect data supplied
     */
    public CryptoModuleMkIdentifier(byte[] cmMkIdent, EncodingType cmMkIdentType,
                                    int minSignificantBytes, EncodingType keyMkIdentType) throws SecureCryptoException
    {
        this.minSignificantBytes = minSignificantBytes;
        this.keyMkIdentType = keyMkIdentType;

        if (cmMkIdent == null)
        {
            cmMkIdentifier = new byte[0];
        }
        else
        {
            cmMkIdentifier = convert("CryptoModule MK Identifier", cmMkIdent, cmMkIdentType, keyMkIdentType);
        }


        if (cmMkIdentifier.length < minSignificantBytes)
        {
            String errMsg = "Crypto Module's MK Identifier is less than " + minSignificantBytes
                    + " bytes: " + ByteArrayUtilities.stringify_nospaces(cmMkIdentifier);

            log.warn(errMsg);
            throw new SecureCryptoException(errMsg);
        }

        if (minSignificantBytes < 0)
        {
            throw new SecureCryptoException("Invalid 'minSignificantChars' value: " + minSignificantBytes);
        }
    }

    public byte[] getAs(EncodingType encodingType) throws SecureCryptoException
    {
        return convert("MK Identifier", cmMkIdentifier, EncodingType.BINARY, encodingType);
    }

    public boolean matches(byte[] mkId, EncodingType encodingType) throws SecureCryptoException
    {
        byte[] mkIdBinary = convert("MK Identifier", mkId, encodingType, keyMkIdentType);

        boolean matched;
        if (mkIdBinary.length < minSignificantBytes)
        {
            //Not enough characters in key's MKIdentifier to check
            matched = false;
        }
        else
        {
            matched = true;

            int mkCharsToCheck = Math.min(mkIdBinary.length, cmMkIdentifier.length);
            for (int i = 0; i < mkCharsToCheck; i++)
            {
                matched &= cmMkIdentifier[i] == mkIdBinary[i];
            }
        }

        return matched;
    }

    public boolean matches(CryptoModuleMkIdentifier mkId) throws SecureCryptoException
    {
        return matches(mkId.cmMkIdentifier, mkId.keyMkIdentType);
    }

    public boolean matchesKey(Key key) throws SecureCryptoException
    {
        //MK Identifiers inside Key object is a byte array in BCD form.  e.g. 8 bytes for 16 digits
        byte[] keyMkId = key.getMKIdentifier();

        boolean matched;
        if (keyMkId.length < minSignificantBytes)
        {
            //Not enough characters in key's MKIdentifier to check
            matched = false;
        }
        else
        {
            matched = true;

            int mkCharsToCheck = Math.min(keyMkId.length, cmMkIdentifier.length);
            for (int i = 0; i < mkCharsToCheck; i++)
            {
                matched &= cmMkIdentifier[i] == keyMkId[i];
            }
        }

        return matched;
    }

    public boolean matchesKeys(NonProcessingExcuses excuses, Key... keys) throws SecureCryptoException
    {
        boolean matchesAllKeys = true;

        for (Key key : keys)
        {
            boolean keyOk = matchesKey(key);
            matchesAllKeys &= keyOk;

            if (!keyOk && excuses != null)
            {
                excuses.addKeyExcuse(key, "Key's MKIdentifier is 0x" +
                        ByteArrayUtilities.stringify_nospaces(key.getMKIdentifier()) +
                        ", but this CryptoModule's MKIdentifier is 0x" +
                        ByteArrayUtilities.stringify_nospaces(cmMkIdentifier));
            }
        }

        return matchesAllKeys;
    }

    private static byte[] convert(String dataItemName, byte[] srcData, EncodingType srcType, EncodingType destType) throws SecureCryptoException
    {
        byte[] srcDataBinary;
        switch (srcType)
        {
            case BINARY:
                srcDataBinary = srcData;
                break;

            case HEX:
                srcDataBinary = unHexify(dataItemName, srcType, srcData);
                break;

            case DOUBLE_HEX:
                srcDataBinary = unHexify(dataItemName, srcType, srcData);
                srcDataBinary = unHexify(dataItemName, srcType, srcDataBinary);
                break;

            default:
                log.error("Bad srcType = " + srcType);
                throw new SecureCryptoException("Bad srcType = " + srcType);
        }

        byte[] destData;
        switch (destType)
        {
            case BINARY:
                destData = srcDataBinary;
                break;

            case HEX:
                destData = ByteArrayUtilities.stringify_nospaces(srcDataBinary).toUpperCase().getBytes();
                break;

            case DOUBLE_HEX:
                destData = ByteArrayUtilities.stringify_nospaces(srcDataBinary).toUpperCase().getBytes();
                destData = ByteArrayUtilities.stringify_nospaces(destData).toUpperCase().getBytes();
                break;

            default:
                log.error("Bad destType = " + srcType);
                throw new SecureCryptoException("Bad destType = " + srcType);
        }

        return destData;
    }

    private static byte[] unHexify(String dataItemName, EncodingType srcType, byte[] srcData) throws SecureCryptoException
    {
        String srcDataStr = new String(srcData);
        try
        {
            return ByteArrayUtilities.byteify_nospaces(srcDataStr);
        }
        catch (NumberFormatException x)
        {
            throw new SecureCryptoException(dataItemName + " was not valid " + srcType);
        }
    }
}
