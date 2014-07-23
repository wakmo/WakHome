/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * http://garygregory.wordpress.com/2011/09/09/multi-threaded-unit-testing/
 */
package wak.work.multithread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;

public class MultiThreadedTestCase
{
    final ThreadSafeUniqueIdGenerator threadSafe = new ThreadSafeUniqueIdGenerator();
    final ThreadLeakUniqueIdGenerator threadLeak = new ThreadLeakUniqueIdGenerator();
    
     private void test(final int threadCount) throws InterruptedException, ExecutionException
    {
        
        Callable<Long> task = new Callable<Long>()
        {
            @Override
            public Long call()
            {
                return threadSafe.nextId();
                //return threadLeak.nextId();
            }
        };
        List<Callable<Long>> tasks = Collections.nCopies(threadCount, task);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        List<Future<Long>> futures = executorService.invokeAll(tasks);
        List<Long> resultList = new ArrayList<Long>(futures.size());
        // Check for exceptions
        for (Future<Long> future : futures)
        {
            // Throws an exception if an exception was thrown by the task.
            resultList.add(future.get());
        }
        // Validate the IDs
        Assert.assertEquals(threadCount, futures.size());
        List<Long> expectedList = new ArrayList<Long>(threadCount);
        for (long i = 1; i <= threadCount; i++)
        {
            expectedList.add(i);
        }
        Collections.sort(resultList);
        System.out.println("expectedList:"+expectedList);
        System.out.println("resultList:"+resultList);
        Assert.assertEquals(expectedList, resultList);
    }
    
    @Test
    public void test1000() throws InterruptedException, ExecutionException
    {
        test(100);
    }
    
/*
    @Test
    public void test01() throws InterruptedException, ExecutionException
    {
        test(1);
    }

    @Test
    public void test02() throws InterruptedException, ExecutionException
    {
        test(2);
    }

    @Test
    public void test04() throws InterruptedException, ExecutionException
    {
        test(4);
    }

    @Test
    public void test08() throws InterruptedException, ExecutionException
    {
        test(8);
    }

    @Test
    public void test16() throws InterruptedException, ExecutionException
    {
        test(16);
    }

    @Test
    public void test32() throws InterruptedException, ExecutionException
    {
        test(32);
    }
  */
}