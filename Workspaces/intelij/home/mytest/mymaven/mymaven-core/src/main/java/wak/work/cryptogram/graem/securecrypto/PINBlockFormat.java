/*
	@author Sunil Krishnamurthy
*/

package wak.work.cryptogram.graem.securecrypto;

import java.io.Serializable;

public class PINBlockFormat implements Serializable
{
    public static final PINBlockFormat FORMAT_0 = new PINBlockFormat(1, "FORMAT_0", "01");
    public static final PINBlockFormat FORMAT_1 = new PINBlockFormat(5, "FORMAT_1", "05");
    public static final PINBlockFormat FORMAT_3 = new PINBlockFormat(47, "FORMAT_3", "47");

    private static final PINBlockFormat[] ALL_FORMATS = new PINBlockFormat[]
            {
                    FORMAT_0, FORMAT_1, FORMAT_3
            };


    private final int value;
    private final String description;
    private final String thalesCode;


    private PINBlockFormat(int value, String description, String thalesCode)
    {
        this.value = value;
        this.description = description;
        this.thalesCode = thalesCode;
    }

    public String toString()
    {
        return description;
    }

    public int getValue()
    {
        return value;
    }

    public String getThalesCode()
    {
        return thalesCode;
    }

    public byte[] getThalesCodeBytes()
    {
        return thalesCode.getBytes();
    }

    public boolean equals(Object obj)
    {
        boolean equal = false;

        if (obj != null && obj instanceof PINBlockFormat)
        {
            PINBlockFormat pbf = (PINBlockFormat)obj;
            equal = (pbf.value == value);
        }

        return equal;
    }

    public static PINBlockFormat getPINBlockFormat(int value) throws IllegalArgumentException
    {
        PINBlockFormat retVal = null;
        for (PINBlockFormat f : ALL_FORMATS)
        {
            if (f.value == value)
            {
                retVal = f;
            }
        }

        if (retVal == null)
        {
            throw new IllegalArgumentException("Invalid value for PINBlockFormat: " + value);
        }

        return retVal;
    }
}
