/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.exceptions;

/**
 *
 * @author thushara.pethiyagoda
 */
public class ScriptValidationException extends ScriptProcessingRuntimeException
{
    static final long serialVersionUID = -3387516993124229941L;
    public ScriptValidationException()
    {
        super();
    }
    
    public ScriptValidationException(String message)
    {
        super(message);
    }
            
}
