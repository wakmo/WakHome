
package wak.work.cryptogram.graem.securecrypto.generickeyvalue;

public class SCIKeyUsage implements java.io.Serializable
{
  static final long serialVersionUID = -1644660645888457674L;

  private int alg;
  private int usage;

  public SCIKeyUsage() {}

  public SCIKeyUsage(int alg, int usage)
  {
    this.alg = alg;
    this.usage = usage;
  }

  public int getAlgorithm()
  {
    return alg;
  }

  public int getUsage()
  {
    return usage;
  }
}
