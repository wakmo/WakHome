/*
 *
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SCIKeyUsages.java %
 *  Created:	 Tue Oct 21 15:05:17 2003
 *  Created By:	 %created_by:  build %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  SCIKeyUsages.java~4:java:UKPMA#1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */

package wak.work.cryptogram.graem.securecrypto.generickeyvalue;

public class SCIKeyUsages implements java.io.Serializable
{
        static final long serialVersionUID = -344560677526810433L;

	public static final int BDK             = 0;
	public static final int ECK             = 1;
	public static final int DCK             = 2;
	public static final int KDK             = 3;
	public static final int TRK             = 4;
	public static final int VEK             = 5;

	private static String[] names = {"BDK", "ECK", "DCK", "KDK", "TRK", "VEK" };

	public static String translate(int usage) {
		String ret;
		if (isValid(usage))
			ret = names[usage];
		else
			ret = "Unidentified Key Usage " + usage;
		return ret;
	}

	public static int translate(String usage) throws SCIKeyException{
		int ret = -1;
		for (int x=0; x<names.length; x++) {
			if (usage.equals(names[x])) {
				ret = x;
				break;
			}
		}
		if (ret < 0)
			throw new SCIKeyException("Invalid Key Usage " + usage, null);
		return ret;
	}

	public static boolean isValid(int usage) {
			boolean ret = true;
			if ( (usage < 0) || (usage >= names.length) )
				ret = false;
			return ret;
		}
}