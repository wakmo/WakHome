package wak.work.cryptogram.graem.securecrypto;

import wak.work.cryptogram.graem.securecrypto.keycache.KeyCache;
import org.apache.log4j.Logger;

import java.util.Arrays;


/**
 * SecureCrypto provides the interface to the Secure Crypto Interface achitecture. All hardware modules
 * capable of being used by the Secure Crypto Interface must implement this class. Instances of this class
 * are obtained by using the SecureCryptoFactory class.
 *
 * @author Richard Izzard
 * @version 1.0
 */

public abstract class SecureCrypto
{
    private static final Logger log = Logger.getLogger(SecureCrypto.class);


    protected static final byte[] AHASH_IV = new byte[]
        {
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55,
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55,
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55,
            (byte) 0x55, (byte) 0x55, (byte) 0x55, (byte) 0x55
        };


    private enum ExtendedBool { YES, NO, DONT_KNOW }


    //One of the SecureCryptoFactory constants (e.g. SecureCryptoFactory.RG7000_HSM_TYPE)
    protected final int hsmType;

    private final String defaultKeyGenSmType;

    private final boolean hasDefaultKeyGenSmType;

    //Use strong primes to generate RSA keys (as opposed to standard primes)?
    //true=use strong primes, false=use standard primes
    private final boolean useStrongPrimesByDefault;

    private final CryptoModuleMkIdentifier keyGenDefaultMkIdentifier;

    //Can this SecureCrypto generate keys when no MkIdentifier is specified in the GenerateKey message?
    private ExtendedBool mkIdentifierOkForDefaultGenKeys;

    //RSA keys are generated using a key cache in the hope of speeding things up
    protected final KeyCache keyCache;

    //Is this Hsm Connection for key caching only?
    private final boolean forKeyCachingOnly;




    protected SecureCrypto(int hsmType, HsmConnectionParams connectionParams,
                           CryptoModuleMkIdentifier keyGenDefaultMkIdentifier,
                           KeyCache keyCache) throws SecureCryptoException
    {
        this.hsmType = hsmType;
        this.keyGenDefaultMkIdentifier = keyGenDefaultMkIdentifier;
        this.keyCache = keyCache;
        this.forKeyCachingOnly = connectionParams.isKeyCaching();

        String cmSmType = getSmType();

        useStrongPrimesByDefault = connectionParams.isUseStrongPrimes();

        defaultKeyGenSmType = connectionParams.getDefaultKeygenSmType();
        hasDefaultKeyGenSmType = (defaultKeyGenSmType == null) || cmSmType.equals(defaultKeyGenSmType);

        mkIdentifierOkForDefaultGenKeys = (keyGenDefaultMkIdentifier == null
                || keyGenDefaultMkIdentifier.getAs(CryptoModuleMkIdentifier.EncodingType.BINARY) == null
                || keyGenDefaultMkIdentifier.getAs(CryptoModuleMkIdentifier.EncodingType.BINARY).length == 0)
                ? ExtendedBool.YES : ExtendedBool.DONT_KNOW;
    }

    //Constructor used by "FAKE" HSMs
    protected SecureCrypto()
    {
        hsmType = SecureCryptoFactory.FAKE_HSM_TYPE;

        useStrongPrimesByDefault = false;

        hasDefaultKeyGenSmType = true;
        defaultKeyGenSmType = "FAKE";
        keyGenDefaultMkIdentifier = null;
        mkIdentifierOkForDefaultGenKeys = ExtendedBool.YES;
        keyCache = null;
        forKeyCachingOnly = false;
    }


    //e.g. "RG7000", "PS90000", etc
    public abstract String getSmType();

    public String getDefaultKeyGenSmType()
    {
        return defaultKeyGenSmType;
    }

    public boolean isCanHandleSmType(String smType)
    {
        String cmSmType = getSmType();
        
        return cmSmType == null || cmSmType.equalsIgnoreCase(smType);
    }

    public boolean isCanHandleMkIds(NonProcessingExcuses excuses, Key... keys) throws SecureCryptoException
    {
        CryptoModuleMkIdentifier cmMkIdentifier = getMkIdentifier();
        return (cmMkIdentifier == null) || cmMkIdentifier.matchesKeys(excuses, keys);
    }

    //Assumed mkId is encoded as binary
    public abstract boolean isCanHandleMkId(byte[] mkId) throws SecureCryptoException;

    //Can this SecureCrypto generate keys when no MkIdentifier is specified in the GenerateKey message?
    public boolean isMkIdentifierOkForDefaultGenKeys() throws SecureCryptoException
    {
        boolean canProcess;

        switch (mkIdentifierOkForDefaultGenKeys)
        {
            case YES:
                canProcess = true;
                break;

            case NO:
                canProcess = false;
                break;

            case DONT_KNOW:
            {
                CryptoModuleMkIdentifier mkIdentifier = getMkIdentifier();
                canProcess = (mkIdentifier == null) || mkIdentifier.matches(keyGenDefaultMkIdentifier);

                mkIdentifierOkForDefaultGenKeys = canProcess? ExtendedBool.YES : ExtendedBool.NO;

                log.info("This CryptoModule can process key generation commands using default MkIdentifier: " + isMkIdentifierOkForDefaultGenKeys());
                break;
            }

            default:
                String errMsg = "Invalid state: mkIdentifierOkForDefaultGenKeys=" + mkIdentifierOkForDefaultGenKeys;
                log.error(errMsg);
                throw new SecureCryptoException("Internal error: " + errMsg);
        }

        return canProcess;
    }

    //Can this SecureCrypto generate keys when no SmType is specified in the GenerateKey message?
    public boolean hasDefaultKeyGenSmType()
    {
        return hasDefaultKeyGenSmType;
    }

    //Default implementation
    public abstract boolean isBusy();

    /**
     * The function creates a data image containing confidential data and enciphers it using a specified key.
     * A plaintext image is supplied. Confidential data (usually cryptographic keys) are brought into clear
     * within the security module, inserted in the required manner at specified offsets in the image, and
     * the whole enciphered using the specified key and returned. Confidential data is only in clear within
     * the security module.
     * The enciphering key must be of type ECK.
     * The key must be an Authentic key in the case of asymmetric (e.g. RSA) encipherment.
     * The enciphering algorithm may be any supported by the security module, and is implied by the
     * algorithm attribute of the supplied key.
     * The specification of the elements to be inserted into the image is contained in fragmentSet.
     * This specification has a doubly iterative form.
     * Each element of fragmentSet contains one Protected key K, and a number of associated fragmentOffsets.
     * Each fragmentOffset defines what is to be inserted into the plaintext image, and at what offset,
     * using key K.
     * The fragments inserted are of three kinds:
     * �	The whole or part of the key K (for example, a whole DES key, the most significant half of a double
     * DES key, a whole RSA key, a CRT component of an RSA key).
     * �	An enciphered data item (fragment type = DATA). In this case the data item is recovered into clear
     * by deciphering it using key K. The resulting clear data item can be processed in a limited way, and
     * the whole, or a portion of it, inserted into the image. There is a capability for some constant data
     * to be exclusive OR'ed with the clear data before being inserted. The detailed rules are given in
     * section 6.
     * Constraints for fragment type = DATA:
     * �	The data must be an integral number of 8 byte blocks.
     * �	Key K must be a double DES key of type ECK.
     * �	The data must be presented DES enciphered under key K, using DES CBC mode with an IV of 0 (equivalent
     * to ECB mode for a single block).
     * �	An enciphered data item (fragment type = PIN). This option allows for the special case of insertion of
     * a PIN recovered from an enciphered PIN block. It works exactly as for fragment type = DATA, with
     * different restrictions. (Note, the implementation does not know about specific PIN block formats.
     * The user must use the fragment processing facilities to achieve the required prcessing. Technically a
     * distinct PIN option is not necessary. It is provided to enable certain types of security module to be
     * more easily supported.)
     * Constraints for fragment type = PIN:
     * �	The PIN block must be a single 8 byte block.
     * �	Key K must be a double DES key of type ECK.
     * �	The PIN block must be presented DES enciphered under key K.
     *
     * @param image   Plain text image.
     * @param key     Key usged to encipher the constructed image
     * @param fragSetArray Image definition.
     * @return Enciphered image.
     * @throws SecureCryptoException Thrown if the parameters are incorrect or is the cryptographic
     *                               hardware module has a problem building the image.
     */
    public byte[] buildEncipher(byte[] image, AuthenticKey key, FragmentSetArray fragSetArray)
            throws SecureCryptoException
    {
        checkBuildEncipherCondition(image, key, fragSetArray);
        return buildSecureImage(image, key, fragSetArray);
    }


