/*
 *
 *  Copyright (C) 1999 NatWest Bank PLC. All rights reserved.
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:                   java
 *  Created:                11 November 1999 18:10:10
 *  Last modified:          11 November 1999 18:10:10
 *
 *  Amendment Record
 *  Ver     Date        Author  Description
 *
 *
 *  Continuus filename:     ByteArrayUtilities.java~1:java:2
 *
 */
package wak.work.cryptogram.helper;

/**
  * DESCRIPTION INSERTED HERE
  *
  * @author         AUTHOR INSERTED HERE
  *
  *
  * @version 		1
  * @since
  * @see
  *
  */

import org.apache.log4j.Logger;

import java.util.StringTokenizer;
import java.util.Vector;

/**
  * A collection of operations used to manipulate byte arrays.
  *
  * @author         M. Del Pellegrino
  *
  * @version 		2
  *
  */
public class ByteArrayUtilities
{
    private static final Logger log = Logger.getLogger(ByteArrayUtilities.class);


    /**
        *  getDERLengthBytes() - Creates the length bytes for a DER encoded object.
        *
        *  @param int offset  - The offset of the tag in the array.
        *
        *  @return byte[] - the DER encoded length bytes.
        */
    public static String getDERLength(int length)
    {
        byte[] lengthBytes = null;
        // Length
        if (length < 128)
        {
            lengthBytes = new byte[1];
            lengthBytes[0] = (byte)length;
        }
        else if (128 <= length & length < 256)
        {
            lengthBytes = new byte[2];
            lengthBytes[0] = (byte)0x81;
            lengthBytes[1] = (byte)length;
        }
        else
        {
            //logger.warn("Lengths > 256 not catered for!");           
            String message = "DER lengths could only cater upto 256";
            throw new IllegalArgumentException(message);
        }
        return ByteArrayUtilities.stringify_nospaces(lengthBytes);
    }
    
    public static String getInt2Hex(int length)
    {
        return Integer.toHexString(length);
    }

    public static String ByteToString(byte b)
    {
        return "" + (char)b;
    }


    public static byte[] stringToByteArray(String value)
    {
      byte[] ret = new byte[value.length()];
      for (int x=0; x<value.length(); x++) {
     		ret[x] = (byte)value.charAt(x);
      }
      return ret;
    }
    /**
     * Determine the number that an array of byte represent, given that element
     * 0 in the array is the most significant.
     *
     * @param   The array of bytes to be evaluated.
     *
     * @return  The bnumber repretesented by the pased byte array, as a long
     *
     * @exception   Throws an IllegalArgumentException if the array contains too
     *              many bytes (> 7).
     *
     * @author  M. Del Pellegrino
     * @since   1
     */
    public static long evaluate(byte[] bytes)
    {
        long            value = 0;
        UnsignedByte    ub;

        // limit it to 7 bytes which will fit in a signed long
        if(bytes.length < 8 && bytes.length > 0)
        {
            for(int loop = 0; loop < bytes.length; loop++)
            {
                ub = new UnsignedByte(bytes[loop]);
                value += ub.intValue() * Math.pow(256, (bytes.length - loop - 1));
            }
        }
        else
        {
            value = 0;

            String message = "Can only handle lengths represent between 1 to 7 bytes";
            throw new IllegalArgumentException(message);
        }

        return value;
    }



    /**
     * Returns a byte array containing the first supplied byte array, followed by the second, followed
     * by the third, and so on...
     *
     * @param arrays The byte arrays to concatenate
     * @return The byte array which is all the supplied byte arrays concatenated in order
     */
    public static byte[] addByteArrays(byte[]... arrays)
    {
        int length = 0;
        for(byte[] ba : arrays)
        {
            length += ba.length;
        }

        byte[] retArray = new byte[length];
        int pos = 0;

        for(byte[] ba : arrays)
        {
            System.arraycopy(ba, 0, retArray, pos, ba.length);
            pos += ba.length;
        }

        return retArray;
    }


