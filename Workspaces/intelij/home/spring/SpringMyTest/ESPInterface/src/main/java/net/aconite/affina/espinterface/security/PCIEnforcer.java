/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.security;

import com.platform7.standardinfrastructure.utilities.encryption.EncryptionUtils;
import com.platform7.standardinfrastructure.utilities.encryption.exception.EncryptionException;
import net.aconite.affina.espinterface.constants.EspConstant;
import net.aconite.affina.espinterface.exceptions.EspMessageSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.jms.JmsHeaders;
import org.springframework.integration.support.MessageBuilder;

import java.util.Arrays;

/**
 * @author wakkir.muzammil
 */
public class PCIEnforcer implements IPCIEnforcer
{
    private static final Logger logger = LoggerFactory.getLogger(PCIEnforcer.class.getName());

    private final Boolean doDecrypt;
    private final String decryptKeyAlias;
    private final Boolean doEncrypt;
    private final String encryptKeyAlias;
    private final String encryptionEncording;

    public PCIEnforcer(final Boolean doEncrypt, final String encryptKeyAlias, final String encryptionEncording)
    {
        this.doDecrypt = true;
        this.decryptKeyAlias = "AES";
        this.doEncrypt = doEncrypt;
        this.encryptKeyAlias = encryptKeyAlias;
        this.encryptionEncording = encryptionEncording;
    }

    public PCIEnforcer(final Boolean doDecrypt, final String decryptKeyAlias, final Boolean doEncrypt, final String encryptKeyAlias, final String encryptionEncording)
    {
        this.doDecrypt = doDecrypt;
        this.decryptKeyAlias = decryptKeyAlias;
        this.doEncrypt = doEncrypt;
        this.encryptKeyAlias = encryptKeyAlias;
        this.encryptionEncording = encryptionEncording;
    }

    @ServiceActivator
    public Message decryptMessage(Message inMessage) throws EspMessageSecurityException
    {
        // At info level, the data recorded shall be limited to the message type
        //   and its identifier (tracking reference or service instance).
        // At debug level, the complete message shall be recorded.

        MessageHeaders inHeaders = inMessage.getHeaders();
        Object inPayload = inMessage.getPayload();

        logger.debug("DecryptMessage : Incoming Message header: ", inHeaders);
        logger.debug("DecryptMessage : Message payload: ", inPayload);
        logger.debug(toString());

        Message outMessage;

        if (inPayload instanceof byte[])
        {
            try
            {
                byte[] bytesMessage = (byte[]) inPayload;
                String messagetext;
                //if("true".equalsIgnoreCase(doDecrypt.trim()))   
                if (doDecrypt)
                {
                    //decrypt the bytearray and assign to message text
                    messagetext = new String(EncryptionUtils.decrypt(bytesMessage));
                }
                else
                {
                    messagetext = new String(bytesMessage);
                }

                outMessage = generateDecryptedMessage(inHeaders, messagetext);
            }
            catch (EncryptionException ex)
            {
                throw new EspMessageSecurityException(ex.getMessage(), ex);
            }
        }
        else if (inPayload instanceof String)
        {
            outMessage = inMessage;
        }
        else
        {
            throw new EspMessageSecurityException("Invalid JMS Message received!");
        }
        return outMessage;
    }

    @ServiceActivator
    public Message encryptMessage(Message inMessage) throws EspMessageSecurityException
    {
        // At info level, the data recorded shall be limited to the message type
        //   and its identifier (tracking reference or service instance).
        // At debug level, the complete message shall be recorded.

        MessageHeaders inHeaders = inMessage.getHeaders();
        Object inPayload = inMessage.getPayload();

        logger.debug("EncryptMessage : Incoming Message header: ", inHeaders);
        logger.debug("EncryptMessage : Message payload: ", inPayload);
        logger.debug(toString());

        Message outMessage;

        if (doEncrypt)
        {
            try
            {
                byte[] bt = EncryptionUtils.encrypt(((String) inPayload).getBytes(), encryptKeyAlias, encryptionEncording);
                outMessage = generateEncryptedMessage(inHeaders, bt);
            }
            catch (EncryptionException ex)
            {
                throw new EspMessageSecurityException(ex.getMessage(), ex);
            }
        }
        else
        {
            outMessage = inMessage;
        }
        return outMessage;
    }


    private Message<String> generateDecryptedMessage(MessageHeaders headers, String sourceData)
    {
        logger.info("DecryptedMessage body : " + sourceData);

        return MessageBuilder.withPayload(sourceData)
                .copyHeaders(headers)
                .setHeader(JmsHeaders.TYPE, EspConstant.JMS_TEXT_MESSAGE)
                .setHeader(EspConstant.MQ_MESSAGE_TYPE, EspConstant.SC_DECRYPTED_MESSAGE)
                .setCorrelationId(sourceData.hashCode())
                .build();
    }

    private Message<byte[]> generateEncryptedMessage(MessageHeaders headers, byte[] sourceData)
    {
        logger.info("EncryptedMessage body : " + Arrays.toString(sourceData));

        // Set the correlation id to original fileName.
        return MessageBuilder.withPayload(sourceData)
                .copyHeaders(headers)
                .setHeader(JmsHeaders.TYPE, EspConstant.JMS_BYTES_MESSAGE)
                .setHeader(EspConstant.MQ_MESSAGE_TYPE, EspConstant.SC_ENCRYPTED_MESSAGE)
                .setCorrelationId(Arrays.hashCode(sourceData))
                .build();
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\nDoDecrypt : " + doDecrypt.booleanValue());
        sb.append("\tDecryptKeyAlias : " + decryptKeyAlias);
        sb.append("\nDoEncrypt : " + doEncrypt.booleanValue());
        sb.append("\tEncryptKeyAlias : " + encryptKeyAlias);
        sb.append("\nEncording : " + encryptionEncording);
        return sb.toString();
    }

}

