/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.test.multithread;

/**
 *
 * @author wakkir.muzammil
 */

/**
* Generates sequential unique IDs starting with 1, 2, 3, and so on. <p>
* This class is NOT thread-safe. </p>
*/
public class ThreadLeakUniqueIdGenerator
{
    private long counter = 0;

        public long nextId()
        {
            return ++counter;
        }
}
