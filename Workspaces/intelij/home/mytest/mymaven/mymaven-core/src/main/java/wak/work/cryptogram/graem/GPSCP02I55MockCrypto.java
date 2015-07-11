package wak.work.cryptogram.graem;

import wak.work.cryptogram.graem.securecrypto.*;
//import com.platform7.securecrypto.generickeyvalue.SCIKey;
//import com.platform7.securecrypto.generickeyvalue.SCIKeyUsage;
//import com.platform7.securecrypto.generickeyvalue.SCIKeyValue;
//import com.platform7.standardinfrastructure.utilities.ByteArrayUtilities;
import cryptix.jce.provider.key.RawSecretKey;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.io.OutputStream;
import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyUsage;
import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyValue;
import wak.work.cryptogram.helper.ByteArrayUtilities;

/**
 * Mock SecureCrypto used to test NKAM APDU generation.
 *
 * It supports key objects where smExt = {0,0} means the clear key is present as the keyValue
 * smExt = {0,1} means a key reference appears in the keyValue field, and this must have been added
 *     to this class by previously calling addDesKeyMapping() or addPrivateKeyMapping()
 *
 * Created by graeme.rowles on 10/11/2014.
 */
public class GPSCP02I55MockCrypto extends SecureCrypto
{
    private static final String DES3_CBC_INIT = "DESede/CBC/NoPadding";
    private static final String DES3_ECB_INIT = "DESede/ECB/NoPadding";
    private static final String DES3_ALG = "DESede";

    private static final String DES_CBC_INIT = "DES/CBC/NoPadding";
    private static final String DES_ECB_INIT = "DES/ECB/NoPadding";
    private static final String DES_ALG = "DES";




    private final Map<String, byte[]> desKeyMap;
    private final Map<String, CrtKey> privateKeyMap;

    public GPSCP02I55MockCrypto()
    {
        desKeyMap = new HashMap<String, byte[]>();
        privateKeyMap = new HashMap<String, CrtKey>();
    }

    public void addDesKeyMapping(String keyName, String keyValue)
    {
        desKeyMap.put(keyName, ByteArrayUtilities.byteify_nospaces(keyValue));
    }

    public void addPrivateKeyMapping(String keyName, String p, String q, String u, String dp1, String dq1)
    {
        privateKeyMap.put(keyName, new CrtKey(p, q, u, dp1, dq1));
    }

    //Will return the same "random" data each time
    public byte[] generateRandom(int numOfBytes) throws SecureCryptoException
    {
        byte[] retVal = new byte[numOfBytes];
        for (int i = 0; i < numOfBytes; i++)
        {
            retVal[i] = (byte)(i ^ 0xAA);
        }

        return retVal;
    }

    @Override
    public String getSmType()
    {
        return "FAKE";
    }

    @Override
    public boolean isCanHandleMkId(byte[] mkId) throws SecureCryptoException
    {
        return true;
    }

    @Override
    public boolean isBusy()
    {
        return false;
    }

    @Override
    public boolean getAvailability() throws SecureCryptoException
    {
        return true;
    }

    @Override
    public Capability getCapability(Function function, String smType) throws SecureCryptoException
    {
        if (!function.equals(Function.BUILD_HASH))
        {
            String errMsg = "getCapability is only supported the BUILD_HASH function, but function=" + function;
            throw new SecureCryptoException(errMsg);
        }

        //Note: Our BUILD_HASH implementation for NCipher HSMs can currently only do MACs
        return new Capability(true,
                new Algorithm[] {Algorithm.DES1E, Algorithm.DES2EDE, Algorithm.DES3EDE},
                new FragmentType[] {},
                new HashAlgorithm[] {HashAlgorithm.CBC_P0K1M8},
                new PadMode[] {PadMode.NoPadding});
    }

