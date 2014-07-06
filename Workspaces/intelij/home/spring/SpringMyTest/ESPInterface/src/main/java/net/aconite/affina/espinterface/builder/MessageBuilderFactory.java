/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.builder;

import net.aconite.affina.espinterface.constants.EspConstant;

/**
 * @author wakkir.muzammil
 */
public class MessageBuilderFactory
{
    public IMessageBuilder getBuilder(String builderName)
    {
        if (builderName == null || builderName.trim().length() == 0)
        {
            return null;
        }

        if (EspConstant.CARD_SETUP_REQUEST.equalsIgnoreCase(builderName))
        {
            return new CardSetupRequestBuilder();
        }
        else if (EspConstant.STAGE_SCRIPT_REQUEST.equalsIgnoreCase(builderName))
        {
            return new StageScriptRequestBuilder();
        }
        else if (EspConstant.SCRIPT_STATUS_RESPONSE.equalsIgnoreCase(builderName))
        {
            return new ScriptStatusResponseBuilder();
        }

        return null;
    }

}
