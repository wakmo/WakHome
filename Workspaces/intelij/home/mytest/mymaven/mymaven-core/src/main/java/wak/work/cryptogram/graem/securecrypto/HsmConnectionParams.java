package wak.work.cryptogram.graem.securecrypto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: growles
 * Date: 12-Jul-2010
 * Time: 16:12:52
 *
 * Contains the parameters required to describe an HSM connection.
 * Further HSM connection may be required (e.g. for key caching).  In this case the getExtraConnections()
 * method will describe them.
 *
 * These can be parsed from a connection string by this class:
 * (e.g. "RG7000,192.168.0.86,1500" or "NCipher,1")
 */
public class HsmConnectionParams
{
    private static final Logger log = Logger.getLogger(HsmConnectionParams.class);

    //HSM Type strings that can appear on the "cryptoList" system parameter
    public static final String HSM_RG7000 = "RG7000";
    public static final String HSM_RG8000 = "RG8000";
    public static final String HSM_PS9000 = "PS9000";
    public static final String HSM_CM250 = "CM250";
    public static final String HSM_NCIPHER = "nCipher";
    public static final String HSM_DUMMY = "Dummy";
    public static final String HSM_FAKE = "Fake";


    //One of the SecureCryptoFactory HSM_TYPEs
    private final int hsmType;


    //For Thales RG7000, RG8000, PS9000 and CM250
    private String ip;
    private int port;

    //For Thales RG8000, RG8000 and CM250
    //Set to true if this connection is for key caching, false otherwise
    private boolean keyCaching;

    //For Thales PS9000
    private int lmkSet;

    //For Thales RG8000, RG8000 and CM250
    //If this connection is for key caching, then this is a counter to identify the key caching connection
    //If this connection is not for key caching then this field is not used
    private int keyCacherId;

    //For Thales RG8000, RG8000 and CM250
    //RSA Key generation commands will return valid keys, but each time these RSA key generate functions
    //are called requesting the same key length using the same public modulus, the same key
    //will be returned each time.  This is for Affina performance testing only.
    private boolean fakeKeyGen;

    //Special case for key caching: One line in the cryptoParams can signify multiple connections
    //for key caching... This value is set to the number of extra connections required
    private HsmConnectionParams[] extraConnections;


    //For NCipher HSM
    private int moduleNumber;

    //For Dummy HSM
    private int dummyNumber;

    private String sceName;

    //Use TCP/IP OS KeepAlive functionality?
    private boolean useKeepAlive;

    //Number of seconds between each periodic socket disconnect/reconnect, or 0 to disable
    private int socketReconnectPeriod;

    //Number of seconds to wait between heartbeats
    private int heartbeatPeriod;

    //Maximum wait time, in seconds, to wait for a response from an HSM
    private int socketReadTimeout;

    //Time to wait before automatically trying to reconnect, in milliseconds
    private long retryFailedConnectionPeriod;

    //Number of HSMs in use
    private int numOfHsms;

    //A number between 0 and (numOfHsms - 1) identifying this HSM.  The number is not important, except that
    //it should be in this range and unique for each HSM.  Intended use is to stagger socket reconnection
    //times
    private int hsmCount;

    //Should the YU command, which is not available on a Thales RG7000 HSM, be implemented in software
    //for an RG7000 HSM.
    //This was added in as a "hack" so we can continue using the YU command while our CM250 HSM is being repaired.
    private boolean implementRG7000YUInSoftware;

    //The LMK Check Value for the Thales HSMs can be specified here, preventing them from being
    //read by the HSM.  This solves the problem where the Soft Thales HSM does not support the "NO01" command
    private String forcedThalesLmkCheckValue;

    //When generating RSA keys should strong primes be used (as opposed to standard primes)?
    //true=use strong primes, false=use standard primes
    private boolean useStrongPrimes;

    //When SCI starts should this HSM be kept out of the pool ready to be manually added in later?
    private boolean startOutOfPool;


    //The default SMType and MKId to use for key generation if no SMType and/or MkId is present
    //in the KeyGenerate message
    private String defaultKeygenSmType;
    private String defaultKeygenMkId;


    
    private String[] connectionParams;
    private int connectionParam;

    private String connectionParamsStr;