    @Override
    protected byte[] doCipherImage(byte[] image, EncDec encDec, ProtectedKey key, CipherMode mode, byte[] iv) throws SecureCryptoException
    {
        int cipherMode = encDec.equals(EncDec.encrypt)? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;

        byte[] keyBytes = lookupDesKey(key);

        String init;
        String alg = keyBytes.length == 8? DES_ALG : DES3_ALG;

        if (keyBytes.length == 8 && mode.equals(CipherMode.CBC))
        {
            init = DES_CBC_INIT;
        }
        else if (keyBytes.length != 8 && mode.equals(CipherMode.CBC))
        {
            init = DES3_CBC_INIT;
        }
        else if (keyBytes.length == 8 && mode.equals(CipherMode.ECB))
        {
            init = DES_ECB_INIT;
        }
        else if (keyBytes.length != 8 && mode.equals(CipherMode.ECB))
        {
            init = DES3_ECB_INIT;
        }
        else
        {
            throw new SecureCryptoException("Invalid: keyBytes.length=" + keyBytes.length + ", mode=" + mode);
        }

        //Clone image
        byte[] adjustedImage = ByteArrayUtilities.addByteArrays(image);

        //xor the IV (if present) to the start of the message
        if (iv != null && iv.length != 0)
        {
            if (iv.length != 8)
            {
                throw new SecureCryptoException("IV is not 8 bytes in length: " + ByteArrayUtilities.stringify_nospaces(iv));
            }

            if ((image.length % 8) != 0)
            {
                throw new SecureCryptoException("Image is not a multiple of 8 bytes in length: " + ByteArrayUtilities.stringify_nospaces(image));
            }

            if (image.length == 0)
            {
                throw new SecureCryptoException("image is empty - nothing to " + encDec);
            }

            for (int i = 0; i < 8; i++)
            {
                adjustedImage[i] ^= iv[i];
            }
        }

        return des(init, alg, cipherMode, adjustedImage, keyBytes);
    }

    @Override
    protected byte[] doCalculateKeyCheckValue(ProtectedKey pKey) throws SecureCryptoException
    {
        byte[] desKey = lookupDesKey(pKey);

        String init = (desKey.length == 8)? DES_CBC_INIT : DES3_CBC_INIT;
        String alg = (desKey.length == 8)? DES_ALG : DES3_ALG;

        return des(init, alg, Cipher.ENCRYPT_MODE, new byte[8], desKey);
    }

    public byte[] buildHash(byte[] image, HashParams hashParams) throws SecureCryptoException
    {
        wak.work.cryptogram.graem.securecrypto.Key key = hashParams.getKey();
        byte[] macKey = lookupDesKey(key);

        if (!"CBC_000108".equals(hashParams.getHashAlgorithm().toString()))
        {
            throw new SecureCryptoException("Hash Algorithm not supported: " + hashParams.getHashAlgorithm());
        }

        return wholesaleMac(image, macKey);
    }

    private static byte[] wholesaleMac(byte[] data, byte[] key) throws SecureCryptoException
    {
        byte[] block = des(DES3_CBC_INIT, DES3_ALG, Cipher.ENCRYPT_MODE, data, key);
        return ByteArrayUtilities.partOfByteArray(block, block.length - 8, block.length);
    }

    @Override
    public byte[] buildSecureImage(byte[] image, ProtectedKey key, CipherMode mode, byte[] iv, FragmentSetArray fragSetArray) throws SecureCryptoException
    {
        //Clone image
        byte[] adjustedImage = ByteArrayUtilities.addByteArrays(image);

        List<FragmentSet> fragmentSets = fragSetArray.getFragmentSets();
        for (FragmentSet fs : fragmentSets)
        {
            wak.work.cryptogram.graem.securecrypto.Key keyToOperateOn = fs.getKey();

            List<FragOffset> fragmentOffsets = fs.getFragmentOffsets();

            for (FragOffset fo : fragmentOffsets)
            {
                FragmentType fragType = fo.getFragType();
                int offset = fo.getOffset();

                byte[] componentData;
                if (fragType.equals(FragmentType.P))
                {
                    CrtKey crtKey = lookupPrivateKey(keyToOperateOn);
                    componentData = crtKey.p;
                }
                else if (fragType.equals(FragmentType.Q))
                {
                    CrtKey crtKey = lookupPrivateKey(keyToOperateOn);
                    componentData = crtKey.q;
                }
                else if (fragType.equals(FragmentType.U))
                {
                    CrtKey crtKey = lookupPrivateKey(keyToOperateOn);
                    componentData = crtKey.u;
                }
                else if (fragType.equals(FragmentType.DP))
                {
                    CrtKey crtKey = lookupPrivateKey(keyToOperateOn);
                    componentData = crtKey.dp1;
                }
                else if (fragType.equals(FragmentType.DQ))
                {
                    CrtKey crtKey = lookupPrivateKey(keyToOperateOn);
                    componentData = crtKey.dq1;
                }
                else if (fragType.equals(FragmentType.WHOLE))
                {
                    componentData = lookupDesKey(keyToOperateOn);
                }
                else
                {
                    throw new SecureCryptoException("FragmentType not supported: " + fragType);
                }

                System.arraycopy(componentData, 0, adjustedImage, offset, componentData.length);
            }
        }

        return cipherImage(adjustedImage, EncDec.encrypt, key, mode, iv);
    }

