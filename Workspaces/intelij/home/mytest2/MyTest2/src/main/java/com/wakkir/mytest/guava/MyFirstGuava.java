package com.wakkir.mytest.guava;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;


/**
 * User: wakkir
 * Date: 15/05/14
 * Time: 21:35
 */
public class MyFirstGuava
{

    public void TestPrecondition()
    {
        Engine engine=new Engine(1,null,3);
        engine.setRunning(true);
        Car car=new Car(engine);
        car.drive(10.0);

        System.out.println("car : "+car);
    }

    public void TestStopwatch()
    {
        Stopwatch stopwatch = Stopwatch.createStarted();
        try
        {
            Thread.sleep(3);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        long nanos = stopwatch.elapsed(TimeUnit.NANOSECONDS);
        System.out.println("ElapsedTime in ns : "+nanos);
    }

    private static final CharMatcher ID_MATCHER = CharMatcher.DIGIT.or(CharMatcher.is('-'));
    public void TestCharMatcher()
    {
        CharSequence sequence="zdfABCdefg123";
        System.out.println("CharSequence : "+sequence);
        CharMatcher anyOf=CharMatcher.anyOf(sequence);
        CharMatcher noneOf=CharMatcher.noneOf(sequence);
        System.out.println("anyOf : "+anyOf.matches('x'));
        System.out.println("noneOf : "+noneOf.matches('x'));


        CharMatcher is=CharMatcher.is('B');
        CharMatcher inRange=CharMatcher.inRange('e', 'z');
        System.out.println("is : "+is.matches('A'));
        System.out.println("inRange : "+inRange.matches('p'));

        CharSequence userInput="345-3E454refewrf";
        System.out.println("userInput1 : "+userInput);
        String str=ID_MATCHER.retainFrom(userInput);
        System.out.println("userInput2 : "+str);
    }

    private static final Joiner JOINER =Joiner.on(", ").skipNulls();
    public void TestStringJoining()
    {
        String str=JOINER.join("Kurt", "Kevin", null, "Chris");// yields: "Kurt, Kevin, Chris"
        System.out.println("Joiner : "+str);
    }

    private static final Splitter SPLITTER =Splitter.on(",").trimResults().omitEmptyStrings();
    public void TestStringSplitting()
    {
        Iterable str= SPLITTER.split(" foo, ,bar, quux,");
        System.out.println("Splitting : "+str);
    }

    public static void main(String[] args)
    {
        System.out.println("starting...");
        MyFirstGuava guava=new MyFirstGuava();


        //guava.TestPrecondition();
        //guava.TestStopwatch();
        //guava.TestCharMatcher();
        //guava.TestStringJoining();
        guava.TestStringSplitting();

        System.out.println("ended...");

    }
}
