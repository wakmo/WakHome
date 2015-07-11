/*
 * Created on 22-Jan-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package wak.work.cryptogram.graem.securecrypto;

/**
 * @author aingerr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class ByteClass implements java.io.Serializable
{
	byte[] data;
	int	size;
	
	public ByteClass(byte[] byteData, int byteSize)
	{
		this.data = byteData;
		this.size = byteSize;
	}
	
	public byte[] getByte()
	{
		return data;
	}
	public int getSize()
	{
		return size;
	}
	


}