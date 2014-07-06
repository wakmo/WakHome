/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.scripting.statusupdate;

import net.aconite.affina.espinterface.helper.Result;
import net.aconite.affina.espinterface.scripting.generic.*;

/**
 *
 * @author thushara.pethiyagoda
 */
public class ScriptStatusUpdateEventHandler implements ScriptEventListener<ScriptStatusUpdateDataHolder>
{

    /** The script processor.*/
    private ScriptProcessable<ScriptStatusUpdateDataHolder> scriptProcessable;
    
    /**
     * 
     * @param sProcessable 
     */
    public ScriptStatusUpdateEventHandler(ScriptProcessable<ScriptStatusUpdateDataHolder> sProcessable)
    {
        scriptProcessable = sProcessable;
    }

    /**
     * 
     * @param scriptStatusUpdateEvent 
     */
    @Override
    public Result onScriptAlert(ScriptEvent<ScriptStatusUpdateDataHolder> scriptStatusUpdateEvent)
    {
        return scriptProcessable.processScript(scriptStatusUpdateEvent.getScriptMessage());
    }
}
