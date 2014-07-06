/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.model;

import java.util.*;

/**
 *
 * @author thushara.pethiyagoda
 */
public class ScriptableProduct
{
    Iterator<ScriptableApplication> scriptableApps;
    /** Parent product id.*/
    private long parentId;
    /** To indicate whether this product qualifies for sending card update scripts.*/
    private boolean isScriptable;
    /** Product name. */
    private String productName;
    /** Version of product. */
    private String version;
    /** Scope of product. */
    private String scope;
    /** Area that this prodcut belongs to. */
    private String area;
    
}
