package wak.work.cryptogram.graem.securecrypto;

public enum PKIKeyType
{
    Signature("Signature", '0'),
    Key_management("Key Management", '1'),
    Signature_and_Key_Management("Signature & Key Management", '2'),
    Type3("Type 3 (for EMV)", '3');


    private final String description;
    private final char ps9000TypeCode;

    PKIKeyType(String description, char ps9000TypeCode)
    {
        this.description = description;
        this.ps9000TypeCode = ps9000TypeCode;
    }

    public static PKIKeyType getFrom(String str) throws SecureCryptoException
    {
        for (PKIKeyType v : values())
        {
            if (v.description.equals(str))
            {
                return v;
            }
        }

        throw new SecureCryptoException("Cannot get PKIKeyType from String: " + str);
    }

    public static PKIKeyType getFrom(char ps9000TypeCode) throws SecureCryptoException
    {
        for (PKIKeyType v : values())
        {
            if (v.ps9000TypeCode == ps9000TypeCode)
            {
                return v;
            }
        }

        throw new SecureCryptoException("Cannot get PKIKeyType from PS9000 type code char: " + ps9000TypeCode);
    }

    public String toString()
    {
        return description;
    }

    public char getPs9000TypeCode()
    {
        return ps9000TypeCode;
    }
}