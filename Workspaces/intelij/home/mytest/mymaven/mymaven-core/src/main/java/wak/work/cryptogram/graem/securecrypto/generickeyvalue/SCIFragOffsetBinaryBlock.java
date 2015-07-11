/*
 *

 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SCIFragOffsetBinaryBlock.java %
 *  Created:	 Thu Nov 13 23:02:15 2003
 *  Created By:	 %created_by:  build %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  SCIFragOffsetBinaryBlock.java~2:java:UKPMA#1 %
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
 *  Name:		 %name:   SCIFragOffsetBinaryBlock.java %
 *  Created:	 18 February 2002 14:13:59
 *  Created By:	 %created_by:  build %
 *  Last modified:	 %date_modified:  18 February 2002 14:13:59 %
 *  CI Idenitifier:	 %full_filespec:  SCIFragOffsetBinaryBlock.java~2:java:UKPMA#1 %
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
  * FragOffsetBinaryBlock represents any complex data items that are added as fragements
  * so far this relates only to DATA and PIN's
  *
  * @author         %derived_by: build %
  *
  *
  * @version 		%version:  2 %
  * @since
  * @see
  *
  */
public abstract class SCIFragOffsetBinaryBlock extends SCIFragOffset {

  private byte [] encData;
  private byte[] clearData;
  private int itemStart=-1;
  private int itemLength=-1;
  private byte checkByte;

  boolean checkByteSet = false;

  /** Creates new FragOffsetBinaryBlock
   * @param offset Position within the image to store the data.
   * @param fType Fragment type either DATA or PIN.
   * @param encData Encrypted data to be stored.
   * @param clearData Clear data to be stored.
   */
  public SCIFragOffsetBinaryBlock(int offset, SCIFragmentType fType, byte[] encData, byte[] clearData) {
	super(offset, fType);

	this.encData = encData;
	this.clearData = clearData;
  }

  /** Creates new FragOffsetBinaryBlock
   * @param offset Position within the image to store the data.
   * @param fType Fragment type either DATA or PIN.
   * @param encData Encrypted data to be stored.
   * @param clearData Clear data to be stored.
   * @param itemStart Offset within the data item to start from.
   * @param itemLength  Length of the data to store in the image.
  */
  public SCIFragOffsetBinaryBlock(int offset, SCIFragmentType fType, byte[] encData, byte[] clearData, int itemStart, int itemLength) {
	this(offset, fType, encData, clearData);
	setItemStart(itemStart);
	setItemLength(itemLength);
  }

  /** Set the optional start offset.
   * @param itemStart Offset within the data item to start from.
   */
  public void setItemStart(int itemStart) {
	this.itemStart = itemStart;
  }

  /** Set the optional length of the data to stroe within the image.
   * @param itemLength  Length of the data to store in the image.
   */
  public void setItemLength (int itemLength) {
	this.itemLength = itemLength;
  }

  /** Set the optional check byte of the data to stroe within the image.
   * @param checkByte  Check Byte of the data to store in the image.
   */
  public void setCheckByte (byte checkByte) {
	this.checkByte = checkByte;
	checkByteSet = true;
  }

  public byte[] getEncData() {
	return encData;
  }

  public byte[] getClearData() {
	return clearData;
  }

  public String getItemStart() {
	String ret = null;

	if (itemStart>-1) {
	  ret = Integer.toString(itemStart);
	}

	return ret;
  }

  public String getItemLength() {
	String ret = null;

	if (itemLength>-1) {
	  ret = Integer.toString(itemLength);
	}

	return ret;
  }

  public String getCheckByte() {
	String ret = null;

	if (checkByteSet) {
	  ret = Byte.toString(checkByte);
	}
	return ret;
  }

  public byte[] getEncDataBytes() {
	return encData;
  }

  public byte[] getClearDataBytes() {
	return clearData;
  }

  public int getItemStartVal() {
	return itemStart;
  }

  public int getItemLengthVal() {
	return itemLength;
  }

}