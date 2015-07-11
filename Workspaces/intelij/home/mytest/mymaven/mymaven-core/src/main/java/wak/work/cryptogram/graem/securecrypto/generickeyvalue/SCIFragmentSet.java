/*
 *
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SCIFragmentSet.java %
 *  Created:	 Thu Nov 13 20:10:49 2003
 *  Created By:	 %created_by:  build %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  SCIFragmentSet.java~3:java:UKPMA#1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */

/*
 * SCIFragmentSet.java
 *
 * Created on 13 November 2003, 10:56
 */
package wak.work.cryptogram.graem.securecrypto.generickeyvalue;

import java.util.ArrayList;
import java.util.List;

/**
 * SCIFragmentType contains all the information to insert a component into a secured
 * image.
 *
 * @author Richard Izzard
 * @version 1.0
 */
public class SCIFragmentSet
{
    private final SCIKey key;

    /**
     * @link aggregation
     * @associates <{SCIFragOffset}>
     * @supplierCardinality 1..*
     */
    private final List<SCIFragOffset> offsetList;

    /**
     * Creates new SCIFragmentType
     *
     * @param key    Key used to protect the Fragment offset.
     * @param offset The fragment offset.
     * @throws SCIKeyException Thrown if the key or offset is not specified.
     */
    public SCIFragmentSet(SCIKey key, SCIFragOffset offset) throws SCIKeyException
    {
        if (key == null)
        {
            throw new SCIKeyException("Cannot create a SCIFragmentSet with key=null");
        }
        if (offset == null)
        {
            throw new SCIKeyException("Cannot create a SCIFragmentSet with offset=null");
        }

        int fragKeyAlg = key.getAlgorithm();
        if (SCIKeyAlgorithms.DES1E != fragKeyAlg
                && SCIKeyAlgorithms.DES2EDE != fragKeyAlg
                && SCIKeyAlgorithms.DES2DDD != fragKeyAlg
                && SCIKeyAlgorithms.DES3EDE != fragKeyAlg 
                && !SCIKeyAlgorithms.isRSA(fragKeyAlg))
        {
            throw new SCIKeyException("Algorithm type not supported: " + fragKeyAlg);
        }

        this.key = key;
        offsetList = new ArrayList<SCIFragOffset>();
        addFragOffset(offset);
    }

    /**
     * Add another fragment offset whose data is enciphered under the key given in the constructor.
     *
     * @param offset The offset to add.
     * @throws SCIKeyException Thrown if no offset is specified.
     */
    public void addFragOffset(SCIFragOffset offset) throws SCIKeyException
    {
        if (offset == null)
        {
            throw new SCIKeyException("Cannot add a null fragment offset");
        }
        
        SCIFragmentType fragType = offset.getFragType();
        if (SCIFragmentType.DATA == fragType && SCIKeyUsages.ECK != key.getEncodingUsage())
        {
            throw new SCIKeyException("DATA fragment type can only be inserted if the enciphering key is ECK");
        }

        int fragKeyAlg = key.getEncodingAlgorithm();
        if (SCIFragmentType.KEY1 == fragType && SCIKeyAlgorithms.DES1E != fragKeyAlg)
        {
            throw new SCIKeyException("Key1 fragment type can only be inserted if the enciphering algorithm is DES1E");
        }

        if (SCIFragmentType.KEY2 == fragType && SCIKeyAlgorithms.DES2EDE != fragKeyAlg)
        {
            throw new SCIKeyException("Key2 fragment type can only be inserted if the enciphering algorithm is DES2EDE");
        }

        if ( (SCIFragmentType.DP == fragType
                || SCIFragmentType.DQ == fragType
                || SCIFragmentType.P == fragType
                || SCIFragmentType.Q == fragType
                || SCIFragmentType.U == fragType)
                && !SCIKeyAlgorithms.isRSA(fragKeyAlg) )
        {
            throw new SCIKeyException("The SCIFragmentType was an RSA_CRT type, but the key algorithm was not an RSA algorithm: fragType="
                    + fragType + ", fragKeyAlg=" + fragKeyAlg);
        }

        offsetList.add(offset);
    }

    public SCIKey getKey()
    {
        return key;
    }

    public List<SCIFragOffset> getFragmentOffsets()
    {
        return offsetList;
    }


    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append(key);

        int idx = 0;
        for (SCIFragOffset offset : offsetList)
        {
            buff.append("\nOffset #").append(idx++).append(": ").append(offset);
        }

        return buff.toString();
    }}