    /**
     * Refers to the ProtectedKey version of buildEncipher.
     * Tests whether or not this SecureCrypto can execute a BuildEncipher command with the supplied
     * FragmentSetArray
     *
     * @param fragSetArray The FragmentSetArray to test with
     *
     * @return false if this SecureCrypto definitely cannot process this command (and so another
     *     HSM type should be used instead), or true otherwise
     * @throws SecureCryptoException An error occurred determining
     */
    public boolean isThisHsmCanBuildEncipher(FragmentSetArray fragSetArray) throws SecureCryptoException
    {
        return isThisHsmCanBuildSecureImage(fragSetArray);
    }

    /**
     * Check if this crypto module can use all the keys passed in.
     *
     * e.g. If this SecureCrypto is for a Thales PS9000, and one of the keys has an SMType of NCIPHER,
     * then false will be returned.
     *
     * The SMType of each key, as well as the MK Identifier, is checked
     *
     * @param keys The keys which are required to be used on this crypto module
     * @return true if all keys can be used on this crypto module, or false otherwise
     * @throws SecureCryptoException Failed to complete the check
     */
    public boolean isCanUseKeys(Key... keys) throws SecureCryptoException
    {
        return isCanUseKeys(null, keys);
    }

    /**
     * Check if this crypto module can use all the keys passed in.
     *
     * e.g. If this SecureCrypto is for a Thales PS9000, and one of the keys has an SMType of NCIPHER,
     * then false will be returned.
     *
     * The SMType of each key, as well as the MK Identifier, is checked
     *
     * @param excuses List of excuses to be added to explaining why this SecureCrypto
     *     cannot use they key(s).  Pass as null if no excuses required.
     * @param keys The keys which are required to be used on this crypto module
     *
     * @return true if all keys can be used on this crypto module, or false otherwise
     * @throws SecureCryptoException Failed to complete the check
     */
    public boolean isCanUseKeys(NonProcessingExcuses excuses, Key... keys) throws SecureCryptoException
    {
        boolean allKeysOk = true;
        boolean someKeysOk = false;

        //Check SMType of all keys matches this module's SMType
        for (int i = 0; i < keys.length; i++)
        {
            Key key = keys[i];
            if (key == null)
            {
                throw new SecureCryptoException("Key " + i + " is null");
            }

            String keySmType = key.getSMType();
            boolean keyOk = isCanHandleSmType(keySmType);

            allKeysOk &= keyOk;
            someKeysOk |= keyOk;

            if (!keyOk && excuses != null)
            {
                excuses.addKeyExcuse(key, "Key SM Type is " + key.getSMType() + " but this CryptoModule's SMType is " + getSmType());
            }
        }

        if (someKeysOk && !allKeysOk)
        {
            //Keys have differing SMTypes - this means the original SCICommand contained invalid data
            //so throw an exception
            throw new SecureCryptoException("Two or more keys have differing SMTypes");
        }


        allKeysOk &= isCanHandleMkIds(excuses, keys);

        return allKeysOk;
    }

    /**
     * Returns the MK Identifier for the Crypto Module, or null if the Crypto Module does not
     * have one.
     *
     * @return Object containing the crypto module's MK identifier
     */
    protected abstract CryptoModuleMkIdentifier getMkIdentifier() throws SecureCryptoException;


    /**
     * Refers to the ProtectedKey (symmetric crypto) version of BuildSecureImage.
     * Tests whether or not this SecureCrypto can execute a Build Secure Image command with the supplied
     * FragmentSetArray
     *
     * @param fragSetArray The FragmentSetArray to test with
     *
     * @return false if this SecureCrypto definitely cannot process this command (and so another
     *     HSM type should be used instead), or true otherwise
     * @throws SecureCryptoException An error occurred determining
     */
    protected boolean isThisHsmCanBuildSecureImage(FragmentSetArray fragSetArray) throws SecureCryptoException
    {
        //Default implementation
        return true;
    }

    /**
     * The function creates a data image containing confidential data and enciphers it using a specified key.
     * A plaintext image is supplied. Confidential data (usually cryptographic keys) are brought into clear
     * within the security module, inserted in the required manner at specified offsets in the image, and
     * the whole enciphered using the specified key and returned. Confidential data is only in clear within
     * the security module.
     * The enciphering key must be of type ECK.
     * It must be a Protected key in the case of symmetric (e.g. DES) encipherment.
     * The enciphering algorithm may be any supported by the security module, and is implied by the
     * algorithm attribute of the supplied key.
     * The specification of the elements to be inserted into the image is contained in fragmentSet.
     * This specification has a doubly iterative form.
     * Each element of fragmentSet contains one Protected key K, and a number of associated fragmentOffsets.
     * Each fragmentOffset defines what is to be inserted into the plaintext image, and at what offset,
     * using key K.
     * The fragments inserted are of three kinds:
     * �	The whole or part of the key K (for example, a whole DES key, the most significant half of a double
     * DES key, a whole RSA key, a CRT component of an RSA key).
     * �	An enciphered data item (fragment type = DATA). In this case the data item is recovered into clear
     * by deciphering it using key K. The resulting clear data item can be processed in a limited way, and
     * the whole, or a portion of it, inserted into the image. There is a capability for some constant data
     * to be exclusive OR'ed with the clear data before being inserted. The detailed rules are given in
     * section 6.
     * Constraints for fragment type = DATA:
     * �	The data must be an integral number of 8 byte blocks.
     * �	Key K must be a double DES key of type ECK.
     * �	The data must be presented DES enciphered under key K, using DES CBC mode with an IV of 0 (equivalent
     * to ECB mode for a single block).
     * �	An enciphered data item (fragment type = PIN). This option allows for the special case of insertion of
     * a PIN recovered from an enciphered PIN block. It works exactly as for fragment type = DATA, with
     * different restrictions. (Note, the implementation does not know about specific PIN block formats.
     * The user must use the fragment processing facilities to achieve the required prcessing. Technically a
     * distinct PIN option is not necessary. It is provided to enable certain types of security module to be
     * more easily supported.)
     * Constraints for fragment type = PIN:
     * �	The PIN block must be a single 8 byte block.
     * �	Key K must be a double DES key of type ECK.
     * �	The PIN block must be presented DES enciphered under key K.
     *
     * @param image        Plain text image.
     * @param key          Key usged to encipher the constructed image
     * @param mode         DES mode for encipherment of the image either ECB or CBC.
     * @param iv           Initialisation vector.
     * @param fragSetArray Template of the image to build.
     * @return Enciphered image. The image must be a multiple of eight bytes.
     * @throws SecureCryptoException Thrown if the parameters are incorrect or the cryptographic
     *                               hardware module has a problem building the image.
     */
    public byte[] buildEncipher(byte[] image, ProtectedKey key, CipherMode mode, byte[] iv, FragmentSetArray fragSetArray) throws SecureCryptoException
    {
        checkBuildEncipherCondition(image, key, fragSetArray);
        return buildSecureImage(image, key, mode, iv, fragSetArray);
    }

    /**
     * The function derives a new DES key by using an existing DES key deriving key to encipher a piece of
     * (non-confidential) data. The function is defined for DES keys only. The key deriving key is passed
     * as a Protected key, and must have a top level key usage definition of KDK (Key Deriving Key).
     * The data to be enciphered to create the new key is passed in the cipher request. This also indicates
     * whether DES ECB or CBC encipherment is to be used, and allows for the specification of an IV in the
     * latter case.
     * The data supplied for encipherment must be an integral number of 8 byte blocks. Its length determines
     * the length of the derived key (they are identical). Note that this does not have to be related to the
     * length of the key deriving key (which may be a single, double or triple DES key).
     * The attributes (algorithm and usage) of each level of the derived key will have been specified when the key deriving key itself was generated - the attributes of the derived key are created by discarding the first level of attributes of the key deriving key (the KDK level), and retaining all remaining levels.
     * The derived key is created by taking each 8 byte block of image in turn, transforming it with DES ECB
     * encipherment using the key derivation key and algorithm specified in key.attributes.algorithm, forcing
     * odd parity on the result, and concatenating the resulting blocks.
     * Note that this function is not intended for data encipherment - the result is a key, and will not have
     * the value expected for normal data encipherment because of the enforced odd parity.
     *
     * @param image       Data new key is derived from.
     * @param derivingKey Deriving key.
     * @param mode        Enciphering mode
     * @param iv          Initialisation vector.
     * @return New derived key.
     * @throws SecureCryptoException Thrown when key cannot be derived from given properties.
     */
    public ProtectedKey generateDerivedKey(byte[] image, ProtectedKey derivingKey, CipherMode mode, byte[] iv) throws SecureCryptoException
    {
        final KeyUsage[] ku = derivingKey.getKeyUsage();

        if ((ku == null) || (ku.length < 2))
        {
            throw new SecureCryptoException("Invalid key usage, must be multi level");
        }

        final Algorithm derivingAlgorithm = ku[1].getAlgorithm();

        if (!Usage.KDK.equals(derivingKey.getEncodingUsage()))
        {
            throw new SecureCryptoException("Invalid key usage, must be KDK");
        }

        if (!Component.Complete.equals(derivingKey.getComponent()))
        {
            throw new SecureCryptoException("Invalid key component, must be complete");
        }

        if (CipherMode.CBC.equals(mode))
        {
            if ((image != null) && (java.lang.Math.IEEEremainder(image.length, 8) != 0))
            {
                throw new SecureCryptoException("Invalid CBC image, must be a multiple 8 bytes long");
            }
        }
        else
        {
            if (Algorithm.DES1E.equals(derivingAlgorithm) && (image != null) && (image.length != 8))
            {
                throw new SecureCryptoException("Invalid image length for DES1E, must be 8 bytes long");
            }
            else if (Algorithm.DES2EDE.equals(derivingAlgorithm) && (image != null) && (image.length != 16))
            {
                throw new SecureCryptoException("Invalid image length for DESEDE, must be 16 bytes long");
            }
        }
        return doGenerateDerivedKey(image, derivingKey, mode, iv);
    }

