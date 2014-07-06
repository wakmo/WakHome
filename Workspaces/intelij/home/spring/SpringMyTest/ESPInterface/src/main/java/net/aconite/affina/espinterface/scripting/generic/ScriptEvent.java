/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.scripting.generic;

import net.aconite.affina.espinterface.constants.ScriptUpdateType;

/**
 *
 * @author thushara.pethiyagoda
 */
public abstract class ScriptEvent<T extends ScriptDataHolder>
{
    /***/
    private T scriptMessage;
    /***/
    private ScriptUpdateType scriptType;
    
    /**
     * 
     * @param scriptStatusUpdate 
     */
    public ScriptEvent(T scriptStatusUpdate, ScriptUpdateType sType)
    {
        scriptMessage = scriptStatusUpdate;
        scriptType = sType;
    }
    /**
     * 
     * @return 
     */
    public T getScriptMessage()
    {
        return scriptMessage;
    }
    
    public ScriptUpdateType getScriptType()
    {
        return scriptType;
    }
}
