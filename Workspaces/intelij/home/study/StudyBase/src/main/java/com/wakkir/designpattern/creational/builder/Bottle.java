package com.wakkir.designpattern.creational.builder;

/**
 * User: wakkir.muzammil
 * Date: 04/11/13
 * Time: 10:57
 */
public class Bottle implements Packing
{
    @Override
    public String pack()
    {
        return "Bottle";
    }
}
