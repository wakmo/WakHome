/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.factory;

import net.aconite.affina.espinterface.cardselection.CardContentsSelectable;
import net.aconite.affina.espinterface.cardselection.CardContentsSelector;

/**
 * @author thushara.pethiyagoda
 */
public class CardSelectorFactory
{
    public static CardContentsSelectable getCardSelectable()
    {
        return new CardContentsSelector();
    }
}
