/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.dao;

import com.platform7.pma.request.emvscriptrequest.ESPCardSetup;
import java.sql.SQLException;
import java.util.Vector;
import net.aconite.affina.espinterface.persistence.GenericPersistentDAO;
import net.aconite.affina.espinterface.persistence.Workable;

/**
 * @author wakkir.muzammil
 */
public class CardSetupDAO extends GenericPersistentDAO
{
    public CardSetupDAO()
    {
        super();
    }


    public void doTransactionalWork()
    {
        //ESPCardSetup esp = super.getRegisteredObject(ESPCardSetup.class);
        //esp.setAeTrackingId(null);
        //super.doTransactionAndCommit();

    }

    public Vector getCardSetupByName(String name, String value) throws SQLException
    {
        Vector cardSetupList = getObjectByName(value, ESPCardSetup.class, name);
        return cardSetupList;
    }

    public <T> void doTransactionalWorkAndCommit(T arg)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addTransactionalWorker(Workable workable)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /*
    public Vector getCardSetupDetails(UnitOfWork uow,String status, String trackingId) 
    {
        Expression e1,e2;
        Vector cardSetupDtls = new Vector(16);

        ExpressionBuilder builder = new ExpressionBuilder();
        e1    = builder.get("status").equal(status);
        e2    = builder.get("aeTrackingId").equal(trackingId);
        cardSetupDtls = QueryUtils.readAllObjects(uow ,ESPCardSetup.class, e1.and(e2));
        if(cardSetupDtls.size() < 1)
               log.debug(" There is no new card setup record available to send to ESP");
        else
               log.debug(" No. of card setup requests availble to send to ESP :"+cardSetupDtls.size());

       return cardSetupDtls;
     }
     * */


}
