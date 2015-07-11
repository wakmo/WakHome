package wak.work.cryptogram.graem.securecrypto;

import wak.work.cryptogram.helper.ByteArrayUtilities;

/**
 * User: growles
 * Date: 08-Nov-2011
 * Time: 11:00:45
 */
public class ThalesKey
{
    //Key size in bytes, excluding the U, T or Z prefix
    private int keyByteSize;

    //U, T, Z or blank
    private String keyType;

    //X, Y, Z or null - they key type that this key will export to (null if this key is already X or Y)
    private String exportToKeyType;

    //U, T, Z or null - they key type that this key will import to (null if this key is already U or T)
    private String importToKeyType;

    //Number of prefix characters read (U, T, X, Y or Z) from start of key (should be 0 or 1)
    private int prefixCharsRead;

    //The key value excluding the prefix character
    private final String keyValue;

    //Total number of characters read to read in this key. Expected: 16, 17, 33 or 49
    private final int charsRead;


    /**
     * Constructs a ThalesKey using a native key.  This includes an optional prefix, i.e. U or T.
     *
     * @param nativeKey A byte array containing the native key, complete with U or T prefix (or similar prefix)
     *                  in the form of one character per byte.
     *                  nativeKey will contain 33 bytes of data for the typical DES2EDE key.
     * @param startPos The offset of where the key is placed
     * @throws SecureCryptoException Key could not be read
     */
    public ThalesKey(byte[] nativeKey, int startPos) throws SecureCryptoException
    {
        if (nativeKey.length < startPos + 16)
        {
            throw new SecureCryptoException("Key too small: " + ByteArrayUtilities.stringify_nospaces(nativeKey));
        }

        char keyTypeChar = (char)nativeKey[startPos];
        setKeyType(keyTypeChar);


        int keyCharsWithoutPrefix = keyByteSize * 2;
        charsRead = keyCharsWithoutPrefix + prefixCharsRead;
        if (startPos + charsRead > nativeKey.length)
        {
            throw new SecureCryptoException("Key data too short: " + ByteArrayUtilities.stringify_nospaces(nativeKey));
        }

        keyValue = new String(nativeKey, startPos + prefixCharsRead, keyCharsWithoutPrefix);
        validateKeyValue();
    }

    /**
     * Constructs a ThalesKey using a native key.  This includes an optional prefix, i.e. U or T.
     * The ThalesKey must be at the beginning of the byte array and the byte array must contain no
     * spurious bytes.
     *
     * @param nativeKey A byte array containing the native key, complete with U or T prefix (or similar prefix)
     *                  in the form of one character per byte.
     *                  nativeKey will contain 33 bytes of data for the typical DES2EDE key.
     * @throws SecureCryptoException Key could not be read
     */
    public ThalesKey(byte[] nativeKey) throws SecureCryptoException
    {
        this(nativeKey, 0);

        if (charsRead != nativeKey.length)
        {
            throw new SecureCryptoException("Key data is too big: " + ByteArrayUtilities.stringify_nospaces(nativeKey));
        }
    }

    /**
     * Constructs a ThalesKey using a native key.  This includes an optional prefix, i.e. U or T.
     * The ThalesKey must be at the beginning of the String and the String must contain no
     * spurious data at the end.
     *
     * @param nativeKey A String containing the native key, complete with U or T prefix (or similar prefix)
     *                  in hexified format
     *                  nativeKey will contain 33 characters of data for the typical DES2EDE key.
     * @throws SecureCryptoException Key could not be read
     */
    public ThalesKey(String nativeKey) throws SecureCryptoException
    {
        this(nativeKey.getBytes(), 0);
    }

    /**
     * Constructor where a key value and its prefix are separated.
     *
     * @param keyUnderLmk Key, as a binary byte array of length 8, 16 or 24
     * @param smExtension Byte array of length 4; smExtension[3] will contain the U or T (or similar) key prefix
     * @throws SecureCryptoException Invalid data supplied
     */
    public ThalesKey(byte[] keyUnderLmk, byte[] smExtension) throws SecureCryptoException
    {
        if (smExtension == null) throw new SecureCryptoException("SM Extension is null");
        if (smExtension.length != 4) throw new SecureCryptoException("SM Extension is not 4 bytes in length: " +
                ByteArrayUtilities.stringify_nospaces(smExtension));

        char keyTypeChar = (char)smExtension[3];
        setKeyType(keyTypeChar);

        if (keyByteSize > keyUnderLmk.length)
        {
            throw new SecureCryptoException("Insufficient key data. Expected " + keyByteSize + " bytes, but" +
                    "key data was: " + ByteArrayUtilities.stringify_nospaces(keyUnderLmk));
        }

        charsRead = keyByteSize + prefixCharsRead;

        if (keyUnderLmk.length != keyByteSize)
        {
            throw new SecureCryptoException("Key data is too big: " + ByteArrayUtilities.stringify_nospaces(keyUnderLmk) +
                " : expected length=" + keyByteSize + " bytes");
        }

        keyValue = ByteArrayUtilities.stringify_nospaces(keyUnderLmk).toUpperCase();
    }

