package wak.work.cryptogram.graem.securecrypto;

import wak.work.cryptogram.helper.ByteArrayUtilities;

/**
 * User: growles
 * Date: 19-Apr-2012
 * Time: 08:52:27
 */
public class ValueChecker
{
    private ValueChecker()
    {
    }

    public static void checkNumeric(String name, String value) throws SecureCryptoException
    {
        check(name, "0123456789", value, 0, 0, "contains non-numeric data");
    }

    public static void checkNumeric(String name, String value, int minLen, int maxLen) throws SecureCryptoException
    {
        check(name, "0123456789", value, minLen, maxLen, "contains non-numeric data");
    }

    public static void checkNumeric(String name, String value, int reqdLen) throws SecureCryptoException
    {
        check(name, "0123456789", value, reqdLen, reqdLen, "contains non-numeric data");
    }

    public static void checkNumeric(String name, byte[] value) throws SecureCryptoException
    {
        check(name, "0123456789", value, 0, 0, "contains non-numeric data");
    }

    public static void checkNumeric(String name, byte[] value, int minLen, int maxLen) throws SecureCryptoException
    {
        check(name, "0123456789", value, minLen, maxLen, "contains non-numeric data");
    }

    public static void checkNumeric(String name, byte[] value, int reqdLen) throws SecureCryptoException
    {
        check(name, "0123456789", value, reqdLen, reqdLen, "contains non-numeric data");
    }

    public static void checkHex(String name, String value) throws SecureCryptoException
    {
        check(name, "0123456789ABCDEFabcdef", value, 0, 0, "contains non-hexadecimal data");
    }

    public static void checkHex(String name, String value, int minLen, int maxLen) throws SecureCryptoException
    {
        check(name, "0123456789ABCDEFabcdef", value, minLen, maxLen, "contains non-hexadecimal data");
    }

    public static void checkHex(String name, String value, int reqdLen) throws SecureCryptoException
    {
        check(name, "0123456789ABCDEFabcdef", value, reqdLen, reqdLen, "contains non-hexadecimal data");
    }

    public static void checkHex(String name, byte[] value) throws SecureCryptoException
    {
        check(name, "0123456789ABCDEFabcdef", value, 0, 0, "contains non-hexadecimal data");
    }

    public static void checkHex(String name, byte[] value, int minLen, int maxLen) throws SecureCryptoException
    {
        check(name, "0123456789ABCDEFabcdef", value, minLen, maxLen, "contains non-hexadecimal data");
    }

    public static void checkHex(String name, byte[] value, int reqdLen) throws SecureCryptoException
    {
        check(name, "0123456789ABCDEFabcdef", value, reqdLen, reqdLen, "contains non-hexadecimal data");
    }

    public static void checkBCD(String name, byte[] value, int minByteLen, int maxByteLen) throws SecureCryptoException
    {
        checkLength(name, value, minByteLen, maxByteLen);
        checkNumeric(name, ByteArrayUtilities.stringify_nospaces(value));
    }

    public static void checkBCD(String name, byte[] value, int reqdLen) throws SecureCryptoException
    {
        checkBCD(name, value, reqdLen, reqdLen);
    }

    public static void check(String name, String allowedChars, String value) throws SecureCryptoException
    {
        check(name, allowedChars, value, 0, 0, "contains invalid data");
    }

    public static void check(String name, String allowedChars, String value, int minLen, int maxLen) throws SecureCryptoException
    {
        check(name, allowedChars, value, minLen, maxLen, "contains invalid data");
    }

    public static void check(String name, String allowedChars, String value, int reqdLen) throws SecureCryptoException
    {
        check(name, allowedChars, value, reqdLen, reqdLen, "contains invalid data");
    }

    public static void check(String name, String allowedChars, byte[] value) throws SecureCryptoException
    {
        check(name, allowedChars, value, 0, 0, "contains invalid data");
    }

    public static void check(String name, String allowedChars, byte[] value, int minLen, int maxLen) throws SecureCryptoException
    {
        check(name, allowedChars, value, minLen, maxLen, "contains invalid data");
    }

    public static void check(String name, String allowedChars, byte[] value, int reqdLen) throws SecureCryptoException
    {
        check(name, allowedChars, value, reqdLen, reqdLen, "contains invalid data");
    }

    public static void checkLength(String name, String value, int reqdLen) throws SecureCryptoException
    {
        checkLength(name, value, reqdLen, reqdLen);
    }

    public static void checkLength(String name, String value, int minLen, int maxLen) throws SecureCryptoException
    {
        checkNull(name, value);

        if (value.length() < minLen)
        {
            throw new SecureCryptoException(name + " is too short.  It should be at least " + minLen
                + " characters in length.",
                    name + " = " + value);
        }

        if (maxLen > 0 && value.length() > maxLen)
        {
            throw new SecureCryptoException(name + " is too long.  It should be at most" + maxLen
                + " characters in length.",
                    name + " = " + value);
        }
    }

    public static void checkLength(String name, byte[] value, int reqdLen) throws SecureCryptoException
    {
        checkLength(name, value, reqdLen, reqdLen);
    }

    public static void checkLength(String name, byte[] value, int minLen, int maxLen) throws SecureCryptoException
    {
        checkNull(name, value);

        if (value.length < minLen)
        {
            String errMsg = name + " is too short.  It should be ";
            if (minLen != maxLen)
            {
                errMsg += "at least ";
            }
            errMsg += minLen + " bytes in length.";
            String extMsg = name + " = " + hexify(value);

            throw new SecureCryptoException(errMsg, extMsg);
        }

        if (maxLen > 0 && value.length > maxLen)
        {
            String errMsg = name + " is too long.  It should be ";
            if (minLen != maxLen)
            {
                errMsg += "at most ";
            }
            errMsg += maxLen + " bytes in length.";
            String extMsg = name + " = " + hexify(value);

            throw new SecureCryptoException(errMsg, extMsg);
        }
    }

    public static void checkNull(String name, Object value) throws SecureCryptoException
    {
        if (value == null)
        {
            throw new SecureCryptoException(name + " is null");
        }
    }


    private static void check(String name, String allowedChars, String value, int minLen, int maxLen, String badDataDescription)
        throws SecureCryptoException
    {
        checkLength(name, value, minLen, maxLen);

        for (char c : value.toCharArray())
        {
            if (allowedChars.indexOf(c) < 0)
            {
                throw new SecureCryptoException(name + " " + badDataDescription,
                        name + ": " + value);
            }
        }
    }

    private static void check(String name, String allowedChars, byte[] value, int minLen, int maxLen, String badDataDescription)
        throws SecureCryptoException
    {
        checkLength(name, value, minLen, maxLen);

        for (byte b : value)
        {
            char c = (char)b;

            if (allowedChars.indexOf(c) < 0)
            {
                throw new SecureCryptoException(name + " " + badDataDescription,
                        name + ": 0x" + hexify(value));
            }
        }
    }

    private static String hexify(byte[] value)
    {
        return ByteArrayUtilities.stringify_nospaces(value);
    }
}
