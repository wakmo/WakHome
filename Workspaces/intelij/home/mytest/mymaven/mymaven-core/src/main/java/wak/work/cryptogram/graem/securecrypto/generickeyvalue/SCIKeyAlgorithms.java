/*
 *
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SCIKeyAlgorithms.java %
 *  Created:	 Tue Oct 21 15:04:28 2003
 *  Created By:	 %created_by:  gopalac %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  SCIKeyAlgorithms.java~INPMA#7:java:UKPMA#1 %
 *  CI Idenitifier:	 %full_filespec:  SCIKeyAlgorithms.java~INPMA#7:java:UKPMA#1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */

package wak.work.cryptogram.graem.securecrypto.generickeyvalue;

public class SCIKeyAlgorithms implements java.io.Serializable
{
        static final long serialVersionUID = 1371845394372324336L;

	public static final int DES1E       = 0;
	public static final int DES1D       = 1;
	public static final int DES2EDE     = 2;
	public static final int DES2EEE     = 3;
	public static final int DES2DDD     = 4;
	public static final int DES3EDE     = 5;
	public static final int DES3EEE     = 6;
	public static final int RSA     	= 7;//added to aid generateasymmetric key action command changes for software sce
	public static final int RSA_ALL     = 8;
	public static final int RSA_CRT     = 9;
	public static final int RSA_MODEXP  = 10;

	public static String[] names = { "DES1E",
                                         "DES1D",
                                         "DES2EDE",
                                         "DES2EEE",
                                         "DES2DDD",
                                         "DES3EDE",
                                         "DES3EEE",
                                         "RSA",//added to aid generateasymmetric key action command changes for software sce
                                         "RSA_ALL",
                                         "RSA_CRT",
                                         "RSA_MODEXP" };

	public static String translate(int algorithm) {
		String ret;
		if (isValid(algorithm))
			ret = names[algorithm];
		else
			ret = "Invalid SCI Key Algorithm " + algorithm;
		return ret;
	}

	public static int translate(String algorithm) throws SCIKeyException{
		int ret = -1;
		for (int x=0; x<names.length; x++) {
			if (algorithm.equals(names[x])) {
				ret = x;
				break;
			}
		}
		if (ret < 0)
			throw new SCIKeyException("Invalid Key Algorithm " + algorithm, null);
		return ret;
	}

	public static boolean isValid(int algorithm) {
			boolean ret = true;
			if ( (algorithm < 0) || (algorithm >= names.length) )
				ret = false;
			return ret;
	}

	public static boolean isRSA(int alg) {
		return (alg >= SCIKeyAlgorithms.RSA && alg <= SCIKeyAlgorithms.RSA_MODEXP);//added to aid generateasymmetric key action command changes for software sce
	}
}
