/*
 *

 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   FragOffsetPin.java %
 *  Created:	 18 February 2002 14:13:14
 *  Created By:	 %created_by:  build %
 *  Last modified:	 %date_modified:  18 February 2002 14:13:14 %
 *  CI Idenitifier:	 %full_filespec:  FragOffsetPin.java~2:java:UKPMA#1 %
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
  * Special type of fragment offset for pins.
  *
  * @author         %derived_by: build %
  *
  *
  * @version 		%version:  2 %
  * @since
  * @see
  *
  */
public class FragOffsetPin extends FragOffsetBinaryBlock
{
  public FragOffsetPin() {}

  /** Creates new FragOffsetPin
   * @param offset Position within the image to store the data.
   * @param encData Encrypted data to be stored.
   * @param clearData Clear data to be stored.
   */
  public FragOffsetPin(int offset, byte[] encData, byte[] clearData) {
    super(offset, FragmentType.PIN, encData, clearData);
  }

  /** Creates new FragOffsetPin
   * @param offset Position within the image to store the data.
   * @param encData Encrypted data to be stored.
   * @param clearData Clear data to be stored.
   * @param itemStart Offset within the data item to start from.
   * @param itemLength  Length of the data to store in the image.
  */
  public FragOffsetPin(int offset, byte[] encData, byte[] clearData, int itemStart, int itemLength) {
    super(offset, FragmentType.PIN, encData, clearData, itemStart, itemLength);
  }
}