    /**
     * The function provides a method of importing a new keyEncryptingKey enciphered under a keyEncryptingKey enciphering keyEncryptingKey,
     * which must be a Protected keyEncryptingKey of usage TRK. The input parameter encipheredKey is the binary
     * value of the keyEncryptingKey to be imported, enciphered under keyEncipheringKey. The function uses the keyEncryptingKey
     * enciphering keyEncryptingKey to decipher the enciphered keyEncryptingKey, then convert it into Protected keyEncryptingKey form, when
     * it can be safely returned.
     * The attributes (algorithm and usage) of each level of the introduced keyEncryptingKey will have been specified
     * when the keyEncryptingKey enciphering keyEncryptingKey itself was generated - the attributes of the introduced keyEncryptingKey are
     * derived by discarding the first level of attributes of the keyEncryptingKey deriving keyEncryptingKey (the TRK level) and
     * retaining the rest.
     * DES ECB mode will have been used to encipher the keyEncryptingKey being imported. This mechanism is only used for
     * the import of symmetric keys. Odd parity is forced on the derived keyEncryptingKey created by this function.
     *
     * @param keyUnderKek Represents the new keyEncryptingKey being introduced into the system.
     * @param keyEncryptingKey  The keyEncryptingKey encrypting keyEncryptingKey that protects the new keyEncryptingKey during transmission.
     * 
     * @return The introduced keyEncryptingKey enciphered under the appropriate master keyEncryptingKey.
     * 
     * @throws SecureCryptoException       Thrown if the new cannot be introduced.
     * @throws SecureCryptoModuleException Thrown if the parameters specified cannot be executed
     *                                     by the module.
     */
    public ProtectedKey importEncipheredKey(byte[] keyUnderKek, ProtectedKey keyEncryptingKey) throws SecureCryptoException
    {
        KeyUsage[] kekUsage = keyEncryptingKey.getKeyUsage();

        if (keyUnderKek == null)
        {
            throw new SecureCryptoException("keyUnderKek is null");
        }

        if ((kekUsage == null) || (kekUsage.length < 2))
        {
            throw new SecureCryptoException("Key usage for Key Encrypting Key must have at least 2 levels");
        }

        Usage usage = kekUsage[0].getUsage();
        Algorithm alg = kekUsage[0].getAlgorithm();

        if (!Usage.TRK.equals(usage))
        {
            throw new SecureCryptoException("The level 0 usage of the Key Encrypting Key must be TRK");
        }

        if (!Algorithm.DES2EDE.equals(alg))
        {
            throw new SecureCryptoException("The level 0 of the Key Encrypting Key algorithm must be " + Algorithm.DES2EDE.toString());
        }

        if (!Component.Complete.equals(keyEncryptingKey.getComponent()))
        {
            throw new SecureCryptoException("The keyEncryptingKey must be Complete, rather than a component");
        }
        
//        The following check does not make sense for PS9000-HSM:
//        There a 2DES keyEncryptingKey can be 33 bytes long: 1 byte keyEncryptingKey scheme, the other bytes hex-encoded (one byte per nibble)
//        final Algorithm alg1 = kekUsage[1].getAlgorithm();
//        if (alg1.isSingleDES() && (keyUnderKek.length != 8))
//        {
//            throw new SecureCryptoException("Single length DES keys must have 8 bytes of keyUnderKek");
//        }
//
//        if (alg1.isDoubleDES() && (keyUnderKek.length != 16))
//        {
//            throw new SecureCryptoException("Double length DES keys must have 16 bytes of keyUnderKek");
//        }
        return doImportEncipheredKey(keyUnderKek, keyEncryptingKey);
    }

    public ProtectedKey importEncipheredKey(byte[] data, ProtectedKey key, RefinedKeyUsage rku) throws SecureCryptoException
    {
        log.warn("Operation not supported on this HSM: importEncipheredKey(byte[] data, ProtectedKey key, RefinedKeyUsage rku)");
        throw new SecureCryptoException("Operation not supported on this HSM.");
    }
    
    /**
     * 
     * @param keyEncipheringKey KEK
     * @param padding padding mode only needed for RSA keys
     * @param mode ECB or CBC
     * @param iv only needed if cipher mode is CBC
     * @param keyToBeWrapped    
     * @return wrapped key as ByteArray
     * @throws SecureCryptoException
     */
    public abstract byte[] wrapKey(
    	ProtectedKey keyEncipheringKey,
    	PadMode padding,
    	CipherMode mode,
    	byte[] iv,
    	ProtectedKey keyToBeWrapped
    ) throws SecureCryptoException ;

    /**
     * The function provides a method of exporting a key enciphered under a key enciphering key
     * (transport key). The transport key must be a Protected key of usage ECK. The key to be
     * enciphered must also be a protected DES key, with Usage set to any of the values shown.
     *
     * This function is used when it is required to share keys with a third party and a transport
     * key has already been established for this purpose (this will usually have been carried out
     * by sharing the transport key in multi-component form).
     *
     * The same effect can normally be obtained using the buildEncipher function, which inserts
     * the key to be exported into an image and then enciphers it. Note that some security modules
     * place constraints on keys which can be exported in multi-component form. For such SMs the
     * buildEncipher route might not be available for a transport key which is shared in component
     * form - exportEncipheredKey should be used instead. Conversely, the transport key created
     * for this purpose may only be usable to export keys - it cannot be used in other
     * circumstances where an ECK might be expected to be usable, such as cipherData. This topic
     * is clarified in the SM dependent parts of this specification.
     */
    public byte[] exportEncipheredKey(ProtectedKey keyEncipheringKey, ProtectedKey keyForEncipherment) throws SecureCryptoException
    {

        // keyEncipheringKey.attributes.keyUsage(0).algorithm = DES*
        // keyEncipheringKey.attributes.keyUsage(0).usage = ECK
        // keyEncipheringKey.attributes.component = COMPLETE
        if (!keyEncipheringKey.getEncodingAlgorithm().isDES())
        {
            throw new SecureCryptoException("Enciphering key must be DES");
        }

        if (!Usage.ECK.equals(keyEncipheringKey.getEncodingUsage()))
        {
            throw new SecureCryptoException("Enciphering key must be ECK");
        }

        if (!Component.Complete.equals(keyEncipheringKey.getComponent()))
        {
            throw new SecureCryptoException("Enciphering key must be complete");
        }

        // keyForEncipherment.attributes.keyUsage(0).algorithm = DES*
        if (!keyForEncipherment.getEncodingAlgorithm().isDES())
        {
            throw new SecureCryptoException("Encipherment key must be complete");
        }

        // keyForEncipherment.attributes.keyUsage(0).usage = ECK | DCK | BDK | KDK
        if (!Usage.ECK.equals(keyForEncipherment.getEncodingUsage()) &&
                !Usage.DCK.equals(keyForEncipherment.getEncodingUsage()) &&
                !Usage.BDK.equals(keyForEncipherment.getEncodingUsage()) &&
                !Usage.KDK.equals(keyForEncipherment.getEncodingUsage()))
        {
            // ncr fix for UKPMA#1536
            throw new SecureCryptoException("Key being enciphered must be either ECK, DCK, BDK or KDK");
        }

        // keyForEncipherment.attributes.component = COMPLETE
        if (!Component.Complete.equals(keyForEncipherment.getComponent()))
        {
            throw new SecureCryptoException("Enciphering key must be complete");
        }

        return doExportEncipheredKey(keyEncipheringKey, keyForEncipherment);
    }

    /**
     * The function calculates and returns the 3 byte KCV (Key Check Value) of the key protectedKey. The
     * function is only applicable to DES keys. The KCV is the most significant three bytes of the
     * encipherment of zero using protectedKey.
     * The algorithm associated with the key protectedKey is of the form DESnXXX, where n = 1, 2, or 3,
     * depending on whether this is a single, double or triple DES key (see the definition of the type
     * Algorithm).
     *
     * @param pKey The key to calculate the check sum over.
     * @return The most significate three bytes of the encipherment.
     * @throws SecureCryptoException       If there is a problem calculating the check sum.
     * @throws SecureCryptoModuleException Thrown if the parameters specified cannot be executed
     *                                     by the module.
     */
    public byte[] calculateKeyCheckValue(ProtectedKey pKey) throws SecureCryptoException
    {
        if (pKey == null)
        {
            throw new SecureCryptoException("No key supplied");
        }
        if (!pKey.getEncodingAlgorithm().isDES())
        {
            throw new SecureCryptoException("Can only calulate the check value for a DES key");
        }
        return doCalculateKeyCheckValue(pKey);
    }