    /**
     * Constructor where a key value and its prefix are separated.
     *
     * @param keyUnderLmk Key, as a hex String of length 16, 32 or 48
     * @param smExtension String of length 4; smExtension.charAt(3) will contain the U or T (or similar) key prefix
     * @throws SecureCryptoException Invalid data supplied
     */
    public ThalesKey(String keyUnderLmk, String smExtension) throws SecureCryptoException
    {
        if (smExtension == null) throw new SecureCryptoException("SM Extension is null");
        if (smExtension.length() != 4) throw new SecureCryptoException("SM Extension is not 4 characters in length: " + smExtension);

        setKeyType(smExtension.charAt(3));

        if (keyByteSize > keyUnderLmk.length())
        {
            throw new SecureCryptoException("Insufficient key data. Expected " + keyByteSize + " bytes, but" +
                    "key data was: " + keyUnderLmk);
        }

        int hexCharLength = keyByteSize * 2;
        charsRead = hexCharLength + prefixCharsRead;

        if (keyUnderLmk.length() != hexCharLength)
        {
            throw new SecureCryptoException("Key data is too big: '" + keyUnderLmk +
                    "' : expected length=" + hexCharLength + " hex digits");
        }

        keyValue = keyUnderLmk.toUpperCase();
        validateKeyValue();
    }

    private void setKeyType(char keyTypeChar) throws SecureCryptoException
    {
        prefixCharsRead = 1;
        switch (keyTypeChar)
        {
            case 'Z':
                keyByteSize = 8;
                keyType = "Z";
                exportToKeyType = "Z";
                importToKeyType = "Z";
                break;
            case 'U':
                keyByteSize = 16;
                keyType = "U";
                exportToKeyType = "X";
                importToKeyType = null;
                break;
            case 'T':
                keyByteSize = 24;
                keyType = "T";
                exportToKeyType = "Y";
                importToKeyType = null;
                break;
            case 'X':
                keyByteSize = 16;
                keyType = "X";
                exportToKeyType = null;
                importToKeyType = "U";
                break;
            case 'Y':
                keyByteSize = 24;
                keyType = "Y";
                exportToKeyType = null;
                importToKeyType = "T";
                break;
            default:
                if ("0123456789ABCDEF".indexOf(keyTypeChar) < 0)
                {
                    throw new SecureCryptoException("Invalid key type " + keyTypeChar);
                }
                keyByteSize = 8;
                keyType = "Z";
                prefixCharsRead = 0;
        }
    }

    public String getKeyType()
    {
        return keyType;
    }

    //Returns the number of characters read from the source data in the constructor
    public int getCharsRead()
    {
        return charsRead;
    }

    public static boolean isFormedCorrectly(String keyUnderLmk)
    {
        boolean formedCorrectly = true;

        try
        {
            new ThalesKey(keyUnderLmk.getBytes(), 0);
        }
        catch (SecureCryptoException x)
        {
            //Not an error, so don't log anything
            formedCorrectly = false;
        }

        return formedCorrectly;
    }

    public int getKeySize()
    {
        return keyByteSize * 8;
    }

    public String getKey()
    {
        return keyType + keyValue;
    }

    public boolean isImportExportKey()
    {
        return "XY".contains(keyType);
    }

    public boolean isNativeKey()
    {
        return "XY".contains(keyType);
    }

    public String getKeyWithoutTypeChar()
    {
        return keyValue;
    }

    public String getExportToKeyType()
    {
        return exportToKeyType;
    }

    public String getImportToKeyType()
    {
        return importToKeyType;
    }

    private void validateKeyValue() throws SecureCryptoException
    {
        for (char c : keyValue.toCharArray())
        {
            if ("0123456789ABCDEF".indexOf(c) < 0)
            {
                throw new SecureCryptoException("Thales key contains non-hexadecimal characters: " + keyValue);
            }
        }
    }
}
