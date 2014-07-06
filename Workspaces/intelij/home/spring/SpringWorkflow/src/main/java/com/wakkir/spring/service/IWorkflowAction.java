package com.wakkir.spring.service;

import java.util.List;

/**
 * User: wakkir
 * Date: 07/03/14
 * Time: 23:12
 */
public interface IWorkflowAction<T>
{
    /**
     * Execute action.
     *
     * @param context
     * @throws Exception
     */

    //public void doAction(IContext context) throws Exception;

    public List<T> doAction(IContext context) throws Exception;

}
