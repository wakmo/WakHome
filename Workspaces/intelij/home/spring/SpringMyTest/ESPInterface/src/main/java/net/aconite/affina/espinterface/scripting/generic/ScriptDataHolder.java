/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.scripting.generic;

/**
 * This class acts as a xml schema object holder ScriptStatusUpdate request messages.
 * @author thushara.pethiyagoda
 */
public abstract class ScriptDataHolder<T>
{
    /** The object mapped to the ScriptStatusUpdate xml schema. */
    private T ssUpdate;
    
    /**
     * Constructs a ScriptStatusUpdateDataHolder with a ScriptStatusUpdate message.
     * @param ssupd 
     */
    public ScriptDataHolder(T ssupd)
    {
        ssUpdate = ssupd;
    }
    /**
     * Returns a ScriptStatusUpdate object.
     * @return ScriptStatusUpdate
     */
    public T getScriptStatusUpdate()
    {
        return ssUpdate;
    }
}
