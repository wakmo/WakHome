/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.scripting.statusupdate;

import net.aconite.affina.espinterface.scripting.generic.ScriptDataHolder;
import net.aconite.affina.espinterface.xmlmapping.sem.ScriptStatusUpdate;

/**
 * This class acts as a xml schema object holder ScriptStatusUpdate request messages.
 * @author thushara.pethiyagoda
 */
public class ScriptStatusUpdateDataHolder extends ScriptDataHolder<ScriptStatusUpdate>
{
    /**
     * Constructs a ScriptStatusUpdateDataHolder with a ScriptStatusUpdate message.
     * @param ssupd 
     */
    public ScriptStatusUpdateDataHolder(ScriptStatusUpdate ssupd)
    {
        super(ssupd);
    }
    /**
     * Returns a ScriptStatusUpdate object.
     * @return ScriptStatusUpdate
     */
    @Override
    public ScriptStatusUpdate getScriptStatusUpdate()
    {
        return super.getScriptStatusUpdate();
    }
}
