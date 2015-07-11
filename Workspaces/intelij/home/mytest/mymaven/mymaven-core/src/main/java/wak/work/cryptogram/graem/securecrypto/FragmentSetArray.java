/*
 * FragmentSetArray.java
 *
 * Created on 07 November 2001, 19:18
 */

package wak.work.cryptogram.graem.securecrypto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIFragmentSetArray;
import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyException;

/**
 * Container for a group of fragmentsets.
 *
 * @author Richard Izzard
 * @version 1.0
 */
public class FragmentSetArray implements Serializable
{
    private static final long serialVersionUID = 983654423423541L;


	private static final Logger log = Logger.getLogger(FragmentSetArray.class);
    

    private static final int MIN_PIN_DES_KEY_SIZE = 16;

    private final List<FragmentSet> fragmentSetList;
    private int maxRSAKeySize;

    /**
     * Creates new FragmentSetArray
     *
     * @param fragSet The first fragment set in the collection.
     * @throws SecureCryptoException Thrown if no fragment set is specified.
     */
    public FragmentSetArray(FragmentSet fragSet) throws SecureCryptoException
    {
    	this();        
        addFragmentSet(fragSet);
    }
    
    public FragmentSetArray() throws SecureCryptoException
    {
        fragmentSetList = new ArrayList<FragmentSet>();
        maxRSAKeySize = 0;        
    }

    /**
     * Add another fragment set to the collection.
     *
     * @param fragSet Fragment set to add.
     * @throws SecureCryptoException Thrown if the fragment set is null.
     */
    public void addFragmentSet(FragmentSet fragSet) throws SecureCryptoException
    {
        if (fragSet == null)
        {
            throw new SecureCryptoException("Null fragment set supplied");
        }

        fragmentSetList.add(fragSet);

        final Key fragKey = fragSet.getKey();
        final Algorithm fragAlg = fragKey.getEncodingAlgorithm();

        if (fragAlg == Algorithm.RSA_CRT)
        {
            if (maxRSAKeySize < fragKey.getKeySize())
            {
                maxRSAKeySize = fragKey.getKeySize();
            }
        }
    }
    // A more permanent solution is required.

    public int getMaxKeySize()
    {
        int maxKeySize = MIN_PIN_DES_KEY_SIZE;

        if (maxRSAKeySize != 0)
        {
            maxKeySize = ((5 * maxRSAKeySize) / 16) + 90;
        }

        return maxKeySize;
    }

    public List<FragmentSet> getFragmentSets()
    {
        return fragmentSetList;
    }

    public SCIFragmentSetArray toSciFragmentSetArray() throws SecureCryptoException
    {
        SCIFragmentSetArray sciFragSetArray;

        try
        {
            final FragmentSet[] fragmentSets = fragmentSetList.toArray(new FragmentSet[fragmentSetList.size()]);

            sciFragSetArray = new SCIFragmentSetArray(fragmentSets[0].toSCISciFragmentSet());

            for (int i = 1; i < fragmentSets.length; i++)
            {
                sciFragSetArray.addFragmentSet(fragmentSets[i].toSCISciFragmentSet());
            }
        }
        catch (final SCIKeyException x)
        {
            final String errMsg = "Failed to convert fragmentSetArray into an SCIFragmentSetArray";
            log.warn(errMsg, x);
            throw new SecureCryptoException(errMsg, x);
        }

        return sciFragSetArray;
    }

    @Override
    public String toString()
    {
        final StringBuffer buff = new StringBuffer();
        for (final FragmentSet fragSet : fragmentSetList)
        {
            buff.append("\nFragmentSet: ").append(fragSet);
        }

        return buff.toString();
    }
}