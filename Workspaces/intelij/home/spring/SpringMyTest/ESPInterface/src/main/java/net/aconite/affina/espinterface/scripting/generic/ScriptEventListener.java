/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.scripting.generic;

import net.aconite.affina.espinterface.helper.Result;

/**
 *
 * @author thushara.pethiyagoda
 */
public interface ScriptEventListener<T extends ScriptDataHolder>
{
    public Result onScriptAlert(final ScriptEvent<T> scriptStatusUpdateEvent);
}
