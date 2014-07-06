/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.persistence;

/**
 * An interface that defines the work which can be carried out by database worker
 * in a transactional context.
 * @author thushara.pethiyagoda
 */
public interface Workable
{
    /**
     * Implement this method to execute activities that can be done within a database transaction.
     */
    public <T> void doWork(T arg);
}
