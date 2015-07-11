package wak.work.cryptogram.graem.securecrypto;

import java.io.OutputStream;
import java.io.Serializable;

public interface Key extends Serializable
{
    public Algorithm getEncodingAlgorithm() throws SecureCryptoException;

    public Usage getEncodingUsage() throws SecureCryptoException;

    public Component getComponent() throws SecureCryptoException;

    public byte[] getKeyValue() throws SecureCryptoException;

    public KeyType getKeyType() throws SecureCryptoException;

    public KeyUsage[] getKeyUsage() throws SecureCryptoException;

    public int getKeySize() throws SecureCryptoException;

    public String getSMType() throws SecureCryptoException;

    public byte[] getMKIdentifier() throws SecureCryptoException;

    public byte[] getSMExtension() throws SecureCryptoException;

    public wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKey extractTransportableKey() throws SecureCryptoException;

    public void exportKey(OutputStream os) throws SecureCryptoException;
}
