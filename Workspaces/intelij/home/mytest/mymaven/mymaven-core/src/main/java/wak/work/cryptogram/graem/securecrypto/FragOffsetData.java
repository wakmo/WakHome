/*
 * FragData.java
 *
 * Created on 24 October 2001, 09:56
 */

package wak.work.cryptogram.graem.securecrypto;

/** Special type of fragment offset for general encrypted data items.
 * @author Richard Izzard
 * @version 1.0
 */

public class FragOffsetData extends FragOffsetBinaryBlock {

  public FragOffsetData(){}

  /** Creates new FragOffsetData
   * @param offset Position within the image to store the data.
   * @param encData Encrypted data to be stored.
   * @param clearData Clear data to be stored.
   */
  public FragOffsetData(int offset, byte[] encData, byte[] clearData) {
    super(offset, FragmentType.DATA, encData, clearData);
  }

  /** Creates new FragOffsetData
   * @param offset Position within the image to store the data.
   * @param encData Encrypted data to be stored.
   * @param clearData Clear data to be stored.
   * @param itemStart Offset within the data item to start from.
   * @param itemLength  Length of the data to store in the image.
  */
  public FragOffsetData(int offset, byte[] encData, byte[] clearData, int itemStart, int itemLength) {
    super(offset, FragmentType.DATA, encData, clearData, itemStart, itemLength);
  }
}
