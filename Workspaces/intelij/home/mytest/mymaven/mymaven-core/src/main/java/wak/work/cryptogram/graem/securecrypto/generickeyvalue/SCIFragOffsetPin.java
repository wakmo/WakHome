/*
 *
 * 
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SCIFragOffsetPin.java %
 *  Created:	 Thu Nov 13 23:04:24 2003
 *  Created By:	 %created_by:  rotondia %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  SCIFragOffsetPin.java~1:java:UKPMA#1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *  
 *
 * 
 *
 */
/*
 *
 *  � 2001 Datacard Platform Seven Limited.  All rights reserved.
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   FragOffsetPin.java %
 *  Created:	 18 February 2002 14:13:14
 *  Created By:	 %created_by:  izzardr %
 *  Last modified:	 %date_modified:  18 February 2002 14:13:14 %
 *  CI Idenitifier:	 %full_filespec:  FragOffsetPin.java~1:java:1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */
package wak.work.cryptogram.graem.securecrypto.generickeyvalue;

/**
  * Special type of fragment offset for pins.
  *
  * @author         %derived_by: izzardr %
  *
  *
  * @version 		%version:  1 %
  * @since
  * @see
  *
  */
public class SCIFragOffsetPin extends SCIFragOffsetBinaryBlock {
  /** Creates new FragOffsetPin
   * @param offset Position within the image to store the data.
   * @param encData Encrypted data to be stored.
   * @param clearData Clear data to be stored.
   */
  public SCIFragOffsetPin(int offset, byte[] encData, byte[] clearData) {
	super(offset, SCIFragmentType.PIN, encData, clearData);
  }

  /** Creates new FragOffsetPin
   * @param offset Position within the image to store the data.
   * @param encData Encrypted data to be stored.
   * @param clearData Clear data to be stored.
   * @param itemStart Offset within the data item to start from.
   * @param itemLength  Length of the data to store in the image.
  */
  public SCIFragOffsetPin(int offset, byte[] encData, byte[] clearData, int itemStart, int itemLength) {
	super(offset, SCIFragmentType.PIN, encData, clearData, itemStart, itemLength);
  }
}