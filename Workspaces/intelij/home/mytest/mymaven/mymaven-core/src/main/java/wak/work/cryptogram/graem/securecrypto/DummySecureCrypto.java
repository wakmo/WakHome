package wak.work.cryptogram.graem.securecrypto;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Random;
import wak.work.cryptogram.helper.ByteArrayUtilities;

/**
 * Created with IntelliJ IDEA.
 * User: graeme.rowles
 * Date: 01/05/13
 * Time: 09:07
 *
 * A special "Crypto Module" which returns dummy results.  Intented to be used for performance
 * testing Affina without the need for real HSMs.
 */
public class DummySecureCrypto extends SecureCrypto
{
    private static final Logger log = Logger.getLogger(DummySecureCrypto.class);


    private static final String DUMMY_SM_TYPE = "PS9000";

    private static final String GENERATED_KEY_SM_TYPE = "RG7000";
    private static final String GENERATED_KEY_MK_ID = "000000";


    private final Random rand;

    public DummySecureCrypto(HsmConnectionParams hsmConnectionParams) throws SecureCryptoException
    {
        super(SecureCryptoFactory.DUMMY_HSM_TYPE,
                hsmConnectionParams,
                getCmMkIdentifier(),
                null);

        rand = new Random(0);
    }

    @Override
    public String getSmType()
    {
        return DUMMY_SM_TYPE;
    }

    @Override
    public boolean isCanHandleSmType(String smType)
    {
        return true;
    }

    @Override
    public boolean isCanHandleMkIds(NonProcessingExcuses excuses, Key... keys) throws SecureCryptoException
    {
        return true;
    }

    @Override
    public boolean isCanHandleMkId(byte[] mkId) throws SecureCryptoException
    {
        return true;
    }

    //Can this SecureCrypto generate keys when no MkIdentifier is specified in the GenerateKey message?
    public boolean isMkIdentifierOkForDefaultGenKeys() throws SecureCryptoException
    {
        return true;
    }

    //Can this SecureCrypto generate keys when no SmType is specified in the GenerateKey message?
    public boolean hasDefaultKeyGenSmType()
    {
        return true;
    }


    @Override
    public boolean isBusy()
    {
        return false;
    }

    @Override
    protected CryptoModuleMkIdentifier getMkIdentifier() throws SecureCryptoException
    {
        return getCmMkIdentifier();
    }

    @Override
    public byte[] wrapKey(ProtectedKey keyEncipheringKey, PadMode padding, CipherMode mode, byte[] iv,
                          ProtectedKey keyToBeWrapped) throws SecureCryptoException
    {
        checkNotNull("keyToBeWrapped", keyToBeWrapped);

        byte[] keyToBeWrappedValue = keyToBeWrapped.getKeyValue();
        checkNotNull("KeyToBeWrappedValue", keyToBeWrappedValue);

        byte[] wrappedKey;
        if (PadMode.L0_00.equals(padding))
        {
            wrappedKey = optionalPad(keyToBeWrappedValue, (byte)0);
        }
        else if (PadMode.L1_00.equals(padding))
        {
            wrappedKey = concat(int2Bytes(keyToBeWrapped.getKeySize() / 8, 1), keyToBeWrappedValue);
            wrappedKey = optionalPad(wrappedKey, (byte)0);
        }
        else if (PadMode.L2_00.equals(padding))
        {
            wrappedKey = concat(int2Bytes(keyToBeWrapped.getKeySize() / 8, 2), keyToBeWrappedValue);
            wrappedKey = optionalPad(wrappedKey, (byte)0);
        }
        else if (PadMode.L0_80.equals(padding))
        {
            wrappedKey = pad(keyToBeWrappedValue, (byte)0x80);
        }
        else if (PadMode.L1_80.equals(padding))
        {
            wrappedKey = concat(int2Bytes(keyToBeWrapped.getKeySize() / 8, 1), keyToBeWrappedValue);
            wrappedKey = pad(wrappedKey, (byte)0x80);
        }
        else if (PadMode.L2_80.equals(padding))
        {
            wrappedKey = concat(int2Bytes(keyToBeWrapped.getKeySize() / 8, 2), keyToBeWrappedValue);
            wrappedKey = pad(wrappedKey, (byte)0x80);
        }
        else if (PadMode.NoPadding.equals(padding))
        {
            wrappedKey = keyToBeWrapped.getKeyValue();
        }
        else if (PadMode.PKCS1.equals(padding))
        {
            //todo What is PKCS1 padding?
            wrappedKey = concat(int2Bytes(keyToBeWrapped.getKeySize() / 8, 2), keyToBeWrappedValue);
            wrappedKey = pad(wrappedKey, (byte)0x80);
        }
        else
        {
            throw new SecureCryptoException("Invalid padding mode: " + padding);
        }

        return wrappedKey;
    }

