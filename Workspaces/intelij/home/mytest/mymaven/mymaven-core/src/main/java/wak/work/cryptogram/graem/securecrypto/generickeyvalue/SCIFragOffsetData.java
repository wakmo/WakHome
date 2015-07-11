/*
 *
 * 
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SCIFragOffsetData.java %
 *  Created:	 Thu Nov 13 23:03:53 2003
 *  Created By:	 %created_by:  rotondia %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  SCIFragOffsetData.java~1:java:UKPMA#1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *  
 *
 * 
 *
 */
/*
 * FragData.java
 *
 * Created on 24 October 2001, 09:56
 */

package wak.work.cryptogram.graem.securecrypto.generickeyvalue;

/** Special type of fragment offset for general encrypted data items.
 * @author Richard Izzard
 * @version 1.0
 */

public class SCIFragOffsetData extends SCIFragOffsetBinaryBlock {

  /** Creates new FragOffsetData
   * @param offset Position within the image to store the data.
   * @param encData Encrypted data to be stored.
   * @param clearData Clear data to be stored.
   */
  public SCIFragOffsetData(int offset, byte[] encData, byte[] clearData) {
	super(offset, SCIFragmentType.DATA, encData, clearData);
  }

  /** Creates new FragOffsetData
   * @param offset Position within the image to store the data.
   * @param encData Encrypted data to be stored.
   * @param clearData Clear data to be stored.
   * @param itemStart Offset within the data item to start from.
   * @param itemLength  Length of the data to store in the image.
  */
  public SCIFragOffsetData(int offset, byte[] encData, byte[] clearData, int itemStart, int itemLength) {
	super(offset, SCIFragmentType.DATA, encData, clearData, itemStart, itemLength);
  }
}