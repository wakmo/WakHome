package wak.work.cryptogram.graem.securecrypto;


import java.io.Serializable;
import wak.work.cryptogram.helper.ByteArrayUtilities;

/**
 * Data structure to hold the ICC certificate, the ICC public key and ICC public key remainder
 * Some data is encrypted under a KEK which itself is encrypted variant 1 of LMK 24-25.
 * This kek is implicitly known by the client (? or TODO-9000: add param kek to this datastructure)
 *
 * @author andreas.heilemann
 */
public class EmvDdaData implements Serializable
{
    private static final long serialVersionUID = -3557637598797092834L;

    private final byte[] iccCert;
    private final byte[] iccPublicKeyRemainder;
    private final byte[] iccPublicExponent;
    private final byte[] iccPublicModulus;

    private final byte[] encryptedCrtP;
    private final byte[] encryptedCrtQ;
    private final byte[] encryptedCrtDP;
    private final byte[] encryptedCrtDQ;
    private final byte[] encryptedCrtU;

    private final byte[] encryptedPrivateExponent;
    private final byte[] encryptedModulus;


    /**
     * Constructor for CRT and MODEXP
     *
     * @param iccCert               Signed Card Certificate
     * @param iccPublicKeyRemainder Card Public Key Remainder
     * @param iccPublicKeyExponent  Card Public Key Exponent
     * @param iccPublicKeyModulus   Card Public Key Modulus - in clear
     * @param encryptedCrtP         Prime p encrypted under KEK using triple DES CBC
     * @param encryptedCrtQ         Prime q encrypted under KEK using triple DES CBC
     * @param encryptedCrtDP        d1 = d mod (p-1) encrypted under KEK using triple DES CBC
     * @param encryptedCrtDQ        d2 = d mod (q-1) encrypted under KEK using triple DES CBC
     * @param encryptedCrtU         Modular inverse of q encrypted under KEK using triple DES CBC
     * @param encryptedPrivateExponent Private exponent encrypted under KEK - or null if CRT only was used
     * @param encryptedModulus      Modulus encrypted under KEK - or null if CRT only was used
     *
     */
    public EmvDdaData(byte[] iccCert, byte[] iccPublicKeyRemainder, byte[] iccPublicKeyExponent, byte[] iccPublicKeyModulus,
                      byte[] encryptedCrtP, byte[] encryptedCrtQ, byte[] encryptedCrtDP,
                      byte[] encryptedCrtDQ, byte[] encryptedCrtU, byte[] encryptedPrivateExponent,
                      byte[] encryptedModulus)
    {
        this.iccCert = iccCert;
        this.iccPublicKeyRemainder = iccPublicKeyRemainder;
        this.iccPublicExponent = iccPublicKeyExponent;
        this.iccPublicModulus = iccPublicKeyModulus;
        this.encryptedCrtP = encryptedCrtP;
        this.encryptedCrtQ = encryptedCrtQ;
        this.encryptedCrtDP = encryptedCrtDP;
        this.encryptedCrtDQ = encryptedCrtDQ;
        this.encryptedCrtU = encryptedCrtU;
        this.encryptedPrivateExponent = encryptedPrivateExponent;
        this.encryptedModulus = encryptedModulus;
    }


    /**
     * Returns Signed Card Certificate.
     *
     * @return Signed Card Certificate.
     */
    public byte[] getIccCert()
    {
        return iccCert;
    }

    /**
     * Returns  Card Public Key Remainder.
     *
     * @return Card Public Key Remainder.
     */
    public byte[] getIccPublicKeyRemainder()
    {
        return iccPublicKeyRemainder;
    }

    public byte[] getIccPublicExponent()
    {
        return iccPublicExponent;
    }

    public byte[] getIccPublicModulus()
    {
        return iccPublicModulus;
    }

    public byte[] getEncryptedCrtP()
    {
        return encryptedCrtP;
    }

    public byte[] getEncryptedCrtQ()
    {
        return encryptedCrtQ;
    }

    public byte[] getEncryptedCrtDP()
    {
        return encryptedCrtDP;
    }

    public byte[] getEncryptedCrtDQ()
    {
        return encryptedCrtDQ;
    }

    public byte[] getEncryptedCrtU()
    {
        return encryptedCrtU;
    }

    public byte[] getEncryptedPrivateExponent()
    {
        return encryptedPrivateExponent;
    }

    public byte[] getEncryptedModulus()
    {
        return encryptedModulus;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        appendByteArray(sb, "iccCert                 ", iccCert);
        appendByteArray(sb, "iccPublicKeyRemainder   ", iccPublicKeyRemainder);
        appendByteArray(sb, "iccPublicExponent       ", iccPublicExponent);
        appendByteArray(sb, "iccPublicModulus        ", iccPublicModulus);
        appendByteArray(sb, "encryptedCrtP           ", encryptedCrtP);
        appendByteArray(sb, "encryptedCrtQ           ", encryptedCrtQ);
        appendByteArray(sb, "encryptedCrtDP          ", encryptedCrtDP);
        appendByteArray(sb, "encryptedCrtDQ          ", encryptedCrtDQ);
        appendByteArray(sb, "encryptedCrtU           ", encryptedCrtU);
        appendByteArray(sb, "encryptedPrivateExponent", encryptedPrivateExponent);
        appendByteArray(sb, "encryptedModulus        ", encryptedModulus);

        return sb.toString();
    }

    private static void appendByteArray(StringBuilder sb, String name, byte[] value)
    {
        sb.append("\n").append(name).append(": ");

        if (value == null)
        {
            sb.append("null");
        }
        else
        {
            sb.append(ByteArrayUtilities.stringify_nospaces(value).toUpperCase());
        }
    }
}
