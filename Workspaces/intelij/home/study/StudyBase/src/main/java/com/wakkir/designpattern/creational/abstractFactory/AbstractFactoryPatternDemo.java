package com.wakkir.designpattern.creational.abstractFactory;

/**
 * User: wakkir
 * Date: 01/11/13
 * Time: 03:08
 */

/*
Abstract Factory patterns works around a super-factory which creates other factories. This factory is also called as Factory of factories. This type of design pattern comes under creational pattern as this pattern provides one of the best ways to create an object.

In Abstract Factory pattern an interface is responsible for creating a factory of related objects, without explicitly specifying their classes. Each generated factory can give the objects as per the Factory pattern.

Implementation:
We're going to create a Shape and Color interfaces and concrete classes implementing these interfaces. We creates an abstract factory class AbstractFactory as next step. Factory classes ShapeFactory and ColorFactory are defined where each factory extends AbstractFactory. A factory creator/generator class FactoryProducer is created.

AbstractFactoryPatternDemo, our demo class uses FactoryProducer to get a AbstractFactory object. It will pass information (CIRCLE / RECTANGLE / SQUARE for Shape) to AbstractFactory to get the type of object it needs. It also passes information (RED / GREEN / BLUE for Color) to AbstractFactory to get the type of object it needs.
*/

public class AbstractFactoryPatternDemo
{
    public static void main(String[] args)
    {
        //get shape factory
        IAbstractFactory shapeFactory = FactoryProducer.getFactory("SHAPE");

        //get an object of IShape Circle
        IShape shape1 = shapeFactory.getShape("CIRCLE");

        //call draw method of IShape Circle
        shape1.draw();

        //get an object of IShape Rectangle
        IShape shape2 = shapeFactory.getShape("RECTANGLE");

        //call draw method of IShape Rectangle
        shape2.draw();

        //get an object of IShape Square
        IShape shape3 = shapeFactory.getShape("SQUARE");

        //call draw method of IShape Square
        shape3.draw();

        //get color factory
        IAbstractFactory colorFactory = FactoryProducer.getFactory("COLOR");

        //get an object of Color Red
        IColour color1 = colorFactory.getColour("RED");

        //call fill method of Red
        color1.fill();

        //get an object of Color Green
        IColour color2 = colorFactory.getColour("Green");

        //call fill method of Green
        color2.fill();

        //get an object of Color Blue
        IColour color3 = colorFactory.getColour("BLUE");

        //call fill method of Color Blue
        color3.fill();
    }
}