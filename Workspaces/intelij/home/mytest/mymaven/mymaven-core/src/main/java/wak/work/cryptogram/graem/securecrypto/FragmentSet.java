/*
 * FragmentSet.java
 *
 * Created on 24 October 2001, 10:56
 */

package wak.work.cryptogram.graem.securecrypto;

import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIFragmentSet;
import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyException;
import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyUsage;
import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyValue;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * FragmentSet contains all the information to insert a component into a secured
 * image.
 *
 * @author Richard Izzard
 * @version 1.0
 */
public class FragmentSet implements Serializable
{
    private static final Logger log = Logger.getLogger(FragmentSet.class);
    

    private final ProtectedKey key;

    private final List<FragOffset> offsetList;


    /**
     * Creates new FragmentSet
     *
     * @param key    Key used to protect the Fragment offset.
     * @param offset The fragment offset.
     * @throws SecureCryptoException Invalid key or offset supplied
     */
    public FragmentSet(ProtectedKey key, FragOffset offset) throws SecureCryptoException
    {
        this(key);

        addFragOffset(offset);
    }

    /**
     * Creates new FragmentSet without an initial offset
     *
     * @param key    Key used to protect the Fragment offset.
     * @throws SecureCryptoException Invalid key specified
     */
    public FragmentSet(ProtectedKey key) throws SecureCryptoException
    {
        if (key == null)
        {
            throw new SecureCryptoException("FragmentSet key is null");
        }

        Algorithm fragKeyAlg = key.getEncodingAlgorithm();
        if (fragKeyAlg != Algorithm.DES1E
                && fragKeyAlg != Algorithm.DES2EDE
                && fragKeyAlg != Algorithm.DES2DDD
                && fragKeyAlg != Algorithm.DES3EDE
                && !fragKeyAlg.isRSA())
        {
            throw new SecureCryptoException("Invalid algorithm for fragment set: " + fragKeyAlg);
        }

        this.key = key;
        offsetList = new ArrayList<FragOffset>();
    }

    /**
     * Add another fragment offset whose data is enciphered under the key given in the constructor.
     *
     * @param offset The offset to add.
     * @throws SecureCryptoException Thrown if no offset is specified.
     */
    public void addFragOffset(FragOffset offset) throws SecureCryptoException
    {
        if (offset == null)
        {
            throw new SecureCryptoException("Fragment offset is null");
        }

        FragmentType fragType = offset.getFragType();
        if (FragmentType.DATA.equals(fragType) && !Usage.ECK.equals(key.getEncodingUsage()))
        {
            throw new SecureCryptoException("DATA item can only be inserted if the enciphering key is ECK, but it is " + fragType);
        }

        Algorithm fragKeyAlg = key.getEncodingAlgorithm();
        if (FragmentType.KEY1.equals(fragType) && !Algorithm.DES1E.equals(fragKeyAlg))
        {
            throw new SecureCryptoException("FragmentType is KEY1 but Algorithm is not DES1E.  Algorithm=" + fragKeyAlg);
        }

        if (FragmentType.KEY2.equals(fragType) && !Algorithm.DES2EDE.equals(fragKeyAlg))
        {
            throw new SecureCryptoException("FragmentType is KEY2 but Algorithm is not DES2EDE.  Algorithm=" + fragKeyAlg);
        }

        if ((FragmentType.DP.equals(fragType)
                || FragmentType.DQ.equals(fragType)
                || FragmentType.P.equals(fragType)
                || FragmentType.Q.equals(fragType)
                || FragmentType.U.equals(fragType)) &&
                !(fragKeyAlg.isRSA()))
        {
            throw new SecureCryptoException("Key algorithm and fragment type are incompatible: fragType = "
                + fragType + ", fragKeyAlg=" + fragKeyAlg);
        }

        offsetList.add(offset);
    }

    public Key getKey()
    {
        return key;
    }

    public List<FragOffset> getFragmentOffsets()
    {
        return offsetList;
    }

    public SCIFragmentSet toSCISciFragmentSet() throws SecureCryptoException
    {
        SCIFragmentSet sciFragmentSet;

        try
        {
            SCIKeyUsage[] sciKeyUsages = new SCIKeyUsage[key.getKeyUsage().length];
            int idx = 0;
            for (KeyUsage usage : key.getKeyUsage())
            {
                sciKeyUsages[idx++] = usage.toSCIKeyUsage();
            }

            FragOffset[] offsets = offsetList.toArray(new FragOffset[offsetList.size()]);
            SCIKeyValue sciKey = new SCIKeyValue(key.getMKIdentifier(),
                    key.getSMType(),
                    sciKeyUsages,
                    key.getComponent().toInt(),
                    key.getKeyType().toInt(),
                    key.getKeySize(),
                    1,
                    key.getSMExtension(),
                    key.getKeyValue());

            sciFragmentSet = new SCIFragmentSet(sciKey, offsets[0].toSCIFragOffset());
            for (int i = 1; i < offsets.length; i++)
            {
                sciFragmentSet.addFragOffset(offsets[i].toSCIFragOffset());
            }
        }
        catch (SCIKeyException x)
        {
            String errMsg = "Failed to convert fragmentSet to an SCIFragmentSet";
            log.warn(errMsg, x);
            throw new SecureCryptoException(errMsg, x);
        }

        return sciFragmentSet;
    }

    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append(key);

        int idx = 0;
        for (FragOffset offset : offsetList)
        {
            buff.append("\nOffset #").append(idx++).append(": ").append(offset);
        }

        return buff.toString();
    }
}
