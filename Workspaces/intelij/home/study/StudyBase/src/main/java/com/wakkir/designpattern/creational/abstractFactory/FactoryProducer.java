package com.wakkir.designpattern.creational.abstractFactory;

/**
 * User: wakkir
 * Date: 01/11/13
 * Time: 03:07
 */
public class FactoryProducer
{
    public static IAbstractFactory getFactory(String choice)
    {
        if (choice.equalsIgnoreCase("SHAPE"))
        {
            return new ShapeFactory();
        }
        else if (choice.equalsIgnoreCase("COLOR"))
        {
            return new ColourFactory();
        }
        return null;
    }
}