package com.wakkir.designpattern.TransferObjectPattern;

/**
 * User: wakkir.muzammil
 * Date: 29/10/13
 * Time: 17:30
 */
public class StudentVO
{

    private String name;
    private int rollNo;

    StudentVO(String name, int rollNo)
    {
        this.name = name;
        this.rollNo = rollNo;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getRollNo()
    {
        return rollNo;
    }

    public void setRollNo(int rollNo)
    {
        this.rollNo = rollNo;
    }
}
