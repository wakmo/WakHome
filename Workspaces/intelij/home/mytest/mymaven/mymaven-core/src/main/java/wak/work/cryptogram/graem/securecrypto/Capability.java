/*
 *
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   Capability.java %
 *  Created:	 Thu Aug 08 16:29:50 2002
 *  Created By:	 %created_by:  izzardr %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  Capability.java~1:java:1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */

package wak.work.cryptogram.graem.securecrypto;

import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIFragmentType;
import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIHashAlgorithm;

import java.io.Serializable;

public class Capability implements Serializable
{
    private final boolean functionSupported;

    private final Algorithm[] supportedAlgorithms;
    private final FragmentType[] supportedFragTypes;
    private final HashAlgorithm[] supportedHashAlgs;
    private final PadMode[] supportedPadModes;

    public Capability(boolean functionSupported,
                      Algorithm[] supportedAlgorithms, FragmentType[] supportedFragTypes,
                      HashAlgorithm[] supportedHashAlgs, PadMode[] supportedPadModes)
    {
        this.functionSupported = functionSupported;

        this.supportedAlgorithms = (supportedAlgorithms == null)? new Algorithm[0] : supportedAlgorithms;
        this.supportedFragTypes = (supportedFragTypes == null)? new FragmentType[0] : supportedFragTypes;
        this.supportedHashAlgs = (supportedHashAlgs == null)? new HashAlgorithm[0] : supportedHashAlgs;
        this.supportedPadModes = (supportedPadModes == null)? new PadMode[0] : supportedPadModes;
    }

    public boolean isFunctionSupported()
    {
        return functionSupported;
    }

    public boolean isSupported(Algorithm alg)
    {
        return isSupported(supportedAlgorithms, alg);
    }

    public boolean isSupported(FragmentType fragType)
    {
        boolean supported = false;

        for (FragmentType nextFragType : supportedFragTypes)
        {
            supported |= nextFragType.getValue() == fragType.getValue();
        }

        return supported;
    }

    public boolean isSupported(HashAlgorithm hashAlg)
    {
        return isSupported(supportedHashAlgs, hashAlg);
    }

    public boolean isSupported(PadMode padMode)
    {
        return isSupported(supportedPadModes, padMode);
    }

    public boolean isSupported(SCIHashAlgorithm sciHashAlg)
    {
        String sciHashAlgStr = sciHashAlg.toString();
        HashAlgorithm hashAlg = HashAlgorithm.getHashAlgorithm(sciHashAlgStr);

        return isSupported(hashAlg);
    }

    public boolean isSupported(SCIFragmentType sciFragType)
    {
        String sciFragmentTypeStr = sciFragType.toString();
        FragmentType fragType = FragmentType.getFragmentType(sciFragmentTypeStr);

        return isSupported(fragType);
    }


    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Function supported: ").append(functionSupported).append("\n");
        append(sb, "Supported algorithms      : ", supportedAlgorithms);
        append(sb, "Supported fragment types  : ", supportedFragTypes);
        append(sb, "Supported hash algorithms : ", supportedHashAlgs);
        append(sb, "Supported pad modes       : ", supportedPadModes);

        return sb.toString();
    }

    private static boolean isSupported(Object[] itemArray, Object item)
    {
        boolean supported = false;

        for (Object nextItem : itemArray)
        {
            supported |= nextItem.equals(item);
        }

        return supported;
    }

    private static void append(StringBuilder sb, String title, Object[] itemArray)
    {
        sb.append(title);

        boolean first = true;
        for (Object item : itemArray)
        {
            if (first)
            {
                first = false;
            }
            else
            {
                sb.append(", ");
            }

            sb.append(item);
        }

        sb.append("\n");
    }
}
