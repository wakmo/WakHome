package wak.work.cryptogram.graem.securecrypto;

/** Generates instances of the SecureCrypto.
 * @author Richard Izzard
 * @version 1.0
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import wak.work.cryptogram.graem.securecrypto.keycache.KeyCache;
import org.apache.log4j.Logger;

public final class SecureCryptoFactory
{
    private static final Logger log = Logger.getLogger(SecureCryptoFactory.class);

    public static final int RG7000_HSM_TYPE = 7000;
    public static final int RG8000_HSM_TYPE = 8000;
    public static final int PS9000_HSM_TYPE = 9000;
    public static final int CM250_HSM_TYPE = 250;
    public static final int NCIPHER_HSM_TYPE = 1;
    public static final int DUMMY_HSM_TYPE = -3;
    public static final int FAKE_HSM_TYPE = -1;


    private SecureCryptoFactory()
    {
    }

    /**
     * Retrieve a SecureCrypto implementation for connected HSM (e.g. RG7000, or RG8000 or NCipher)
     *
     * @param connectionParams Connection parameters for the HSM
     * @param logConnectFails Log if connections f
     * @return SecureCrypto implementation for the HSM
     * @throws SecureCryptoException Thrown if the cryptographic hardware module cannot be located or connection is denied.
     */
    public static SecureCrypto getSecureCrypto(HsmConnectionParams connectionParams, Boolean logConnectFails) throws SecureCryptoException
    {
        return getSecureCrypto(connectionParams, logConnectFails, null);
    }

    /**
     * Retrieve a SecureCrypto implementation for connected HSM (e.g. RG7000, or RG8000 or NCipher)
     *
     * @param connectionParams Connection parameters for the HSM
     * @param logConnectFails Log if connections f
     * @param keyCache RSA Key Cache shared between multiple HSMs.  Pass as null if key caching not required.
     * @return SecureCrypto implementation for the HSM
     * @throws SecureCryptoException Thrown if the cryptographic hardware module cannot be located or connection is denied.
     */
    public static SecureCrypto getSecureCrypto(HsmConnectionParams connectionParams, Boolean logConnectFails,
                                               KeyCache keyCache) throws SecureCryptoException
    {
        if (log.isDebugEnabled())
        {
	        log.debug("getSecureCrypto(): " + connectionParams.toString());
        }

        SecureCrypto sci;

        try
        {
            Class sciClass;
            Constructor constructor;

            switch (connectionParams.getHsmType())
            {
	            case PS9000_HSM_TYPE:
	                sciClass = Class.forName("net.aconite.securecrypto.PS9000SecureCrypto");
	                constructor = sciClass.getDeclaredConstructor(HsmConnectionParams.class, Boolean.class, KeyCache.class);
	                sci = (SecureCrypto) constructor.newInstance(connectionParams, logConnectFails, keyCache);
	                break;
                
	            case RG7000_HSM_TYPE:
                case RG8000_HSM_TYPE:
                case CM250_HSM_TYPE:
                    sciClass = Class.forName("wak.work.cryptogram.graem.securecrypto.RG7000SecureCrypto");
                    constructor = sciClass.getDeclaredConstructor(HsmConnectionParams.class, Boolean.class, KeyCache.class);
                    sci = (SecureCrypto) constructor.newInstance(connectionParams, logConnectFails, keyCache);
                    break;

                case NCIPHER_HSM_TYPE:
                    sciClass = Class.forName("wak.work.cryptogram.graem.securecrypto.ncipher.NCipherSecureCrypto");
                    constructor = sciClass.getDeclaredConstructor(HsmConnectionParams.class, KeyCache.class);
                    sci = (SecureCrypto) constructor.newInstance(connectionParams, keyCache);
                    break;

                case DUMMY_HSM_TYPE:
                    sciClass = Class.forName("wak.work.cryptogram.graem.securecrypto.DummySecureCrypto");
                    constructor = sciClass.getDeclaredConstructor(HsmConnectionParams.class);
                    sci = (SecureCrypto) constructor.newInstance(connectionParams);
                    break;

                case FAKE_HSM_TYPE:
                    sciClass = Class.forName("wak.work.cryptogram.graem.securecrypto.FakeSecureCrypto");
                    constructor = sciClass.getDeclaredConstructor();
                    sci = (SecureCrypto) constructor.newInstance();
                    break;

                default:
                    final String msg = "Internal error: Unknown HSM type code: " + connectionParams.getHsmType() + " : " + connectionParams.toString();
                    log.error(msg);
                    throw new SecureCryptoException(msg);
            }
        }
        catch (InvocationTargetException x)
        {
            String errMsg = "Cannot instantiate crypto engine";
            if (logConnectFails)
            {
	            log.warn(errMsg, x);
            }
            throw new SecureCryptoException(errMsg, x.getTargetException());
        }
        catch (final Throwable x)
        {
            String errMsg = "Cannot instantiate crypto engine";
            log.error(errMsg, x);
            throw new SecureCryptoException(errMsg, x);
        }
        
        return sci;
    }

    /**
     * @deprecated Use getSecureCrypto() instead
     * (Still used by Infrastructure.jar - 28 January 2011)
     */
    @Deprecated
    public static SecureCrypto getFakeSecureCrypto() throws SecureCryptoException
    {
        return getSecureCrypto(new HsmConnectionParams("FAKE"), true);
    }
}
