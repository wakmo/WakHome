package com.wakkir.designpattern.creational.abstractFactory;

/**
 * User: wakkir
 * Date: 01/11/13
 * Time: 03:01
 */

public interface IAbstractFactory
{
    IColour getColour(String color);

    IShape getShape(String shape);
}