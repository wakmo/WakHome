package com.convertor.wrapper;

/**
 * Created with IntelliJ IDEA.
 * User: Wakkir.Muzammil
 * Date: 08/10/13
 * Time: 08:56
 * To change this template use File | Settings | File Templates.
 */
public class MyLock
{
        /**
         * Is true if something "has" the lock, or false if the lock is available
         */
        private static boolean isRunning;

        public MyLock()
        {
            isRunning = false;
        }

        /**
         * Attempts to "get" the lock.
         *
         * @return true if the lock was successfully obtained;
         *         false if another thread already has the lock
         */
        public static synchronized boolean isRunning()
        {
            //boolean gotLock;

            /*if (isRunning)
            {
                 //gotLock = true;
                isRunning = true;
            }
            else
            {
                 //gotLock = false;
                isRunning = false;
            }*/

            return isRunning;
        }

    public static void setIsRunning(boolean running)
    {
                  isRunning = running;
    }

        /**
         * Releases the lock.
         *
         * This method MUST be called after a caller has called getLock() and got a 'true' return value,
         * but MUST NOT be called after a caller has called getLock() and has got a 'false' return value.
         */
        public static synchronized void stopRunning()
        {
            //if (!locked) log.error("Noting has the lock, but releaseLock() was called!");
            isRunning = false;
        }

}
