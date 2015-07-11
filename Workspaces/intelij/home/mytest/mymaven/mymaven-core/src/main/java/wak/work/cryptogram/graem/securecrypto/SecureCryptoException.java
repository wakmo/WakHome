/*
 * SecureCryptoException.java
 *
 * Created on 10 October 2001, 11:05
 */

package wak.work.cryptogram.graem.securecrypto;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Thrown when a module encounters a cryptographic problem.
 *
 * @author richard
 */
public class SecureCryptoException extends Exception
{
    private static final Logger log = Logger.getLogger(SecureCryptoException.class);

    private final String extendedMessage;

    private final String causingExceptionMessage;
    private final String causingExceptionStackTrace;
    private final String causingExceptionClass;

    private final boolean retryMightWork;


    public SecureCryptoException(String message)
    {
        this(message, null, null, false);
    }

    public SecureCryptoException(String message, String extendedMessage)
    {
        this(message, extendedMessage, null, false);
    }

    public SecureCryptoException(String message, Throwable causingException)
    {
        this(message, null, causingException, false);
    }

    public SecureCryptoException(String message, String extendedMessage, Throwable causingException)
    {
        this(message, extendedMessage, causingException, false);
    }

    public SecureCryptoException(String message, Throwable causingException, boolean retryMightWork)
    {
        this(message, null, causingException, retryMightWork);
    }

    public SecureCryptoException(String message, String extendedMessage, Throwable causingException,
                                 boolean retryMightWork)
    {
        super(message);

        this.extendedMessage = extendedMessage;
        this.retryMightWork = retryMightWork;

        //Do not store the causingException in a member variant here - if the causing exception is
        //not known to the client then the client will suffer a ClassNotFoundException (e.g. if
        //causingException is an NCipher exception inside the NCipher.jar file)
        if (causingException != null)
        {
            causingExceptionClass = causingException.getClass().getName();
            causingExceptionMessage = causingException.getMessage();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream pw = new PrintStream(out, true);
            causingException.printStackTrace(pw);
            causingExceptionStackTrace = new String(out.toByteArray());
        }
        else
        {
            causingExceptionClass = null;
            causingExceptionMessage = null;
            causingExceptionStackTrace = null;
        }
    }

    public String getMessage()
    {
        StringBuilder sb = new StringBuilder(super.getMessage());

        if (causingExceptionMessage != null)
        {
            sb.append(" $ ");
            sb.append(causingExceptionMessage);
        }

        return sb.toString();
    }

    public String getExtendedMessage()
    {
        return extendedMessage;
    }

    public String getFullMessage()
    {
        StringBuilder sb = new StringBuilder(super.getMessage());

        if (extendedMessage != null)
        {
            sb.append(" : ");
            sb.append(extendedMessage);
        }

        if (causingExceptionMessage != null)
        {
            sb.append(" $ ");
            sb.append(causingExceptionMessage);
        }

        return sb.toString();
    }

    public void printStackTrace()
    {
        super.printStackTrace();

        if (causingExceptionStackTrace != null)
        {
            System.err.println("----> caused by:\n" + causingExceptionStackTrace);
        }
    }

    @Override
    public void printStackTrace(PrintWriter s)
    {
        super.printStackTrace(s);

        if (causingExceptionStackTrace != null)
        {
            s.append("----> Caused by:").append(causingExceptionStackTrace);
        }
    }

    public boolean hasCausingException()
    {
        return (causingExceptionClass != null);
    }

    public boolean retryMightWork()
    {
        return retryMightWork;
    }

    public String getCausingExceptionClass()
    {
        return causingExceptionClass;
    }

    public String getCausingExceptionStackTrace()
    {
        return causingExceptionStackTrace;
    }
}
