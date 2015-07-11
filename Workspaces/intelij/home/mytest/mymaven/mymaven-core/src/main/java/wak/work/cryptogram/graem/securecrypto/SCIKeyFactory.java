/*
*

*
*  Project:                PROJECT NAME INSERTED HERE
*
*  Type:		 %cvtype:  java %
*  Name:		 %name:   SCIKeyFactory.java %
*  Created:	 Mon Oct 20 18:21:42 2003
*  Created By:	 %created_by:  build %
*  Last modified:	 %date_modified:  %
*  CI Idenitifier:	 %full_filespec:  SCIKeyFactory.java~10:java:UKPMA#1 %
*
*  Amendment Record
*  Version 	Date        Author  		Description
*
*
*
*
*/

package wak.work.cryptogram.graem.securecrypto;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;

import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKey;
import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyException;
import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyTypes;
import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCISecureModules;

public class SCIKeyFactory
{
    private static final Logger log = Logger.getLogger(SCIKeyFactory.class);


    private static SCIKeyFactory instance = null;
    private static final Object lock = new Object();

    private SCIKeyFactory()
    {
        log.debug("Singleton instance created.");
    }

    public static SCIKeyFactory getInstance()
    {
        if (instance == null)
        {
            synchronized (lock)
            {
                if (instance == null)
                {
                    instance = new SCIKeyFactory();
                }
            }
        }

        return instance;
    }

    public ProtectedKey createSCIProtectedKey(SCIKey keyValue) throws SecureCryptoException
    {
        final Key key = createSCIKey(keyValue);
        if (!(key instanceof ProtectedKey))
        {
            throw new SecureCryptoException("Invalid key - protected key expected - " + keyValue);
        }

        return (ProtectedKey) key;
    }

    public AuthenticKey createSCIAuthenticKey(SCIKey keyValue) throws SecureCryptoException
    {
        final Key key = createSCIKey(keyValue);
        if (!(key instanceof AuthenticKey))
        {
            throw new SecureCryptoException("Invalid key - authentic key expected - " + keyValue);
        }

        return (AuthenticKey) key;
    }

    public Key createSCIKey(SCIKey keyValue) throws SecureCryptoException
    {
        boolean isAuthenticKey;
        switch (keyValue.getKeyType())
        {
            case (SCIKeyTypes.PROTECTED):
                isAuthenticKey = false;
                break;
            case (SCIKeyTypes.AUTHENTIC):
                isAuthenticKey = true;
                break;

            default:
                throw new SecureCryptoException("Invalid key type for key - " + keyValue);
        }

        final String smType = keyValue.getSMType();
        int smTypecode;
        try
        {
            smTypecode = SCISecureModules.translate(smType);
        }
        catch (final SCIKeyException x)
        {
            final String errMsg = "Invalid SM Type (i.e. HSM Type) code: " + smType;
            log.warn(errMsg, x);
            throw new SecureCryptoException(errMsg, x);
        }

        String keyClassName = "wak.work.cryptogram.graem.securecrypto.";

        // NOTE! This will need updating as more Security Module types are added
        // It currently works for: Thales RG7000, RG8000, CM250, PS9000; SCE; Fake; NCipher
        switch (smTypecode)
        {
        	case SCISecureModules.PS9000:
        		keyClassName = "net.aconite.securecrypto.PS9000";
        		break;
            case SCISecureModules.SCE:
            case SCISecureModules.RG7000:
            case SCISecureModules.FAKE:
                //Class name is of the form: wak.work.cryptogram.graem.securecrypto.RG7000AuthenticKey
                keyClassName += smType;
                break;
            
            case SCISecureModules.NCIPHER:
                //Class name is of the form: wak.work.cryptogram.graem.securecrypto.ncipher.NCipherAuthenticKey
                keyClassName += "ncipher.NCipher";
                break;

            default:
                throw new SecureCryptoException("Invalid security module type for key: " + keyValue);
        }
        keyClassName += isAuthenticKey? "AuthenticKey" : "ProtectedKey";

        Object key;
        try
        {
            //Create a new instance of the Key class (whose name is in keyClassName)
            //using the constructor (SCIKey keyValue)
            final Class keyClass = Class.forName(keyClassName);
            final Class[] paramTypes = new Class[]{SCIKey.class};
            final Constructor constructor = keyClass.getConstructor(paramTypes);
            final Object[] paramValues = new Object[]{keyValue};
            key = constructor.newInstance(paramValues);
        }
        catch (final InvocationTargetException x)
        {
            final String errMsg = "Failed to create a new instance of " + keyClassName;
            log.warn(errMsg, x);
            log.warn(x.getTargetException());
            throw new SecureCryptoException(errMsg, x.getTargetException());
        }
        catch (final Throwable x)
        {
            final String errMsg = "Cannot instantiate key for given security module. keyValue=" + keyValue;
            log.warn(errMsg, x);
            throw new SecureCryptoException(errMsg, x);
        }

        if (!(key instanceof Key))
        {
            final String errMsg = "Invalid key - this is not a key object: " + keyClassName
                    + ", for keyValue = " + keyValue;
            log.warn(errMsg);
            throw new SecureCryptoException(errMsg);
        }
        
        return (Key) key;
    }

    public SCIKey createSCIKeyValue(Key key) throws SecureCryptoException
    {
        return key.extractTransportableKey();
    }
}
