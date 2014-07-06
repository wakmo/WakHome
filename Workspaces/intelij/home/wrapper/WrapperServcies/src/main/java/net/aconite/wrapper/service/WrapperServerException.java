package net.aconite.wrapper.service;

/**
 * User: wakkir.muzammil
 * Date: 16/10/13
 * Time: 18:39
 */
@SuppressWarnings("SameParameterValue")
class WrapperServerException extends Exception
{
    public WrapperServerException(String message)
    {
        super(message);
    }

    public WrapperServerException(String message, Throwable cause)
    {
        super(message, cause);
    }
}