/*
 *
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SCISecureModules.java %
 *  Created:	 Tue Oct 21 15:22:23 2003
 *  Created By:	 %created_by:  build %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  SCISecureModules.java~6:java:UKPMA#1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */

package wak.work.cryptogram.graem.securecrypto.generickeyvalue;

public class SCISecureModules implements java.io.Serializable
{
    static final long serialVersionUID = -4381967374067754412L;

    public static final int SCE = 0;
    public static final int RG7000 = 1;
    public static final int FAKE = 2;
    public static final int NCIPHER = 3;
    public static final int PS9000 = 4;

    // TODO Use these names also in all implementations of SecureCrypto.getModuleName() 
    private static final String[] NAMES = {"SCE", "RG7000", "Fake", "nCipher.sWorld", "PS9000"};

    public static boolean isValid(int sm)
    {
        boolean ret = true;
        if ((sm < 0) || (sm >= NAMES.length))
        {
            ret = false;
        }
        return ret;
    }

    public static boolean isValid(String smName)
    {
        boolean ret = false;
        for (final String name : NAMES)
        {
            ret |= smName.equals(name);
        }
        return ret;
    }

    public static String translate(int sm)
    {
        String ret;
        if (isValid(sm))
        {
            ret = NAMES[sm];
        }
        else
        {
            ret = "Invalid security module ID - " + sm;
        }
        return ret;
    }

    public static int translate(String smName) throws SCIKeyException
    {
        int ret = -1;
        for (int x = 0; x < NAMES.length; x++)
        {
            if (smName.equals(NAMES[x]))
            {
                ret = x;
                break;
            }
        }

        if (ret < 0)
        {
            throw new SCIKeyException("Invalid security module name - " + smName, null);
        }

        return ret;
    }
}
