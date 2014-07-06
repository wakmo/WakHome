/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.factory;

import com.platform7.standardinfrastructure.appconfig.*;
import com.platform7.standardinfrastructure.database.AffinaTOPLinkSessionManager;
import oracle.jdbc.pool.OracleDataSource;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author thushara.pethiyagoda
 */
public class PersistentContextFactory
{
    /**
     * @param classpthConfigFilename
     * @return
     */
    public static ConfigurableApplicationContext getConfigurableApplicationContext(final String classpthConfigFilename)
    {
        return new ClassPathXmlApplicationContext(classpthConfigFilename);
    }

    /**
     * @param appContext
     * @param beanName
     * @return
     */
    public static AffinaTOPLinkSessionManager getSessionManager(String beanName)
    {
        return (AffinaTOPLinkSessionManager) AppConfig.getBean(beanName);
    }

    /**
     * @param password
     * @return
     * @throws SQLException
     */
    public static DataSource getDatasource(final String password) throws SQLException
    {
        return getOracleDataSourcePMA(password);
    }

    /**
     * @return
     * @throws SQLException
     */
    private static DataSource getOracleDataSourcePMA(final String password) throws SQLException
    {
        OracleDataSource ds = new OracleDataSource();
        ds.setURL("jdbc:oracle:thin:@///AffinaDB");
        ds.setUser("pma");
        ds.setPassword(password);
        return ds;

    }

    /**
     * @param ds
     * @return
     */
    private AffinaTOPLinkSessionManager getSessionManagerPMA(DataSource ds)
    {
        AffinaTOPLinkSessionManager sm = new AffinaTOPLinkSessionManager();
        sm.setDataSource(ds);
        //sm.setPassword(((OracleDataSource)ds).getUser());
        sm.setUser(((OracleDataSource) ds).getUser());
        sm.setTopLinkMappingClassName("com.platform7.stdpma.databaseproject.toplinkproject");
        sm.initialize();

        return sm;
    }
}
