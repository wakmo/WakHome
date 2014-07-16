/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.test.multithread;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author wakkir.muzammil
 */

/**
* Generates sequential unique IDs starting with 1, 2, 3, and so on. <p>
* This class is thread-safe. </p>
*/
public class ThreadSafeUniqueIdGenerator
{
    private final AtomicLong counter = new AtomicLong();

        public long nextId()
        {
            return counter.incrementAndGet();
        }
}