    /**
     * The function enciphers or deciphers the input data using a specified algorithm and key,
     * and returns the cipher result. The cipher request contains the input image, the ciphering key as a
     * Protected key and a flag to indicate whether an encipher or a decipher operation is required.
     * The symmetric cipher request also contains some algorithm specific parameters - the mode (ECB or CBC)
     * and, for CBC, the value of the initial vector.
     *
     * @param image  Image to be enciphered / deciphered.
     * @param encDec Encipher / Decipher indicator.
     * @param key    Ciphering key.
     * @param mode   Cipherment mode EBC / CBC.
     * @param iv     Initialisation vector.
     * @return Image enciphered or deciphered using the key supplied.
     * @throws SecureCryptoException       Cannot perform the desired operation.
     * @throws SecureCryptoModuleException Thrown if the parameters specified cannot be executed
     *                                     by the module.
     */
    public byte[] cipherImage(byte[] image, EncDec encDec, ProtectedKey key, CipherMode mode, byte[] iv)
            throws SecureCryptoException
    {
        checkCipherConditions(image, encDec, key);
        return doCipherImage(image, encDec, key, mode, iv);
    }

    /**
     * The function enciphers or deciphers the input data using a specified algorithm and key,
     * and returns the cipher result. The cipher request contains the input image, the ciphering key as a
     * Protected key (in the case of a private key) or an Authentic key (in the case of a public key), and
     * a flag to indicate whether an encipher or a decipher operation is required.
     * The asymmetric function is only intended for single block operation. For RSA the block to be
     * enciphered or deciphered should have a binary value less than the key's modulus to ensure a
     * correct result. This is not checked. There are separate functions for signing messages and
     * verifying signatures using RSA.
     *
     * @param image  Image to be enciphered / deciphered.
     * @param encDec Encipher / Decipher indicator.
     * @param key    Ciphering key.
     * @return Image enciphered or deciphered using the key supplied.
     * @throws SecureCryptoException       Cannot perform the desired operation.
     * @throws SecureCryptoModuleException Thrown if the parameters specified cannot be executed
     *                                     by the module.
     */
    public byte[] cipherImage(byte[] image, EncDec encDec, Key key) throws SecureCryptoException
    {
        checkCipherConditions(image, encDec, key);
        if (EncDec.encrypt.equals(encDec) && !(key instanceof AuthenticKey))
        {
            throw new SecureCryptoException("To encipher with an asymmetric key supply the Authentic key");
        }
        if (EncDec.decrypt.equals(encDec) && !(key instanceof ProtectedKey))
        {
            throw new SecureCryptoException("To decipher with an asymmetric key supply the Protected key");
        }

        return doCipherImage(image, encDec, key);
    }

    /**
     * A single, double or triple length key can be specified. A variety of algorithms are supported, for
     * example DES1E, DES2EDE, DES2DDD, or in general DESnXXX.
     * The digit n specifies the number of 8 byte keys which constitute the whole DES key (1, 2, or 3),
     * and the remainder of the specification gives the elementary DES operations.
     * All DES keys are generated with odd parity. No SCI functions ever check that DES keys being used
     * have any particular parity.
     *
     * @param keyUsage Usage of the key to be generated.
     * @param keySize  Size of the key to be generated.
     * @return New key enciphered under a master key.
     * @throws SecureCryptoException Thrown if new key cannot be generated.
     */
    public ProtectedKey generateKey(KeyUsage[] keyUsage, int keySize) throws SecureCryptoException
    {
        if (keyUsage == null)
        {
            throw new SecureCryptoException("Valid key usage must be supplied: keyUsage was null");
        }
        if (keyUsage.length == 0)
        {
            throw new SecureCryptoException("Valid key usage must be supplied: keyUsage was empty");
        }

        for (int level = 0; level < keyUsage.length; level++)
        {
            final boolean isFinalLevel = level + 1 == keyUsage.length;
            final KeyUsage thisLevelUsage = keyUsage[level];
            
            if (!thisLevelUsage.getAlgorithm().isDES())
            {
                throw new SecureCryptoException("The key usage algorithm must be DES, but keyUsage at level" + level + " is " + thisLevelUsage);
            }

            if (!isFinalLevel
                    && (!Usage.KDK.equals(thisLevelUsage.getUsage()))
                    && (!Usage.TRK.equals(thisLevelUsage.getUsage())))
            {
                throw new SecureCryptoException("Only TRK and KDK usage types are allowed in levels which aren't the final level.  But here level=" + level + " and usage=" + thisLevelUsage);
            }

            if (isFinalLevel && !Usage.BDK.equals(thisLevelUsage.getUsage()) &&
                    !Usage.DCK.equals(thisLevelUsage.getUsage()) &&
                    !Usage.ECK.equals(thisLevelUsage.getUsage()))
            {
                throw new SecureCryptoException("Last usage level must be either BDK, DCK or ECK, but here the final level's usage is " + thisLevelUsage);
            }
        }
        
        return doGenerateKey(keyUsage, keySize);
    }

    protected abstract ProtectedKey doGenerateKey(KeyUsage[] keyUsage, int keySize) throws SecureCryptoException;

    public abstract ProtectedKey generateKey(KeyUsage[] keyUsage, RefinedKeyUsage rku,
            String smType, byte[] mkIdentifier) throws SecureCryptoException;

    //Returns true if this SecureCrypto is only to be used for Key Caching, or false otherwise
    public boolean isForKeyCaching()
    {
        return forKeyCachingOnly;
    }


    public abstract KeyPair generateKey(RsaKeyGenOptions keyGenOptions) throws SecureCryptoException;
    

    /**
     * The function returns a random number of the requested size. The random number is generated using the
     * security module's hardware random number generation capability. The quality and unpredictability of
     * a sequence of random numbers delivered by this function is dependent on the properties of the
     * individual device.
     *
     * @param size The size of the random number to be generated.
     * @return Random number generated.
     * @throws SecureCryptoException Thrown if random number generation fails.
     */
    public abstract byte[] generateRandom(int size) throws SecureCryptoException;

    /**
     * The function returns information about the availability of an SCI cryptographic resource
     * to satisfy SCI function calls.
     *
     * @return True if an SCI resource is currently available, otherwise it returns False.
     */
    public abstract boolean getAvailability() throws SecureCryptoException;

    /**
     * The output identifies whether or not the function is supported and, if it is,
     * identifies the options provided. For example, an enquiry on buildEncipher
     * defines which fragment types can be written into the image and which algorithm
     * types are supported for the encipherment of the final image. The options available
     * for each function type for the currently supported security modules can be
     * determined from sections 7 and 8 of the SCI logical specification.
     *
     * A Crypto Module of the default SM Type (from SCI configuration) will have its capabilities returned.
     *
     * @param function The SCI function under investigation.
     * @return Capability object specifying the parameters that can be applied to the given function.
     */
    public Capability getCapability(Function function) throws SecureCryptoException
    {
        return getCapability(function, null);
    }

    /**
     * The output identifies whether or not the function is supported and, if it is,
     * identifies the options provided. For example, an enquiry on buildEncipher
     * defines which fragment types can be written into the image and which algorithm
     * types are supported for the encipherment of the final image. The options available
     * for each function type for the currently supported security modules can be
     * determined from sections 7 and 8 of the SCI logical specification.
     *
     * @param function The SCI function under investigation.
     * @param smType The SM Type that this function must be executed on.  If null then the default
     *               SM Type will be used
     * @return Capability object specifying the parameters that can be applied to the given function.
     */
    public abstract Capability getCapability(Function function, String smType) throws SecureCryptoException;

    /**
     * Get the status of the cryptographic hardware module.
     *
     * @return Current hardware module status.
     * @throws SecureCryptoException Thrown if hardware module experiences an error.
     */
    public abstract SecureModuleStatus getStatus() throws SecureCryptoException;

