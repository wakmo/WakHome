package com.wakkir.designpattern.behavioral.visitor;

/**
 * User: wakkir.muzammil
 * Date: 26/11/13
 * Time: 16:17
 */
public class VisitorPatternDemo {
    public static void main(String[] args) {

        ComputerPart computer = new Computer();
        computer.accept(new ComputerPartDisplayVisitor());
    }
}