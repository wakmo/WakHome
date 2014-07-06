package com.wakkir.designpattern.behavioral.visitor;

/**
 * User: wakkir.muzammil
 * Date: 26/11/13
 * Time: 16:17
 */
public interface ComputerPartVisitor {
    public void visit(Computer computer);
    public void visit(Mouse mouse);
    public void visit(Keyboard keyboard);
    public void visit(Monitor monitor);
}

