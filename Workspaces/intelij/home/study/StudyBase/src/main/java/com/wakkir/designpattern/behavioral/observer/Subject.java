package com.wakkir.designpattern.behavioral.observer;

/**
 * User: wakkir
 * Date: 17/11/13
 * Time: 23:54
 */

import java.util.ArrayList;
import java.util.List;

public class Subject
{

    private List<Observer> observers = new ArrayList<Observer>();
    private int state;

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
        notifyAllObservers();
    }

    public void attach(Observer observer)
    {
        observers.add(observer);
    }

    public void notifyAllObservers()
    {
        for (Observer observer : observers)
        {
            observer.update();
        }
    }
}