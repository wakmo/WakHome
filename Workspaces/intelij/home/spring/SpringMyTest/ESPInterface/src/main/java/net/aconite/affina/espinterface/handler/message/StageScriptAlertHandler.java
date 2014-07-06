package net.aconite.affina.espinterface.handler.message;

import net.aconite.affina.espinterface.builder.IMessageBuilder;
import net.aconite.affina.espinterface.builder.MessageBuilderFactory;
import net.aconite.affina.espinterface.builder.MessageContent;
import net.aconite.affina.espinterface.constants.EspConstant;
import net.aconite.affina.espinterface.exceptions.EspMessageBuilderException;
import net.aconite.affina.espinterface.exceptions.EspMessageTransformationException;
import net.aconite.affina.espinterface.xmlmapping.affina.StageScriptAlert;
import net.aconite.affina.espinterface.xmlmapping.sem.StageScriptRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.jms.JmsHeaders;
import org.springframework.integration.support.MessageBuilder;

import java.util.ArrayList;
import java.util.List;


public class StageScriptAlertHandler implements IEspMessageHandler
{
    private static final Logger logger = LoggerFactory.getLogger(StageScriptAlertHandler.class.getName());

    @Splitter
    public List<Message> split(Message inMessage) throws EspMessageTransformationException
    {
        // At info level, the data recorded shall be limited to the message type
        //   and its identifier (tracking reference or service instance).
        // At debug level, the complete message shall be recorded.

        MessageHeaders inHeaders = inMessage.getHeaders();
        StageScriptAlert inPayload = (StageScriptAlert) inMessage.getPayload();

        logger.debug("process : Incoming Message header: ", inHeaders);
        logger.debug("process : Message payload: ", inPayload);

        String inTrackId = inPayload.getTrackingReference();
        MessageContent messageContent = new MessageContent(EspConstant.STAGE_SCRIPT_ALERT, inTrackId);

        MessageBuilderFactory msgBuilderFactory = new MessageBuilderFactory();
        IMessageBuilder builder = msgBuilderFactory.getBuilder(EspConstant.STAGE_SCRIPT_REQUEST);

        List<StageScriptRequest> requests = null;
        try
        {
            requests = builder.buildSSList(messageContent);
        }
        catch (EspMessageBuilderException ex)
        {
            throw new EspMessageTransformationException(ex.getMessage(), ex);
        }

        List<Message> outMessages = new ArrayList<Message>();
        for (StageScriptRequest request : requests)
        {
            Message outMessage = generateStageScriptRequestMessage(inHeaders, request);
            outMessages.add(outMessage);
        }

        return outMessages;


    }

    @Transformer
    public Message transform(Message inMessage) throws EspMessageTransformationException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //==========================================================================

    private Message<StageScriptRequest> generateStageScriptRequestMessage(MessageHeaders headers, StageScriptRequest sourceData)
    {
        logger.info("Created StageScriptRequest Message. Identfier: " + sourceData.getTrackingReference());

        return MessageBuilder.withPayload(sourceData)
                .copyHeaders(headers)
                .setHeader(JmsHeaders.TYPE, EspConstant.JMS_TEXT_MESSAGE)
                .setHeader(EspConstant.MQ_MESSAGE_TYPE, EspConstant.STAGE_SCRIPT_REQUEST)
                .build();
    }
}
