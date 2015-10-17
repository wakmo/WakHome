package wak.work.cryptogram.graem.securecrypto.keycache;

import wak.work.cryptogram.graem.securecrypto.*;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import wak.work.cryptogram.helper.ByteArrayUtilities;

/**
 * Temporarily holds keys generated by an HSM.
 * Contains a set of KeyPool's.
 *
 * User: growles
 * Date: 08-Feb-2012
 * Time: 09:42:12
 */
public class KeyCache
{
    private static final Logger log = Logger.getLogger(KeyCache.class);

    //The NCipher does not use MkIdentifiers, so for key caching purposes this is set in the
    //keyCache itself as "1", and when being requested by NCipherSecureCrypto it is looked up
    //using MkId = "1".
    public static final String NCIPHER_MKID_FOR_KEYCACHING = "1";


    private final Pattern POOL_CONFIG_PATTERN = Pattern.compile("\\s*([0-9A-Fa-f]{1,})\\s*,\\s*([0-9]{1,})\\s*,\\s*(CRT|MODEXP|ALL)\\s*,\\s*([0-9]{1,})\\s*(,\\s*([A-Za-z0-9\\.]+)?(,\\s*([A-Fa-f0-9]+)?(\\s*,(.*))?)?)?\\s*");
    private static final int PUBLIC_EXPONENT_GROUP = 1;
    private static final int KEY_SIZE_GROUP = 2;
    private static final int KEY_TYPE_GROUP = 3;
    private static final int MAX_POOL_SIZE_GROUP = 4;
    private static final int SM_TYPE_GROUP = 6;
    private static final int MK_ID_GROUP = 8;
    private static final int ADDITIONAL_DATA_GROUP = 10;

    private final RsaKeyGenerator keyGenerator;

    private final Map<String, KeyPool> keyPoolMap;

    private final KeyCachingThread[] keyCachingThreads;

    private final String defaultSmType;
    private final String defaultMkId;


    private final Object waitingObj = new Object();