    public HsmConnectionParams(String connectionParamsStr) throws SecureCryptoException
    {
        this.connectionParamsStr = connectionParamsStr;

        connectionParams = connectionParamsStr.split(",");
        connectionParam = 0;

        ip = null;
        port = -1;
        lmkSet = 0;
        keyCaching = false;
        defaultKeygenSmType = null;
        defaultKeygenMkId = null;
        extraConnections = null;
        moduleNumber = -1;
        dummyNumber = -1;
        sceName = null;
        startOutOfPool = false;
        fakeKeyGen = false;

        String hsmTypeStr = parseString("HSM Type");

        if (hsmTypeStr.equalsIgnoreCase(HSM_RG7000))
        {
            hsmType = SecureCryptoFactory.RG7000_HSM_TYPE;
            parseIpAddress();
            parsePort();
            parseOutFlag();
            log.debug("Adding Thales HSM (RG7000 series): " + ip + ":" + port);
        }
        else if (hsmTypeStr.equalsIgnoreCase(HSM_RG8000))
        {
            hsmType = SecureCryptoFactory.RG8000_HSM_TYPE;
            parseIpAddress();
            parsePort();
            parseKeyCaching();
            parseFakeKeyGen();
            parseOutFlag();
            log.debug("Adding Thales HSM (RG8000 series): " + ip + ":" + port);
        }
        else if (hsmTypeStr.equalsIgnoreCase(HSM_PS9000))
        {
            hsmType = SecureCryptoFactory.PS9000_HSM_TYPE;
            parseIpAddress();
            parsePort();
            parseKeyCaching();
            parseLmkSet();
            parseOutFlag();
            log.debug("Adding Thales HSM (" + HSM_PS9000 + " series): " + ip + ":" + port);
        }
        else if (hsmTypeStr.equalsIgnoreCase(HSM_CM250))
        {
            hsmType = SecureCryptoFactory.CM250_HSM_TYPE;
            parseIpAddress();
            parsePort();
            parseKeyCaching();
            parseFakeKeyGen();
            parseOutFlag();
            log.debug("Adding Thales HSM (CM250 series): " + ip + ":" + port);
        }
        else if (hsmTypeStr.equalsIgnoreCase(HSM_NCIPHER))
        {
            hsmType = SecureCryptoFactory.NCIPHER_HSM_TYPE;
            parseModuleNumber();
            parseKeyCaching();
            parseOutFlag();
            log.debug("Adding NCipher HSM: Module " + moduleNumber);
        }
        else if (hsmTypeStr.equalsIgnoreCase(HSM_DUMMY))
        {
            hsmType = SecureCryptoFactory.DUMMY_HSM_TYPE;
            dummyNumber = 1;
            parseNumOfThreads();
            parseOutFlag();

            log.debug("Adding Dummy HSM: " + dummyNumber);
        }
        else if (hsmTypeStr.equalsIgnoreCase(HSM_FAKE))
        {
            hsmType = SecureCryptoFactory.FAKE_HSM_TYPE;
            log.debug("Adding Fake HSM");
        }
        else
        {
            String errMsg = "Unknown HSM Type: '" + hsmTypeStr + "' from connection settings: " + connectionParamsStr;
            log.warn(errMsg);
            throw new SecureCryptoException(errMsg);
        }

        checkNoMoreTokens();

        if (keyCaching && fakeKeyGen)
        {
            throw new SecureCryptoException("An HSM connection cannot be both a key cacher and a fake key generator");
        }

        //Duplicate the 'startOutOfPool' property to all extra connections
        if (extraConnections != null)
        {
            for (HsmConnectionParams cp : extraConnections)
            {
                cp.startOutOfPool = startOutOfPool;
            }
        }


        useKeepAlive = true;
        socketReconnectPeriod = 0;
    }

    public HsmConnectionParams(String ip, int port, int hsmType)
    {
        this.ip = ip;
        this.port = port;
        this.hsmType = hsmType;
        keyCaching = false;
        defaultKeygenSmType = null;
        defaultKeygenMkId = null;
        extraConnections = null;
        useKeepAlive = true;
        socketReconnectPeriod = 0;
        moduleNumber = -1;
        dummyNumber = -1;
        sceName = null;
        implementRG7000YUInSoftware = false;
        useStrongPrimes = true;
        startOutOfPool = false;
        fakeKeyGen = false;
        forcedThalesLmkCheckValue = null;
    }