    @Override
    protected ProtectedKey doGenerateKey(KeyUsage[] keyUsage, int keySize) throws SecureCryptoException
    {
        if (keySize != 64 && keySize != 128 && keySize != 192)
        {
            throw new SecureCryptoException("Invalid key size: " + keySize);
        }

        byte[] keyValue = new byte[keySize / 8];
        rand.nextBytes(keyValue);

        return makeProtectedKey(keyUsage, keySize, "2609", keyValue);
    }

    @Override
    public ProtectedKey generateKey(KeyUsage[] keyUsage, RefinedKeyUsage rku, String smType,
                                    byte[] mkIdentifier) throws SecureCryptoException
    {
        byte[] keyValue = new byte[16];
        rand.nextBytes(keyValue);

        String rkuStr = rku.getSmExt9000() + "U";
        byte[] rkuBytes = rkuStr.getBytes();

        return makeProtectedKey(keyUsage, 128, hexify(rkuBytes), keyValue);
    }

    @Override
    public KeyPair generateKey(RsaKeyGenOptions keyGenOptions) throws SecureCryptoException
    {
        int keySize = keyGenOptions.getKeySize();
        KeyUsage keyUsage = keyGenOptions.getKeyUsage();
        byte[] publicExp = keyGenOptions.getPublicExponent();

        byte[] privateKeyValue = new byte[(keySize / 8) * 3];
        rand.nextBytes(privateKeyValue);

        byte[] publicModulus = new byte[keySize / 8];
        rand.nextBytes(publicModulus);
        publicModulus[0] |= (byte)0x80;
        DerEncodedPublicKey derPublicKey = new DerEncodedPublicKey(new BigInteger(hexify(publicExp), 16),
                new BigInteger(publicModulus));
        byte[] derPublicKeyBytes = derPublicKey.getPublicKey();

        ProtectedKey privateKey = makeProtectedKey(new KeyUsage[] {keyUsage},
                keySize,
                "303043",
                privateKeyValue);

        AuthenticKey publicKey = makeAuthenticKey(keyUsage,
                keySize,
                "303044",
                derPublicKeyBytes);

        return new KeyPair(privateKey, publicKey);
    }

    @Override
    public byte[] generateRandom(int size) throws SecureCryptoException
    {
        byte[] retVal = new byte[size];
        rand.nextBytes(retVal);

        return retVal;
    }

    @Override
    public boolean getAvailability() throws SecureCryptoException
    {
        return true;
    }

    @Override
    public Capability getCapability(Function function, String smType) throws SecureCryptoException
    {
        throw new SecureCryptoException("getCapability() not supported on Dummy HSM");
    }

    @Override
    public SecureModuleStatus getStatus() throws SecureCryptoException
    {
        //Returning something indicates a "good" status
        return new HsmGoodStatus();
    }

    @Override
    public Key importNativeKey(String smType, byte[] nativeKey, RefinedKeyUsage rku, KeyUsage[] keyUsage,
                               String mkId, Component comp, int cryptoEngineVersion) throws SecureCryptoException
    {
        if (nativeKey == null) throw new SecureCryptoException("Native key is null");
        if (nativeKey.length == 0) throw new SecureCryptoException("Native key is empty");

        byte[] importedKey = new byte[nativeKey.length];
        for (int i = 0; i < importedKey.length; i++)
        {
            importedKey[i]++;
        }

        int keySize;
        byte[] smExt;

        if (nativeKey.length == 8)
        {
            keySize = 64;
            smExt = rku.getSmExt7000();
        }
        else if (nativeKey.length == 9)
        {
            keySize = 64;
            smExt = (rku.getSmExt9000() + "Z").getBytes();
        }
        else if (nativeKey.length == 16)
        {
            keySize = 128;
            smExt = rku.getSmExt7000();
        }
        else if (nativeKey.length == 17)
        {
            keySize = 128;
            smExt = (rku.getSmExt9000() + "U").getBytes();
        }
        else if (nativeKey.length == 24)
        {
            keySize = 192;
            smExt = rku.getSmExt7000();
        }
        else if (nativeKey.length == 25)
        {
            keySize = 192;
            smExt = (rku.getSmExt9000() + "T").getBytes();
        }
        else
        {
            keySize = 128;
            smExt = (rku.getSmExt9000() + "U").getBytes();
        }

        return makeProtectedKey(keyUsage, keySize, hexify(smExt), importedKey);
    }

