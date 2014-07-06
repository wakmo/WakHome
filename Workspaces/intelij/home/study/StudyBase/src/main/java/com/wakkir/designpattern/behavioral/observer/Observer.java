package com.wakkir.designpattern.behavioral.observer;

/**
 * User: wakkir
 * Date: 17/11/13
 * Time: 23:56
 */
public abstract class Observer
{
    protected Subject subject;

    public abstract void update();
}
