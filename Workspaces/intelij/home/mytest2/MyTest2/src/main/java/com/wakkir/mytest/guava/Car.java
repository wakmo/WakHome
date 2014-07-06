package com.wakkir.mytest.guava;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * User: wakkir
 * Date: 15/05/14
 * Time: 22:02
 */
public class Car
{
    Engine engine;


    public Car(Engine engine)
    {
        this.engine = Preconditions.checkNotNull(engine); // NPE
    }

    public Engine getEngine()
    {
        return engine;
    }

    public void setEngine(Engine engine)
    {
        this.engine = engine;
    }

    public void drive(double speed)
    {
        Preconditions.checkArgument(speed > 0.0, "speed (%s) must be positive", speed); // IAE
        Preconditions.checkState(engine.isRunning(), "engine must be running"); // ISE

    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("Engine", engine)
                .omitNullValues()
                .toString();
    }
}
