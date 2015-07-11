
package wak.work.cryptogram.graem.securecrypto;

public interface AuthenticKey extends Key
{
  public byte[] extractPublicKey() throws SecureCryptoException;
}
