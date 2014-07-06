package com.wakkir.designpattern.creational.factory;

/**
 * User: wakkir
 * Date: 28/10/13
 * Time: 23:54
 */
public class Square implements IShape
{

    @Override
    public void draw()
    {
        System.out.println("Inside Square::draw() method.");
    }
}
