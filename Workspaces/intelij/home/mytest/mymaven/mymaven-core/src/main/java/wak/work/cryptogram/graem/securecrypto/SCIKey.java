package wak.work.cryptogram.graem.securecrypto;

import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyValue;
import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyUsage;

import java.io.OutputStream;

import org.apache.log4j.Logger;


public class SCIKey implements Key
{
    private static final Logger log = Logger.getLogger(SCIKey.class);

    private final SCIKeyValue key;

    public SCIKey(wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKey key)
    {
        this.key = new SCIKeyValue(key.getMKIdentifier(),
                key.getSMType(),
                key.getKeyUsage(),
                key.getComponent(),
                key.getKeyType(),
                key.getKeySize(),
                key.getCryptoEngineVersion(),
                key.getSMExtension(),
                key.getKeyValue());
    }

    public SCIKey(byte[] mkIdentifier,
                  String smType,
                  KeyUsage[] keyUsage,
                  Component component,
                  KeyType keyType,
                  int keySize,
                  int version,
                  byte[] smExtension,
                  byte[] keyValue)

    {
        SCIKeyUsage[] newKeyUsage = new SCIKeyUsage[keyUsage.length];
        for (int i = 0; i < keyUsage.length; i++)
        {
            newKeyUsage[i] = new SCIKeyUsage(keyUsage[i].getAlgorithm().toInt(), keyUsage[i].getUsage().toInt());
        }

        key = new SCIKeyValue(mkIdentifier,
                smType,
                newKeyUsage,
                component.toInt(),
                keyType.toInt(),
                keySize,
                version,
                smExtension,
                keyValue);
    }

    public Algorithm getEncodingAlgorithm() throws SecureCryptoException
    {
        return Algorithm.getAlgorithm(key.getKeyUsage()[0].getAlgorithm());
    }

    public Usage getEncodingUsage() throws SecureCryptoException
    {
        return Usage.getUsage(key.getKeyUsage()[0].getUsage());
    }

    public Component getComponent() throws SecureCryptoException
    {
        return Component.getComponent(key.getComponent());
    }

    public byte[] getKeyValue() throws SecureCryptoException
    {
        return key.getKeyValue();
    }

    public KeyType getKeyType() throws SecureCryptoException
    {
        return KeyType.getKeyType(key.getKeyType());
    }

    public KeyUsage[] getKeyUsage() throws SecureCryptoException
    {
        SCIKeyUsage[] intKeyUsage = key.getKeyUsage();
        KeyUsage[] extKeyUsage = new KeyUsage[intKeyUsage.length];
        for (int i = 0; i < intKeyUsage.length; i++)
        {
            extKeyUsage[i] = new KeyUsage(Algorithm.getAlgorithm(intKeyUsage[i].getAlgorithm()),
                    Usage.getUsage(intKeyUsage[i].getUsage()));
        }

        return extKeyUsage;
    }

    public int getKeySize() throws SecureCryptoException
    {
        return key.getKeySize();
    }

    public String getSMType() throws SecureCryptoException
    {
        return key.getSMType();
    }

    public byte[] getMKIdentifier() throws SecureCryptoException
    {
        return key.getMKIdentifier();
    }

    public byte[] getSMExtension() throws SecureCryptoException
    {
        return key.getSMExtension();
    }

    public wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKey extractTransportableKey()
    {
        return key;
    }

    public void exportKey(OutputStream os) throws SecureCryptoException
    {
        log.trace("enter: exportKey(OutputStream os)");

        SCIKeyExporter.exportKey(os, this);

        log.trace("exit: exportKey(OutputStream os)");
    }
}