    @Override
    public Key importNativeKey(KeyType privOrAuth, String smType, int keySize, byte[] nativeKey,
                               RefinedKeyUsage keyType, KeyUsage[] keyUsage, String mkId,
                               Component comp, int cryptoEngineVersion) throws SecureCryptoException
    {
        throw new SecureCryptoException("importNativeKey is not implemented for Dummy HSM as this is currently only used by GKMS");
    }

    @Override
    public byte[] signMessage(byte[] message, HashParams hashParams, PadMode padMode, ProtectedKey signatureKey) throws SecureCryptoException
    {
        //Length seems to equal key modulus length every time

        int macLen = signatureKey.getKeySize() / 8;

        byte[] retVal = new byte[macLen];
        rand.nextBytes(retVal);

        return retVal;
    }

    @Override
    public AuthenticKey verifyPublicKey(Key[] keys, Usage usage, VerifyAlgorithm verifyAlg, byte[] keyCertificate) throws SecureCryptoException
    {
        throw new SecureCryptoException("verifyPublicKey not implemented for " + getSmType());
    }

    @Override
    protected byte[] doCipherImage(byte[] image, EncDec encDec, ProtectedKey key, CipherMode mode, byte[] iv) throws SecureCryptoException
    {
        byte[] retVal = new byte[image.length];
        rand.nextBytes(retVal);

        return retVal;
    }

    @Override
    protected byte[] doCipherImage(byte[] image, EncDec encDec, Key key) throws SecureCryptoException
    {
        byte[] retVal = new byte[image.length];
        rand.nextBytes(retVal);

        return retVal;
    }

    @Override
    protected byte[] doCalculateKeyCheckValue(ProtectedKey pKey) throws SecureCryptoException
    {
        checkNotNull("pKey", pKey);

        byte[] keyValue = pKey.getKeyValue();
        checkNotNull("keyValue", keyValue);

        if (keyValue.length < 3) throw new SecureCryptoException("KeyValue is not 3 bytes in length");

        byte[] retVal = new byte[3];
        System.arraycopy(keyValue, 0, retVal, 0, 3);

        return retVal;
    }

    @Override
    protected byte[] doBuildHash(byte[] image, FragmentSetArray fragSetArray, HashParams hashParams) throws SecureCryptoException
    {
        //??
        return new byte[20];
    }

    @Override
    protected byte[] doCreateRealMultosKTU(byte[] image, FragmentSetArray fragSetArray,
                                           AuthenticKey tkck_pk, byte[] cardCertificate,
                                           MultosVerifyAlgorithm multosVerifyAlgorithm,
                                           AuthenticKey hashModulus) throws SecureCryptoException
    {
        int ktuSize = tkck_pk.getKeySize() / 8;
        return new byte[ktuSize];
    }

    @Override
    protected boolean doVerifyMessageSignature(byte[] message, HashParams hashParams, PadMode padMode, AuthenticKey verificationKey, byte[] signature) throws SecureCryptoException
    {
        return true;
    }

    @Override
    public byte[] buildSecureImage(byte[] image, ProtectedKey key, CipherMode mode, byte[] iv,
                                   FragmentSetArray fragSet) throws SecureCryptoException
    {
        return image;
    }

    @Override
    public byte[] buildSecureImage(byte[] image, AuthenticKey key, FragmentSetArray fragSet) throws SecureCryptoException
    {
        return image;
    }

