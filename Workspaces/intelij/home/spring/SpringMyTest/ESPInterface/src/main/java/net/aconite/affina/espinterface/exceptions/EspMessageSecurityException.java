/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.exceptions;

/**
 * @author wakkir.muzammil
 */
public class EspMessageSecurityException extends EspInterfaceException
{
    private static final long serialVersionUID = 4233390399647614871L;


    public EspMessageSecurityException()
    {
        super();
    }

    public EspMessageSecurityException(String message)
    {
        super(message);
    }

    public EspMessageSecurityException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public EspMessageSecurityException(Throwable cause)
    {
        super(cause);
    }
}
