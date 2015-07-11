/*
 *
 
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   EncDec.java %
 *  Created:	 08 February 2002 11:20:24
 *  Created By:	 %created_by:  izzardr %
 *  Last modified:	 %date_modified:  15 February 2002 10:08:14 %
 *  CI Idenitifier:	 %full_filespec:  EncDec.java~3:java:1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */

 /*
 * EncDec.java
 *
 * Created on 06 February 2002, 11:42
 */

package wak.work.cryptogram.graem.securecrypto;

/**
  * This class provides an indication of cipherment direction.
  *
  * @author         %derived_by: izzardr %
  *
  *
  * @version 		%version:  3 %
  * @since
  * @see
  *
  */

public class EncDec {

    private String direction;

    /**
     * Encryption required.
     */
    public static final EncDec encrypt = new EncDec("Encrypt");
    /**
     * Decryption required.
     */
    public static final EncDec decrypt = new EncDec("Decrypt");

    /** Creates new EncDec */
    private EncDec(String dir) {
        direction = dir;
    }

    public String toString() {
        return direction;
    }

    public static EncDec getEncDec(String str) {
      EncDec ed = null;
      if (str.equals("Encrypt")) {
        ed = encrypt;
      }
      else if (str.equals("Decrypt")) {
        ed = decrypt;
      }
      return ed;
    }
}