    @Override
    public KeyAndCheckValue formSymmetricKeyFromComponents(String[] components, RefinedKeyUsage rku) throws SecureCryptoException
    {
        checkNotNull("components", components);
        if (components.length == 0) throw new SecureCryptoException("components is empty");

        byte[] runningXor = null;
        for (String component : components)
        {
            checkNotNull("Component", component);

            byte[] bytes;
            try
            {
                bytes = unHexify(component);
            }
            catch (NumberFormatException x)
            {
                throw new SecureCryptoException("Component contains invalid hex: " + component);
            }

            if (runningXor == null)
            {
                runningXor = bytes;
            }
            else
            {
                if (runningXor.length != bytes.length)
                {
                    throw new SecureCryptoException("Components are different lengths");
                }

                for (int i = 0; i < runningXor.length; i++)
                {
                    runningXor[i] ^= bytes[i];
                }
            }
        }

        byte[] kcv = new byte[3];
        System.arraycopy(runningXor, 0, kcv, 0, Math.max(kcv.length, runningXor.length));

        return new KeyAndCheckValue(hexify(runningXor), hexify(kcv));
    }

    @Override
    protected ProtectedKey doGenerateDerivedKey(byte[] image, ProtectedKey key, CipherMode mode,
                                                byte[] iv) throws SecureCryptoException
    {
        return key;
    }

    @Override
    protected ProtectedKey doImportEncipheredKey(byte[] data, ProtectedKey key) throws SecureCryptoException
    {
        return key;
    }

    @Override
    public String getModuleName()
    {
        return getSmType();
    }

    @Override
    protected byte[] doExportEncipheredKey(ProtectedKey keyEncipheringKey, ProtectedKey keyForEncipherment) throws SecureCryptoException
    {
        int keyLen = keyForEncipherment.getKeySize() / 8;
        byte[] keyValue = keyForEncipherment.getKeyValue();

        byte[] retVal;
        if (keyValue.length == 0)
        {
            retVal = new byte[keyLen];
            rand.nextBytes(retVal);
        }
        else
        {
            retVal = keyValue;
            while (retVal.length < keyLen)
            {
                retVal = concat(retVal, retVal);
            }

            if (retVal.length > keyLen)
            {
                byte[] temp = retVal;
                retVal = new byte[keyLen];
                System.arraycopy(temp, 0, retVal, 0, keyLen);
            }
        }

        return retVal;
    }

    @Override
    protected byte[] doTranslateMultosKtu(byte[] pseudoKTU, ProtectedKey pseudoKTUEncipheringKey, AuthenticKey tkck_pk, byte[] cardKeyCertificate, MultosVerifyAlgorithm multosVerifyAlgorithm, AuthenticKey hashModulus) throws SecureCryptoException
    {
        int ktuSize = tkck_pk.getKeySize() / 8;
        return new byte[ktuSize];
    }

    @Override
    public byte[] reEncipherData(ProtectedKey keyFrom, ProtectedKey keyTo, CipherMode modeFrom, CipherMode modeTo, byte[] ivFrom, byte[] ivTo, byte[] encipheredData) throws SecureCryptoException
    {
        checkNotNull("encipheredData", encipheredData);
        byte[] retVal = new byte[encipheredData.length];
        for (int i = 0; i < encipheredData.length; i++)
        {
            retVal[i] = (byte)(encipheredData[i] + 1);
        }

        return retVal;
    }

    @Override
    public byte[] generateVisaPVV(ProtectedKey zpk, ProtectedKey pvk, byte[] pinBlock, PINBlockFormat pinBlockFormat, String pan, int pvki) throws SecureCryptoException
    {
        int i = Math.abs(pinBlock[0] << 8 + pinBlock[1]);
        String s = "0000" + i;
        s = s.substring(s.length() - 4);

        return unHexify(s);
    }

    /**
     * Generates a Visa CVV or a MasterCard CVC
     *
     * @param cvvType Type of CVV to generate (includes indicating Card Scheme)
     * @param cvvKey The key to use to generate the CVV
     * @param pan The PAN of the card the CVV is for
     * @param expiryDateMMYY Card expiry date, 2 bytes, in BCD form
     * @param serviceCode The Service Code
     * @return The generated CVV / CVC / iCVV (etc)
     * @throws SecureCryptoException An error occurred producing the CVV
     */
    public String generateCVV(CvvType cvvType, ProtectedKey cvvKey,
                                       String pan, String expiryDateMMYY,
                                       String serviceCode) throws SecureCryptoException
    {
        String panExt = "000" + pan;
        panExt = panExt.substring(panExt.length() - 3);

        String expDateExt = "000" + expiryDateMMYY;
        expDateExt = expDateExt.substring(expDateExt.length() - 3);

        int panExtVal = Integer.parseInt(panExt);
        int expDateExtVal = Integer.parseInt(expDateExt, 16);

        int val = panExtVal + expDateExtVal;
        String valStr = "000" + val;
        valStr = valStr.substring(valStr.length() - 3);

        return valStr;
    }