    /**
     * Work out the byte representation of the passed number.  It is assumed
     * that the bytes are unsigned.
     *
     * @param   number  The ineteger to be represented as bytes.
     *
     * @return  A byte array that represents the passed number.
     *
     * @exception   Throws an IllegalArgumentException if an negative number is
     *              paseed.
     *
     * @author  M. Del Pellegrino
     * @since   1
     */
    public static byte[] renderToBytes(int length)
    {
        int             byteNumber;
        int             result, remainder, divisor;
        byte[]          asBytes;
        UnsignedByte    ub;
        Vector          theBytes    = new Vector();

        byteNumber = 1;

        if(length == 0)
        {
            asBytes     = new byte[1];
            asBytes[0]  = 0;
        }
        else if(length < 0)
        {
            throw new IllegalArgumentException("Cannot render negative numbers");
        }
        else
        {
            while(length > 0)
            {
                divisor     = (int)Math.pow(256, byteNumber);
                byteNumber++;

                result      = length / divisor;
                remainder   = length % divisor;
                ub          = new UnsignedByte(remainder);
                theBytes.addElement(ub);

                length      = result;
            }

            // Copy the answer into a byte array, in reverse order
            asBytes = new byte[theBytes.size()];

            for(int loop = 0; loop < theBytes.size(); loop++)
            {
                ub              = (UnsignedByte)theBytes.elementAt(asBytes.length - loop -1);
                asBytes[loop]   = ub.byteValue();
            }
        }

        return asBytes;
    }

    /**
     * Work out the byte representation of the passed number.  We assume bytes
     * are unsigned.  Bug out if the number of bytes is greater than the
     * passed max.
     *
     * @param   number  The ineteger to be represented as bytes.
     *
     * @return  A byte array that represents the passed number.
     *
     * @exception   Throws an IllegalArgumentException if an negative number is
     *              paseed.
     *
     * @exception   Throws an IllegalArgumentException if the number passed is
     *              too big to fit into the specified number of bytes.
     *
     *
     * @author  M. Del Pellegrino
     * @since   1
     */
    public static byte[] renderToBytes(int length, int maxBytes)
    {
        byte[] bytes = renderToBytes(length);

        if(bytes.length > maxBytes)
        {
            throw new IllegalArgumentException("Number to big to be represented in the given number of bytes");
        }

        return bytes;
    }

    /**
     * Turns the byte array passed into a human readable representation.  The
     * format is a string of hex. byte pairs. I.e.:<br>
     * Array {f,0,8,2,c,a,0} becomes :<br>
     * "0f 08 2c a0"
     *
     * @param   bytes   The bytes to be made readable.
     *
     * @return  A formatted string representing the passed bytes.
     *
     * @author  M. Del Pellegrino
     * @since   1
     */
    public static String stringify(byte[] bytes)
    {
        StringBuffer    sb = new StringBuffer();
        String          aByteString;

        if(bytes.length > 0)
        {
            aByteString = UnsignedByte.toHexString(bytes[0]);
            if(aByteString.length() == 1 )
            {
                sb.append('0');
            }
            sb.append(aByteString);

            for(int loop = 1; loop < bytes.length; loop++)
            {
                sb.append(' ');

                aByteString = UnsignedByte.toHexString(new UnsignedByte(bytes[loop]));
                if(aByteString.length() == 1)
                {
                    sb.append('0');
                }
                sb.append(aByteString);
            }
        }

        return new String(sb);
    }

    static public String stringify_nospaces(byte[] bytes)
    {
        final char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        if(bytes == null)
            return null;

        if(bytes.length == 0)
            return "";

        StringBuilder s = new StringBuilder(bytes.length << 1);

        for (byte b : bytes)
        {
            s.append(hexChars[(b & 0xF0) >> 4]);
            s.append(hexChars[b & 0x0F]);
        }

        return s.toString();
    }

    /**
     * Takes a String, which should contain hex formated numbers, and turns
     * them into a byte array.  The nubers should be separated by spaces.<br>
     * Example:<br>
     * "00 0b fa 11 1f c0"<br>
     * becomes:<br>
     * array { 0, 0, 0, b, f, a, 1, 1, 1, f, c, 0 }
     *
     * @param   string  The string to be encoded.
     *
     * @return  A byte array derived from the passed String.
     *
     * @author  M. Del Pellegrino
     * @since   1
     */
    public static byte[] byteify(String string)
    {
        StringTokenizer strTok;
        byte[]          bytes;
        UnsignedByte    aUByte;
        Vector          elements = new Vector();
        String          bString;

        //If the third character is not a space, send this string to byteify_nospaces.
        if (string.length() > 2)
        {
          char c = string.charAt(2);
          if (c != ' ')
            return (byteify_nospaces(string));
        }

        strTok = new StringTokenizer(string, " ");
        while(strTok.hasMoreElements())
        {
            bString = (String)strTok.nextElement();
            bString = "0x" + bString;    // So it is parsed as a hex
            aUByte  = UnsignedByte.decode(bString);
            elements.addElement(aUByte);
        }

        bytes   = new byte[elements.size()];

        for(int loop = 0; loop < elements.size(); loop++)
        {
            bytes[loop] = ((UnsignedByte)elements.elementAt(loop)).byteValue();
        }

        return bytes;
    }

