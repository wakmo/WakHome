/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.cardselection;

import java.util.Iterator;
import net.aconite.affina.espinterface.model.BusinessFunction;
import net.aconite.affina.espinterface.model.EmvParameter;
import net.aconite.affina.espinterface.model.ScriptableApplication;
import net.aconite.affina.espinterface.model.ScriptableProduct;

/**
 * Interface defining methods related to selecting card/product/application related data.
 * @author thushara.pethiyagoda
 */
public interface CardContentsSelectable
{
    /**
     * Returns a list of products that references an application profile that is tagged for SEM.
     * @return 
     */
    public Iterator<ScriptableProduct> getScriptableProducts(boolean isScriptable);
    /**
     * Returns a list of Business Applications that references an application profile that is tagged for SEM.
     * @return 
     */
    public Iterator<ScriptableApplication> getScriptableApplications(ScriptableProduct product);
    /**
     * Returns a list of parameters that canbe changes
     * @return 
     */
    public Iterator<EmvParameter> getScriptableParameters(BusinessFunction busFunction);
    /**
     * Returns a set of Business Functions defined per Scriptable Application.
     * @return 
     */
    public Iterator<BusinessFunction> getScriptableBusinessFunctions(ScriptableApplication application);
    /**
     * 
     * @param product 
     */
    public void getRiskProfileGroup(ScriptableProduct product);
}
