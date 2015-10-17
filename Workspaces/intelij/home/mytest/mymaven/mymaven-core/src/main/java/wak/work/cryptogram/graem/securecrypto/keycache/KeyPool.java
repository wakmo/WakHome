package wak.work.cryptogram.graem.securecrypto.keycache;

import wak.work.cryptogram.graem.securecrypto.*;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import wak.work.cryptogram.helper.ByteArrayUtilities;

/**
 * Temporarily holds keys generated by an HSM.
 * All the keys in a KeyPool have the same public key exponent and the same modulus length
 *
 * User: growles
 * Date: 08-Feb-2012
 * Time: 09:42:19
 */
public class KeyPool
{
    private static final Logger log = Logger.getLogger(KeyPool.class);
    

    private final KeyAttributes keyAttributes;
    private RsaKeyGenerator keyGenerator;


    //The store of produced keys
    //This member variable is protected by synchronized(this)
    private final KeyPair[] cachedKeys;

    //Current number of keys in the pool
    //This member variable is protected by synchronized(this)
    private int poolSize;

    //The number of key generations currently in progress and being waited for for this pool
    //This member variable is protected by synchronized(this)
    private int generationsInProgress;

    //Did an exception get thrown the last time a key was attempted to be generated for this pool?
    //(Used to prevent repeated error logging)
    //This member variable is protected by synchronized(this)
    private boolean errorOccurredLastTime;


    /**
     *
     * @param keyAttributes Details required to create the key, i.e. Modulus size, public exponent and CRT/MODEXP
     * @param maxPoolSize Maximum number of keys that can be stored in this pool
     * @param keyGenerator Used for generating keys to put in the pool
     */
    public KeyPool(KeyAttributes keyAttributes, int maxPoolSize, RsaKeyGenerator keyGenerator)
    {
        this.keyAttributes = keyAttributes;
        this.keyGenerator = keyGenerator;

        cachedKeys = new KeyPair[maxPoolSize];
        poolSize = 0;

        generationsInProgress = 0;
        errorOccurredLastTime = false;
    }

    //Returns null if pool is empty
    public KeyPair getCachedKey()
    {
        KeyPair keyPair = null;

        //Try to grab a cached key from the pool
        synchronized (this)
        {
            if (poolSize > 0)
            {
                //Grab a cached key from the pool and remove it from the pool
                keyPair = cachedKeys[poolSize - 1];
                poolSize--;
            }
        }

        return keyPair;
    }

    void report(KeyCacheReport report) throws SecureCryptoException
    {
        int keySize = keyAttributes.getKeySize();
        Algorithm algorithm = keyAttributes.getAlgorithm();
        String usageStr = keyAttributes.getUsage().toString();
        String smType = keyAttributes.getSmType();
        String mkId = keyAttributes.getMkId();
        String additionalData = keyAttributes.getAdditionalData();

        report.addPool(poolSize, cachedKeys.length, keySize, algorithm, smType, mkId, additionalData);
    }

    /**
     * Returns a percentage of how full this pool currently is compared to its maximum size.
     * The current pool size is considered to include any keys that are currently in the process
     * of being generated in order to be added to this pool.
     *
     * @return Percentage expressing how "full" this pool currently is (between 0.0 and 1.0)
     */
    double getPercentageFull()
    {
        double percentageFull;

        synchronized (this)
        {
            double maxPossibleCurrentPoolSize = poolSize + generationsInProgress;
            double maxPoolSize = cachedKeys.length;

            percentageFull = maxPossibleCurrentPoolSize / maxPoolSize;
        }

        return percentageFull;
    }

