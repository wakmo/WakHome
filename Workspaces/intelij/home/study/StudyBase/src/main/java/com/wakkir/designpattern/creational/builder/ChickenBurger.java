package com.wakkir.designpattern.creational.builder;

/**
 * User: wakkir.muzammil
 * Date: 04/11/13
 * Time: 11:01
 */
public class ChickenBurger extends Burger
{

    @Override
    public float price()
    {
        return 50.5f;
    }

    @Override
    public String name()
    {
        return "Chicken Burger";
    }
}
