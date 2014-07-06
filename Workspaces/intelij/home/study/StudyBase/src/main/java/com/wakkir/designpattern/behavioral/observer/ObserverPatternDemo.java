package com.wakkir.designpattern.behavioral.observer;

/**
 * User: wakkir
 * Date: 17/11/13
 * Time: 23:59
 */
/*
Observer pattern is used when there is one to many relationship between objects such as if one object is modified, its depenedent objects are to be notified automatically. Observer pattern falls under behavioral pattern category.

Implementation:
Observer pattern uses three actor classes. Subject, Observer and Client. Subject, an object having methods to attach and de-attach observers to a client object. We've created classes Subject, Observer abstract class and concrete classes extending the abstract class the Observer.

ObserverPatternDemo, our demo class will use Subject and concrete class objects to show observer pattern in action.
*/
public class ObserverPatternDemo
{
    public static void main(String[] args)
    {
        Subject subject = new Subject();

        new HexaObserver(subject);
        new OctalObserver(subject);
        new BinaryObserver(subject);

        System.out.println("First state change: 15");
        subject.setState(15);
        System.out.println("Second state change: 10");
        subject.setState(10);
    }
}
