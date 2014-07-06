package com.wakkir.designpattern.creational.builder;

/**
 * User: wakkir.muzammil
 * Date: 04/11/13
 * Time: 11:01
 */
public class VegBurger extends Burger
{

    @Override
    public float price()
    {
        return 25.0f;
    }

    @Override
    public String name()
    {
        return "Veg Burger";
    }
}