/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.exceptions;

/**
 * @author wakkir.muzammil
 */
public class EspMessageBuilderException extends EspInterfaceException
{
    private static final long serialVersionUID = 4233390499747614871L;

    public EspMessageBuilderException()
    {
        super();
    }

    public EspMessageBuilderException(String message)
    {
        super(message);
    }

    public EspMessageBuilderException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public EspMessageBuilderException(Throwable cause)
    {
        super(cause);
    }

}
