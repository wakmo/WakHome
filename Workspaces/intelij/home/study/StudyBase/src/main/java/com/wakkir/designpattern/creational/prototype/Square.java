package com.wakkir.designpattern.creational.prototype;

/**
 * User: wakkir.muzammil
 * Date: 04/11/13
 * Time: 14:30
 */
public class Square extends Shape
{

    public Square()
    {
        type = "Square";
    }

    @Override
    public void draw()
    {
        System.out.println("Inside Square::draw() method.");
    }
}