/*
 *

 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SecureCryptoModuleException.java %
 *  Created:	 11 February 2002 10:00:01
 *  Created By:	 %created_by:  build %
 *  Last modified:	 %date_modified:  15 February 2002 11:49:51 %
 *  CI Idenitifier:	 %full_filespec:  SecureCryptoModuleException.java~4:java:UKPMA#1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */
package wak.work.cryptogram.graem.securecrypto;

/**
 * Thrown when a cryptographic module is unable to support cryptographic functionality that may
 * be available in a different sort of module. This generally occurs when a subset of parameters
 * is available.
 *
 * @author %derived_by: build %
 * @version %version:  4 %
 * @see
 */
public class SecureCryptoModuleException extends SecureCryptoException
{
    /**
     * Constructs an <code>SecureCryptoModuleException</code> with the specified detail message.
     *
     * @param message the detail message.
     * @param causingException The exception which was caught which then resulted in this exception
     *     being thrown
     */
    public SecureCryptoModuleException(String message, Throwable causingException)
    {
        super(message, causingException);
    }

    public SecureCryptoModuleException(String message)
    {
        super(message);
    }
}