    /**
     * Creates a new KeyCache with all the KeyPools identified by poolConfigStr.
     *
     * poolConfigStr is a String specifying the keyPools as follows:
     *
     * poolConfigStr = "pubExp1,keySize1,keyType1,maxPoolSize1;pubExp2,keySize2,keyType2,maxPoolSize2;..."
     * where
     *   pubExp is the public exponent, as a hexadecimal big integer
     *   keySize is the key size (in bits) expressed as an integer
     *   keyType is either "CRT" or "MODEXP"
     *   maxPoolSize is the maximum number of keys this pool should store, expressed as an integer
     *
     * poolConfigStr can be passed as empty, in which case no key pools will be created and no key
     * caching will take place.
     *
     * e.g.:
     * poolConfigStr = "3,1024,CRT,1000;10001,1152,MODEXP,500" 
     *
     * @param poolConfigStr Key pool data, as specified above
     * @param numKeyCachingThreads The number of simultaneous HSM calls that should be made for
     *     key caching.  Typically this should equal the number of HSMs or perhaps be one less than
     *     the number of HSMs, thus keeping one free for other work
     * @param keyGenerator Used for generating keys that are being put in the cache
     *
     * @throws SecureCryptoException Error in key caching configuration
     */
    public KeyCache(String poolConfigStr, int numKeyCachingThreads, RsaKeyGenerator keyGenerator,
                    String defaultSmType, String defaultMkId) throws SecureCryptoException
    {
        this.keyGenerator = keyGenerator;
        this.defaultSmType = defaultSmType;
        this.defaultMkId = defaultMkId;

        keyCachingThreads = new KeyCachingThread[numKeyCachingThreads];
        Arrays.fill(keyCachingThreads, null);

        String[] poolConfigs = poolConfigStr.split(";");
        keyPoolMap = new HashMap<String, KeyPool>();

        for (String poolConfig : poolConfigs)
        {
            if (log.isDebugEnabled()) log.debug("Attempting to add key cache: " + poolConfig);

            poolConfig = poolConfig.trim();
            if (poolConfig.length() > 0)
            {
                Matcher m = POOL_CONFIG_PATTERN.matcher(poolConfig);
                if (!m.matches())
                {
                    throw new SecureCryptoException("Invalid key caching configuration: " + poolConfig);
                }

                String publicExpStr = m.group(PUBLIC_EXPONENT_GROUP);
                String keySizeStr = m.group(KEY_SIZE_GROUP);
                String algorithmStr = m.group(KEY_TYPE_GROUP);
                String maxPoolSizeStr = m.group(MAX_POOL_SIZE_GROUP);

                //These can be null as they are optional
                String smType = m.group(SM_TYPE_GROUP);
                String mkId = m.group(MK_ID_GROUP);
                String additionalData = m.group(ADDITIONAL_DATA_GROUP);

                if (smType == null)
                {
                    smType = defaultSmType;
                }

                //Special case for PS9000 - All RSA keys are RSA_ALL
                if (smType.equalsIgnoreCase("PS9000"))
                {
                    if (!algorithmStr.equalsIgnoreCase("ALL"))
                    {
                        log.warn("In key caching configuration a PS9000 cache has been set up with algorithm="
                            + algorithmStr + ".  However all PS9000 RSA keys are of type 'ALL', so 'ALL' is assumed");
                    }
                    algorithmStr = "ALL";
                }

                //Special case for NCipher - we don't current use an MkId, so just use a blank.
                if (smType.equalsIgnoreCase("nCipher.sWorld"))
                {
                    if (mkId != null && mkId.length() > 0)
                    {
                        log.warn("In key caching configuration an nCipher cache has been set up with mkId="
                                + mkId + ".  However the nCipher does not use an MkId so this will be ignored.");
                    }

                    mkId = NCIPHER_MKID_FOR_KEYCACHING;
                }

                if (mkId == null)
                {
                    mkId = defaultMkId;
                }

                if ((publicExpStr.length() & 1) == 1)
                {
                    //publicExpStr has odd length - make it even
                    publicExpStr = "0" + publicExpStr;
                }
                byte[] publicExp = ByteArrayUtilities.byteify_nospaces(publicExpStr);

                int keySize = Integer.parseInt(keySizeStr);
                int maxPoolSize = Integer.parseInt(maxPoolSizeStr);

                Algorithm algorithm;
                if (algorithmStr.equalsIgnoreCase("CRT"))
                {
                    algorithm = Algorithm.RSA_CRT;
                }
                else if (algorithmStr.equalsIgnoreCase("MODEXP"))
                {
                    algorithm = Algorithm.RSA_MODEXP;
                }
                else
                {
                    algorithm = Algorithm.RSA_ALL;
                }

                //At Dick's request, all keys in the cache are of type BDK
                KeyUsage keyUsage = new KeyUsage(algorithm, Usage.BDK);

                KeyAttributes keyAttributes = new KeyAttributes(publicExp, keySize, keyUsage,
                        smType, mkId, additionalData);
                KeyPool pool = new KeyPool(keyAttributes, maxPoolSize, keyGenerator);

                if (keyPoolMap.containsKey(keyAttributes.getShortString()))
                {
                    throw new SecureCryptoException("Duplicate key cache configuration: " + poolConfig);
                }

                keyPoolMap.put(keyAttributes.getShortString(), pool);
            }
        }

    }

