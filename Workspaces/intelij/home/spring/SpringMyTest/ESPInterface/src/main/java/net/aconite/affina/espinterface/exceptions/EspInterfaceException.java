/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.exceptions;

/**
 * @author wakkir.muzammil
 */
public class EspInterfaceException extends Exception
{
    private static final long serialVersionUID = 4233390499647614871L;


    public EspInterfaceException()
    {
        super();
    }

    public EspInterfaceException(String message)
    {
        super(message);
    }

    public EspInterfaceException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public EspInterfaceException(Throwable cause)
    {
        super(cause);
    }

}
