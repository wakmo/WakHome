package com.wakkir.util;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir.muzammil
 * Date: 19/06/12
 * Time: 15:03
 * To change this template use File | Settings | File Templates.
 */
public class FeedbackUtilException extends Exception
{
    public FeedbackUtilException(String message)
    {
        super(message);
    }

    public FeedbackUtilException(String message, Throwable cause)
    {
        super(message, cause);
    }
}