package com.wakkir.designpattern.creational.singleton;

/**
 * User: wakkir.muzammil
 * Date: 29/10/13
 * Time: 17:19
 */
/*
Singleton pattern is one of the simplest design patterns in Java. This type of design pattern comes under creational pattern as this pattern provides one of the best way to create an object.

This pattern involves a single class which is responsible to creates own object while making sure that only single object get created. This class provides a way to access its only object which can be accessed directly without need to instantiate the object of the class.

Implementation  :
We're going to create a SingleObject class. SingleObject class have its constructor as private and have a static instance of itself.

SingleObject class provides a static method to get its static instance to outside world. SingletonPatternDemo, our demo class will use SingleObject class to get a SingleObject object.
*/
public class SingletonPatternDemo
{
    public static void main(String[] args)
    {

        //illegal construct
        //Compile Time Error: The constructor SingleObject() is not visible
        //SingleObject object = new SingleObject();

        //Get the only object available
        SingleObject object = SingleObject.getInstance();

        //show the message
        object.showMessage();

        //show the message
        object.setMessage("DDD");

        //show the message
        object.showMessage();

        //Get the only object available
        SingleObject object1 = SingleObject.getInstance();

        //show the message
        object1.showMessage();
    }
}