    /**
     * Convert a hex string into an byte array
     */
    public static byte[] byteify_nospaces(String str) {
      //If string has spaces in it , send it to byteify.
       //Todo : str is always in hex and must be validated to verify to have even length
      if (str.length() > 2)
        {
          char c = str.charAt(2);
          if (c == ' ')
            return (byteify(str));
        }

      String byteStr;
      byte[] result = new byte[str.length()/2];
      for (int i=0; i<str.length()/2; i++) {
        // Prefix byteStr with Ox
        byteStr = "0x";
        byteStr += str.substring((i*2), (i*2)+2);
        result[i] = (byte)Integer.decode(byteStr).intValue();
      }
    return result;
  }
    /**
     * Extend the passed byte array with leading bytes, filled with a pad value
     *
     * @param   target  The array to be extended.
     * @param   length  The total length the new array should be.
     * @param   padByte The value to fill the new, leading, bytes with
     *
     * @return  The extended array.
     *
     * @author  M. Del Pellegrino
     * @since   1
     */
    public static byte[] padLeft(byte[] target, int length, byte padByte)
    {
        byte[]  padded;

        if(target.length > length)
        {
            padded = null;
            throw new IllegalArgumentException("Canoot clip, only pad.");
        }
        else
        {
            padded = new byte[length];

            for(int loop = 0; loop < (length - target.length); loop++)
            {
                padded[loop] = padByte;
            }

            System.arraycopy(target, 0, padded, (length - target.length), target.length);
        }

        return padded;
    }

    /**
     * Remove leading bytes from the passed array of bytes.
     *
     * @param   data    The array to be reduced.
     * @param   prune   The number of leadin bytes to remove.
     *
     * @return  The new, shorter, array.
     *
     * @author  M. Del Pellegrino
     * @since   1
     */
    public static byte[] pruneFront(byte[] data, int prune)
    {
        byte[] prunedData;

        prunedData = new byte[data.length - prune];
        System.arraycopy(data, prune, prunedData, 0, data.length - prune);

        return prunedData;
    }

    /**
     * Determine if the two passed arrays have exactly the same values.
     *
     * @param   array1  The first array.
     * @param   array2  The second array.
     *
     * @return  true if the arrays are equel, otherwise false.
     *
     * @author  M. Del Pellegrino
     * @since   1
     */
    public static boolean areEqual(byte[] array1, byte[] array2)
    {
        boolean equality;

        if(array1.length == array2.length)
        {
            equality = true;
            for(int loop = 0; equality && loop < array1.length; loop++)
            {
                if(array1[loop] != array2[loop])
                {
                    equality = false;
                }
            }
        }
        else
        {
            equality = false;
        }

        return equality;
    }

	/**
	 *
	 * @param str
	 *            0
	 * @return 30
	 */
	public static String convertStringToHex(String str) {
		char[] chars = str.toCharArray();
		StringBuilder hex = new StringBuilder();
        for (char c : chars)
        {
            hex.append(Integer.toHexString((int) c));
        }
		return hex.toString();
	}

	/**
	 *
	 * @param hex
	 *            49204c6f7665204a617661
	 * @return I Love Java
	 */
	public static String convertHexToString(String hex) {
		StringBuilder sb = new StringBuilder();
		// 49204c6f7665204a617661 split into two characters 49, 20, 4c...
		for (int i = 0; i < hex.length() - 1; i += 2) {
			// grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			// convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			// convert the decimal to character
			sb.append((char) decimal);
		}
		return sb.toString();
	}

	/**
	 *
	 * @param hex
	 *            31 30
	 * @return 16
	 */
	public static int convertHexStringToInt(String hex) {
		return Integer.valueOf(hex, 16);
	}

	public static int convertHexBytesToInt(byte[] hex) {
		String hexString = stringify_nospaces(hex);
		String asciiString = convertHexToString(hexString);
		return convertHexStringToInt(asciiString);
	}