    @Override
    protected ProtectedKey doGenerateDerivedKey(byte[] image, ProtectedKey key, CipherMode mode, byte[] iv) throws SecureCryptoException
    {
        byte[] keyBytes = lookupDesKey(key);

        String init;
        String alg = keyBytes.length == 8? DES_ALG : DES3_ALG;

        if (keyBytes.length == 8 && mode.equals(CipherMode.CBC))
        {
            init = DES_CBC_INIT;
        }
        else if (keyBytes.length != 8 && mode.equals(CipherMode.CBC))
        {
            init = DES3_CBC_INIT;
        }
        else if (keyBytes.length == 8 && mode.equals(CipherMode.ECB))
        {
            init = DES_ECB_INIT;
        }
        else if (keyBytes.length != 8 && mode.equals(CipherMode.ECB))
        {
            init = DES3_ECB_INIT;
        }
        else
        {
            throw new SecureCryptoException("Invalid: keyBytes.length=" + keyBytes.length + ", mode=" + mode);
        }

        byte[] derivedKeyValue = des(init, alg, Cipher.ENCRYPT_MODE, image, keyBytes);

        KeyUsage[] usages = key.getKeyUsage();
        KeyUsage[] derivedUsages = new KeyUsage[usages.length - 1];
        System.arraycopy(usages, 1, derivedUsages, 0, derivedUsages.length);

        return makeProtectedKey(derivedUsages, derivedKeyValue, new byte[] {0, 0});
    }

    private static ProtectedKey makeProtectedKey(final KeyUsage[] usages, final byte[] value, final byte[] smExt)
    {
        return new ProtectedKey()
        {
            public Algorithm getEncodingAlgorithm() throws SecureCryptoException
            {
                return Algorithm.DES2EDE;
            }

            public Usage getEncodingUsage() throws SecureCryptoException
            {
                return Usage.ECK;
            }

            public Component getComponent() throws SecureCryptoException
            {
                return Component.Complete;
            }

            public byte[] getKeyValue() throws SecureCryptoException
            {
                return value;
            }

            public KeyType getKeyType() throws SecureCryptoException
            {
                return KeyType.PROTECTED;
            }

            public KeyUsage[] getKeyUsage() throws SecureCryptoException
            {
                return usages;
            }

            public int getKeySize() throws SecureCryptoException
            {
                return value.length * 8;
            }

            public String getSMType() throws SecureCryptoException
            {
                return "Fake";
            }

            public byte[] getMKIdentifier() throws SecureCryptoException
            {
                return new byte[0];
            }

            public byte[] getSMExtension() throws SecureCryptoException
            {
                return smExt;
            }

            @Override
            public SCIKeyValue extractTransportableKey() throws SecureCryptoException
            {
                SCIKeyUsage[] sciKeyUsages = new SCIKeyUsage[usages.length];
                for (int i = 0; i < sciKeyUsages.length; i++)
                {
                    sciKeyUsages[i] = new SCIKeyUsage(usages[i].getAlgorithm().toInt(),
                            usages[i].getUsage().toInt());
                }

                return new SCIKeyValue(getMKIdentifier(),
                        getSMType(),
                        sciKeyUsages,
                        getComponent().toInt(),
                        getKeyType().toInt(),
                        getKeySize(),
                        1,
                        getSMExtension(),
                        value);
            }

            public void exportKey(OutputStream os) throws SecureCryptoException
            {
            }
        };
    }

    @Override
    public String getModuleName()
    {
        return "Mocked";
    }

    private static byte[] des(String init, String alg, int mode, byte[] data, byte[] keyBytes) throws SecureCryptoException
    {
        keyBytes = toTripleDesKey(keyBytes);

        try
        {
            Cipher desCipher = Cipher.getInstance(init);

            Key key = new RawSecretKey(alg, keyBytes);

            if (init.contains("CBC"))
            {
                desCipher.init(mode, key, new IvParameterSpec(new byte[8]));
            }
            else
            {
                desCipher.init(mode, key);
            }

            return desCipher.doFinal(data);
        }
        catch (Exception x)
        {
            x.printStackTrace();
            throw new SecureCryptoException("Encryption failed", x);
        }
    }

    private static byte[] toTripleDesKey(byte[] data)
    {
        if (data.length == 16)
        {
            byte[] keyL = ByteArrayUtilities.partOfByteArray(data, 0, 8);
            byte[] keyR = ByteArrayUtilities.partOfByteArray(data, 8, 16);
            data = ByteArrayUtilities.addByteArrays(keyL, keyR, keyL);
        }

        return data;
    }

