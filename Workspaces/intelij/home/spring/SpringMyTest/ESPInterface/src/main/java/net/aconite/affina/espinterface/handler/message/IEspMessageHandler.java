package net.aconite.affina.espinterface.handler.message;

import net.aconite.affina.espinterface.exceptions.EspMessageTransformationException;
import org.springframework.integration.Message;

import java.util.List;


public interface IEspMessageHandler
{
    public Message transform(Message message) throws EspMessageTransformationException;

    public List<Message> split(Message message) throws EspMessageTransformationException;

}
