/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.builder;

import net.aconite.affina.espinterface.xmlmapping.sem.StatusType;

import java.io.Serializable;

/**
 * @author wakkir.muzammil
 */
public class MessageContent implements Serializable
{
    private static final long serialVersionUID = 7526472295622776147L;  // unique id

    private String type;
    private String trackingReference;
    private StatusType status;

    private String errorData;
    private String errorDescription;
    private String errorCode;

    public MessageContent(String type, String trackingReference)
    {
        this.type = type;
        this.trackingReference = trackingReference;
    }


    public MessageContent(String type, String trackingReference, StatusType status)
    {
        this.type = type;
        this.trackingReference = trackingReference;
        this.status = status;
    }

    public MessageContent(String type, String trackingReference, StatusType status, String errorData, String errorDescription, String errorCode)
    {
        this.type = type;
        this.trackingReference = trackingReference;
        this.status = status;
        this.errorData = errorData;
        this.errorDescription = errorDescription;
        this.errorCode = errorCode;
    }


    @Override
    public String toString()
    {
        return "MessageContent{" + "type=" + type + ", trackingReference=" + trackingReference + ", status=" + status + ", errorData=" + errorData + ", errorDescription=" + errorDescription + ", errorCode=" + errorCode + '}';
    }
    
    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getTrackingReference()
    {
        return trackingReference;
    }

    public void setTrackingReference(String trackingReference)
    {
        this.trackingReference = trackingReference;
    }

    public StatusType getStatus()
    {
        return status;
    }

    public void setStatus(StatusType status)
    {
        this.status = status;
    }

    public String getErrorData()
    {
        return errorData;
    }

    public void setErrorData(String errorData)
    {
        this.errorData = errorData;
    }

    public String getErrorDescription()
    {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription)
    {
        this.errorDescription = errorDescription;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }


}
