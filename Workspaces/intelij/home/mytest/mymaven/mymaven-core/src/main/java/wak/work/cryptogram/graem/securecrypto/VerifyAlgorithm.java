/*
 *
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   VerifyAlgorithm.java %
 *  Created:	 18 February 2002 13:27:58
 *  Created By:	 %created_by:  izzardr %
 *  Last modified:	 %date_modified:  28 February 2002 13:42:24 %
 *  CI Idenitifier:	 %full_filespec:  VerifyAlgorithm.java~4:java:2 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */

package wak.work.cryptogram.graem.securecrypto;

/**
  * Represents the verification algorithms that may be used within SCI for public keys.
  *
  * @author         %derived_by: izzardr %
  *
  *
  * @version 		%version:  4 %
  * @since
  * @see
  *
  */

public class VerifyAlgorithm {
  private String[] algDetails = new String[] {"MULTOS3.4",
                                              "MULTOS4",
                                              "X.509"};

  private int index;

  /**
   * MULTOS3.4 verification algorithm.
   */
  public static final VerifyAlgorithm MULTOS3_4 = new VerifyAlgorithm(0);

  /**
   * MULTOS4 verification algorithm.
   */

  public static final VerifyAlgorithm MULTOS4   = new VerifyAlgorithm(1);

  /**
   * X.509 verification algorithm.
   */
  public static final VerifyAlgorithm X509      = new VerifyAlgorithm(2);

  private VerifyAlgorithm(int index){
    this.index = index;
  }

  public String toString() {
    return algDetails[index];
  }

  public static VerifyAlgorithm getVerifyAlgorithm(String str) {
    VerifyAlgorithm va = null;

    if (str.equals("MULTOS3.4")) {
      va = MULTOS3_4;
    }
    else if (str.equals("MULTOS4")) {
      va = MULTOS4;
    }
    else if (str.equals("X.509")) {
      va = X509;
    }

    return va;
  }
}