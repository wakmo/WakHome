/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.cardselection;

import com.platform7.pma.card.*;
import java.util.Vector;
import net.aconite.affina.espinterface.helper.*;
import net.aconite.affina.espinterface.model.ScriptableCard;
import net.aconite.affina.espinterface.persistence.GenericPersistentDAO;
import net.aconite.affina.espinterface.persistence.Persistent;
import org.eclipse.persistence.expressions.Expression;
import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.queries.ReadAllQuery;
/**
 *
 * @author thushara.pethiyagoda
 */
public class SelectableCardGenerator implements CardGenerator
{
    /**
     * R.5-1
     * Fetches an Active SoftCard matching the input parameters and returns a cut down version called ScriptableCard
     * specific to Scripting operations.
     * Returns null if one cannot be found.
     * @param pan Personal identification number
     * @param psn pan sequence number
     * @param expirationDate Card valid to date.
     * @return ScriptableCard as explained above
     */
    @Override
    public ScriptableCard generateCard(final String pan, final String psn, final long expirationDate)
    {        
        SoftCard softCard = generateSoftCard(pan, psn, expirationDate);
        ScriptableCard card = null;
        if(softCard != null)
        {
            SoftCard sc = softCard;
            long accountId = sc.get_t1Account().getPrimaryKey().longValue();
            long expnDate = sc.getValidTo().getTime();
            card = new ScriptableCard(accountId, sc.getPlasticNumber(), sc.getTextualName(), sc.getCardStatus(),
                                                sc.getPANSequenceNumber(), sc.getCardId(), expnDate, sc);
        }        
        return card;
    }  
    /**
     * Returns a SoftCard matching PAN, PSN and Expiration Date.
     * If no match is found then returns null.
     * @param pan PAN
     * @param psn Pan sequence number
     * @param expirationDate Card Expiration Date
     * @return SoftCard as explained above
     */
    @Override
    public SoftCard generateSoftCard(final String pan, final String psn, final long expirationDate)
    {
        Persistent persistentDAO = GenericPersistentDAO.getPersistent();
        ReadAllQuery rq = new ReadAllQuery();
        rq.setReferenceClass(SoftCard.class);
        ExpressionBuilder builder = new ExpressionBuilder();
        Expression expPan = builder.get("plasticNumber").equal(pan);
        Expression expPsn = builder.get("panSequenceNumber").equal(psn);
        Expression expExpDate = builder.get("validTo").equal(DateHelper.getTimestampUSFormat(expirationDate));
        Expression  cardStatus = builder.get("cardStatus").equal(CardStatus.ACTIVE);
        Expression selection = expPan.and(expPsn).and(expExpDate).and(cardStatus);
        rq.setSelectionCriteria(selection);
        
        Vector softCards = persistentDAO.executeQuery(rq);        
        SoftCard sc = null;
        if(!softCards.isEmpty())
        {
            sc = (SoftCard) softCards.get(0);            
        }        
        return sc;
    }  
}
