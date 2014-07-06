package com.wakkir.designpattern.creational.abstractFactory;

/**
 * User: wakkir
 * Date: 28/10/13
 * Time: 23:58
 */
//public class ShapeFactory extends AbstractFactory
public class ShapeFactory implements IAbstractFactory
{
    @Override
    public IShape getShape(String shapeType)
    {
        if (shapeType == null)
        {
            return null;
        }
        if (shapeType.equalsIgnoreCase("CIRCLE"))
        {
            return new Circle();
        }
        else if (shapeType.equalsIgnoreCase("RECTANGLE"))
        {
            return new Rectangle();
        }
        else if (shapeType.equalsIgnoreCase("SQUARE"))
        {
            return new Square();
        }
        return null;
    }

    @Override
    public IColour getColour(String colour)
    {
        return null;
    }
}