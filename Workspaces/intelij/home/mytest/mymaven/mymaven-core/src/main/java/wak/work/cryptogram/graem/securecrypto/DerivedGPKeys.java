package wak.work.cryptogram.graem.securecrypto;


import java.io.Serializable;
import wak.work.cryptogram.helper.ByteArrayUtilities;

/**
 * User: growles
 * Date: 25-Jan-2012
 * Time: 16:23:19
 */
public class DerivedGPKeys implements Serializable
{
    private final byte[] wrappedCkEnc;
    private final byte[] ckEncKcv;

    private final byte[] wrappedCkMac;
    private final byte[] ckMacKcv;

    private final byte[] wrappedCkDek;
    private final byte[] ckDekKcv;

    public DerivedGPKeys(byte[] wrappedCkEnc, byte[] ckEncKcv, byte[] wrappedCkMac, byte[] ckMacKcv, byte[] wrappedCkDek, byte[] ckDekKcv)
    {
        this.wrappedCkEnc = wrappedCkEnc;
        this.ckEncKcv = ckEncKcv;
        this.wrappedCkMac = wrappedCkMac;
        this.ckMacKcv = ckMacKcv;
        this.wrappedCkDek = wrappedCkDek;
        this.ckDekKcv = ckDekKcv;
    }

    public byte[] getWrappedCkEnc()
    {
        return wrappedCkEnc;
    }

    public byte[] getCkEncKcv()
    {
        return ckEncKcv;
    }

    public byte[] getWrappedCkMac()
    {
        return wrappedCkMac;
    }

    public byte[] getCkMacKcv()
    {
        return ckMacKcv;
    }

    public byte[] getWrappedCkDek()
    {
        return wrappedCkDek;
    }

    public byte[] getCkDekKcv()
    {
        return ckDekKcv;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Derived Global Platform Keys:");
        sb.append("\nWrappedCkEnc: ").append(ByteArrayUtilities.stringify_nospaces(wrappedCkEnc));
        sb.append("\nWrappedCkEnc CV: ").append(ByteArrayUtilities.stringify_nospaces(ckEncKcv));
        sb.append("\nWrappedCkMac: ").append(ByteArrayUtilities.stringify_nospaces(wrappedCkMac));
        sb.append("\nWrappedCkMac CV: ").append(ByteArrayUtilities.stringify_nospaces(ckMacKcv));
        sb.append("\nWrappedCkDek: ").append(ByteArrayUtilities.stringify_nospaces(wrappedCkDek));
        sb.append("\nWrappedCkDek CV: ").append(ByteArrayUtilities.stringify_nospaces(ckDekKcv));

        return sb.toString();
    }
}
