package com.wakkir.designpattern.creational.prototype;

/**
 * User: wakkir.muzammil
 * Date: 04/11/13
 * Time: 14:30
 */
public class Rectangle extends Shape
{

    public Rectangle()
    {
        type = "Rectangle";
    }

    @Override
    public void draw()
    {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
