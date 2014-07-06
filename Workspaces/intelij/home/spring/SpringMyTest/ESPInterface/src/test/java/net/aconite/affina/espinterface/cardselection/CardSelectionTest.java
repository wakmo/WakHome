/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.cardselection;

import com.platform7.standardinfrastructure.database.AffinaTOPLinkSessionManager;
import junit.framework.TestCase;
import oracle.jdbc.pool.OracleDataSource;
import org.eclipse.persistence.sessions.UnitOfWork;

import java.sql.SQLException;

/**
 * @author thushara.pethiyagoda
 */
public class CardSelectionTest extends TestCase
{

    private final AffinaTOPLinkSessionManager sm = new AffinaTOPLinkSessionManager();
    private UnitOfWork uow;

    public CardSelectionTest()
    {
        super("CardSelectionTest");
    }

    /**
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception
    {
        try
        {
            doLocalSetup();
            uow = sm.getUnitOfWork();
            assertNotNull(uow);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    /**
     *
     */
    public void testSelectCard()
    {
        UnitOfWork uow = sm.getUnitOfWork();
        assertNotNull(uow);
    }

    /**
     * DB Connection.
     *
     * @throws SQLException
     */
    private void doLocalSetup() throws SQLException
    {
        OracleDataSource ds = new OracleDataSource();
        ds.setURL("jdbc:oracle:thin:@///AffinaDB");
        ds.setUser("pma");
        ds.setPassword("affina");
        sm.setDataSource(ds);
        sm.setPassword("affina");
        sm.setUser("pma");
        sm.setTopLinkMappingClassName("com.platform7.stdpma.databaseproject.toplinkproject");
        sm.initialize();
    }
}
