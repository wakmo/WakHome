/*
 *
 *  Copyright (C) 1999 NatWest Bank PLC. All rights reserved.
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:                   java
 *  Created:                11 November 1999 18:12:32
 *  Last modified:          11 November 1999 18:12:32
 *
 *  Amendment Record
 *  Ver     Date        Author  Description
 *
 *
 *  Continuus filename:     UnsignedByte.java~1:java:2
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
public class UnsignedByte extends Number
{
    //*************************************************************************
    // PRIVATE CLASS ATTRIBUTES
    //*************************************************************************
    private static final byte   s_signBitMask   = (byte)0x80;
    private static final int    s_signBitValue  = 0x80;
    //*************************************************************************
    //  PRIVATE INSTANCE ATTRIBUTES
    //*************************************************************************
    private byte    m_byteValue;
    private int     m_intValue;

    //*************************************************************************
    // PUBLIC CLASS ATTRIBUTES
    //*************************************************************************
    public final static short MAX_VALUE  = 255;
    public final static short MIN_VALUE  = 0;

    //*************************************************************************
    // PUBLIC INTERFACE
    //*************************************************************************
    //=========================================================================
    // CONSTRUCTORS
    //=========================================================================
    public UnsignedByte()
    {
        this(0);
    }

    public UnsignedByte(byte aByte)
    {
        this.setByte(aByte);
    }

    public UnsignedByte(Byte aByte)
    {
        this(aByte.byteValue());
    }

    public UnsignedByte(int anInt)
    {
        if(
            anInt >= MIN_VALUE
            &&
            anInt <= MAX_VALUE)
        {
            if(anInt > 127)
            {
                m_byteValue = (byte)(anInt - 256);
            }
            else
            {
                m_byteValue = (byte)anInt;
            }
        }
        else
        {
            throw new NumberFormatException("UnsignedByte range is from 0 to 255");
        }
    }

    public String toHexString()
    {
        return makeHexString(intValue());
    }

    //=========================================================================
    // BIT OPERATIONS
    //=========================================================================
    public void setBits(byte byteWithBits)
    {
        byte b = (byte)(m_byteValue | byteWithBits);
        setByte(b);
    }

    public void unsetBits(byte byteWithBits)
    {
        byte notBits;
        // First invert the bits then and with the byte value
        notBits = (byte)~byteWithBits;
        setByte((byte)(m_byteValue & notBits));
    }

    /**
     * Retrieves, a byte containing the values for for the bits set in the
     * passed byte.  All others will be set to zero
     *
     * @param   targetBit   A byte with only the bits set for the the positions
     *          of interest in this UnsignedByte.
     *
     * @return  a byte with all bits set to zero, except for those set in the
     *          passed parameter.  These bit have the value that are in the
     *          byte held within this UnsignedByte
     *
     * @author  M. Del Pellegrino
     * @since   1
     */
    public byte getBits(byte targetBits)
    {
        byte keyBits;

        keyBits = (byte)(m_byteValue & targetBits);

        return keyBits;
    }

    /**
     * Determines whether the <i>target</i>th bit is set or not.
     *
     * @param   target  The index from 0-7 into the eight bytes of the bit, to check
     *
     * @return  true if the bit is set otherwise false
     */
    public boolean isBitSet(int target)
    {
        boolean setState;

        if(target >= 0 && target <= 7)
        {
            // Create a mask with just the target bit set, by using it (less -1)
            // as a factor of 2.  AND this with the byte value will give a
            // result of zero if the bit is not set.
            byte bMask  = (byte)Math.pow(2, target );
            byte ans    = (byte)(m_byteValue & bMask);

            if(ans == 0)
            {
                setState = false;
            }
            else
            {
                setState = true;
            }
        }
        else
        {
            setState = false;
            String message  = "There are 8 bits in these bytes, the target is from"
                            + " 0 to 7, you tried to query bit "
                            + String.valueOf(target) + ".";
            throw new IllegalArgumentException(message);
        }

        return setState;
    }

    /**
     * Sets the <i>target</i>th bit.
     *
     * @param   target  The index from 0-7 into the eight bytes of the bit, to set.
     * @param   boolean The state to set the bit, true is on (1), false is off (0).
     *
     */
    public void setBit(int target, boolean setValue)
    {
        boolean setState;

        if(target >= 0 && target <= 7)
        {
            // Create a mask with just the target bit set, by using it (less -1)
            // as a factor of 2.
            // Or this with the byte value will set the bit. AND it with NOT
            // this value will unset the bit

            byte bMask  = (byte)Math.pow(2, target );

            if(setValue == true)
            {   // Set the value
                this.setByte((byte)(m_byteValue | bMask));
            }
            else
            {   // Unset the bit
                this.setByte( (byte)(m_byteValue & (~bMask)));
            }
        }
        else
        {
            String message  = "There are 8 bits in these bytes, the target is from"
                            + " 0 to 7, you tried to adjust bit "
                            + String.valueOf(target) + ".";
            throw new IllegalArgumentException(message);
        }
    }

    //=========================================================================
    // STATIC STRING REPRESENTATION METHODS
    //=========================================================================
    public static String toString( byte b)
    {
        UnsignedByte ub = new UnsignedByte(b);

        return ub.toString();
    }

    public static String toHexString( byte b)
    {
        return UnsignedByte.toHexString(new UnsignedByte(b));
    }

    public static String toHexString( UnsignedByte ub)
    {
        return makeHexString(ub.intValue());
    }

    public static byte parseUnsignedByte(String s, int radix)throws NumberFormatException
    {
	    int i = Integer.parseInt(s, radix);

	    if (i < MIN_VALUE || i > MAX_VALUE)
	    {
	        throw new NumberFormatException("UnsignedBytes are in the range of 0-255");
	    }

	    return (byte)i;
    }

    public static UnsignedByte valueOf(String s, int radix) throws NumberFormatException
    {
	    return new UnsignedByte(parseUnsignedByte(s, radix));
    }

    public static UnsignedByte valueOf( String s) throws NumberFormatException
    {
	    return valueOf(s, 10);
    }

    public static UnsignedByte decode(String nm) throws NumberFormatException
    {
        UnsignedByte ub;

	    if (nm.startsWith("0x"))
	    {
	        ub = UnsignedByte.valueOf(nm.substring(2), 16);
	    }
	    else if (nm.startsWith("#"))
	    {
	        ub = UnsignedByte.valueOf(nm.substring(1), 16);
	    }
	    else if (nm.startsWith("0") && nm.length() > 1)
	    {
	        ub = UnsignedByte.valueOf(nm.substring(1), 8);
	    }
	    else
	    {
	        ub = UnsignedByte.valueOf(nm);
	    }

        return ub;
    }

    //=========================================================================
    // Public methods overriding Number
    //=========================================================================
    /**
     * Returns the value of the UnsignedByte, in a byte <B>as if it were
     * unsigned</B>.  Beware comparing/manipulating the value as the JRE will
     * interpret it as signed.  If the value is to be tested or adjusted,
     * retrieve it as an <i>int</i> or <i>short</i> etc.
     *
     * @return  a byte containg a representation of an unsigned byte (i.e. 0 to
     *          255)
     *
     * @author  M. Del Pellegrino
     * @since   1
     */
    public byte byteValue( )
    {
        return m_byteValue;
    }

    public double doubleValue( )
    {
        return (double)m_intValue;
    }

    public float floatValue( )
    {
        return (float)m_intValue;
    }

    public int intValue( )
    {
        return (int)m_intValue;
    }

    public long longValue( )
    {
        return (long)m_intValue;
    }

    public short shortValue( )
    {
        return (short)m_intValue;
    }

    //=========================================================================
    // Public methods overriding Object
    //=========================================================================
    public boolean equals( Object obj)
    {
        boolean equality;
	    if ((obj != null) && (obj instanceof UnsignedByte))
	    {
	        equality = (this.m_byteValue == ((UnsignedByte)obj).byteValue());
	    }
	    else
	    {
	        equality = false;
	    }

	    return equality;
    }
    public int hashCode( )
    {
	    return m_intValue;
    }
    public String toString( )
    {
	    return String.valueOf((int)m_intValue);
    }

    //*************************************************************************
    // PRIVATE INTERFACE
    //*************************************************************************
    private void setByte(byte theByte)
    {
         m_byteValue = theByte;

        if(theByte < MIN_VALUE)
        {   // No negatives
            // Unset the sign bit, and add 128
            byte b;
            b = (byte)(theByte & (~s_signBitMask));

            m_intValue = (short)b + s_signBitValue;
        }
        else
        {   // It cannot be too large as max. byte is 127
            m_intValue = (int)theByte;
        }
   }

   private static String makeHexString(int intValue)
   {
        String byteString;

        byteString = Integer.toHexString(intValue);

        // If the number is negative it may have preceding 'f's, which should
        // be removed.
        if(byteString.length() > 2)
        {
            byteString = byteString.substring(
                            byteString.length() - 2);
        }
        return byteString;
   }

}