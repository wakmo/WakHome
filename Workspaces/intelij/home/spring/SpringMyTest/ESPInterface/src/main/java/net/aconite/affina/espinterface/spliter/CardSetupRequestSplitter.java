package net.aconite.affina.espinterface.spliter;

import net.aconite.affina.espinterface.xmlmapping.sem.CardSetupRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.annotation.Splitter;

import java.util.ArrayList;
import java.util.List;


public class CardSetupRequestSplitter
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CardSetupRequestSplitter.class.getName());


    @Splitter
    public List<Message> splitMessage(Message message)
    {
        // At info level, the data recorded shall be limited to the message type
        //   and its identifier (tracking reference or service instance).
        // At debug level, the complete message shall be recorded.

        MessageHeaders headers = message.getHeaders();
        Object payload = message.getPayload();

        LOGGER.debug("Incoming Message header: ", headers);
        LOGGER.debug("Message payload: ", payload);

        ((CardSetupRequest) message.getPayload()).setTrackingReference("Helllloo");
        List<Message> outMessages = new ArrayList<Message>();
        outMessages.add(message);
        return outMessages;


    }
}