    //Returns null if no keys available
    public KeyPair getCachedKey(KeyAttributes keyAttributes) throws SecureCryptoException
    {
        boolean changed = false;

        //If "Generate Key" request contains no HSM type then use the default
        String smType = keyAttributes.getSmType();
        if (smType == null || smType.length() == 0)
        {
            smType = defaultSmType;
            changed = true;
        }

        //If "Generate Key" request contains no MK Identifier then use the default
        String mkId = keyAttributes.getMkId();
        if (mkId == null || mkId.length() == 0)
        {
            mkId = defaultMkId;
            changed = true;
        }

        //At Dick's request, all keys in the cache are of type BDK.  It is ok to return
        //a BDK key even if the requested key is of a different type
        KeyUsage keyUsage = keyAttributes.getKeyUsage();
        if (Usage.BDK.toInt() != keyUsage.getUsage().toInt())
        {
            keyUsage = new KeyUsage(keyUsage.getAlgorithm(), Usage.BDK);
            changed = true;
        }

        if (changed)
        {
            keyAttributes = new KeyAttributes(keyAttributes.getPublicExp(),
                    keyAttributes.getKeySize(),
                    keyUsage,
                    smType,
                    mkId,
                    keyAttributes.getAdditionalData());
        }


        KeyPool keyPool = keyPoolMap.get(keyAttributes.getShortString());

        KeyPair keyPair = null;
        if (keyPool != null)
        {
            //Key caching is available for this type of key, so get a key from the cache now
            keyPair = keyPool.getCachedKey();

            if (keyPair != null)
            {
                notifyKeyUsed();
            }
        }

        return keyPair;
    }

    public void startUp()
    {
        if (keyCachingThreads.length >= 1)
        {
            if (keyCachingThreads[0] == null)
            {
                for (int i = 0; i < keyCachingThreads.length; i++)
                {
                    keyCachingThreads[i] = new KeyCachingThread();
                    keyCachingThreads[i].start();
                }
            }
            else
            {
                log.warn("startUp() already called");
            }
        }
    }

    public void shutDown()
    {
        if (keyCachingThreads.length >= 1)
        {
            if (keyCachingThreads[0] == null)
            {
                log.warn("startUp() not called");
            }
            else
            {
                for (KeyCachingThread t : keyCachingThreads)
                {
                    t.shutDown();
                }
            }
        }
    }

    public KeyCacheReport generateReport() throws SecureCryptoException
    {
        KeyCacheReport report = new KeyCacheReport();
        for (KeyPool pool : keyPoolMap.values())
        {
            pool.report(report);
        }

        return report;
    }


    private void notifyKeyUsed()
    {
        synchronized (waitingObj)
        {
            waitingObj.notifyAll();
        }
    }



                     
    private class KeyCachingThread extends Thread
    {
        private boolean shuttingDown;

        KeyCachingThread()
        {
            shuttingDown = false;
        }

        void shutDown()
        {
            shuttingDown = true;
            interrupt();
        }

        public void run()
        {
            KeyPool[] keyPools = keyPoolMap.values().toArray(new KeyPool[keyPoolMap.size()]);

            //Just wait a few seconds to allow SCI to start up before keyCaching starts.
            //Otherwise there will be errors in the log
            try
            {
                sleep(10000);
            }
            catch (InterruptedException x)
            {
                //Do nothing - the test on shuttingDown will see if we should continue or not
            }


            while (!shuttingDown)
            {
                boolean keyAdded = false;

                //Sort keyPools into ascending order of percent fullness (i.e. most "empty" pool first)
                Arrays.sort(keyPools, new Comparator<KeyPool>()
                {
                    public int compare(KeyPool p1, KeyPool p2)
                    {
                        Double p1PercentageFull = p1.getPercentageFull();
                        Double p2PercentageFull = p2.getPercentageFull();

                        return p1PercentageFull.compareTo(p2PercentageFull);
                    }
                });

                //Now try to add a key to one of the pools, starting from the one which
                //is the most empty (percentage-wise)
                //Really only keyPool[0] needs doing, but we have the loop in case there's an error
                //- in which case we will try keyPool[1], then keyPool[2] and so on...
                for (KeyPool keyPool : keyPools)
                {
                    if (!shuttingDown && !keyAdded)
                    {
                        keyAdded = keyPool.addNewKeyToPoolIfNeeded();
                    }
                }

                if (!shuttingDown && !keyAdded)
                {
                    //Either all pools are full, or no pools could have any cached keys added
                    //because of an error occurring
                    synchronized (waitingObj)
                    {
                        //Wait 10 seconds, or until a cached key is retrieved
                        //(which would mean a new cached key now needs generating)
                        try
                        {
                            waitingObj.wait(10000);
                        }
                        catch (InterruptedException x)
                        {
                            log.debug("Key Caching Thread interrupted");
                        }
                    }
                }
            }
        }
    }
}