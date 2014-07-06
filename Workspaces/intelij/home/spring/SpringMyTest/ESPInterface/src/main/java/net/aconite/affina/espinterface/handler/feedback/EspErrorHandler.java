/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.handler.feedback;

import net.aconite.affina.espinterface.constants.EspConstant;
import net.aconite.affina.espinterface.utils.ESPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.*;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.MessageTransformationException;

/**
 * @author wakkir.muzammil
 */
public class EspErrorHandler implements IEspFeedbackHandler
{
    private static final Logger logger = LoggerFactory.getLogger(EspErrorHandler.class.getName());

    /**
     * @param inMessage
     */
    @ServiceActivator
    public Message process(Message<MessagingException> inMessage)
    {
        MessageHeaders inHeaders = inMessage.getHeaders();
        MessagingException inPayload = inMessage.getPayload();

        logger.debug("process : Incoming Message header: ", inHeaders);
        logger.debug("process : Message payload: ", inPayload);

        String errorMessage = buildErrorPayload(inPayload);

        Message outMessage = generateErrorMessage(inHeaders, errorMessage);

        return outMessage;
    }

    private Message<String> generateErrorMessage(MessageHeaders headers, String sourceData)
    {
        logger.info("Created Error Message. Identfier: " + sourceData);

        return MessageBuilder.withPayload(sourceData)
                .copyHeaders(headers)
                .setHeader("jms_type", EspConstant.ERROR_MESSAGE)
                .build();
    }

    private String buildErrorPayload(MessagingException inPayload)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(ESPUtils.getDefaultPayloadHeader());

        String errorMessage;
        if (inPayload instanceof MessageHandlingException)
        {
            errorMessage = inPayload.getCause().getMessage();
        }
        else if (inPayload instanceof MessageTransformationException)
        {
            errorMessage = inPayload.getCause().getMessage();
        }
        else if (inPayload instanceof MessageDeliveryException)
        {
            errorMessage = inPayload.toString();
        }
        else
        {
            errorMessage = inPayload.getCause().getMessage();
        }
        sb.append(errorMessage);
        //String request=inPayload.getCause().getMessage();//getFailedMessage().getPayload().toString();

        logger.error(sb.toString(), inPayload.getCause());

        return sb.toString();
    }

}
