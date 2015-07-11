
package wak.work.cryptogram.graem.securecrypto;

public class RSAPrivateKeyWrapper implements java.security.PrivateKey
{
  private ProtectedKey key;

  public RSAPrivateKeyWrapper(ProtectedKey key) throws SecureCryptoException
  {
    this.key = key;
  }

  public ProtectedKey getKey()
  {
    return key;
  }

  public String getAlgorithm()
  {
    return null;
  }

  public String getFormat()
  {
    return null;
  }

  public byte[] getEncoded()
  {
    return null;
  }
}
