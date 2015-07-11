
package wak.work.cryptogram.graem.securecrypto.generickeyvalue;

/**
 * These objects are stored in the Affina database
 */
public interface SCIKey
{
  public boolean isValid();
  public byte[] getMKIdentifier();
  public String getSMType();
  public SCIKeyUsage[] getKeyUsage();
  public int getAlgorithm();
  public int getEncodingAlgorithm();
  public int getEncodingUsage();
  public int getComponent();
  public int getKeyType();
  public int getKeySize();
  public int getCryptoEngineVersion();
  public byte[] getSMExtension();
  public byte[] getKeyValue();
  public String toString();
}