    //Special constructor used internally when splitting up a single HSMConnectionParams object into
    //multiple HSMConnectionParams objects
    private HsmConnectionParams(int hsmType)
    {
        this.hsmType = hsmType;

        ip = null;
        port = -1;
        keyCaching = false;
        keyCacherId = -1;
        extraConnections = null;
        useKeepAlive = true;
        socketReconnectPeriod = 0;
        moduleNumber = -1;
        dummyNumber = -1;
        sceName = null;
        implementRG7000YUInSoftware = false;
        useStrongPrimes = true;
        startOutOfPool = false;
        fakeKeyGen = false;
        forcedThalesLmkCheckValue = null;
        defaultKeygenSmType = null;
        defaultKeygenMkId = null;
    }

    public String getIp()
    {
        return ip;
    }

    public int getPort()
    {
        return port;
    }

    public int getLmkSet()
    {
        return lmkSet;
    }

    public boolean isKeyCaching()
    {
        return keyCaching;
    }

    public int getModuleNumber()
    {
        return moduleNumber;
    }

    public String getSceName()
    {
        return sceName;
    }

    public int getDummyNumber()
    {
        return dummyNumber;
    }

    //Returns one of the SecureFactoryConnection HSM_TYPEs
    public int getHsmType()
    {
        return hsmType;
    }

    public boolean isUseKeepAlive()
    {
        return useKeepAlive;
    }

    public void setUseKeepAlive(boolean useKeepAlive)
    {
        this.useKeepAlive = useKeepAlive;
    }

    public int getSocketReconnectPeriod()
    {
        return socketReconnectPeriod;
    }

    public void setSocketReconnectPeriod(int socketReconnectPeriod)
    {
        this.socketReconnectPeriod = socketReconnectPeriod;
    }

    public void setHsmCount(int hsmCount, int numOfHsms)
    {
        this.hsmCount = hsmCount;
        this.numOfHsms = numOfHsms;
    }

    public int getNumOfHsms()
    {
        return numOfHsms;
    }

    public int getHsmCount()
    {
        return hsmCount;
    }

    public int getHeartbeatPeriod()
    {
        return heartbeatPeriod;
    }

    public void setHeartbeatPeriod(int heartbeatPeriod)
    {
        this.heartbeatPeriod = heartbeatPeriod;
    }

    public int getSocketReadTimeout()
    {
        return socketReadTimeout;
    }

    public void setSocketReadTimeout(int socketReadTimeout)
    {
        this.socketReadTimeout = socketReadTimeout;
    }

    public long getRetryFailedConnectionPeriod()
    {
        return retryFailedConnectionPeriod;
    }

    public void setRetryFailedConnectionPeriod(long retryFailedConnectionPeriod)
    {
        this.retryFailedConnectionPeriod = retryFailedConnectionPeriod;
    }

    public boolean isImplementRG7000YUInSoftware()
    {
        return implementRG7000YUInSoftware;
    }

    public void setImplementRG7000YUInSoftware(boolean implementRG7000YUInSoftware)
    {
        this.implementRG7000YUInSoftware = implementRG7000YUInSoftware;
    }

    public String getForcedThalesLmkCheckValue()
    {
        return forcedThalesLmkCheckValue;
    }

    public void setForcedThalesLmkCheckValue(String forcedThalesLmkCheckValue)
    {
        this.forcedThalesLmkCheckValue = forcedThalesLmkCheckValue;
    }

    public void setUseStrongPrimes(boolean useStrongPrimes)
    {
        this.useStrongPrimes = useStrongPrimes;
    }

    public boolean isUseStrongPrimes()
    {
        return useStrongPrimes;
    }

    public boolean isStartOutOfPool()
    {
        return startOutOfPool;
    }

    public void setStartOutOfPool(boolean startOutOfPool)
    {
        this.startOutOfPool = startOutOfPool;
    }

    public boolean isFakeKeyGen()
    {
        return fakeKeyGen;
    }

    public void setFakeKeyGen(boolean fakeKeyGen)
    {
        this.fakeKeyGen = fakeKeyGen;
    }

    public String getDefaultKeygenSmType()
    {
        return defaultKeygenSmType;
    }

    public void setDefaultKeygenSmType(String defaultKeygenSmType)
    {
        this.defaultKeygenSmType = defaultKeygenSmType;
    }

    public String getDefaultKeygenMkId()
    {
        return defaultKeygenMkId;
    }