    private byte[] lookupDesKey(wak.work.cryptogram.graem.securecrypto.Key key) throws SecureCryptoException
    {
        byte[] keyValueBytes = key.getKeyValue();

        byte[] smExt = key.getSMExtension();
        if ("0000".equals(ByteArrayUtilities.stringify_nospaces(smExt)))
        {
            return keyValueBytes;
        }

        String keyName = new String(keyValueBytes);

        if (!desKeyMap.containsKey(keyName))
        {
            throw new SecureCryptoException("No such DES key: " + keyName);
        }

        return desKeyMap.get(keyName);
    }

    private CrtKey lookupPrivateKey(wak.work.cryptogram.graem.securecrypto.Key key) throws SecureCryptoException
    {
        byte[] keyValueBytes = key.getKeyValue();

        byte[] smExt = key.getSMExtension();
        if ("0000".equals(ByteArrayUtilities.stringify_nospaces(smExt)))
        {
            if (keyValueBytes.length % 5 != 0) throw new SecureCryptoException("Mocked private key value is not a multiple of 5");

            String keyValueStr = ByteArrayUtilities.stringify_nospaces(keyValueBytes).toUpperCase();

            int compSize = keyValueBytes.length / 5;
            compSize *= 2;

            return new CrtKey(
                    keyValueStr.substring(0, compSize),
                    keyValueStr.substring(compSize, 2 * compSize),
                    keyValueStr.substring(2 * compSize, 3 * compSize),
                    keyValueStr.substring(3 * compSize, 4 * compSize),
                    keyValueStr.substring(4 * compSize, 5 * compSize));
        }

        String keyName = new String(keyValueBytes);

        if (!privateKeyMap.containsKey(keyName))
        {
            throw new SecureCryptoException("No such private key: " + keyName);
        }

        return privateKeyMap.get(keyName);
    }


    private class CrtKey
    {
        private final byte[] p;
        private final byte[] q;
        private final byte[] u;
        private final byte[] dp1;
        private final byte[] dq1;

        public CrtKey(String p, String q, String u, String dp1, String dq1)
        {
            this.p = ByteArrayUtilities.byteify(p);
            this.q = ByteArrayUtilities.byteify(q);
            this.u = ByteArrayUtilities.byteify(u);
            this.dp1 = ByteArrayUtilities.byteify(dp1);
            this.dq1 = ByteArrayUtilities.byteify(dq1);
        }
    }