    /**
     * Return the index of the byte, if it exists in the array
     * @param array
     * @param valueToFind
     * @return
     */
    public static int indexOf(byte[] array, byte valueToFind) {
        for (int i = 0; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return -1;
    }
    /**
     * Validates if the given String contains a digit or alphanumeric.
     * if str="12", true
     * if str="A", false
     * @param str
     * @return
     */
    public static boolean isStringNumeric(String str) {
        return str.matches("\\d*?");
    }

    public static byte[] copyOfRange(byte[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);

        byte[] copy = new byte[newLength];
        System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
        return copy;
    }
    
    public static boolean isHex(String value) 
    {
        boolean ret;
        try 
        {
            long t = Long.parseLong(value, 16);
            ret = true;
        } 
        catch (NumberFormatException e) 
        {
            ret = false;
        }
        return (ret);
    }
    
    public static boolean isNumber(String value) 
    {
        boolean ret;
        try 
        {
            long t = Long.parseLong(value);
            ret = true;
        } 
        catch (NumberFormatException e) 
        {
            ret = false;
        }
        return (ret);
    }

    /**
     * Converts a long into a byte[] in big endian form
     *
     * @param paramName  For logging purposes - will form part of the exception message if an error occurs
     * @param val        long to convert
     * @param numOfBytes Number of bytes to return
     *
     * @return the long as bytes in big endian form
     */
    public static byte[] longToBigEndian(String paramName, long val, int numOfBytes) throws IllegalArgumentException
    {
        if (val < 0)
        {
            String errMsg = paramName + " has a negative value: " + val;
            log.warn(errMsg);
            throw new IllegalArgumentException(errMsg);
        }

        byte[] bytes = new byte[numOfBytes];

        long valLeft = val;
        for (int x = (numOfBytes - 1); x >= 0; x--)
        {
            bytes[x] = (byte) (valLeft & 0xFF);
            valLeft >>= 8;
        }

        if (valLeft != 0)
        {
            String errMsg = paramName + " has a value of: " + val +
                    ", but this is too large to store in a byte array of size " + numOfBytes;
            log.warn(errMsg);
            throw new IllegalArgumentException(errMsg);
        }

        return bytes;
    }

    public static byte[] longToBigEndian(long val, int numOfBytes) throws IllegalArgumentException
    {
        return longToBigEndian("Parameter", val, numOfBytes);
    }

    /**
     * Converts a long into a byte[] in little endian form
     *
     * @param paramName  For logging purposes - will form part of the exception message if an error occurs
     * @param val        long to convert
     * @param numOfBytes Number of bytes to return
     *
     * @return the long as bytes in little endian form
     */
    public static byte[] longToLittleEndian(String paramName, long val, int numOfBytes) throws IllegalArgumentException
    {
        byte[] bytes = longToBigEndian(paramName, val, numOfBytes);
        return reverseByteArray(bytes);
    }

    public static byte[] longToLittleEndian(long val, int numOfBytes) throws IllegalArgumentException
    {
        return longToLittleEndian("Parameter", val, numOfBytes);
    }

    /**
     * Reverses the order of the bytes in the supplied byte array.
     *
     * Example:
     * Input Array: {1, 2, 3, 4}
     * Output Array: {4, 3, 2, 1}
     *
     * @param bytes The input byte array
     * @return The reversed byte array
     */
    public static byte[] reverseByteArray(byte[] bytes)
    {
        int len = bytes.length;

        byte[] output = new byte[len];

        for(int i = 0; i < len; i++)
        {
            output[len - i - 1] = bytes[i];
        }

        return output;
    }

    /**
     * Returns part of a supplied byte array.
     *
     * @param src The source byte array
     * @param startOffset The first index to copy
     * @param endOffset The index after the last index to copy
     * @return (endOffset-startOffset) bytes of data, copied from index 'startOffset' onwards from 'src'
     * @throws IllegalArgumentException 'startOffset' if after 'endOffset', or 'startOffset' and/or 'endOffset'
     *     are greater than the length of 'src'
     */
    public static byte[] partOfByteArray(byte[] src, int startOffset, int endOffset) throws IllegalArgumentException
    {
        if (startOffset < 0) throw new IllegalArgumentException("startOffset is negative");
        if (endOffset < 0) throw new IllegalArgumentException("endOffset is negative");
        if (startOffset > endOffset) throw new IllegalArgumentException("startOffset is greater than endOffset");
        if (startOffset > src.length) throw new IllegalArgumentException("startOffset exceeds the length of the array");
        if (endOffset > src.length) throw new IllegalArgumentException("endOffset exceeds the length of the array");

        byte[] retVal = new byte[endOffset - startOffset];
        System.arraycopy(src, startOffset, retVal, 0, retVal.length);

        return retVal;
    }
}
