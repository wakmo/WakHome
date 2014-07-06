/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.security;

import net.aconite.affina.espinterface.exceptions.EspMessageSecurityException;
import org.springframework.integration.Message;

/**
 * @author wakkir.muzammil
 */
public interface IPCIEnforcer
{
    public Message decryptMessage(Message message) throws EspMessageSecurityException;

    public Message encryptMessage(Message message) throws EspMessageSecurityException;
}
