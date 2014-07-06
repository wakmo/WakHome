/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.cardselection;

import com.platform7.pma.card.SoftCard;
import net.aconite.affina.espinterface.model.ScriptableCard;

/**
 *
 * @author thushara.pethiyagoda
 */
public interface CardGenerator
{
    public ScriptableCard generateCard(final String pan, final String psn, final long expirationDate);
    public SoftCard generateSoftCard(final String pan, final String psn, final long expirationDate);
}