    @Override
    public byte[] translatePINFormat(ProtectedKey ZPK, byte[] inputPinBlock, PINBlockFormat inputPinBlockFormat, String accountNumber, PINBlockFormat outputPinBlockFormat, OutputEncMode outputEncMode) throws SecureCryptoException
    {
        checkNotNull("inputPinBlock", inputPinBlock);
        byte[] retVal = new byte[inputPinBlock.length];
        for (int i = 0; i < inputPinBlock.length; i++)
        {
            retVal[i] = (byte)(inputPinBlock[i] - 1);
        }

        return retVal;
    }

    @Override
    public byte[] translatePin(ProtectedKey keyFrom, ProtectedKey keyTo, byte[] inputPinBlock, PINBlockFormat inputPinBlockFormat, String accountNumber, PINBlockFormat outputPinBlockFormat) throws SecureCryptoException
    {
        checkNotNull("inputPinBlock", inputPinBlock);
        byte[] retVal = new byte[inputPinBlock.length];
        for (int i = 0; i < inputPinBlock.length; i++)
        {
            retVal[i] = (byte)(inputPinBlock[i] - 2);
        }

        return retVal;
    }

    @Override
    public void close()
    {
    }

    @Override
    public KeyKcv deriveEmvCardKey(ProtectedKey masterKey, DerivationOption derivationOption, String pan, byte[] panSequenceNumber, ProtectedKey kek, CipherMode mode, byte[] iv) throws SecureCryptoException
    {
        byte[] keyData = masterKey.getKeyValue();
        for (int i = 0; i < keyData.length; i++)
        {
            keyData[i] = (byte)(keyData[i] + 3);
        }

        byte[] longKcv = concat(unHexify("000000"), keyData);
        byte[] kcv = new byte[3];
        System.arraycopy(longKcv, longKcv.length - 3, kcv, 0, 3);

        return new KeyKcv(keyData, kcv);
    }

    @Override
    public byte[] generateSdaSignature(byte[] dataAuthenticationCode, byte[] staticAuthenticationData,
                                       HashAlgorithm hashAlgorithm, ProtectedKey signingKey) throws SecureCryptoException
    {
        int sigLen = signingKey.getKeySize() / 8;
        byte[] retVal = new byte[sigLen];
        rand.nextBytes(retVal);

        return retVal;
    }

    @Override
    public byte[] generateSdaSignature(ProtectedKey mkDAC, String pan, byte[] panSequenceNumber,
                                       byte[] staticAuthenticationData, HashAlgorithm hashAlgorithm,
                                       ProtectedKey signingKey) throws SecureCryptoException
    {
        int sigLen = signingKey.getKeySize() / 8;
        byte[] retVal = new byte[sigLen];
        rand.nextBytes(retVal);

        return retVal;
    }

