/*
 *
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SCIFragmentSetArray.java %
 *  Created:	 Thu Nov 13 20:11:08 2003
 *  Created By:	 %created_by:  build %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  SCIFragmentSetArray.java~3:java:UKPMA#1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */
/*
 * FragmentSetArray.java
 *
 * Created on 07 November 2001, 19:18
 */

package wak.work.cryptogram.graem.securecrypto.generickeyvalue;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for a group of fragmentsets.
 *
 * @author Richard Izzard
 * @version 1.0
 */
public class SCIFragmentSetArray
{
    private static final int MIN_PIN_DES_KEY_SIZE = 16;
    /**
     * @link aggregation
     * @associates <{SCIFragmentSet}>
     * @supplierCardinality 1..*
     */
    private final List<SCIFragmentSet> fragmentSetList;
    private int maxRSAKeySize;

    /**
     * Creates new SCIFragmentSetArray
     *
     * @param fragSet The first fragment set in the collection.
     * @throws SCIKeyException Thrown if no fragment set is specified.
     */
    public SCIFragmentSetArray(SCIFragmentSet fragSet) throws SCIKeyException
    {
        fragmentSetList = new ArrayList<SCIFragmentSet>();
        maxRSAKeySize = 0;
        
        addFragmentSet(fragSet);
    }

    /**
     * Add another fragment set to the collection.
     *
     * @param fragSet Fragment set to add.
     * @throws SCIKeyException Thrown if the fragment set is null.
     */
    public void addFragmentSet(SCIFragmentSet fragSet) throws SCIKeyException
    {
        if (fragSet == null)
        {
            throw new SCIKeyException("Null fragment set supplied");
        }

        fragmentSetList.add(fragSet);

        SCIKey fragKey = fragSet.getKey();
        int fragSetAlg = fragKey.getEncodingAlgorithm();

        if (SCIKeyAlgorithms.isRSA(fragSetAlg))
        {
            if (maxRSAKeySize < fragKey.getKeySize())
            {
                maxRSAKeySize = fragKey.getKeySize();
            }
        }
    }

    public int getMaxKeySize()
    {
        int maxKeySize = MIN_PIN_DES_KEY_SIZE;

        if (maxRSAKeySize != 0)
        {
            maxKeySize = (5 * maxRSAKeySize) / 16;
        }

        return maxKeySize;
    }

    public List<SCIFragmentSet> getFragmentSets()
    {
        return fragmentSetList;
    }

    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        for (SCIFragmentSet fragSet : fragmentSetList)
        {
            buff.append("\nSCIFragmentSet: ").append(fragSet);
        }

        return buff.toString();
    }
}