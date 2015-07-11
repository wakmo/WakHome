/*
 *
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SCIFragmentType.java %
 *  Created:	 Thu Nov 13 20:15:20 2003
 *  Created By:	 %created_by:  build %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  SCIFragmentType.java~3:java:UKPMA#1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */
/*
 * Fragment.java
 *
 * Created on 23 October 2001, 16:14
 */

package wak.work.cryptogram.graem.securecrypto.generickeyvalue;

/** This class provides all the acceptable fragment types.
 * @author Richard Izzard
 * @version 1.0
 */

public class SCIFragmentType {

  public static final String [] fragmentDescriptor = new String []{"WHOLE",
                                                                   "KEY1",
                                                                   "KEY2",
                                                                   "KEY3",
                                                                   "N",
                                                                   "D",
                                                                   "E",
                                                                   "P",
                                                                   "Q",
                                                                   "DP",
                                                                   "DQ",
                                                                   "U",
                                                                   "DATA",
                                                                   "PIN",
                                                                   "PIN_0"};

  public static final int _WHOLE = 0;
  public static final int _KEY1  = 1;
  public static final int _KEY2  = 2;
  public static final int _KEY3  = 3;
  public static final int _N     = 4;
  public static final int _D     = 5;
  public static final int _E     = 6;
  public static final int _P     = 7;
  public static final int _Q     = 8;
  public static final int _DP    = 9;
  public static final int _DQ    = 10;
  public static final int _U     = 11;
  public static final int _DATA  = 12;
  public static final int _PIN   = 13;
  public static final int _PIN_0 = 14;

  /** Represents fragment type WHOLE.
   */
  public static final SCIFragmentType WHOLE = new SCIFragmentType(_WHOLE);
  /** Represents fragment type KEY 1.
   * Which is the first key in a single, double or triple length DES key.
   */
  public static final SCIFragmentType KEY1  = new SCIFragmentType(_KEY1);
  /** Represents fragment type KEY 2.
   * Which is the second key in a double or triple length DES key.
   */
  public static final SCIFragmentType KEY2  = new SCIFragmentType(_KEY2);
  /** Represents fragment type KEY 3.
   * Which is the third key in a triple length DES key.
   */
  public static final SCIFragmentType KEY3  = new SCIFragmentType(_KEY3);
  /** Represents fragment type N.
   * Which is the N component of an RSA key.
   */
  public static final SCIFragmentType N     = new SCIFragmentType(_N);
  /** Represents fragment type D.
   * Which is the D component of an RSA key.
   */
  public static final SCIFragmentType D     = new SCIFragmentType(_D);
  /** Represents fragment type E.
   * Which is the E component of an RSA key.
   */
  public static final SCIFragmentType E     = new SCIFragmentType(_E);
  /** Represents fragment type P.
   * Which is the P component of an RSA key.
   */
  public static final SCIFragmentType P     = new SCIFragmentType(_P);
  /** Represents fragment type Q.
   * Which is the Q component of an RSA key.
   */
  public static final SCIFragmentType Q     = new SCIFragmentType(_Q);
  /** Represents fragment type DP.
   * Which is the DP component of an RSA key.
   */
  public static final SCIFragmentType DP    = new SCIFragmentType(_DP);
  /** Represents fragment type DQ.
   * Which is the DQ component of an RSA key.
   */
  public static final SCIFragmentType DQ    = new SCIFragmentType(_DQ);
  /** Represents fragment type U.
   * Which is the U component of an RSA key.
   */
  public static final SCIFragmentType U     = new SCIFragmentType(_U);
  static final SCIFragmentType DATA         = new SCIFragmentType(_DATA);
  static final SCIFragmentType PIN          = new SCIFragmentType(_PIN);
  static final SCIFragmentType PIN_0        = new SCIFragmentType(_PIN_0);

  private int fragment;

  /** Creates new Fragment */
  private SCIFragmentType(int fragment) {
	this.fragment = fragment;
  }

  public int getValue() {
	return fragment;
  }

  /** Converts fragment type to a String representation.
   * @return Fragment type as a string.
   */
  public String toString(){
	return fragmentDescriptor[fragment];
  }

  public static SCIFragmentType getFragmentType(String fType) {
	SCIFragmentType ft = null;
	for (int f=0; f<fragmentDescriptor.length && ft==null; f++) {
	  if (fragmentDescriptor[f].equals(fType)){
		ft = getFragmentType(f);
	  }
	}
	return ft;
  }

  public static SCIFragmentType getFragmentType(int index) {
	SCIFragmentType ft = null;
	switch (index){
	  case _WHOLE:
		ft = WHOLE;
	  break;
	  case _KEY1:
		ft = KEY1;
	  break;
	  case _KEY2:
		ft = KEY2;
	  break;
	  case _KEY3:
		ft = KEY3;
	  break;
	  case _N:
		ft = N;
	  break;
	  case _D:
		ft = D;
	  break;
	  case _E:
		ft = E;
	  break;
	  case _P:
		ft = P;
	  break;
	  case _Q:
		ft = Q;
	  break;
	  case _DP:
		ft = DP;
	  break;
	  case _DQ:
		ft = DQ;
	  break;
	  case _U:
		ft = U;
	  break;
	  case _DATA:
		ft = DATA;
	  break;
	  case _PIN:
		ft = PIN;
	  break;
	  case _PIN_0:
		ft = PIN_0;
	  break;
	}
	return ft;
  }
}