    @Override
    public EmvDdaData generateEmvDdaData(RsaKeyType rsaKeyType, int keyLength, byte[] publicExponent,
                                         ProtectedKey kek, PrimeStrength primeStrength, PadMode padMode,
                                         CipherMode mode, byte[] iv, EmvCertificateType emvCertificateType,
                                         CardScheme cardScheme, HashAlgorithm hashAlgorithm,
                                         SignatureAlgorithm signatureAlgorithm, String pan,
                                         byte[] certificateExpirationDate, byte[] certificateSerialNumber,
                                         byte[] staticAuthenticationData, ProtectedKey issuerPrivateKey) throws SecureCryptoException
    {
        int certSize = issuerPrivateKey.getKeySize() / 8;

        byte[] iccCert = new byte[certSize];
        rand.nextBytes(iccCert);

        int iccKeyLen = keyLength / 8;

        int reminderLen = Math.max(0, iccKeyLen - iccKeyLen - 42); //todo 42 guessed
        byte[] iccPublicKeyRemainder = new byte[reminderLen];
        rand.nextBytes(iccPublicKeyRemainder);

        byte[] clearMod = new byte[iccKeyLen];

        int extraBytes = 0;
        if (PadMode.L0_80.equals(padMode) || PadMode.L1_80.equals(padMode) || PadMode.L2_80.equals(padMode))
        {
            extraBytes++;
        }
        if (PadMode.L1_00.equals(padMode) || PadMode.L1_80.equals(padMode))
        {
            extraBytes++;
        }
        else if (PadMode.L2_00.equals(padMode) || PadMode.L2_80.equals(padMode))
        {
            extraBytes+=2;
        }

        int crtCompLen = (iccKeyLen / 2) + extraBytes;
        crtCompLen = (crtCompLen + 7) / 8;

        int modExpCompLen = iccKeyLen + extraBytes;
        modExpCompLen = (modExpCompLen + 7) / 8;

        byte[] encP = new byte[crtCompLen]; rand.nextBytes(encP);
        byte[] endQ = new byte[crtCompLen]; rand.nextBytes(endQ);
        byte[] encDP = new byte[crtCompLen]; rand.nextBytes(encDP);
        byte[] encDQ = new byte[crtCompLen]; rand.nextBytes(encDQ);
        byte[] encU = new byte[crtCompLen]; rand.nextBytes(encU);
        byte[] encD = new byte[modExpCompLen]; rand.nextBytes(encD);
        byte[] encM = new byte[modExpCompLen]; rand.nextBytes(encM);

        return new EmvDdaData(iccCert,
                iccPublicKeyRemainder,
                publicExponent,
                clearMod,
                encP,
                endQ,
                encDP,
                encDQ,
                encU,
                encD,
                encM);
    }

    @Override
    public EmvDdaData generateEmvDdaData(RsaKeyType rsaKeyType, ProtectedKey iccPrivateKey,
                                         AuthenticKey iccPublicKey, ProtectedKey kek,
                                         PadMode padMode, CipherMode mode, byte[] iv,
                                         EmvCertificateType emvCertificateType, CardScheme cardScheme,
                                         HashAlgorithm hashAlgorithm, SignatureAlgorithm signatureAlgorithm,
                                         String pan, byte[] certificateExpirationDate,
                                         byte[] certificateSerialNumber, byte[] staticAuthenticationData,
                                         ProtectedKey issuerPrivateKey) throws SecureCryptoException
    {

        byte[] derPublicKeyBytes = iccPublicKey.extractPublicKey();
        DerEncodedPublicKey derPublicKey = new DerEncodedPublicKey(derPublicKeyBytes);
        byte[] publicExponent = derPublicKey.getExponentAsBytes();
        byte[] modulus = derPublicKey.getModulusAsBytes();

        int certSize = issuerPrivateKey.getKeySize() / 8;

        byte[] iccCert = new byte[certSize];
        rand.nextBytes(iccCert);

        int iccKeyLen = modulus.length / 8;

        int reminderLen = Math.max(0, iccKeyLen - iccKeyLen - 42); //todo 42 guessed
        byte[] iccPublicKeyRemainder = new byte[reminderLen];
        rand.nextBytes(iccPublicKeyRemainder);

        int extraBytes = 0;
        if (PadMode.L0_80.equals(padMode) || PadMode.L1_80.equals(padMode) || PadMode.L2_80.equals(padMode))
        {
            extraBytes++;
        }
        if (PadMode.L1_00.equals(padMode) || PadMode.L1_80.equals(padMode))
        {
            extraBytes++;
        }
        else if (PadMode.L2_00.equals(padMode) || PadMode.L2_80.equals(padMode))
        {
            extraBytes+=2;
        }

        int crtCompLen = (iccKeyLen / 2) + extraBytes;
        crtCompLen = (crtCompLen + 7) / 8;

        int modExpCompLen = iccKeyLen + extraBytes;
        modExpCompLen = (modExpCompLen + 7) / 8;

        byte[] encP = new byte[crtCompLen]; rand.nextBytes(encP);
        byte[] endQ = new byte[crtCompLen]; rand.nextBytes(endQ);
        byte[] encDP = new byte[crtCompLen]; rand.nextBytes(encDP);
        byte[] encDQ = new byte[crtCompLen]; rand.nextBytes(encDQ);
        byte[] encU = new byte[crtCompLen]; rand.nextBytes(encU);
        byte[] encD = new byte[modExpCompLen]; rand.nextBytes(encD);
        byte[] encM = new byte[modExpCompLen]; rand.nextBytes(encM);

        return new EmvDdaData(iccCert,
                iccPublicKeyRemainder,
                publicExponent,
                modulus,
                encP,
                endQ,
                encDP,
                encDQ,
                encU,
                encD,
                encM);
    }

