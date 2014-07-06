package net.aconite.affina.espinterface.handler.message;

import java.util.ArrayList;
import java.util.Iterator;
import net.aconite.affina.espinterface.builder.IMessageBuilder;
import net.aconite.affina.espinterface.builder.MessageBuilderFactory;
import net.aconite.affina.espinterface.builder.MessageContent;
import net.aconite.affina.espinterface.constants.EspConstant;
import net.aconite.affina.espinterface.exceptions.EspMessageBuilderException;
import net.aconite.affina.espinterface.exceptions.EspMessageTransformationException;
import net.aconite.affina.espinterface.xmlmapping.sem.ScriptStatusResponse;
import net.aconite.affina.espinterface.xmlmapping.sem.ScriptStatusUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.jms.JmsHeaders;
import org.springframework.integration.support.MessageBuilder;

import java.util.List;
import net.aconite.affina.espinterface.constants.*;
import net.aconite.affina.espinterface.factory.*;
import net.aconite.affina.espinterface.helper.Result;
import net.aconite.affina.espinterface.scripting.generic.*;
import net.aconite.affina.espinterface.scripting.statusupdate.ScriptStatusUpdateDataHolder;
import net.aconite.affina.espinterface.scripting.statusupdate.ScriptStatusUpdateEvent;


public class ScriptStatusRequestHandler implements IEspMessageHandler
{
    private static final Logger logger = LoggerFactory.getLogger(ScriptStatusRequestHandler.class.getName());
    private List<ScriptEventListener<ScriptStatusUpdateDataHolder>> scriptListenerList = new ArrayList<ScriptEventListener<ScriptStatusUpdateDataHolder>>();

    @Transformer
    public Message transform(Message inMessage) throws EspMessageTransformationException
    {
        // At info level, the data recorded shall be limited to the message type
        //   and its identifier (tracking reference or service instance).
        // At debug level, the complete message shall be recorded.

        MessageHeaders inHeaders = inMessage.getHeaders();
        ScriptStatusUpdate inPayload = (ScriptStatusUpdate) inMessage.getPayload();

        logger.debug("process : Incoming Message header: ", inHeaders);
        logger.debug("process : Message payload: ", inPayload);
        
        processScriptUpdate(inPayload);              

        String inTrackId = inPayload.getTrackingReference();
        MessageContent messageContent = new MessageContent(EspConstant.STAGE_SCRIPT_REQUEST, inTrackId);

        MessageBuilderFactory msgBuilderFactory = new MessageBuilderFactory();
        IMessageBuilder builder = msgBuilderFactory.getBuilder(EspConstant.SCRIPT_STATUS_RESPONSE);
        ScriptStatusResponse response;
        try
        {
            response = (ScriptStatusResponse) builder.build(messageContent);
        }
        catch (EspMessageBuilderException ex)
        {
            throw new EspMessageTransformationException(ex.getMessage(), ex);
        }


        Message outMessage = generateScriptStatusResponseMessage(inHeaders, response);

        return outMessage;


    }

    @Splitter
    public List<Message> split(Message inMessage) throws EspMessageTransformationException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private Result processScriptUpdate(ScriptStatusUpdate su)
    {
        Iterator<ScriptEventListener<ScriptStatusUpdateDataHolder>> scriptEventListeners = scriptListenerList.iterator();
        Result scriptUpdateResult = Result.getInstance(false, null, "Script was not processed.");
        
        while(scriptEventListeners.hasNext())
        {
            ScriptEventListener<ScriptStatusUpdateDataHolder> scriptEventListener = scriptEventListeners.next();
            ScriptStatusUpdateDataHolder dh = new ScriptStatusUpdateDataHolder(su);
            ScriptEvent<ScriptStatusUpdateDataHolder> scriptEvent = new ScriptStatusUpdateEvent(dh, ScriptUpdateType.STATUSUPDATE);
            scriptUpdateResult = scriptEventListener.onScriptAlert(scriptEvent);
        }
        return scriptUpdateResult;
    }
    //==========================================================================

    private Message<ScriptStatusResponse> generateScriptStatusResponseMessage(MessageHeaders headers, ScriptStatusResponse sourceData)
    {

        logger.info("Created ScriptStatusResponse Message. Identfier: " + sourceData.getTrackingReference());

        return MessageBuilder.withPayload(sourceData)
                .copyHeaders(headers)
                .setHeader(JmsHeaders.TYPE, EspConstant.JMS_TEXT_MESSAGE)
                .setHeader(EspConstant.MQ_MESSAGE_TYPE, EspConstant.SCRIPT_STATUS_RESPONSE)
                .build();
    }

    /**
     * 
     * @return 
     */
    public List<ScriptEventListener<ScriptStatusUpdateDataHolder>> getScriptListenerList()
    {
        return scriptListenerList;
    }

    /**
     * 
     * @param scriptListenerList 
     */
    public void addScriptListener(ScriptEventListener<ScriptStatusUpdateDataHolder> scriptListenerList)
    {
        this.scriptListenerList.add(scriptListenerList);
    }    
}
