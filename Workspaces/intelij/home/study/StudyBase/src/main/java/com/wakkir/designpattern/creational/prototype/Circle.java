package com.wakkir.designpattern.creational.prototype;

/**
 * User: wakkir.muzammil
 * Date: 04/11/13
 * Time: 14:30
 */
public class Circle extends Shape
{

    public Circle()
    {
        type = "Circle";
    }

    @Override
    public void draw()
    {
        System.out.println("Inside Circle::draw() method.");
    }
}