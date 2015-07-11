package wak.work.cryptogram.graem.securecrypto.keycache;

import wak.work.cryptogram.graem.securecrypto.*;

/**
 * User: growles
 * Date: 08-Feb-2012
 * Time: 11:42:06
 */
public interface RsaKeyGenerator
{
    //Generates an RSA Key Pair in the background (i.e. low priority)
    public KeyPair generateKey(RsaKeyGenOptions keyGenOptions) throws SecureCryptoException;
}
