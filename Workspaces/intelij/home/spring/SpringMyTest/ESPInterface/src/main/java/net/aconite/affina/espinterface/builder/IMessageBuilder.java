/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.builder;

import net.aconite.affina.espinterface.exceptions.EspMessageBuilderException;
import net.aconite.affina.espinterface.xmlmapping.sem.CardSetupRequest;
import net.aconite.affina.espinterface.xmlmapping.sem.StageScriptRequest;

import java.util.List;

/**
 * @author wakkir.muzammil
 */
public interface IMessageBuilder
{
    Object build(MessageContent messageContent) throws EspMessageBuilderException;

    List<CardSetupRequest> buildCSList(MessageContent messageContent) throws EspMessageBuilderException;

    List<StageScriptRequest> buildSSList(MessageContent messageContent) throws EspMessageBuilderException;

}