    /**
     * The function creates a data image containing confidential data and hashes it using the specified
     * hash algorithm. This function is aimed at the situation when the data to be hashed contains
     * confidential elements. If the data to be hashed is not confidential then the separate hashMac
     * function is more flexible and likely to be better suited to the requirement.
     * A plaintext image is supplied. Confidential data (usually cryptographic keys) are brought into
     * clear within the security module, inserted in the required manner at specified offsets in the image,
     * and the whole hashed using the specified hash algorithm, and the hash value returned. Confidential
     * data is only in clear within the security module.
     * The image to be populated is specified in image. The hash algorithm is specified in hashParams.
     *
     * @param image        Image to hash.
     * @param fragSetArray Format of image.
     * @param hashParams   Details of the hashing required.
     * @return The hash of the image.
     * @throws SecureCryptoException Thrown if hash fails.
     */
    public byte[] buildHash(byte[] image, FragmentSetArray fragSetArray, HashParams hashParams)
            throws SecureCryptoException
    {
        if (HashAlgorithm.NO_HASH.equals(hashParams.getHashAlgorithm()))
        {
            throw new SecureCryptoException("No hash is not a valid option");
        }
        else if (HashAlgorithm.AHASH.equals(hashParams.getHashAlgorithm()))
        {
            if ((image == null) || (java.lang.Math.IEEEremainder(image.length, 56) != 0))
            {
                throw new SecureCryptoException("Image length must be a multiple 56 bytes for hashing algorithm AHASH.");
            }
            final Key hashKey = hashParams.getKey();
            if (!(hashKey instanceof AuthenticKey))
            {
                throw new SecureCryptoException("Hashing key must be an authentic key for hashing algorithm AHASH");
            }
            if (!hashKey.getEncodingAlgorithm().isRSA())
            {
                throw new SecureCryptoException("Hashing key must be RSA for hashing algorithm AHASH");
            }
        }

        return doBuildHash(image, fragSetArray, hashParams);
    }

    /**
     * The function calculates and returns a hash or MAC value over image. Only images of an integral
     * number of bytes are supported. hashParams will contain a key if the hash or MAC algorithm
     * requires a key. The hash or MAC algorithm pads the image first, in a manner determined by the
     * hash algorithm as defined in section 9.2.
     *
     * @param image      Image to hash.
     * @param hashParams Details of the hashing required.
     * @return Hash of the image.
     * @throws SecureCryptoException       Thrown if the hashing fails.
     * @throws SecureCryptoModuleException Thrown if the parameters specified cannot be executed
     *                                     by the module.
     */
    public byte[] buildHash(byte[] image, HashParams hashParams) throws SecureCryptoException
    {
        if ((image == null) || (image.length == 0))
        {
            throw new SecureCryptoException("Invalid image data specified");
        }

        if (hashParams == null)
        {
            throw new SecureCryptoException("Hash params must be supplied");
        }

        if (HashAlgorithm.NO_HASH.equals(hashParams.getHashAlgorithm()))
        {
            throw new SecureCryptoException("No hash not supported");
        }

        if (hashParams.getHashAlgorithm().isCBC())
        {
            if (!(hashParams.getKey() instanceof ProtectedKey))
            {
                throw new SecureCryptoException("CBC hashing must have a protected key");
            }
            if (!(Usage.ECK.equals(hashParams.getKey().getEncodingUsage())))
            {
                throw new SecureCryptoException("CBC hashing key must be ECK");
            }
        }

        if (HashAlgorithm.AHASH.equals(hashParams.getHashAlgorithm()))
        {
            if (!(hashParams.getKey() instanceof AuthenticKey))
            {
                throw new SecureCryptoException("AHASH must have an authentic key");
            }
            if (!hashParams.getKey().getEncodingAlgorithm().isRSA())
            {
                throw new SecureCryptoException("The AHASH key must be RSA");
            }
        }

        return doBuildHash(image, null, hashParams);
    }


    /**
     * The function creates an SCI key object containing both key.attributes and key.value parts.
     * The value part is the correctly key value in the appropriate security module dependent format.
     * The function puts the supplied attributes into the key object so that it can be used in
     * subsequent SCI function calls. There is no security exposure if this is done incorrectly as
     * the correct security partitioning has already been achieved when the value was enciphered.
     * If the two parts of the SCI key object have inconsistent attributes then function calls
     * which try to use it will return error messages. Note that the consistency of attribute and
     * value parts is not checked by this importNativeKey function.
     * The import function only applies to private keys, both symmetric and asymmetric.
     * The function is primarily intended to support key management activities.
     */
    /*
     * symmetric version
     */
    public abstract Key importNativeKey(
    	String smType, 
    	byte[] nativeKey, 
    	RefinedKeyUsage keyType, 
    	KeyUsage[] keyUsage, 
    	String mkId, 
    	Component comp, 
    	int cryptoEngineVersion
    ) throws SecureCryptoException;
    /*
     * asymmetric version
     */
    public abstract Key importNativeKey(
    	KeyType privOrAuth, 
    	String smType,
    	int keySize,
    	byte[] nativeKey, 
    	RefinedKeyUsage keyType, 
    	KeyUsage[] keyUsage, 
    	String mkId, 
    	Component comp, 
    	int cryptoEngineVersion
    ) throws SecureCryptoException;
    

    /**
     * The function calculates an asymmetric signature over an input message. It allows a variety of
     * options for padding and hashing the message before generating the signature using the supplied
     * signature key. The function is initially for creating RSA signatures.
     * If the security module supports CRT exponentiation, this option will be used for the signature
     * generation. This is unlikely to be of interest to the client application. Note, however, that the
     * option used is not constrained by the algorithm option specified when the key was generated.
     * The signature operation, which is equivalent to the decrypt or private key operation, is applied
     * to a single block of binary data which should have a numerical value smaller than that of the
     * modulus of the signing key. The data to be signed should therefore either obey this constraint or a
     * hash option should be specified which reduces the data to be signed to a size which obeys the constraint.
     * This condition is not checked by the sign message function.
     *
     * @param message      Message to sign.
     * @param hashParams   Specifies the hashing algorithm and key if applicable.
     * @param padMode      Defines the PadMode to use.
     * @param signatureKey Key used to sign the message.
     * @return Signed message.
     * @throws SecureCryptoException       Thrown if cryptographic module fails to perform operation.
     * @throws SecureCryptoModuleException Thrown if the parameters specified cannot be executed
     *                                     by the module.
     */
    public abstract byte[] signMessage(byte[] message, HashParams hashParams, PadMode padMode, ProtectedKey signatureKey) throws SecureCryptoException;

    /**
     * The function verifies an asymmetric signature over an input message. It supports a variety of
     * options for padding and hashing the message before verifying the signature using the supplied
     * public key. The function is initially for verifying RSA signatures.
     * The signature verification operation is equivalent to the encrypt or public key operation.
     * The operation is applied to the signature using the supplied public key verificationKey, and
     * the result compared with that obtained by applying the padding and/or hashing options, if any,
     * to the input message. It the results are identical a True result is returned, otherwise a False
     * result is returned.
     *
     * @param message         Message to verify.
     * @param hashParams      Hash algorithm and key used to sign message.
     * @param padMode         Specifies the padding mode.
     * @param verificationKey Key used to verify the signed message.
     * @param signature       Signature.
     * @return True if the signature is verified and false if not.
     * @throws SecureCryptoException       Thrown if cryptographic module fails to perform operation.
     * @throws SecureCryptoModuleException Thrown if the parameters specified cannot be executed
     *                                     by the module.
     */
    public boolean verifyMessageSignature(byte[] message, HashParams hashParams, PadMode padMode, AuthenticKey verificationKey, byte[] signature) throws SecureCryptoException
    {
        if (!verificationKey.getEncodingAlgorithm().isRSA())
        {
            throw new SecureCryptoException("The verification key must be an RSA key");
        }
        return doVerifyMessageSignature(message, hashParams, padMode, verificationKey, signature);
    }

    /**
     * The function verifies a supplied public key in the form of a key certificate. The data required to
     * carry this out, and the detailed method, are both dependent on the type of key involved. The
     * typical scenario is where a public key certification key is supplied in authentic form in keys
     * (it will probably have been imported via the key management interface and converted into a key of
     * type AuthenticKey under secure conditions, so that its authenticity is assured). The public key
     * certification key is the public counterpart of the private key which will have been used to create
     * the certificate.
     * When the security module has verified the authenticity of the public key, it converts it to
     * AuthenticKey form and returns it.
     *
     * @param keys           to verify.
     * @param usage          Key usage.
     * @param verifyAlg      Verification algorithm.
     * @param keyCertificate Key certificate.
     * @return Authenticated public key.
     * @throws SecureCryptoException Thrown if the parameters specified cannot be executed
     *                               by the module.
     */
    public abstract AuthenticKey verifyPublicKey(Key[] keys, Usage usage, VerifyAlgorithm verifyAlg, byte[] keyCertificate) throws SecureCryptoException;

