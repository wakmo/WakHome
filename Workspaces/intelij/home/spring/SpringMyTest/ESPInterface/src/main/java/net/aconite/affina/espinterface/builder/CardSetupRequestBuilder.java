/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.builder;

import com.platform7.pma.request.emvscriptrequest.ESPCardSetup;
import net.aconite.affina.espinterface.constants.EspConstant;
import net.aconite.affina.espinterface.exceptions.EspMessageBuilderException;
import net.aconite.affina.espinterface.xmlmapping.sem.AppType;
import net.aconite.affina.espinterface.xmlmapping.sem.CardSetupRequest;
import net.aconite.affina.espinterface.xmlmapping.sem.CardType;
import net.aconite.affina.espinterface.xmlmapping.sem.StageScriptRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import net.aconite.affina.espinterface.persistence.GenericPersistentDAO;
import net.aconite.affina.espinterface.persistence.Persistent;

/**
 * @author wakkir.muzammil
 */
public class CardSetupRequestBuilder implements IMessageBuilder
{
    private static final Logger logger = LoggerFactory.getLogger(CardSetupRequestBuilder.class.getName());


    public List<CardSetupRequest> buildCSList(MessageContent messageContent) throws EspMessageBuilderException
    {
        if (messageContent.getTrackingReference() == null || messageContent.getTrackingReference().trim().length() == 0)
        {
            throw new EspMessageBuilderException("AE tracking reference value cannot be null or empty");
        }


        Vector cardSetupList;
        try
        {
            Persistent cardSetupDAO = GenericPersistentDAO.getPersistent();
            cardSetupList = cardSetupDAO.getObjectByName(messageContent.getTrackingReference(), ESPCardSetup.class, EspConstant.DB_AE_TRACKING_ID);
        }
        catch (Exception ex)
        {
            throw new EspMessageBuilderException(ex.getMessage(), ex);
        }

        if (cardSetupList == null || cardSetupList.isEmpty())
        {
            throw new EspMessageBuilderException("There are no records found for the AE Tracking Reference '" + messageContent.getTrackingReference() + "'");
        }
        else
        {
            logger.debug(cardSetupList.size()+" CardSetupRequest will be generated for the AE tracking reference "+messageContent.getTrackingReference());
        }

        List<CardSetupRequest> requests = new ArrayList<CardSetupRequest>();

        for (Object aCardSetupList : cardSetupList)
        {
            ESPCardSetup cardSetup = (ESPCardSetup) aCardSetupList;

            CardSetupRequest request = new CardSetupRequest();
            request.setTrackingReference(cardSetup.getEspTrackingId());

            CardType cardType = new CardType();
            Timestamp expDate = cardSetup.getExpiryDate();
            cardType.setExpirationDate(String.valueOf(expDate.getTime()));
            cardType.setPAN(cardSetup.getPan());
            cardType.setPANSequence(cardSetup.getPanSequenceNumber());
            request.setCard(cardType);

            AppType appType = new AppType();
            appType.setApplicationType(cardSetup.getApplicationType());
            appType.setApplicationVersion(cardSetup.getApplicationVersion());
            request.setApplication(appType);

            requests.add(request);
        }

        return requests;
    }

    public Object build(MessageContent messageContent) throws EspMessageBuilderException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<StageScriptRequest> buildSSList(MessageContent messageContent) throws EspMessageBuilderException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
