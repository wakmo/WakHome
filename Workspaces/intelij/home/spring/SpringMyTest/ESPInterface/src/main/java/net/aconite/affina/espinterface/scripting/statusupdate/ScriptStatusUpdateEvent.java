/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.scripting.statusupdate;

import net.aconite.affina.espinterface.constants.*;
import net.aconite.affina.espinterface.scripting.generic.ScriptEvent;

/**
 * An event representing a ScriptStatusUpdate message.
 * @author thushara.pethiyagoda
 */
public class ScriptStatusUpdateEvent extends ScriptEvent<ScriptStatusUpdateDataHolder>
{

    /**
     * Constructs a ScriptStatusUpdateEvent.
     * @param scriptStatusUpdate
     * @param sType
     */
    public ScriptStatusUpdateEvent(ScriptStatusUpdateDataHolder scriptStatusUpdate, ScriptUpdateType sType)
    {
        super(scriptStatusUpdate, sType);  
        
    }   
}
