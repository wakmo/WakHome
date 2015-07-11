/*
 *
 * 
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SCIFragOffset.java %
 *  Created:	 Thu Nov 13 20:14:53 2003
 *  Created By:	 %created_by:  rotondia %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  SCIFragOffset.java~1:java:UKPMA#1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *  
 *
 * 
 *
 */
/*
 * SCIFragOffset.java
 *
 * Created on 24 October 2001, 10:42
 */

package wak.work.cryptogram.graem.securecrypto.generickeyvalue;

/**
 * This class represents all the fragment offsets with types except for DATA.
 * Use FragDataOffset object instead.
 *
 * @author Richard Izzard
 * @version 1.0
 */

public class SCIFragOffset
{
    private final int offset;
    private final SCIFragmentType type;

    public SCIFragOffset()
    {
        this(0, null);
    }

    /**
     * Creates new SCIFragOffset.
     *
     * @param offset The position at which the data will be stored within the image.
     * @param type   The type of the data to be stored in the image.
     */
    public SCIFragOffset(int offset, SCIFragmentType type)
    {
        this.offset = offset;
        this.type = type;
    }

    public int getOffset()
    {
        return offset;
    }

    /**
     * @return
     */
    public SCIFragmentType getFragType()
    {
        return type;
    }

    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append("offset=").append(offset);
        buff.append(", fragType=").append(type);

        return buff.toString();
    }
}