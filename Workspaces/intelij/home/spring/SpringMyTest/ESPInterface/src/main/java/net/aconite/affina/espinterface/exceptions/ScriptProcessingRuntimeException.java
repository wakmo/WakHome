/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.exceptions;

/**
 *
 * @author thushara.pethiyagoda
 */
public class ScriptProcessingRuntimeException extends RuntimeException
{
    static final long serialVersionUID = -3387516993124229943L;
    public ScriptProcessingRuntimeException()
    {
        super();
    }
    
    public ScriptProcessingRuntimeException(String message)
    {
        super(message);
    }
            
}
