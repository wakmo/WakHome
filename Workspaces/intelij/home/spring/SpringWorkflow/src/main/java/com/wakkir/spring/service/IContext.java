package com.wakkir.spring.service;

/**
 * User: wakkir
 * Date: 07/03/14
 * Time: 23:13
 */
public interface IContext
{
    /**
     * Set value with specified name in context.
     * <p/>
     * If value already exist it should overwrite value with new one.
     *
     * @param name  of attribute
     * @param value which should be stored for specified name
     */

    public void setAttribute(String name, Object value);

    /**
     * Retrieve object with specified name from context,
     * <p/>
     * if object does not exists in context it will return null.
     *
     * @param name of attribute which need to be returned
     * @return Object from context or null if there is no value assigned to specified name
     */

    public Object getAttribute(String name);

}