    /**
     * The function creates a real MULTOS KTU and enciphers it using the target card's public key.
     * The public key is presented in the form of a certificate, cardKeyCertificate, in MULTOS format.
     * The function uses the supplied MULTOS tkck_pk (Transport Key Certification Key - Public Key) and,
     * optionally, hashModulus (MULTOS hash modulus), to verify the card key certificate and extract the
     * public key.
     *
     * @param pseudoKTU             KTU image.
     * @param fragSetArray          Template of the KTU.
     * @param tkck_pk               Transport Key Certificatin Key.
     * @param cardCertificate       Card certificate.
     * @param multosVerifyAlgorithm Mulos verification algorithm.
     * @param hashModulus           Multos hash modulus.
     * @return New real KTU
     * @throws SecureCryptoException       Thrown if cryptographic module fails to perform operation.
     * @throws SecureCryptoModuleException Thrown if the parameters specified cannot be executed
     *                                     by the module.
     */
    public byte[] createRealMultosKTU(byte[] pseudoKTU, FragmentSetArray fragSetArray, AuthenticKey tkck_pk, byte[] cardCertificate, MultosVerifyAlgorithm multosVerifyAlgorithm, AuthenticKey hashModulus) throws SecureCryptoException
    {
        int cardKeyLength;

        if (!Usage.VEK.equals(tkck_pk.getEncodingUsage()))
        {
            throw new SecureCryptoException("The key supplied was not a VEK");
        }
        if (!(tkck_pk.getEncodingAlgorithm().isRSA()))
        {
            throw new SecureCryptoException("The key supplied was not an RSA key");
        }
        if (!Component.Complete.equals(tkck_pk.getComponent()))
        {
            throw new SecureCryptoException("The key supplied was not complete");
        }
        if ((pseudoKTU == null) || (pseudoKTU[0] != (byte) 0x55))
        {
            throw new SecureCryptoException("First byte of pseudo KTU must be 0x55");
        }

        if ((cardCertificate != null) && (cardCertificate.length > 16))
        {
            cardKeyLength = ((cardCertificate[15] & 0xff) << 8) ^ (cardCertificate[16] & 0xff);
        }
        else
        {
            throw new SecureCryptoException("Invalid card certificate supplied");
        }
        if (pseudoKTU.length > cardKeyLength)
        {
            throw new SecureCryptoException("Pseudo KTU must not be longer than card key modulus");
        }

        return doCreateRealMultosKTU(pseudoKTU,
                fragSetArray,
                tkck_pk,
                cardCertificate,
                multosVerifyAlgorithm,
                hashModulus);
    }

    private void checkCipherConditions(byte[] image, EncDec encDec, Key key) throws SecureCryptoException
    {
        if ((image == null) || (image.length == 0))
        {
            throw new SecureCryptoException("An image must be supplied");
        }
        if (!Component.Complete.equals(key.getComponent()))
        {
            throw new SecureCryptoException("Key must be complete");
        }

        final Usage keyUsage = key.getEncodingUsage();

        if (EncDec.encrypt.equals(encDec) && !Usage.ECK.equals(keyUsage) && !Usage.BDK.equals(keyUsage))
        {
            throw new SecureCryptoException("To encrypt the key must be either ECK or BDK");
        }

        if (EncDec.decrypt.equals(encDec) && !Usage.DCK.equals(keyUsage) && !Usage.BDK.equals(keyUsage))
        {
            throw new SecureCryptoException("To decrypt the key must be either DCK or BDK");
        }
    }

    private void checkBuildEncipherCondition(byte[] image, Key key, FragmentSetArray fragSetArray) throws SecureCryptoException
    {
        if (key.getEncodingAlgorithm().isRSA())
        {
            if ((image != null) && (image.length != 0) && (key != null) && ((image.length * 8) > key.getKeySize()))
            {
                throw new SecureCryptoException("Image length must not be larger than the key modulus");
            }
        }
        else
        {
            if ((image == null) || (java.lang.Math.IEEEremainder(image.length, 8) != 0))
            {
                throw new SecureCryptoException("Must be a multiple 8 bytes long");
            }
        }

        if (fragSetArray == null)
        {
            throw new SecureCryptoException("Invalid fragment set must be supplied");
        }

        if (!Usage.ECK.equals(key.getEncodingUsage()))
        {
            throw new SecureCryptoException("Invalid level 0 usage, must be ECK");
        }

        if (!Component.Complete.equals(key.getComponent()))
        {
            throw new SecureCryptoException("Key must be complete");
        }
    }

    /**
     * This function is complementary to createRealMutlosKtu - see section 5.1 Secure Crypto Interface (SCI)
     * logical specification for a description of this function and an explanation of real and pseudo KTUs.
     * Rather than create the pseudo KTU from a plaintext image and inserting confidential data the
     * translateMultosKtu function assumes that the pseudo KTU has already been constructed and is held
     * enciphered under a double DES key - ktu'EncipheringKey. The function deciphers the pseudo KTU from
     * under this key and re-enciphers it under a MULTOS card key's public key, presented in the form of a
     * certificate - cardKeyCertificate. As in the case of createRealMultosKtu the function uses the
     * supplied MULTOS tkck_pk (Transport Key Certification Key - Public Key) and, optionally, hashModulus
     * (MULTOS hash modulus), to verify the card key certificate and extract the public key.
     * Note that to extract the modulus of the card's public key from the certificate requires only the
     * tkck_pk. If a hash modulus is supplied, and the security module being used can support it, the
     * function verifies the correctness of the certificate verification process, thereby ensuring that the
     * extracted public key is correct. For those security modules which cannot support this function, the
     * verification part is not performed.
     * The process completes the KTU by copying in the card specific details from the card key certificate,
     * padding it if necessary to the card key's key length, and enciphering the final KTU using the
     * verified card public key.
     *
     * @param pseudoKTU               Pseudo KTU (KTU')
     * @param pseudoKTUEncipheringKey Key enciphered KTU is enciphered with.
     * @param tkck_pk                 Transport Key Certificatin Key.
     * @param cardKeyCertificate      Card certificate.
     * @param multosVerifyAlgorithm   Mulos verification algorithm.
     * @param hashModulus             Multos hash modulus.
     * @return KTU enciphered using the card's public key
     * @throws SecureCryptoException       Thrown if cryptographic module fails to perform operation.
     * @throws SecureCryptoModuleException Thrown if the parameters specified cannot be executed
     *                                     by the module.
     */
    public byte[] translateMultosKtu(byte[] pseudoKTU,
                                     ProtectedKey pseudoKTUEncipheringKey,
                                     AuthenticKey tkck_pk,
                                     byte[] cardKeyCertificate,
                                     MultosVerifyAlgorithm multosVerifyAlgorithm,
                                     AuthenticKey hashModulus) throws SecureCryptoException
    {
        int cardKeyLength;

        if (!Usage.ECK.equals(pseudoKTUEncipheringKey.getEncodingUsage()))
        {
            throw new SecureCryptoException("The ktu' must be an ECK");
        }
        if (!(pseudoKTUEncipheringKey.getEncodingAlgorithm().isDES()))
        {
            throw new SecureCryptoException("The ktu' must be a DES key");
        }
        if (!Component.Complete.equals(pseudoKTUEncipheringKey.getComponent()))
        {
            throw new SecureCryptoException("The ktu' must be complete");
        }
        if ((cardKeyCertificate != null) && (cardKeyCertificate.length > 16))
        {
            cardKeyLength = ((cardKeyCertificate[15] & 0xff) << 8) ^ (cardKeyCertificate[16] & 0xff);
        }
        else
        {
            throw new SecureCryptoException("Invalid card certificate supplied");
        }
        if (pseudoKTU.length > cardKeyLength)
        {
            throw new SecureCryptoException("Pseudo KTU must not be longer than card key modulus");
        }

        if (!Usage.VEK.equals(tkck_pk.getEncodingUsage()))
        {
            throw new SecureCryptoException("The TKCK_PK supplied was not a VEK");
        }
        if (!(tkck_pk.getEncodingAlgorithm().isRSA()))
        {
            throw new SecureCryptoException("The TKCK_PK supplied was not an RSA key");
        }
        if (!Component.Complete.equals(tkck_pk.getComponent()))
        {
            throw new SecureCryptoException("The TKCK_PK supplied was not complete");
        }

        return doTranslateMultosKtu(pseudoKTU,
                pseudoKTUEncipheringKey,
                tkck_pk,
                cardKeyCertificate,
                multosVerifyAlgorithm,
                hashModulus);
    }

    protected abstract byte[] doCipherImage(byte[] image, EncDec encDec, ProtectedKey key, CipherMode mode, byte[] iv) throws SecureCryptoException;

    protected abstract byte[] doCipherImage(byte[] image, EncDec encDec, Key key) throws SecureCryptoException;

    protected abstract byte[] doCalculateKeyCheckValue(ProtectedKey pKey) throws SecureCryptoException;

    protected abstract byte[] doBuildHash(byte[] image, FragmentSetArray fragSetArray, HashParams hashParams) throws SecureCryptoException;

    protected abstract byte[] doCreateRealMultosKTU(byte[] image, FragmentSetArray fragSetArray, AuthenticKey tkck_pk, byte[] cardCertificate, MultosVerifyAlgorithm multosVerifyAlgorithm, AuthenticKey hashModulus) throws SecureCryptoException;

