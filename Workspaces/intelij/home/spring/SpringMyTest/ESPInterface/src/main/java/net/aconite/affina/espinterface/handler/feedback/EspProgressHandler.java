/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.handler.feedback;

import net.aconite.affina.espinterface.builder.MessageContent;
import net.aconite.affina.espinterface.constants.EspConstant;
import net.aconite.affina.espinterface.utils.ESPUtils;
import net.aconite.affina.espinterface.xmlmapping.sem.CardSetupResponse;
import net.aconite.affina.espinterface.xmlmapping.sem.StageScriptResponse;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wakkir.muzammil
 */
public class EspProgressHandler implements IEspFeedbackHandler
{
    private static final Logger logger = LoggerFactory.getLogger(EspProgressHandler.class.getName());

    private VelocityEngine velocityEngine;

    public EspProgressHandler(VelocityEngine velocityEngine)
    {
        this.velocityEngine = velocityEngine;
    }

    @ServiceActivator
    public Message process(Message inMessage)
    {
        MessageHeaders inHeaders = inMessage.getHeaders();
        Object inPayload = inMessage.getPayload();

        logger.debug("process : Incoming Message header: ", inHeaders);
        logger.debug("process : Message payload: ", inPayload);

        String msg = buildProgressPayload(inPayload);


        Message outMessage = generateProgressMessage(inHeaders, msg);

        return outMessage;
    }


    private Message<String> generateProgressMessage(MessageHeaders headers, String sourceData)
    {
        logger.info("Created Progress Message. Identfier: " + sourceData);

        return MessageBuilder.withPayload(sourceData)
                .copyHeaders(headers)
                .setHeader("jms_type", EspConstant.PROGRESS_MESSAGE)
                .build();
    }

    private String buildProgressPayload(Object inPayload)
    {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(EspConstant.VT_CURRENT_TIME, "");

        StringBuilder sb = new StringBuilder();
        sb.append(ESPUtils.getDefaultPayloadHeader());

        if (inPayload instanceof StageScriptResponse)
        {
            StageScriptResponse res = (StageScriptResponse) inPayload;
            props.put(EspConstant.VT_RESPONSE_TYPE, EspConstant.STAGE_SCRIPT_RESPONSE);
            props.put(EspConstant.VT_TRACKING_REFERENCE, res.getTrackingReference());
            props.put(EspConstant.VT_STATUS, res.getStatus());
            if (res.getError() != null)
            {
                props.put(EspConstant.VT_ERROR_DATA, res.getError().getData());
                props.put(EspConstant.VT_ERROR_DESCRIPTION, res.getError().getDescription());
                props.put(EspConstant.VT_ERROR_CODE, res.getError().getErrorCode());
            }
            sb.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/SemResponse.vm", "UTF-8", props));

        }
        else if (inPayload instanceof CardSetupResponse)
        {
            CardSetupResponse res = (CardSetupResponse) inPayload;
            props.put(EspConstant.VT_RESPONSE_TYPE, EspConstant.CARD_SETUP_RESPONSE);
            props.put(EspConstant.VT_TRACKING_REFERENCE, res.getTrackingReference());
            props.put(EspConstant.VT_STATUS, res.getStatus());
            if (res.getError() != null)
            {
                props.put(EspConstant.VT_ERROR_DATA, res.getError().getData());
                props.put(EspConstant.VT_ERROR_DESCRIPTION, res.getError().getDescription());
                props.put(EspConstant.VT_ERROR_CODE, res.getError().getErrorCode());
            }
            sb.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/SemResponse.vm", "UTF-8", props));

        }
        else
        {
            sb.append(inPayload.toString());
        }

        logger.debug(sb.toString());

        return sb.toString();
    }

}
