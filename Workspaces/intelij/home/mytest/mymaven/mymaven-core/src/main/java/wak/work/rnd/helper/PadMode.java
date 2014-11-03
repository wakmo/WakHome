/*
 * PadMode.java
 *
 * Created on 02 November 2001, 09:45
 */

package wak.work.rnd.helper;

/**
 * @author andreas.heilemann
 */
public enum PadMode
{
    NoPadding
            {
                @Override
                public String toString()
                {
                    return "None";
                }
            },
    PKCS1
            {
                @Override
                public String toString()
                {
                    return "PKCS#1";
                }
            },
    L0_00,
    L1_00,
    L2_00,
    L0_80,
    L1_80,
    L2_80;


    public static PadMode getPadMode(String readUTF)
    {
        if (readUTF.equals("None"))
        {
            return NoPadding;
        }
        else if (readUTF.equals("PKCS#1"))
        {
            return PKCS1;
        }
        else
        {
            return valueOf(readUTF);
        }
    }
}