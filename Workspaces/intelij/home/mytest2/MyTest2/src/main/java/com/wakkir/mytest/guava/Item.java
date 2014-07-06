package com.wakkir.mytest.guava;

import com.google.common.base.Objects;

/**
 * User: wakkir
 * Date: 15/05/14
 * Time: 21:47
 */
public class Item
{
    private String id;
    private String name;

    public Item(String id, String name)
    {
        this.id = id;
        this.id = name;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(getId(), getName());
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("id", getId()).add("name", getName()).toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Item))
        {
            return false;
        }
        Item other = (Item) o;
        return Objects.equal(getId(), other.getId()) && Objects.equal(getName(), other.getName());
    }
}
