package wak.work.cryptogram.graem.securecrypto;

/**
 * An enum indicating the strength of primes required when generating RSA keys
 *
 * User: graeme.rowles
 * Date: 20/02/13
 * Time: 11:43
 */
public enum PrimeStrength
{
    //Use strong primes
    STRONG,

    //Use standard primes
    STANDARD,

    //Use the default prime strength as set in SCI config
    DEFAULT
}
