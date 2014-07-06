/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.config;

import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author wakkir.muzammil
 */
public class ParameterConfigurer implements IConfigurationProperties
{
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(ParameterConfigurer.class.getName());


    /** Properties */
    private final Properties aeConfigProperties = new Properties();

    /**
     * Constructor
     * @param log : Logger
     */
    public ParameterConfigurer()
    {
        aeConfigProperties.put("from.affina.to.esp.pci.doDecrypt", true);
        aeConfigProperties.put("from.affina.to.esp.pci.decryptKeyAlias", "WAKKIR");  
        aeConfigProperties.put("database.showSQL",false);
        aeConfigProperties.put("esp.xml.encoding","UTF-8");
        aeConfigProperties.put("esp.encryiption.encoding","UTF-8");
        aeConfigProperties.put("from.affina.to.esp.queue.name","AFF.ESPINTERFACE.ESP.ICBC");
        aeConfigProperties.put("from.esp.to.affina.queue.name","ESP.ESPINTERFACE.AFF.ICBC");
        aeConfigProperties.put("from.sem.to.esp.queue.name","SEM.ESPINTERFACE.ESP.ICBC");
        aeConfigProperties.put("from.esp.to.sem.queue.name","ESP.ESPINTERFACE.SEM.ICBC");
        aeConfigProperties.put("from.custserv.to.esp.queue.name","CS.ESPINTERFACE.ESP.ICBC");
        aeConfigProperties.put("from.esp.to.custserv.queue.name","ESP.ESPINTERFACE.CS.ICBC");
        aeConfigProperties.put("from.affina.to.esp.pci.doDecrypt",false);
        aeConfigProperties.put("from.affina.to.esp.pci.decryptKeyAlias","AES");
        aeConfigProperties.put("from.esp.to.affina.pci.doEncrypt",false);
        aeConfigProperties.put("from.esp.to.affina.pci.encryptKeyAlias","AES");
        aeConfigProperties.put("from.sem.to.esp.pci.doDecrypt",true);
        aeConfigProperties.put("from.sem.to.esp.pci.decryptKeyAlias","AES");
        aeConfigProperties.put("from.esp.to.sem.pci.doEncrypt",true);
        aeConfigProperties.put("from.esp.to.sem.pci.encryptKeyAlias","AES");
        aeConfigProperties.put("from.custserv.to.esp.pci.doDecrypt",true);
        aeConfigProperties.put("from.custserv.to.esp.decryptKeyAlias","AES");
        aeConfigProperties.put("from.esp.to.custserv.pci.doEncrypt",true);
        aeConfigProperties.put("from.esp.to.custserv.pci.encryptKeyAlias","AES");
    }

    /**
     * Load the propeties file
     */
    public void start()
    {
        
    }
    
    @Override
    public String getProperty(String key) 
    {
        return aeConfigProperties.getProperty(key);
    }

    @Override
    public Properties getProperties() 
    {
        return aeConfigProperties;
    }
    
}
