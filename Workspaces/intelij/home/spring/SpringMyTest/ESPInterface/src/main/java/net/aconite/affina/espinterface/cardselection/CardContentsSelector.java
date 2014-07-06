/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.cardselection;

import java.util.*;
import net.aconite.affina.espinterface.model.*;

/**
 *
 * @author thushara.pethiyagoda
 */
public class CardContentsSelector implements CardContentsSelectable
{

    @Override
    public Iterator<ScriptableProduct> getScriptableProducts(boolean isScriptable)
    {
        return null;
    }

    @Override
    public Iterator<ScriptableApplication> getScriptableApplications(ScriptableProduct product)
    {
        return null;
    }

    @Override
    public Iterator<EmvParameter> getScriptableParameters(BusinessFunction busFunction)
    {
        return null;
    }

    @Override
    public Iterator<BusinessFunction> getScriptableBusinessFunctions(ScriptableApplication application)
    {
        return null;
    }

    @Override
    public void getRiskProfileGroup(ScriptableProduct product)
    {
        
    }  
}
