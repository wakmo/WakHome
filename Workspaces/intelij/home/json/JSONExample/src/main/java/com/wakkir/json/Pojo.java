package com.wakkir.json;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 15/12/12
 * Time: 23:58
 * To change this template use File | Settings | File Templates.
 */
public class Pojo
{
    private String name;
    private int age;

    public Pojo()
    {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    @Override
    public String toString()
    {
        return  "name="+name+", age="+age;
    }
}
