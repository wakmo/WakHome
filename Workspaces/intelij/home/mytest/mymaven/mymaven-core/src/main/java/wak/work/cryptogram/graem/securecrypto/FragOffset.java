/*
 * FragOffset.java
 *
 * Created on 24 October 2001, 10:42
 */

package wak.work.cryptogram.graem.securecrypto;

import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIFragOffset;
import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIFragmentType;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * This class represents all the fragment offsets with types except for DATA.
 * Use FragDataOffset object instead.
 *
 * @author Richard Izzard
 * @version 1.0
 */

public class FragOffset implements Externalizable
{
    /**
     * The position at which the data will be stored within the image
     */
    protected int offset;

    /**
     * The type of the data to be stored in the image
     */
    protected FragmentType type;
    

    public FragOffset()
    {
    }

    /**
     * Creates new FragOffset.
     *
     * @param offset The position at which the data will be stored within the image.
     * @param type   The type of the data to be stored in the image.
     */
    public FragOffset(int offset, FragmentType type)
    {
        this.offset = offset;
        this.type = type;
    }

    public int getOffset()
    {
        return offset;
    }

    public FragmentType getFragType()
    {
        return type;
    }

    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeInt(offset);
        out.writeUTF(type.toString());
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        offset = in.readInt();
        type = FragmentType.getFragmentType(in.readUTF());
    }

    public SCIFragOffset toSCIFragOffset()
    {
        return new SCIFragOffset(offset, SCIFragmentType.getFragmentType(type.getValue()));
    }

    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append("offset=").append(offset);
        buff.append(", fragType=").append(type);

        return buff.toString();
    }
}