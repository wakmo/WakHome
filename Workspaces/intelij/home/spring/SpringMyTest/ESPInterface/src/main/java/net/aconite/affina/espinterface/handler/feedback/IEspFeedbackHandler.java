package net.aconite.affina.espinterface.handler.feedback;

import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;


public interface IEspFeedbackHandler
{
    public Message process(Message<MessagingException> message);


}
