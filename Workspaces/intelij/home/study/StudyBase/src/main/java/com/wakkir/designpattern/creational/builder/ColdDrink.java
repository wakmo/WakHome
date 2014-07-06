package com.wakkir.designpattern.creational.builder;

/**
 * User: wakkir.muzammil
 * Date: 04/11/13
 * Time: 11:00
 */
public abstract class ColdDrink implements Item
{

    @Override
    public Packing packing()
    {
        return new Bottle();
    }

    @Override
    public abstract float price();
}
