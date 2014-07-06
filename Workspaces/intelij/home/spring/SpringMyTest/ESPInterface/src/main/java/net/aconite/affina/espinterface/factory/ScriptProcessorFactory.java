/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.factory;

import net.aconite.affina.espinterface.scripting.generic.*;
import net.aconite.affina.espinterface.scripting.statusupdate.ScriptStatusUpdateDataHolder;
import net.aconite.affina.espinterface.scripting.statusupdate.ScriptStatusUpdateEventHandler;
import net.aconite.affina.espinterface.scripting.statusupdate.ScriptUpdateProcessor;

/**
 *
 * @author thushara.pethiyagoda
 */
public class ScriptProcessorFactory
{
    public static ScriptProcessable<ScriptStatusUpdateDataHolder> createScriptProcessable()
    {
        return ScriptUpdateProcessor.getProcessable();
    }
    
    public static ScriptEventListener<ScriptStatusUpdateDataHolder> createScriptEventListener(ScriptProcessable<ScriptStatusUpdateDataHolder> scriptProcessor)
    {
        return new ScriptStatusUpdateEventHandler(scriptProcessor);
    }
}