    public void setDefaultKeygenMkId(String defaultKeygenMkId)
    {
        this.defaultKeygenMkId = defaultKeygenMkId;
    }

    @Override
    public String toString()
    {
        StringBuilder hsmTypeSb = new StringBuilder();
        switch (hsmType)
        {
            case SecureCryptoFactory.RG7000_HSM_TYPE:
            case SecureCryptoFactory.RG8000_HSM_TYPE:
            case SecureCryptoFactory.PS9000_HSM_TYPE:
            case SecureCryptoFactory.CM250_HSM_TYPE:
                hsmTypeSb.append("[Thales ");
                hsmTypeSb.append(hsmType);
                hsmTypeSb.append(" series: ");
                hsmTypeSb.append(ip).append(" ");
                hsmTypeSb.append(port);
                if (lmkSet > 0)
                {
                    hsmTypeSb.append(" <").append(lmkSet).append(">");
                }
                if (keyCaching)
                {
                    hsmTypeSb.append(" KC").append(keyCacherId);
                }
                if (fakeKeyGen)
                {
                    hsmTypeSb.append(" FKG");
                }
                hsmTypeSb.append("]");
                break;


            case SecureCryptoFactory.NCIPHER_HSM_TYPE:
                hsmTypeSb.append("[nCipher ");
                hsmTypeSb.append(moduleNumber);
                if (keyCaching)
                {
                    hsmTypeSb.append(" KC").append(keyCacherId);
                }
                hsmTypeSb.append("]");
                break;

            case SecureCryptoFactory.DUMMY_HSM_TYPE:
                hsmTypeSb.append("[Dummy ");
                hsmTypeSb.append(dummyNumber).append("]");
                break;

            case SecureCryptoFactory.FAKE_HSM_TYPE:
                hsmTypeSb.append("[!Fake!]");
                break;

            default:
                hsmTypeSb.append("[ !Unknown! ]");
        }

        return hsmTypeSb.toString();
    }

    //Returns the extra connections required to be set up (i.e. for key caching)
    //Returns null if no extra connections are required
    public HsmConnectionParams[] getExtraConnections()
    {
        return extraConnections;
    }

    private void parseIpAddress() throws SecureCryptoException
    {
        ip = parseString("IP Address");
    }

    private void parsePort() throws SecureCryptoException
    {
        port = parseInt("Port");
    }

    private void parseLmkSet() throws SecureCryptoException
    {
        if (nextTokenIsInteger())
        {
            lmkSet = parseInt("LmkSet");
        }
    }

    private void parseModuleNumber() throws SecureCryptoException
    {
        moduleNumber = parseInt("Module Number");
    }

    private void parseOutFlag() throws SecureCryptoException
    {
        startOutOfPool = false;

        if (moreTokens())
        {
            String outToken = peekNextToken();
            startOutOfPool = outToken.equalsIgnoreCase("OUT");

            if (startOutOfPool)
            {
                skipNextToken();
            }
        }
    }

    private String parseString(String itemName) throws SecureCryptoException
    {
        checkNextTokenExists(itemName);
        return nextToken();
    }

    private int parseInt(String itemName) throws SecureCryptoException
    {
        checkNextTokenIsInteger(itemName);

        String valueStr = nextToken();
        return Integer.parseInt(valueStr);
    }