    @Override
    protected CryptoModuleMkIdentifier getMkIdentifier() throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public byte[] wrapKey(ProtectedKey keyEncipheringKey, PadMode padding, CipherMode mode, byte[] iv, ProtectedKey keyToBeWrapped) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    protected ProtectedKey doGenerateKey(KeyUsage[] keyUsage, int keySize) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public ProtectedKey generateKey(KeyUsage[] keyUsage, RefinedKeyUsage rku, String smType, byte[] mkIdentifier) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public KeyPair generateKey(RsaKeyGenOptions keyGenOptions) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public SecureModuleStatus getStatus() throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public wak.work.cryptogram.graem.securecrypto.Key importNativeKey(String smType, byte[] nativeKey, RefinedKeyUsage keyType, KeyUsage[] keyUsage, String mkId, Component comp, int cryptoEngineVersion) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public wak.work.cryptogram.graem.securecrypto.Key importNativeKey(KeyType privOrAuth, String smType, int keySize, byte[] nativeKey, RefinedKeyUsage keyType, KeyUsage[] keyUsage, String mkId, Component comp, int cryptoEngineVersion) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public byte[] signMessage(byte[] message, HashParams hashParams, PadMode padMode, ProtectedKey signatureKey) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }
    @Override
    public AuthenticKey verifyPublicKey(wak.work.cryptogram.graem.securecrypto.Key[] keys, Usage usage, VerifyAlgorithm verifyAlg, byte[] keyCertificate) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    protected byte[] doCipherImage(byte[] image, EncDec encDec, wak.work.cryptogram.graem.securecrypto.Key key) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    protected byte[] doBuildHash(byte[] image, FragmentSetArray fragSetArray, HashParams hashParams) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    protected byte[] doCreateRealMultosKTU(byte[] image, FragmentSetArray fragSetArray, AuthenticKey tkck_pk, byte[] cardCertificate, MultosVerifyAlgorithm multosVerifyAlgorithm, AuthenticKey hashModulus) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    protected boolean doVerifyMessageSignature(byte[] message, HashParams hashParams, PadMode padMode, AuthenticKey verificationKey, byte[] signature) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public byte[] buildSecureImage(byte[] image, AuthenticKey key, FragmentSetArray fragSet) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public KeyAndCheckValue formSymmetricKeyFromComponents(String[] components, RefinedKeyUsage rku) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    protected ProtectedKey doImportEncipheredKey(byte[] data, ProtectedKey key) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    protected byte[] doExportEncipheredKey(ProtectedKey keyEncipheringKey, ProtectedKey keyForEncipherment) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    protected byte[] doTranslateMultosKtu(byte[] pseudoKTU, ProtectedKey pseudoKTUEncipheringKey, AuthenticKey tkck_pk, byte[] cardKeyCertificate, MultosVerifyAlgorithm multosVerifyAlgorithm, AuthenticKey hashModulus) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public byte[] reEncipherData(ProtectedKey keyFrom, ProtectedKey keyTo, CipherMode modeFrom, CipherMode modeTo, byte[] ivFrom, byte[] ivTo, byte[] encipheredData) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public byte[] generateVisaPVV(ProtectedKey zpk, ProtectedKey pvk, byte[] pinBlock, PINBlockFormat pinBlockFormat, String pan, int pvki) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public String generateCVV(CvvType cvvType, ProtectedKey cvvKey, String pan, String expiryDateMMYY, String serviceCode) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public byte[] translatePINFormat(ProtectedKey ZPK, byte[] inputPinBlock, PINBlockFormat inputPinBlockFormat, String accountNumber, PINBlockFormat outputPinBlockFormat, OutputEncMode outputEncMode) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public byte[] translatePin(ProtectedKey keyFrom, ProtectedKey keyTo, byte[] inputPinBlock, PINBlockFormat inputPinBlockFormat, String accountNumber, PINBlockFormat outputPinBlockFormat) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public void close()
    {

    }

    @Override
    public KeyKcv deriveEmvCardKey(ProtectedKey masterKey, DerivationOption derivationOption, String pan, byte[] panSequenceNumber, ProtectedKey kek, CipherMode mode, byte[] iv) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public byte[] generateSdaSignature(byte[] dataAuthenticationCode, byte[] staticAuthenticationData, HashAlgorithm hashAlgorithm, ProtectedKey signingKey) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public byte[] generateSdaSignature(ProtectedKey mkDAC, String pan, byte[] panSequenceNumber, byte[] staticAuthenticationData, HashAlgorithm hashAlgorithm, ProtectedKey signingKey) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public EmvDdaData generateEmvDdaData(RsaKeyType rsaKeyType, int keyLength, byte[] publicExponent, ProtectedKey kek, PrimeStrength primeStrength, PadMode padMode, CipherMode mode, byte[] iv, EmvCertificateType emvCertificateType, CardScheme cardScheme, HashAlgorithm hashAlgorithm, SignatureAlgorithm signatureAlgorithm, String pan, byte[] certificateExpirationDate, byte[] certificateSerialNumber, byte[] staticAuthenticationData, ProtectedKey issuerPrivateKey) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public EmvDdaData generateEmvDdaData(RsaKeyType rsaKeyType, ProtectedKey iccPrivateKey, AuthenticKey iccPublicKey, ProtectedKey kek, PadMode padMode, CipherMode mode, byte[] iv, EmvCertificateType emvCertificateType, CardScheme cardScheme, HashAlgorithm hashAlgorithm, SignatureAlgorithm signatureAlgorithm, String pan, byte[] certificateExpirationDate, byte[] certificateSerialNumber, byte[] staticAuthenticationData, ProtectedKey issuerPrivateKey) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public IssuerCertRequest generateIssuerCertRequest(int modulusLength, byte[] publicExponent, CardScheme cardScheme, HashAlgorithm hashAlgorithm, SignatureAlgorithm signatureAlgorithm, String bin, byte[] certificateExpirationDate, byte[] serviceIdentifierOrCertificateSerialNumber, byte[] trackingNumberOrIssuerPublicKeyIndex, PrimeStrength primeStrength) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public byte[] translateRSAPrivateKey(byte[] privateKeyBytes) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public byte[] translateDESKey(ProtectedKey desKey, RefinedKeyUsage rku) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public wak.work.cryptogram.graem.securecrypto.Key migrateKeyFromOldToNewMasterKey(wak.work.cryptogram.graem.securecrypto.Key key) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public String getKeyDescription(wak.work.cryptogram.graem.securecrypto.Key key)
    {
        return "Mocked key";
    }

    @Override
    public DerivedGPKeys deriveGlobalPlatformKeys(ProtectedKey masterKey, byte[] derivationData, ProtectedKey kek) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }

    @Override
    public byte[] mac(byte[] image, MacParams macParams) throws SecureCryptoException
    {
        throw new SecureCryptoException("Not implemented");
    }
}
