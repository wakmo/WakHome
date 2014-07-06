package com.wakkir.designpattern.creational.abstractFactory;

/**
 * User: wakkir
 * Date: 01/11/13
 * Time: 03:04
 */
//public class ColourFactory extends AbstractFactory
public class ColourFactory implements IAbstractFactory
{
    @Override
    public IShape getShape(String shapeType)
    {
        return null;
    }

    @Override
    public IColour getColour(String color)
    {
        if (color == null)
        {
            return null;
        }
        if (color.equalsIgnoreCase("RED"))
        {
            return new Red();
        }
        else if (color.equalsIgnoreCase("GREEN"))
        {
            return new Green();
        }
        else if (color.equalsIgnoreCase("BLUE"))
        {
            return new Blue();
        }
        return null;
    }
}