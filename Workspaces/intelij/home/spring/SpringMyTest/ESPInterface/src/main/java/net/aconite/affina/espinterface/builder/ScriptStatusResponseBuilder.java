/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.builder;

import net.aconite.affina.espinterface.exceptions.EspMessageBuilderException;
import net.aconite.affina.espinterface.xmlmapping.sem.CardSetupRequest;
import net.aconite.affina.espinterface.xmlmapping.sem.ScriptStatusResponse;
import net.aconite.affina.espinterface.xmlmapping.sem.StageScriptRequest;
import net.aconite.affina.espinterface.xmlmapping.sem.StatusType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author wakkir.muzammil
 */
public class ScriptStatusResponseBuilder implements IMessageBuilder
{
    private static final Logger logger = LoggerFactory.getLogger(ScriptStatusResponseBuilder.class.getName());

    public Object build(MessageContent messageContent) throws EspMessageBuilderException
    {
        if (messageContent.getTrackingReference() == null || messageContent.getTrackingReference().trim().length() == 0)
        {
            throw new EspMessageBuilderException("SEM tracking reference value cannot be null or empty");
        }

        ScriptStatusResponse response = new ScriptStatusResponse();
        response.setTrackingReference(messageContent.getTrackingReference());
        response.setStatus(StatusType.STATUS_OK);

        logger.debug("Script Status Response generated for AE Tracking Reference : "+messageContent.getTrackingReference()+"\n" +response.toString());

        return response;
    }

    public List<CardSetupRequest> buildCSList(MessageContent messageContent) throws EspMessageBuilderException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<StageScriptRequest> buildSSList(MessageContent messageContent) throws EspMessageBuilderException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
