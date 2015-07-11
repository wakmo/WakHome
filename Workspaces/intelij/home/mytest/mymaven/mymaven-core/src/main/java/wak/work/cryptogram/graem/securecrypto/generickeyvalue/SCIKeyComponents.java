/*
 *
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SCIKeyComponents.java %
 *  Created:	 Tue Oct 21 16:09:01 2003
 *  Created By:	 %created_by:  build %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  SCIKeyComponents.java~4:java:UKPMA#1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */

package wak.work.cryptogram.graem.securecrypto.generickeyvalue;

public class SCIKeyComponents implements java.io.Serializable
{
        static final long serialVersionUID = -6161163233731451686L;

	public static final int COMPONENT_COMPLETE    = 0;
	public static final int COMPONENT_1           = 1;
	public static final int COMPONENT_2           = 2;
	public static final int COMPONENT_3           = 3;

	private static String[] names = { "COMPLETE", "1", "2", "3" };

	public static String translate(int component) {
		String ret;
		if (isValid(component))
			ret = names[component];
		else
			ret = "Invalid Key Component " + component;
		return ret;
	}

	public static int translate(String component) throws SCIKeyException{
			int ret = -1;
			for (int x=0; x<names.length; x++) {
				if (component.equals(names[x])) {
					ret = x;
					break;
				}
			}
			if (ret < 0)
				throw new SCIKeyException("Invalid Key Component " + component, null);
			return ret;
		}

	public static boolean isValid(int component) {
		boolean ret = true;
		if ( (component < 0) || (component >= names.length) )
			ret = false;
		return ret;
	}
}