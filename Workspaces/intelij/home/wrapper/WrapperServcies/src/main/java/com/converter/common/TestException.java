package com.converter.common;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir.muzammil
 * Date: 18/10/13
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class TestException extends Exception
{
    public TestException(String message)
    {
        super(message);
    }

    public TestException(String message, Throwable cause)
    {
        super(message, cause);
    }
}