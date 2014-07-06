package com.wakkir.designpattern.creational.builder;

/**
 * User: wakkir.muzammil
 * Date: 04/11/13
 * Time: 11:03
 */
public class Pepsi extends ColdDrink
{

    @Override
    public float price()
    {
        return 35.0f;
    }

    @Override
    public String name()
    {
        return "Pepsi";
    }
}