    private void parseKeyCaching() throws SecureCryptoException
    {
        keyCaching = false;
        keyCacherId = 0;
        extraConnections = null;

        if (moreTokens())
        {
            String nextToken = peekNextToken();

            Pattern p = Pattern.compile("(\\+?)([0-9]?)KEYCACHER");
            Matcher m = p.matcher(nextToken);

            keyCaching = m.matches();

            if (keyCaching)
            {
                skipNextToken();

                boolean keyCachingOnly = !m.group(1).equals("+");

                int numKeyCacheConnections;
                String numOfKeyCachersStr = m.group(2);
                if (numOfKeyCachersStr.length() == 0)
                {
                    //Not specified - assume 1
                    numKeyCacheConnections = 1;
                }
                else
                {
                    numKeyCacheConnections = Integer.parseInt(numOfKeyCachersStr);
                }

                int numExtraKeyCacheConnectionsRequired;
                if (keyCachingOnly)
                {
                    //Use this HSMConnectionParams as one of the key cachers, and we need another
                    //(numKeyCacheConnections - 1)  key cachers
                    keyCaching = true;
                    keyCacherId = 1;
                    numExtraKeyCacheConnectionsRequired = numKeyCacheConnections - 1;
                }
                else
                {
                    //User this HSMConnectionParams as the normal HSM connection, and we need another
                    //(numKeyCacheConnections) key cachers
                    keyCaching = false;
                    keyCacherId = 0;
                    numExtraKeyCacheConnectionsRequired = numKeyCacheConnections;
                }


                //Now create the extra connections for the extra key cachers
                numExtraKeyCacheConnectionsRequired = Math.max(0, numExtraKeyCacheConnectionsRequired);
                extraConnections = new HsmConnectionParams[numExtraKeyCacheConnectionsRequired];
                int kcId = keyCacherId;
                for (int i = 0; i < numExtraKeyCacheConnectionsRequired; i++)
                {
                    kcId++;
                    extraConnections[i] = new HsmConnectionParams(hsmType);
                    extraConnections[i].ip = ip;
                    extraConnections[i].port = port;
                    extraConnections[i].moduleNumber = moduleNumber;
                    extraConnections[i].keyCacherId = kcId;
                    extraConnections[i].keyCaching = true;
                }
            }
        }
    }

    private void parseFakeKeyGen() throws SecureCryptoException
    {
        fakeKeyGen = false;

        if (moreTokens())
        {
            String nextToken = peekNextToken();
            if (nextToken.equalsIgnoreCase("FAKEKEYGEN"))
            {
                fakeKeyGen = true;
                skipNextToken();
            }
        }
    }

    private void parseNumOfThreads() throws SecureCryptoException
    {
        if (nextTokenIsInteger())
        {
            String numConnectionsStr = nextToken();

            int numConnections;
            try
            {
                numConnections = Integer.parseInt(numConnectionsStr);
            }
            catch (NumberFormatException x)
            {
                throw new SecureCryptoException("Invalid number of connections specified for Dummy HSM: " + numConnectionsStr);
            }

            if (numConnections < 1)
            {
                throw new SecureCryptoException("Invalid number of connections specified for Dummy HSM: " + numConnectionsStr);
            }

            if (numConnections > 1)
            {
                extraConnections = new HsmConnectionParams[numConnections - 1];
                for (int i = 0; i < extraConnections.length; i++)
                {
                    extraConnections[i] = new HsmConnectionParams(hsmType);
                    extraConnections[i].dummyNumber = i + 2;
                }
            }
        }
    }

    private boolean nextTokenIsInteger()
    {
        boolean nextTokenIsInt = false;
        if (moreTokens())
        {
            try
            {
                Integer.parseInt(peekNextToken());
                nextTokenIsInt = true;
            }
            catch (NumberFormatException x)
            {
                //Do nothing - just leave nextTokenIsInt = false
            }
        }

        return nextTokenIsInt;
    }

    private boolean moreTokens()
    {
        return connectionParam < connectionParams.length;
    }

    private String nextToken()
    {
        return connectionParams[connectionParam++].trim();
    }

    private String peekNextToken()
    {
        return connectionParams[connectionParam].trim();
    }

    private void skipNextToken()
    {
        connectionParam++;
    }

    private void checkNextTokenExists(String itemName) throws SecureCryptoException
    {
        if (!moreTokens())
        {
            String errMsg = "Could not retrieve " + itemName + " for HSM from connection settings: " + connectionParamsStr;
            log.warn(errMsg);
            throw new SecureCryptoException(errMsg);
        }
    }

    private void checkNextTokenIsInteger(String itemName) throws SecureCryptoException
    {
        checkNextTokenExists(itemName);

        if (!nextTokenIsInteger())
        {
            String valueStr = peekNextToken();

            String errMsg = "Could not retrieve " + itemName + " for HSM from connection settings: '" +
                    connectionParamsStr + "' because '" + valueStr + "' is not an integer value.";
            log.warn(errMsg);
            throw new SecureCryptoException(errMsg);
        }
    }

    private void checkNoMoreTokens() throws SecureCryptoException
    {
        if (moreTokens())
        {
            String errMsg = String.format("Unexpected token '%s' at end of HSM parameter string: %s",
                    nextToken(), connectionParamsStr);
            log.warn(errMsg);
            throw new SecureCryptoException(errMsg);
        }
    }
}
