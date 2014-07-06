package com.wakkir.designpattern.creational.builder;

/**
 * User: wakkir.muzammil
 * Date: 04/11/13
 * Time: 10:50
 */
public interface Item
{
    public String name();

    public Packing packing();

    public float price();
}
