/*
 * Fragment.java
 *
 * Created on 23 October 2001, 16:14
 */

package wak.work.cryptogram.graem.securecrypto;

import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIFragmentType;

import java.io.Serializable;

/**
 * This class provides all the acceptable fragment types.
 *
 * @author Richard Izzard
 * @version 1.0
 */

public class FragmentType implements Serializable
{

    /**
     * Represents fragment type WHOLE.
     */
    public static final FragmentType WHOLE = new FragmentType(SCIFragmentType._WHOLE);
    /**
     * Represents fragment type KEY 1.
     * Which is the first key in a single, double or triple length DES key.
     */
    public static final FragmentType KEY1 = new FragmentType(SCIFragmentType._KEY1);
    /**
     * Represents fragment type KEY 2.
     * Which is the second key in a double or triple length DES key.
     */
    public static final FragmentType KEY2 = new FragmentType(SCIFragmentType._KEY2);
    /**
     * Represents fragment type KEY 3.
     * Which is the third key in a triple length DES key.
     */
    public static final FragmentType KEY3 = new FragmentType(SCIFragmentType._KEY3);
    /**
     * Represents fragment type N.
     * Which is the N component of an RSA key.
     */
    public static final FragmentType N = new FragmentType(SCIFragmentType._N);
    /**
     * Represents fragment type D.
     * Which is the D component of an RSA key.
     */
    public static final FragmentType D = new FragmentType(SCIFragmentType._D);
    /**
     * Represents fragment type E.
     * Which is the E component of an RSA key.
     */
    public static final FragmentType E = new FragmentType(SCIFragmentType._E);
    /**
     * Represents fragment type P.
     * Which is the P component of an RSA key.
     */
    public static final FragmentType P = new FragmentType(SCIFragmentType._P);
    /**
     * Represents fragment type Q.
     * Which is the Q component of an RSA key.
     */
    public static final FragmentType Q = new FragmentType(SCIFragmentType._Q);
    /**
     * Represents fragment type DP.
     * Which is the DP component of an RSA key.
     */
    public static final FragmentType DP = new FragmentType(SCIFragmentType._DP);
    /**
     * Represents fragment type DQ.
     * Which is the DQ component of an RSA key.
     */
    public static final FragmentType DQ = new FragmentType(SCIFragmentType._DQ);
    /**
     * Represents fragment type U.
     * Which is the U component of an RSA key.
     */
    public static final FragmentType U = new FragmentType(SCIFragmentType._U);
    public static final FragmentType DATA = new FragmentType(SCIFragmentType._DATA);
    public static final FragmentType PIN = new FragmentType(SCIFragmentType._PIN);
    public static final FragmentType PIN_0 = new FragmentType(SCIFragmentType._PIN_0);

    private int fragment;

    /**
     * Creates new Fragment
     */
    private FragmentType(int fragment)
    {
        this.fragment = fragment;
    }

    public int getValue()
    {
        return fragment;
    }

    /**
     * Converts fragment type to a String representation.
     *
     * @return Fragment type as a string.
     */
    public String toString()
    {
        return SCIFragmentType.fragmentDescriptor[fragment];
    }

    public static FragmentType getFragmentType(String fType)
    {
        FragmentType ft = null;
        for (int f = 0; f < SCIFragmentType.fragmentDescriptor.length && ft == null; f++)
        {
            if (SCIFragmentType.fragmentDescriptor[f].equals(fType))
            {
                ft = getFragmentType(f);
            }
        }
        return ft;
    }

    public static FragmentType getFragmentType(int index)
    {
        FragmentType ft = null;
        switch (index)
        {
            case SCIFragmentType._WHOLE:
                ft = WHOLE;
                break;
            case SCIFragmentType._KEY1:
                ft = KEY1;
                break;
            case SCIFragmentType._KEY2:
                ft = KEY2;
                break;
            case SCIFragmentType._KEY3:
                ft = KEY3;
                break;
            case SCIFragmentType._N:
                ft = N;
                break;
            case SCIFragmentType._D:
                ft = D;
                break;
            case SCIFragmentType._E:
                ft = E;
                break;
            case SCIFragmentType._P:
                ft = P;
                break;
            case SCIFragmentType._Q:
                ft = Q;
                break;
            case SCIFragmentType._DP:
                ft = DP;
                break;
            case SCIFragmentType._DQ:
                ft = DQ;
                break;
            case SCIFragmentType._U:
                ft = U;
                break;
            case SCIFragmentType._DATA:
                ft = DATA;
                break;
            case SCIFragmentType._PIN:
                ft = PIN;
                break;
            case SCIFragmentType._PIN_0:
                ft = PIN_0;
                break;
        }
        return ft;
    }

    public boolean equals(Object fragType)
    {
        boolean equal = false;

        if (fragType != null && fragType instanceof FragmentType)
        {
            FragmentType thatFragType = (FragmentType)fragType;
            equal = thatFragType.fragment == this.fragment;
        }

        return equal;
    }
}