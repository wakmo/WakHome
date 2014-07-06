package net.aconite.wrapper.client;

/**
 * User: wakkir.muzammil
 * Date: 18/10/13
 * Time: 17:25
 */
public class WrapperClientException extends Exception
{
    public WrapperClientException(String message)
    {
        super(message);
    }

    public WrapperClientException(String message, Throwable cause)
    {
        super(message, cause);
    }
}