package com.wakkir.util;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir.muzammil
 * Date: 26/06/12
 * Time: 14:07
 * To change this template use File | Settings | File Templates.
 */

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.InputStream;

public class PrefixedClasspathResourceLoader extends ClasspathResourceLoader
{
    /** Prefix to be added to any names */
    private String prefix = "";

    @Override
    public void init(ExtendedProperties configuration)
    {
        prefix = configuration.getString("prefix","");
    }

    @Override
    public InputStream getResourceStream(String name) throws ResourceNotFoundException
    {
        return super.getResourceStream(prefix+name);
    }
}
