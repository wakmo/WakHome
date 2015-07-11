/*
 *
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SCIKeyTypes.java %
 *  Created:	 Tue Oct 21 15:22:32 2003
 *  Created By:	 %created_by:  build %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  SCIKeyTypes.java~4:java:UKPMA#1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */

package wak.work.cryptogram.graem.securecrypto.generickeyvalue;

public class SCIKeyTypes implements java.io.Serializable
{
    static final long serialVersionUID = -2234495384922233397L;

    //Private key
    public static final int PROTECTED = 0;

    //Public Key
    public static final int AUTHENTIC = 1;

    private static String[] names = {"PROTECTED", "AUTHENTIC"};


    public static String translate(int type)
    {
        String ret;
        if (isValid(type))
        {
            ret = names[type];
        }
        else
        {
            ret = "Invalid Key Type: " + type;
        }

        return ret;
    }

    public static int translate(String type) throws SCIKeyException
    {
        int ret = -1;
        for (int x = 0; x < names.length; x++)
        {
            if (type.equals(names[x]))
            {
                ret = x;
                break;
            }
        }

        if (ret < 0)
        {
            throw new SCIKeyException("Invalid Key Type: " + type, null);
        }

        return ret;
    }

    public static boolean isValid(int type)
    {
        return (type >= 0) && (type < names.length);
    }
}