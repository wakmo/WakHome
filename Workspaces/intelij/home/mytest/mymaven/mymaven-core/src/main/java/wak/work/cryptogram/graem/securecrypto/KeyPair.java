/*
 * KeyPair.java
 *
 * Created on 02 November 2001, 14:46
 */
package wak.work.cryptogram.graem.securecrypto;

import java.io.Serializable;

/**
 * Contains both private ({@link ProtectedKey}) and public ({@link AuthenticKey}) which represents
 * an asymmetric key pair.
 *
 * @author Richard Izzard
 */
public class KeyPair implements Serializable
{
    //Public key
    private final AuthenticKey authKey;

    //Private key
    private final ProtectedKey protKey;


    public KeyPair()
    {
        protKey = null;
        authKey = null;
    }

    /**
     * Creates new KeyPair
     */
    public KeyPair(ProtectedKey protKey, AuthenticKey authKey)
    {
        this.protKey = protKey;
        this.authKey = authKey;
    }

    /**
     * Retrieves the private key part.
     */
    public ProtectedKey getProtectedKey()
    {
        return protKey;
    }

    /**
     * Retrieves the public key part.
     */
    public AuthenticKey getAuthenticKey()
    {
        return authKey;
    }
}