    protected abstract boolean doVerifyMessageSignature(byte[] message, HashParams hashParams, PadMode padMode, AuthenticKey verificationKey, byte[] signature) throws SecureCryptoException;

    public abstract byte[] buildSecureImage(byte[] image, ProtectedKey key, CipherMode mode, byte[] iv, FragmentSetArray fragSet) throws SecureCryptoException;

    public abstract byte[] buildSecureImage(byte[] image, AuthenticKey key, FragmentSetArray fragSet) throws SecureCryptoException;

    public abstract KeyAndCheckValue formSymmetricKeyFromComponents(String[] components, RefinedKeyUsage rku) throws SecureCryptoException;

    protected abstract ProtectedKey doGenerateDerivedKey(byte[] image, ProtectedKey key, CipherMode mode, byte[] iv) throws SecureCryptoException;

    protected abstract ProtectedKey doImportEncipheredKey(byte[] data, ProtectedKey key) throws SecureCryptoException;

    public abstract String getModuleName();

    protected abstract byte[] doExportEncipheredKey(ProtectedKey keyEncipheringKey, ProtectedKey keyForEncipherment) throws SecureCryptoException;

    protected abstract byte[] doTranslateMultosKtu(byte[] pseudoKTU,
                                                   ProtectedKey pseudoKTUEncipheringKey,
                                                   AuthenticKey tkck_pk,
                                                   byte[] cardKeyCertificate,
                                                   MultosVerifyAlgorithm multosVerifyAlgorithm,
                                                   AuthenticKey hashModulus) throws SecureCryptoException;

    /**
     * Tests if this SecureCrypto (i.e. HSM) is able to do a reEncipherData command
     *
     * Only limited testing is included, and if in doubt, true is returned.
     * False is only returned if this HSM definitely cannot execute the command, but another HSM
     * type (e.g. the Thales 8000) may be able to execute the command.
     *
     * @param keyFrom The decryption key that will be used in the reEncipherData() command
     *
     * @return boolean if the command should be executable, false otherwise
     * @throws SecureCryptoException Bad data inside keyFrom
     */
    public boolean isThisHsmCanReEncipherData(ProtectedKey keyFrom)  throws SecureCryptoException
    {
        //Default implementation
        return true;
    }

    public abstract byte[] reEncipherData(ProtectedKey keyFrom, ProtectedKey keyTo,
                                          CipherMode modeFrom, CipherMode modeTo,
                                          byte[] ivFrom, byte[] ivTo,
                                          byte[] encipheredData) throws SecureCryptoException;

    /**
     * Calculates a Visa PVV
     *
     * @param zpk Key under which the supplied pinBlock is encrypted
     * @param pvk The key to use to produce the PVV
     * @param pinBlock The PIN block containing the PIN which needs a PVV calculating for
     * @param pinBlockFormat The PIN Block format of the pinBlock
     * @param accountNumber12Digits The last 12 digits of the PAN, excluding the final check digit
     * @param pvki PKVI value to use in the PVV calculation
     * @return 2 bytes, which is the BCD representation of the 4-digit PVV
     * @throws SecureCryptoException An error occurred producing the PVV
     *
     * @deprecated Use generateVisaPVV instead
     */
    public byte[] generatePVV(ProtectedKey zpk, ProtectedKey pvk,
                                       byte[] pinBlock, PINBlockFormat pinBlockFormat,
                                       String accountNumber12Digits, int pvki) throws SecureCryptoException
    {
        //Build up a "fake" PAN which is the 12 digits of the PAN followed by a made-up Luhn check
        //digit.  This can then be used as a full pan in a call to generateVisaPVV
        String accNumWithFakeLuhn;
        if (accountNumber12Digits == null)
        {
            //13 zeros
            accNumWithFakeLuhn = "0000000000000";
        }
        else if (accountNumber12Digits.length() == 12)
        {
            accNumWithFakeLuhn = accountNumber12Digits + "0";
        }
        else
        {
            String errMsg = "Account number supplied is not 12 digits in length: " + accountNumber12Digits;
            log.warn(errMsg);
            throw new SecureCryptoException(errMsg);
        }

        return generateVisaPVV(zpk, pvk, pinBlock, pinBlockFormat, accNumWithFakeLuhn, pvki);
    }

    /**
     * Calculates a Visa PVV
     *
     * @param zpk Key under which the supplied pinBlock is encrypted
     * @param pvk The key to use to produce the PVV
     * @param pinBlock The PIN block containing the PIN which needs a PVV calculating for
     * @param pinBlockFormat The PIN Block format of the pinBlock
     * @param pan The full PAN
     * @param pvki PKVI value to use in the PVV calculation
     * @return 2 bytes, which is the BCD representation of the 4-digit PVV
     * @throws SecureCryptoException An error occurred producing the PVV
     */
    public abstract byte[] generateVisaPVV(ProtectedKey zpk, ProtectedKey pvk,
                                       byte[] pinBlock, PINBlockFormat pinBlockFormat,
                                       String pan, int pvki) throws SecureCryptoException;

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
    public abstract String generateCVV(CvvType cvvType, ProtectedKey cvvKey,
                                       String pan, String expiryDateMMYY,
                                       String serviceCode) throws SecureCryptoException;

    public abstract byte[] translatePINFormat(ProtectedKey ZPK, byte[] inputPinBlock, PINBlockFormat inputPinBlockFormat,
                                              String accountNumber, PINBlockFormat outputPinBlockFormat, OutputEncMode outputEncMode) throws SecureCryptoException;

    /**
     * Translates a PIN from encryption under one key to another, and from one format into
     * another.
     * @param keyFrom The source key
     * @param keyTo The destination key
     * @param inputPinBlock The Source PIN Block
     * @param inputPinBlockFormat The Source PIN Block Format
     * @param accountNumber Account Number
     * @param outputPinBlockFormat The Destination PIN Block Format
     * @return The PIN Block re-encrypted into the specified format and encrypted under 'keyTo'.
     * @throws SecureCryptoException An error occurred attempting to translate the PIN
     */
    public abstract byte[] translatePin(ProtectedKey keyFrom, ProtectedKey keyTo,
                                        byte[] inputPinBlock, PINBlockFormat inputPinBlockFormat, String accountNumber,
                                        PINBlockFormat outputPinBlockFormat) throws SecureCryptoException;

    public abstract void close();

    @Override
    protected void finalize() throws Throwable
    {
        close();
        super.finalize();
    }
    
    /**
     * This function derives a card-specific DES key from a master key, a PAN and a PAN sequence number. 
     * The derived key value is returned encrypted under a KEK.
     * masterKey encrypts pan and panSequenceNumber according to [EMV4.2Book2] to produce a card specific key. 
     * If derivationOption=A then the method specified in [EMV4.2Book2, A1.4.1] is used. 
     * If derivationOption=B then the method specified in [EMV4.2Book2, A1.4.2] is used. 
     * The card specific key is used to encrypt a block of binary zeros. 
     * The first 3 bytes of the result is returned as kcv. 
     * The card specific key is then encrypted using kek to produce encryptedCardKey.
     * @param masterKey
     * @param derivationOption
     * @param pan
     * @param panSequenceNumber
     * @param kek
     * @param mode
     * @param iv
     * @return
     * @throws SecureCryptoException
     */
    public abstract KeyKcv deriveEmvCardKey(
    	ProtectedKey masterKey,
    	DerivationOption derivationOption,
    	String pan,
    	byte[] panSequenceNumber,
    	ProtectedKey kek,
    	CipherMode mode,
    	byte[] iv 
    ) throws SecureCryptoException;

    /**
     * This function signs provided static authentication data with an RSA private key,
     * using the supplied dataAuthenticationCode. 
     * The function constructs an SDA signature on the data authentication code and 
     * staticAuthenticationData according to [EMV4.2Book2, 5.4].
     * @param dataAuthenticationCode
     * @param staticAuthenticationData
     * @param hashAlgorithm
     * @param signingKey
     * @return the signature
     * @throws SecureCryptoException
     */
    public abstract byte[] generateSdaSignature(
    	byte[] dataAuthenticationCode,
    	byte[] staticAuthenticationData,
    	HashAlgorithm hashAlgorithm,
    	ProtectedKey signingKey
    ) throws SecureCryptoException;

    /**
     * This function signs provided static authentication data with an RSA private key.
     * It constructs a data authentication code (DAC) from mkDAC, pan and panSequenceNumber. 
     * The function constructs an SDA signature on the data authentication code and 
     * staticAuthenticationData according to [EMV4.2Book2, 5.4].
     * @param mkDAC 
     * @param pan
     * @param panSequenceNumber
     * @param staticAuthenticationData
     * @param hashAlgorithm
     * @param signingKey
     * @return the signature
     * @throws SecureCryptoException
     */
    public abstract byte[] generateSdaSignature(
    	ProtectedKey mkDAC,
    	String pan,
    	byte[] panSequenceNumber,
    	byte[] staticAuthenticationData,
    	HashAlgorithm hashAlgorithm,
    	ProtectedKey signingKey
    ) throws SecureCryptoException;


