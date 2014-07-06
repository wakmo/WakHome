package com.wakkir.designpattern.creational.abstractFactory;

/**
 * User: wakkir
 * Date: 28/10/13
 * Time: 23:54
 */
public class Rectangle implements IShape
{

    @Override
    public void draw()
    {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