    //Returns true if key added, false if pool was full or an error occurred
    boolean addNewKeyToPoolIfNeeded()
    {
        synchronized (this)
        {
            int maxPossibleCurrentPoolSize = poolSize + generationsInProgress;
            if (maxPossibleCurrentPoolSize >= cachedKeys.length)
            {
                //Pool is full
                return false;
            }

            generationsInProgress++;
        }


        boolean success = false;
        try
        {
            KeyPair keyPair = generateKey();

            //Key generated successfully
            synchronized (this)
            {
                cachedKeys[poolSize] = keyPair;
                poolSize++;
                generationsInProgress--;
                errorOccurredLastTime = false;
            }

            success = true;
        }
        catch (Exception x)
        {
            //Key generation failed
            boolean logError;
            synchronized (this)
            {
                generationsInProgress--;
                logError = !errorOccurredLastTime;
                errorOccurredLastTime = true;
            }

            if (logError)
            {
                log.warn("Error generating a key for the key cache with attributes: " + keyAttributes, x);
            }
        }

        return success;
    }


    /**
     * Use the HSM to generate a key pair
     *
     * @return KeyPair - either pulled from the cache or generated now
     *
     * @throws SecureCryptoException An error occurred generating a key
     */
    private KeyPair generateKey() throws SecureCryptoException
    {
        KeyUsage keyUsage = keyAttributes.getKeyUsage();
        int keySize = keyAttributes.getKeySize();
        byte[] publicExponent = keyAttributes.getPublicExp();
        String smType = keyAttributes.getSmType();
        String mkIdHex = keyAttributes.getMkId();
        String additionalData = keyAttributes.getAdditionalData();

        byte[] mkId;
        try
        {
            mkId = (mkIdHex == null)? null : ByteArrayUtilities.byteify_nospaces(mkIdHex);
        }
        catch (NumberFormatException x)
        {
            String errMsg = "MK ID is not hexadecimal in key caching configuration: " + mkIdHex;
            log.warn(errMsg);
            throw new SecureCryptoException(errMsg, x);
        }

        RsaKeyGenOptions keyGenOptions = new RsaKeyGenOptions(keyUsage, keySize, publicExponent);
        keyGenOptions.setSmType(smType);
        keyGenOptions.setSmTypeAndMkIdentifier(smType, mkId);
        keyGenOptions.setForKeyCache(true);

        Map<String, String> additionalPropertiesMap = new HashMap<String, String>();
        if (additionalData != null && additionalData.length() != 0)
        {
            String[] props = additionalData.split(",");
            for (String prop : props)
            {
                prop = prop.trim();
                int equalsPos = prop.indexOf('=');
                if (equalsPos <= 0)
                {
                    String errMsg = "Invalid additional data in key caching configuration: " + additionalData;
                    log.warn(errMsg);
                    throw new SecureCryptoException(errMsg);
                }

                String propName = prop.substring(0, equalsPos);
                propName = propName.toUpperCase();

                String propValue = prop.substring(equalsPos + 1);

                if (additionalPropertiesMap.containsKey(propName))
                {
                    String errMsg = "Invalid additional data in key caching configuration, property " +
                        propName + " exists more than once: " + additionalData;
                    log.warn(errMsg);
                    throw new SecureCryptoException(errMsg);
                }

                additionalPropertiesMap.put(propName, propValue);
            }
        }

        //Special handling of 'type=' setting for PS9000
        if (smType.equalsIgnoreCase("PS9000") && additionalPropertiesMap.containsKey("TYPE"))
        {
            String typeStr = additionalPropertiesMap.get("TYPE");
            if (typeStr.length() != 1 || typeStr.charAt(0) < '0' || typeStr.charAt(0) > '3')
            {
                String errMsg = "Invalid TYPE property in additional data in PS9000 key caching configuration. Expected (0-3) but got TYPE=" + typeStr;
                log.warn(errMsg);
                throw new SecureCryptoException(errMsg);
            }

            PKIKeyType pkiKeyType = PKIKeyType.getFrom(typeStr.charAt(0));
            keyGenOptions.setPkiKeyType(pkiKeyType);
        }

        return keyGenerator.generateKey(keyGenOptions);
    }
}