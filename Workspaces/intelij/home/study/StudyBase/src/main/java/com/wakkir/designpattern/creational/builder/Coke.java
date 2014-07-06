package com.wakkir.designpattern.creational.builder;

/**
 * User: wakkir.muzammil
 * Date: 04/11/13
 * Time: 11:02
 */
public class Coke extends ColdDrink
{

    @Override
    public float price()
    {
        return 30.0f;
    }

    @Override
    public String name()
    {
        return "Coke";
    }
}
