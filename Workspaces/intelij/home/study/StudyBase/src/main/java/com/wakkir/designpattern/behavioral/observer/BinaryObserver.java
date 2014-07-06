package com.wakkir.designpattern.behavioral.observer;

/**
 * User: wakkir
 * Date: 17/11/13
 * Time: 23:57
 */
public class BinaryObserver extends Observer
{

    public BinaryObserver(Subject subject)
    {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update()
    {
        System.out.println("Binary String: " + Integer.toBinaryString(subject.getState()));
    }
}
