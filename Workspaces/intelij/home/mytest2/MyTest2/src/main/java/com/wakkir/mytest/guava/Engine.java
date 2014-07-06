package com.wakkir.mytest.guava;

import com.google.common.base.Objects;

/**
 * User: wakkir
 * Date: 15/05/14
 * Time: 21:36
 */
public class Engine
{
    int id;
    String name;
    int status;
    boolean running;

    public Engine(int status, String name, int id)
    {
        this.status = status;
        this.name = name;
        this.id = id;
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

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public boolean isRunning()
    {
        return running;
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("Id", id)
                .add("Name", name) // petName is @Nullable!
                .add("Status", status)
                .add("Running", running)
                .omitNullValues()
                .toString();
    }
}