    @Override
    public IssuerCertRequest generateIssuerCertRequest(int modulusLength, byte[] publicExponent,
                                                       CardScheme cardScheme,
                                                       HashAlgorithm hashAlgorithm,
                                                       SignatureAlgorithm signatureAlgorithm,
                                                       String bin, byte[] certificateExpirationDate,
                                                       byte[] serviceIdentifierOrCertificateSerialNumber,
                                                       byte[] trackingNumberOrIssuerPublicKeyIndex,
                                                       PrimeStrength primeStrength) throws SecureCryptoException
    {
        if (!HashAlgorithm.SHA1.equals(hashAlgorithm))
        {
            throw new SecureCryptoException("Only SHA-1 hash algorithm is supported");
        }

        byte[] clearM = new byte[modulusLength / 8];
        rand.nextBytes(clearM);

        //Guess : 260 bytes more for private key
        byte[] privateKey = new byte[clearM.length + 260];
        rand.nextBytes(privateKey);

        byte[] publicKey = new DerEncodedPublicKey(new BigInteger(hexify(publicExponent), 16),
                new BigInteger(hexify(clearM), 16)).getPublicKey();

        byte[] selfSignedIssuerPublicKeyCertificate = new byte[clearM.length];
        rand.nextBytes(selfSignedIssuerPublicKeyCertificate);

        byte[] hash = new byte[20];
        rand.nextBytes(hash);

        byte[] mac = new byte[4];
        rand.nextBytes(mac);

        return new IssuerCertRequest(privateKey,
                publicKey,
                selfSignedIssuerPublicKeyCertificate,
                hash,
                mac);
    }

    @Override
    public byte[] translateRSAPrivateKey(byte[] privateKeyBytes) throws SecureCryptoException
    {
        checkNotNull("privateKeyBytes", privateKeyBytes);

        byte[] retVal = new byte[privateKeyBytes.length];
        for (int i = 0; i < privateKeyBytes.length; i++)
        {
            retVal[i] = (byte)(privateKeyBytes[i] + 5);
        }

        return retVal;
    }

    @Override
    public byte[] translateDESKey(ProtectedKey desKey, RefinedKeyUsage rku) throws SecureCryptoException
    {
        checkNotNull("desKey", desKey);
        byte[] desKeyBytes = desKey.getKeyValue();

        byte[] retVal = new byte[desKeyBytes.length];
        for (int i = 0; i < desKeyBytes.length; i++)
        {
            retVal[i] = (byte)(desKeyBytes[i] + 6);
        }

        return retVal;
    }

    @Override
    public Key migrateKeyFromOldToNewMasterKey(Key key) throws SecureCryptoException
    {
        return key;
    }

    @Override
    public String getKeyDescription(Key key)
    {
        String desc = "Key with SMExt=";
        try
        {
            desc += hexify(key.getSMExtension());
        }
        catch (SecureCryptoException x)
        {
            log.warn("Failed to get key description", x);
            desc += "??";
        }

        return desc;
    }

    @Override
    public DerivedGPKeys deriveGlobalPlatformKeys(ProtectedKey masterKey, byte[] derivationData, ProtectedKey kek) throws SecureCryptoException
    {
        return new DerivedGPKeys(new byte[16], new byte[3],
                new byte[16], new byte[3],
                new byte[16], new byte[3]);
    }

    @Override
    public byte[] mac(byte[] image, MacParams macParams) throws SecureCryptoException
    {
        return new byte[8];
    }

