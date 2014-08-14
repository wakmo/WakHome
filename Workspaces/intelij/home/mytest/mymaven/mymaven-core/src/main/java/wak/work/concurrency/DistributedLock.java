/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wak.work.concurrency;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author wakkir
 * 
 * http://stackoverflow.com/questions/92452/distributed-concurrency-control
 */
public class DistributedLock 
{
    public static final DistributedLock instance = new DistributedLock();
    private int counter = 0;
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);

    public void read()
    {
        while (true) 
        {
            rwl.readLock().lock();
            try 
            {
                System.out.println("Counter is " + counter);
            } 
            finally 
            {
                rwl.readLock().unlock();
            }
            try 
            { 
                Thread.sleep(1000); 
            } 
            catch (InterruptedException ie) 
            { 
            }
        }
    }

    public void write()
    {
        while (true) 
        {
            rwl.writeLock().lock();
            try 
            {
               counter++;
               System.out.println("Incrementing counter.  Counter is " + counter);
            } 
            finally 
            {
                 rwl.writeLock().unlock();
            }
            try 
            { 
                Thread.sleep(3000); 
            } 
            catch (InterruptedException ie) 
            { 
            }
        }
    }

    public static void main(String[] args)
    {
        if (args.length > 0)  
        {
            // args --> Writer
            instance.write();
        } 
        else 
        {
            // no args --> Reader
            instance.read();
        }
    }
}
