package com.wakkir.designpattern.creational.singleton;

/**
 * User: wakkir.muzammil
 * Date: 29/10/13
 * Time: 17:17
 */
public class SingleObject
{
    //create an object of SingleObject
    private final static SingleObject instance = new SingleObject();
    private String message;

    //make the constructor private so that this class cannot be
    //instantiated
    private SingleObject()
    {

    }

    //Get the only object available
    public static SingleObject getInstance()
    {
        return instance;
    }

    public void showMessage()
    {
        System.out.println("Hello World! :" + getMessage());
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

}
