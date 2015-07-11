/*
 *

 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   FragOffsetBinaryBlock.java %
 *  Created:	 18 February 2002 14:13:59
 *  Created By:	 %created_by:  aingerr %
 *  Last modified:	 %date_modified:  18 February 2002 14:13:59 %
 *  CI Idenitifier:	 %full_filespec:  FragOffsetBinaryBlock.java~6:java:UKPMA#1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */
package wak.work.cryptogram.graem.securecrypto;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
  * FragOffsetBinaryBlock represents any complex data items that are added as fragements
  * so far this relates only to DATA and PIN's
  *
  * @author         %derived_by: aingerr %
  *
  *
  * @version 		%version:  6 %
  * @since
  * @see
  *
  */
public abstract class FragOffsetBinaryBlock extends FragOffset {

  private byte [] encData;
  private byte[] clearData;
  private int itemStart=-1;
  private int itemLength=-1;
  private boolean hasExtraData;
  private byte checkByte;
  boolean checkByteSet = false;

  public FragOffsetBinaryBlock() {}

  /** Creates new FragOffsetBinaryBlock
   * @param offset Position within the image to store the data.
   * @param fType Fragment type either DATA or PIN.
   * @param encData Encrypted data to be stored.
   * @param clearData Clear data to be stored.
   */
  public FragOffsetBinaryBlock(int offset, FragmentType fType, byte[] encData, byte[] clearData) {
    super(offset, fType);
    this.encData = encData;
    this.clearData = clearData;
    hasExtraData = false;
  }

  /** Creates new FragOffsetBinaryBlock
   * @param offset Position within the image to store the data.
   * @param fType Fragment type either DATA or PIN.
   * @param encData Encrypted data to be stored.
   * @param clearData Clear data to be stored.
   * @param itemStart Offset within the data item to start from.
   * @param itemLength  Length of the data to store in the image.
  */
  public FragOffsetBinaryBlock(int offset, FragmentType fType, byte[] encData, byte[] clearData, int itemStart, int itemLength) {
    this(offset, fType, encData, clearData);
    setItemStart(itemStart);
    setItemLength(itemLength);
    hasExtraData = true;
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

  /** Set the optional check byte of the data to store within the image.
   * @param checkByte  Check Byte of the data to store in the image.
   */
  public void setCheckByte (byte checkByte) {
    this.checkByte = checkByte;
    checkByteSet = true;
  }

  String getEncData() {
    return SecureCryptoUtils.toHexString(encData, false);
  }

  public String getClearData() {
    String cd = null;
    if (clearData!=null) {
      cd = SecureCryptoUtils.toHexString(clearData, false);
    }
    return cd;
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
	
  public boolean gethasExtraData()
  {
  	return hasExtraData;
  }


  public int getItemStartVal() {
    return itemStart;
  }

  public int getItemLengthVal() {
    return itemLength;
  }

	public boolean getCheckByteset() 
	{
		return  checkByteSet;
	}
 
 	public byte getCheckByteb()
 	{
 		return  checkByte;
 	}
 


  /*
  private byte [] encData;
  private byte[] clearData;
  private int itemStart=-1;
  private int itemLength=-1;
  private byte checkByte;
  private boolean hasExtraData;
  boolean checkByteSet = false;
  */

  public void writeExternal(ObjectOutput out) throws IOException
  {
    // super.writeExternal(out);
		out.writeInt(offset);
		out.writeUTF(type.toString());
		if (encData!=null)
				{
					out.writeInt(encData.length);
					ByteClass encDatab = new ByteClass(encData, encData.length);
					out.writeObject(encDatab);
				}
				else
					out.writeInt(0);
		
			if (clearData!=null)
					{
						out.writeInt(clearData.length);
						ByteClass clearDatab = new ByteClass(clearData, clearData.length);
						out.writeObject(clearDatab);
					}
					else
						out.writeInt(0);
					
    
    
    out.writeBoolean(hasExtraData);
    if (hasExtraData)
    {
      out.writeInt(itemStart);
      out.writeInt(itemLength);
    }

		out.writeBoolean(checkByteSet);
  	 if (checkByteSet)
	 			out.writeByte(checkByte);
  }

  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
  {
    // super.readExternal(in);
		offset = in.readInt();
		type = FragmentType.getFragmentType(in.readUTF());
    
		int encDataSize = in.readInt();
			if (encDataSize>0)
			{
				ByteClass encDatab = (ByteClass) in.readObject();
				encData = encDatab.getByte();
			}
    
		int clearDataSize = in.readInt();
				if (clearDataSize>0)
				{
					ByteClass clearDatab = (ByteClass) in.readObject();
					clearData = clearDatab.getByte();
				}
    
    
    hasExtraData = in.readBoolean();
    if (hasExtraData)
    {
      itemStart = in.readInt();
      itemLength = in.readInt();
    }
    checkByteSet = in.readBoolean();
    if (checkByteSet)
      checkByte = in.readByte();
  }
}
