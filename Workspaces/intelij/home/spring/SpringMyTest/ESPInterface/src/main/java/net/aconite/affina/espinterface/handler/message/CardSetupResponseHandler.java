package net.aconite.affina.espinterface.handler.message;

import net.aconite.affina.espinterface.constants.EspConstant;
import net.aconite.affina.espinterface.exceptions.EspMessageTransformationException;
import net.aconite.affina.espinterface.xmlmapping.sem.CardSetupResponse;
import net.aconite.affina.espinterface.xmlmapping.sem.StatusType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.jms.JmsHeaders;
import org.springframework.integration.support.MessageBuilder;

import java.util.List;


public class CardSetupResponseHandler implements IEspMessageHandler
{
    private static final Logger logger = LoggerFactory.getLogger(CardSetupResponseHandler.class.getName());


    @Transformer
    public Message transform(Message inMessage)
    {
        // At info level, the data recorded shall be limited to the message type
        //   and its identifier (tracking reference or service instance).
        // At debug level, the complete message shall be recorded.

        MessageHeaders inHeaders = inMessage.getHeaders();
        CardSetupResponse inPayload = (CardSetupResponse) inMessage.getPayload();

        logger.debug("process : Incoming Message header: ", inHeaders);
        logger.debug("process : Message payload: ", inPayload);

        String trackId = inPayload.getTrackingReference();
        String Status = inPayload.getStatus().value();

        CardSetupResponse response = new CardSetupResponse();
        response.setTrackingReference(trackId);

        //ToDo - need to inform affina and handle trackId status & data

        if (StatusType.STATUS_OK.value().equalsIgnoreCase(Status))
        {
            response.setStatus(StatusType.STATUS_OK);
        }
        else
        {
            response.setStatus(StatusType.ERROR);
            response.setError(inPayload.getError());
        }

        Message outMessage = generateCardSetupResponseMessage(inHeaders, response);

        return outMessage;


    }

    @Splitter
    public List<Message> split(Message inMessage) throws EspMessageTransformationException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //==========================================================================

    private Message<CardSetupResponse> generateCardSetupResponseMessage(MessageHeaders headers, CardSetupResponse sourceData)
    {

        logger.info("Created CardSetupResponse Message. Identfier: " + sourceData.getTrackingReference());

        return MessageBuilder.withPayload(sourceData)
                .copyHeaders(headers)
                .setHeader(JmsHeaders.TYPE, EspConstant.JMS_TEXT_MESSAGE)
                .setHeader(EspConstant.MQ_MESSAGE_TYPE, EspConstant.CARD_SETUP_RESPONSE)
                .build();
    }
}
