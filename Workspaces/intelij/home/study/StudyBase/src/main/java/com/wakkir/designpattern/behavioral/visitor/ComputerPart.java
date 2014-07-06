package com.wakkir.designpattern.behavioral.visitor;

/**
 * User: wakkir.muzammil
 * Date: 26/11/13
 * Time: 16:15
 */
public interface  ComputerPart {
    public void accept(ComputerPartVisitor computerPartVisitor);
}
