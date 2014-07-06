package com.wakkir.designpattern.behavioral.observer;

/**
 * User: wakkir
 * Date: 17/11/13
 * Time: 23:58
 */
public class OctalObserver extends Observer
{

    public OctalObserver(Subject subject)
    {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update()
    {
        System.out.println("Octal String: " + Integer.toOctalString(subject.getState()));
    }
}
