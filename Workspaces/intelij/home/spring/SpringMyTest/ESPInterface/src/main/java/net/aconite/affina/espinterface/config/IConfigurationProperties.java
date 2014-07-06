/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.config;

import java.util.Properties;

/**
 *
 * @author wakkir.muzammil
 */
public interface IConfigurationProperties 
{
    // Allows a client to query a single property
    public String getProperty(String key);

    // The following method method works well with Spring's property placeholder configurer
    public Properties getProperties();
    
}