    /**
     * Generate ICC Key and Generate ICC Certificate.
     * This function produces the asymmetric DDA data for EMV cards.
     * It generates an RSA key pair, which can be retrieved encrypted under kek.
     * Further more a ICC certificate and public key remainder is produced.
     *
     * @param rsaKeyType
     * @param keyLength
     * @param publicExponent
     * @param kek
     * @param primeStrength
     * @param mode
     * @param iv
     * @param emvCertificateType
     * @param cardScheme
     * @param hashAlgorithm
     * @param signatureAlgorithm
     * @param pan
     * @param certificateExpirationDate A 2-byte byte array containing the BCD expiry date in the form MMDD
     * @param certificateSerialNumber A 3-byte byte array
     * @param staticAuthenticationData
     * @param issuerPrivateKey
     * @return
     * @throws SecureCryptoException
     */
    public abstract EmvDdaData generateEmvDdaData(
    	RsaKeyType rsaKeyType,
    	int keyLength,
        byte[] publicExponent,
    	ProtectedKey kek,
        PrimeStrength primeStrength,
    	PadMode padMode,
    	CipherMode mode,
    	byte[] iv,
    	EmvCertificateType emvCertificateType,
    	CardScheme cardScheme,
    	HashAlgorithm hashAlgorithm,
    	SignatureAlgorithm signatureAlgorithm,
    	String pan,
    	byte[] certificateExpirationDate,
    	byte[] certificateSerialNumber,
    	byte[] staticAuthenticationData,
    	ProtectedKey issuerPrivateKey) throws SecureCryptoException;


    /**
     * Wrap existing ICC Key and Generate ICC Certificate.
     * This function produces the asymmetric DDA data for EMV cards.
     * It generates an RSA key pair, which can be retrieved encrypted under kek.
     * Further more a ICC certificate and public key remainder is produced.
     *
     * @param rsaKeyType
     * @param iccPrivateKey
     * @param iccPublicKey
     * @param kek
     * @param mode
     * @param iv
     * @param emvCertificateType
     * @param cardScheme
     * @param hashAlgorithm
     * @param signatureAlgorithm
     * @param pan
     * @param certificateExpirationDate A 2-byte byte array containing the BCD expiry date in the form MMDD
     * @param certificateSerialNumber A 3-byte byte array
     * @param staticAuthenticationData
     * @param issuerPrivateKey
     * @return
     * @throws SecureCryptoException
     */
    public abstract EmvDdaData generateEmvDdaData(
    	RsaKeyType rsaKeyType,
        ProtectedKey iccPrivateKey,
        AuthenticKey iccPublicKey,
        ProtectedKey kek,
    	PadMode padMode,
    	CipherMode mode,
    	byte[] iv,
    	EmvCertificateType emvCertificateType,
    	CardScheme cardScheme,
    	HashAlgorithm hashAlgorithm,
    	SignatureAlgorithm signatureAlgorithm,
    	String pan,
    	byte[] certificateExpirationDate,
    	byte[] certificateSerialNumber,
    	byte[] staticAuthenticationData,
    	ProtectedKey issuerPrivateKey) throws SecureCryptoException;

    /**
     * This function generates an RSA key pair and a certificate request
     * (a so-called issuer public key [EMV4.2Book2, 6.3])
     *  
     * This function is expected to be used by GKMS as well as Affina Enterprise.
     *
     * @param serviceIdentifierOrCertificateSerialNumber Varies depending on card scheme:
     *     For Visa and Amex: Service ID
     *     For MasterCard   : Certificate Serial Number
     * @param trackingNumberOrIssuerPublicKeyIndex Varies depending on card scheme:
     *     For Visa and Amex: Tracking Number
     *     For MasterCard   : Issuer Public Key Index
     */
    public abstract IssuerCertRequest generateIssuerCertRequest(int modulusLength,
            byte[] publicExponent,
            CardScheme cardScheme,
            HashAlgorithm hashAlgorithm,
            SignatureAlgorithm signatureAlgorithm,
            String bin,
            byte[] certificateExpirationDate,
            byte[] serviceIdentifierOrCertificateSerialNumber,
            byte[] trackingNumberOrIssuerPublicKeyIndex,
            PrimeStrength primeStrength) throws SecureCryptoException;


    //Special command for translating RSA private keys from DataCard firmware Thales HSM to PS9000 Thales HSM
    public abstract byte[] translateRSAPrivateKey(byte[] privateKeyBytes) throws SecureCryptoException;

    //Special command for translating DES keys from DataCard firmware Thales HSM to PS9000 Thales HSM
    public abstract byte[] translateDESKey(ProtectedKey desKey, RefinedKeyUsage rku) throws SecureCryptoException;

    public abstract Key migrateKeyFromOldToNewMasterKey(Key key) throws SecureCryptoException;

    //Returns a "meaningful" name for the key type, e.g. ZPK, PVK,...
    //Or returns null if there is no meaningful name
    public abstract String getKeyDescription(Key key);

    public abstract DerivedGPKeys deriveGlobalPlatformKeys(ProtectedKey masterKey, byte[] derivationData,
                                                           ProtectedKey kek) throws SecureCryptoException;

    public abstract byte[] mac(byte[] image, MacParams macParams) throws SecureCryptoException;


    protected boolean isUseStrongPrimes(PrimeStrength primeStrength) throws SecureCryptoException
    {
        boolean useStrongPrimes;

        if (PrimeStrength.DEFAULT.equals(primeStrength))
        {
            useStrongPrimes = useStrongPrimesByDefault;
        }
        else if (PrimeStrength.STANDARD.equals(primeStrength))
        {
            useStrongPrimes = false;
        }
        else if (PrimeStrength.STRONG.equals(primeStrength))
        {
            useStrongPrimes = true;
        }
        else
        {
            String errMsg = "Invalid prime strength indicator: primeStrength=" + primeStrength;
            log.warn(errMsg);
            throw new SecureCryptoException(errMsg);
        }

        return useStrongPrimes;
    }

    /**
     * Checks that the receivedObject is not null and that its class is either expectedClass
     * or is a subclass of expectedClass.  Throws an exception if not.
     *
     * @param expectedClass The class that receivedObject should be
     * @param receivedObject The object whose class it to be tested
     * @throws SecureCryptoException receivedObject was either null or not an instance of class
     *     expectedClass
     */
    protected static void checkResponseClass(Class expectedClass, Object receivedObject) throws SecureCryptoException
    {
        if (receivedObject == null)
        {
            throw new SecureCryptoException("Unexpected response received.  Expected an object of class "
                    + expectedClass + " but instead received null", null, true);
        }

        if (receivedObject instanceof ISCIExceptionResponse)
        {
            ISCIExceptionResponse expRespObj = (ISCIExceptionResponse)receivedObject;
            Throwable x = expRespObj.getSciException();
            throw new SecureCryptoException("Got an exception Response", x);
        }

        if (!receivedObject.getClass().isAssignableFrom(expectedClass))
        {
            throw new SecureCryptoException("Unexpected response received.  Expected an object of class "
                    + expectedClass + " but instead received an object of class: "
                    + receivedObject.getClass(), null, true);
        }
    }

    protected static boolean isKeysEqual(Key key1, Key key2) throws SecureCryptoException
    {
        boolean equals;
        equals = isEqual(key1.getEncodingAlgorithm(), key2.getEncodingAlgorithm());
        equals &= isEqual(key1.getEncodingUsage(), key2.getEncodingUsage());
        equals &= isEqual(key1.getComponent(), key2.getComponent());
        equals &= Arrays.equals(key1.getKeyValue(), key2.getKeyValue());
        equals &= isEqual(key1.getKeyType(), key2.getKeyType());
        equals &= key1.getKeySize() == key2.getKeySize();
        equals &= isEqual(key1.getSMType(), key2.getSMType());
        equals &= Arrays.equals(key1.getMKIdentifier(), key2.getMKIdentifier());
        equals &= Arrays.equals(key1.getSMExtension(), key2.getSMExtension());

        //Check key usage
        KeyUsage[] ku1 = key1.getKeyUsage();
        KeyUsage[] ku2 = key2.getKeyUsage();
        if (!(ku1 == null && ku2 == null))
        {
            if (ku1 == null || ku2 == null)
            {
                equals = false;
            }
            else
            {
                if (ku1.length == ku2.length)
                {
                    for (int i = 0; i < ku1.length; i++)
                    {
                        equals &= isEqual(ku1[i].getAlgorithm(), ku2[i].getAlgorithm());
                        equals &= isEqual(ku1[i].getUsage(), ku2[i].getUsage());
                    }
                }
                else
                {
                    equals = false;
                }
            }
        }

        return equals;
    }


    private static boolean isEqual(Object o1, Object o2)
    {
        return (o1 == o2) || ((o1 != null) && (o2 != null) && o1.equals(o2));
    }
}
