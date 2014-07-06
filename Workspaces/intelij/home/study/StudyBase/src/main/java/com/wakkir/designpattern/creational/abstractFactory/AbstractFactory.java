package com.wakkir.designpattern.creational.abstractFactory;

/**
 * User: wakkir
 * Date: 01/11/13
 * Time: 03:01
 */

public abstract class AbstractFactory
{
    abstract IColour getColour(String colour);

    abstract IShape getShape(String shape);
}