    private static CryptoModuleMkIdentifier getCmMkIdentifier() throws SecureCryptoException
    {
        return new CryptoModuleMkIdentifier(
            "000000",
            CryptoModuleMkIdentifier.EncodingType.HEX,
            0,
            CryptoModuleMkIdentifier.EncodingType.HEX);
    }

    private static void checkNotNull(String name, Object value) throws SecureCryptoException
    {
        if (value == null)
        {
            throw new SecureCryptoException(name + " is null");
        }
    }

    private static byte[] pad(byte[] bytes, byte paddingByte1)
    {
        int lenWithMandPadding = bytes.length + 1;
        int lenWithAllPadding = (lenWithMandPadding + 7) / 8;

        byte[] padded = new byte[lenWithAllPadding];
        System.arraycopy(bytes, 0, padded, 0, bytes.length);
        padded[bytes.length] = paddingByte1;

        return padded;
    }

    private static byte[] optionalPad(byte[] bytes, byte paddingByte1)
    {
        byte[] retVal = bytes;
        if ((bytes.length & 7) == 0)
        {
            //Already a multiple of 8 bytes in length - no padding necessary
            retVal = bytes;
        }
        else
        {
            retVal = pad(bytes, paddingByte1);
        }

        return retVal;
    }

    private static byte[] concat(byte[]... byteArrays)
    {
        int totalLen = 0;
        for (byte[] bytes : byteArrays)
        {
            totalLen += bytes.length;
        }

        byte[] retVal = new byte[totalLen];
        int pos = 0;
        for (byte[] bytes : byteArrays)
        {
            System.arraycopy(bytes, 0, retVal, pos, bytes.length);
            pos += bytes.length;
        }

        return retVal;
    }

    private static byte[] int2Bytes(int value, int numBytes)
    {
        String valueStr = "0000000" + Integer.toString(value, 16);
        valueStr = valueStr.substring(valueStr.length() - 2 * numBytes);

        return unHexify(valueStr);
    }

    private static String hexify(byte[] bytes)
    {
        return ByteArrayUtilities.stringify_nospaces(bytes).toUpperCase();
    }

    private static byte[] unHexify(String hex)
    {
        return ByteArrayUtilities.byteify_nospaces(hex);
    }

    private static ProtectedKey makeProtectedKey(KeyUsage[] keyUsage, int keySize, String smExt,
                                                 byte[] keyValue) throws SecureCryptoException
    {
        return (ProtectedKey)makeKey(keyUsage, keySize, smExt, keyValue, true);
    }

    private static AuthenticKey makeAuthenticKey(KeyUsage keyUsage, int keySize, String smExt,
                                                 byte[] keyValue) throws SecureCryptoException
    {
        return (AuthenticKey)makeKey(new KeyUsage[] {keyUsage}, keySize, smExt, keyValue, false);
    }

    private static Key makeKey(KeyUsage[] keyUsage, int keySize, String smExt,
                               byte[] keyValue, boolean isProtectedKey) throws SecureCryptoException
    {
        StringBuilder sb = new StringBuilder();
        for (KeyUsage ku : keyUsage)
        {
            String kuStr = String.format("    <KeyUsage Algorithm=\"%s\" Usage=\"%s\"/>\n",
                    ku.getAlgorithm(), ku.getUsage());
            sb.append(kuStr);
        }

        String protectedStr = isProtectedKey? "PROTECTED" : "AUTHENTIC";

        String keyXml = String.format(
                "<SCIKeyData>\n" +
                        "    <MKIdentifier>%s</MKIdentifier>\n" +
                        "    <SMType>%s</SMType>\n" +
                        "%s" +
                        "    <Component>COMPLETE</Component>\n" +
                        "    <KeyType>%s</KeyType>\n" +
                        "    <KeySize>%d</KeySize>\n" +
                        "    <CryptoEngineVersion>1</CryptoEngineVersion>\n" +
                        "    <SMExtension>%s</SMExtension>\n" +
                        "    <SCIKeyValue>%s</SCIKeyValue>\n" +
                        "</SCIKeyData>\n",

                GENERATED_KEY_MK_ID,
                GENERATED_KEY_SM_TYPE,
                sb,
                protectedStr,
                keySize,
                smExt,
                hexify(keyValue)
        );

        InputStream in = new ByteArrayInputStream(keyXml.getBytes());
        return SCIXMLKeyFactory.createSCIKey(in);
    }
}
