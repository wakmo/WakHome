package com.wakkir.spring.model;

/**
 * User: wakkir
 * Date: 08/03/14
 * Time: 01:57
 */
public class RequestObject
{
    private int id;
    private String name;

    public RequestObject()
    {
    }

    public RequestObject(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "MyObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
