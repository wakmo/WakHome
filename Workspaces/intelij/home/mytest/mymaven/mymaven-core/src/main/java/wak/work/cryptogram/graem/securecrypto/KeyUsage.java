package wak.work.cryptogram.graem.securecrypto;

import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyUsage;

/**
 * Key usage object which contains both key algorithm and usage attributes.
 *
 * @author Richard Izzard
 * @version 1.0
 */

public class KeyUsage implements java.io.Serializable
{
    private final Algorithm alg;
    private final Usage usage;

    /**
     * Create KeyUsage object from {@link Algorithm} and {@link Usage}
     *
     * @param alg   Algorithm
     * @param usage Usage
     */
    public KeyUsage(Algorithm alg, Usage usage)
    {
        this.alg = alg;
        this.usage = usage;
    }

    /**
     * Retreive the algorithm.
     *
     * @return Algorithm.
     */
    public Algorithm getAlgorithm()
    {
        return alg;
    }

    /**
     * Retreive the usage attribute.
     *
     * @return Usage.
     */
    public Usage getUsage()
    {
        return usage;
    }

    public SCIKeyUsage toSCIKeyUsage()
    {
        return new SCIKeyUsage(alg.toInt(), usage.toInt());
    }

    /**
     * Retreives the algorithm and usage as a String.
     *
     * @return String representation of the key usage.
     */
    public String toString()
    {
        return "algorithm=" + alg.toString() + ",usage=" + usage.toString();
    }
}
