/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.model;

import com.platform7.pma.card.SoftCard;
import java.util.Iterator;

/**
 *
 * @author thushara.pethiyagoda
 */
public class ScriptableCard
{
    /** Application that this card can contain. */
    Iterator<ScriptableProduct> scriptableProducts;
    /** Account linked to this card. */
    private long accountId;
    /** PAN of this card. */
    private String plasticNumber;
    /** Name on the card. */
    private String textualName;
    /** Current status of the card.*/
    private int status;
    /** Pan sequence number. */
    private String psn;
    /** Card identification number.*/
    private String cardId;
    /** Card expiration date.*/
    private long expirationDate;
    /***/
    private boolean isValid = false;
    
    private SoftCard softCard;
    
    
    /**
     * 
     * @param scriptableApplications
     * @param accountId
     * @param plasticNumber
     * @param textualName
     * @param status
     * @param psn
     * @param cardId
     * @param expirationDate 
     */
    public ScriptableCard(long accountId, String plasticNumber,
                          String textualName, int status, String psn, 
                          String cardId, long expirationDate,
                          SoftCard softCard)
    {        
        this.accountId = accountId;
        this.plasticNumber = plasticNumber;
        this.textualName = textualName;
        this.status = status;
        this.psn = psn;
        this.cardId = cardId;
        this.expirationDate = expirationDate;
        this.isValid = true;
        this.softCard = softCard;
    }

    public Iterator<ScriptableProduct> getScriptableProducts()
    {
        return scriptableProducts;
    }

    public long getAccountId()
    {
        return accountId;
    }

    public String getPlasticNumber()
    {
        return plasticNumber;
    }

    public String getTextualName()
    {
        return textualName;
    }

    public int getStatus()
    {
        return status;
    }

    public String getPsn()
    {
        return psn;
    }

    public String getCardId()
    {
        return cardId;
    }

    public long getExpirationDate()
    {
        return expirationDate;
    }    

    public SoftCard getSoftCard()
    {
        return softCard;
    }
    
    /**
     * To indicate whether this instance is valid.
     * If this instance is created by invoking the no args constructor then this would constitute 
     * to an invalid instance.
     * @return 
     */
    public boolean isIsValid()
    {
        return isValid;
    }
    
}
