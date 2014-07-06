/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.exceptions;

import org.springframework.integration.transformer.MessageTransformationException;

/**
 * @author wakkir.muzammil
 */
public class EspMessageTransformationException extends MessageTransformationException
{
    private static final long serialVersionUID = 4233390399647614871L;


    public EspMessageTransformationException(String message)
    {
        super(message);
    }

    public EspMessageTransformationException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
