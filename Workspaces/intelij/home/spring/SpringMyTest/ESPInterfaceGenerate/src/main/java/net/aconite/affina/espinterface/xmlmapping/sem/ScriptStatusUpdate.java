
package net.aconite.affina.espinterface.xmlmapping.sem;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TrackingReference" type="{}TrackingReferenceType"/>
 *         &lt;element name="Card" type="{}CardType"/>
 *         &lt;element name="BusinessFunction">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="FunctionName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ScriptOrder" type="{}ScriptOrderType"/>
 *         &lt;element name="deletionDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="deletedDate" use="required" type="{}JavaDateType" />
 *                 &lt;attribute name="deletionReason" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;maxLength value="100"/>
 *                       &lt;minLength value="0"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="transactionDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="atc" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="transactionDate" use="required" type="{}JavaDateType" />
 *                 &lt;attribute name="scriptBytes" use="required" type="{http://www.w3.org/2001/XMLSchema}hexBinary" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="deviceDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="deviceType" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                 &lt;attribute name="deviceCapabilities" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="scriptDeliveryStatus" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="deliveryStatus" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="ScriptDataItem" type="{}NVPType"/>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="source" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="target" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="datePublished" use="required" type="{}JavaDateType" />
 *       &lt;attribute name="scriptUpdateStatus" use="required" type="{}ScriptStatusUpdateType" />
 *       &lt;attribute name="scriptSequenceNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="autoRetryCount" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "trackingReference",
        "card",
        "businessFunction",
        "scriptOrder",
        "deletionDetails",
        "transactionDetails",
        "deviceDetails",
        "scriptDeliveryStatus",
        "scriptDataItem"
})
@XmlRootElement(name = "scriptStatusUpdate")
public class ScriptStatusUpdate
{

    @XmlElement(name = "TrackingReference", required = true)
    protected String trackingReference;
    @XmlElement(name = "Card", required = true)
    protected CardType card;
    @XmlElement(name = "BusinessFunction", required = true)
    protected ScriptStatusUpdate.BusinessFunction businessFunction;
    @XmlElement(name = "ScriptOrder", required = true)
    protected BigInteger scriptOrder;
    protected ScriptStatusUpdate.DeletionDetails deletionDetails;
    protected ScriptStatusUpdate.TransactionDetails transactionDetails;
    protected ScriptStatusUpdate.DeviceDetails deviceDetails;
    protected ScriptStatusUpdate.ScriptDeliveryStatus scriptDeliveryStatus;
    @XmlElement(name = "ScriptDataItem")
    protected List<NVPType> scriptDataItem;
    @XmlAttribute(name = "source", required = true)
    protected String source;
    @XmlAttribute(name = "target", required = true)
    protected String target;
    @XmlAttribute(name = "datePublished", required = true)
    protected String datePublished;
    @XmlAttribute(name = "scriptUpdateStatus", required = true)
    protected ScriptStatusUpdateType scriptUpdateStatus;
    @XmlAttribute(name = "scriptSequenceNumber", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger scriptSequenceNumber;
    @XmlAttribute(name = "autoRetryCount", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger autoRetryCount;

    /**
     * Gets the value of the trackingReference property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTrackingReference()
    {
        return trackingReference;
    }

    /**
     * Sets the value of the trackingReference property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTrackingReference(String value)
    {
        this.trackingReference = value;
    }

    /**
     * Gets the value of the card property.
     *
     * @return possible object is
     *         {@link CardType }
     */
    public CardType getCard()
    {
        return card;
    }

    /**
     * Sets the value of the card property.
     *
     * @param value allowed object is
     *              {@link CardType }
     */
    public void setCard(CardType value)
    {
        this.card = value;
    }

    /**
     * Gets the value of the businessFunction property.
     *
     * @return possible object is
     *         {@link ScriptStatusUpdate.BusinessFunction }
     */
    public ScriptStatusUpdate.BusinessFunction getBusinessFunction()
    {
        return businessFunction;
    }

    /**
     * Sets the value of the businessFunction property.
     *
     * @param value allowed object is
     *              {@link ScriptStatusUpdate.BusinessFunction }
     */
    public void setBusinessFunction(ScriptStatusUpdate.BusinessFunction value)
    {
        this.businessFunction = value;
    }

    /**
     * Gets the value of the scriptOrder property.
     *
     * @return possible object is
     *         {@link BigInteger }
     */
    public BigInteger getScriptOrder()
    {
        return scriptOrder;
    }

    /**
     * Sets the value of the scriptOrder property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setScriptOrder(BigInteger value)
    {
        this.scriptOrder = value;
    }

    /**
     * Gets the value of the deletionDetails property.
     *
     * @return possible object is
     *         {@link ScriptStatusUpdate.DeletionDetails }
     */
    public ScriptStatusUpdate.DeletionDetails getDeletionDetails()
    {
        return deletionDetails;
    }

    /**
     * Sets the value of the deletionDetails property.
     *
     * @param value allowed object is
     *              {@link ScriptStatusUpdate.DeletionDetails }
     */
    public void setDeletionDetails(ScriptStatusUpdate.DeletionDetails value)
    {
        this.deletionDetails = value;
    }

    /**
     * Gets the value of the transactionDetails property.
     *
     * @return possible object is
     *         {@link ScriptStatusUpdate.TransactionDetails }
     */
    public ScriptStatusUpdate.TransactionDetails getTransactionDetails()
    {
        return transactionDetails;
    }

    /**
     * Sets the value of the transactionDetails property.
     *
     * @param value allowed object is
     *              {@link ScriptStatusUpdate.TransactionDetails }
     */
    public void setTransactionDetails(ScriptStatusUpdate.TransactionDetails value)
    {
        this.transactionDetails = value;
    }

    /**
     * Gets the value of the deviceDetails property.
     *
     * @return possible object is
     *         {@link ScriptStatusUpdate.DeviceDetails }
     */
    public ScriptStatusUpdate.DeviceDetails getDeviceDetails()
    {
        return deviceDetails;
    }

    /**
     * Sets the value of the deviceDetails property.
     *
     * @param value allowed object is
     *              {@link ScriptStatusUpdate.DeviceDetails }
     */
    public void setDeviceDetails(ScriptStatusUpdate.DeviceDetails value)
    {
        this.deviceDetails = value;
    }

    /**
     * Gets the value of the scriptDeliveryStatus property.
     *
     * @return possible object is
     *         {@link ScriptStatusUpdate.ScriptDeliveryStatus }
     */
    public ScriptStatusUpdate.ScriptDeliveryStatus getScriptDeliveryStatus()
    {
        return scriptDeliveryStatus;
    }

    /**
     * Sets the value of the scriptDeliveryStatus property.
     *
     * @param value allowed object is
     *              {@link ScriptStatusUpdate.ScriptDeliveryStatus }
     */
    public void setScriptDeliveryStatus(ScriptStatusUpdate.ScriptDeliveryStatus value)
    {
        this.scriptDeliveryStatus = value;
    }

    /**
     * Gets the value of the scriptDataItem property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scriptDataItem property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScriptDataItem().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link NVPType }
     */
    public List<NVPType> getScriptDataItem()
    {
        if (scriptDataItem == null)
        {
            scriptDataItem = new ArrayList<NVPType>();
        }
        return this.scriptDataItem;
    }

    /**
     * Gets the value of the source property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSource()
    {
        return source;
    }

    /**
     * Sets the value of the source property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSource(String value)
    {
        this.source = value;
    }

    /**
     * Gets the value of the target property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTarget()
    {
        return target;
    }

    /**
     * Sets the value of the target property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTarget(String value)
    {
        this.target = value;
    }

    /**
     * Gets the value of the datePublished property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getDatePublished()
    {
        return datePublished;
    }

    /**
     * Sets the value of the datePublished property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDatePublished(String value)
    {
        this.datePublished = value;
    }

    /**
     * Gets the value of the scriptUpdateStatus property.
     *
     * @return possible object is
     *         {@link ScriptStatusUpdateType }
     */
    public ScriptStatusUpdateType getScriptUpdateStatus()
    {
        return scriptUpdateStatus;
    }

    /**
     * Sets the value of the scriptUpdateStatus property.
     *
     * @param value allowed object is
     *              {@link ScriptStatusUpdateType }
     */
    public void setScriptUpdateStatus(ScriptStatusUpdateType value)
    {
        this.scriptUpdateStatus = value;
    }

    /**
     * Gets the value of the scriptSequenceNumber property.
     *
     * @return possible object is
     *         {@link BigInteger }
     */
    public BigInteger getScriptSequenceNumber()
    {
        return scriptSequenceNumber;
    }

    /**
     * Sets the value of the scriptSequenceNumber property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setScriptSequenceNumber(BigInteger value)
    {
        this.scriptSequenceNumber = value;
    }

    /**
     * Gets the value of the autoRetryCount property.
     *
     * @return possible object is
     *         {@link BigInteger }
     */
    public BigInteger getAutoRetryCount()
    {
        return autoRetryCount;
    }

    /**
     * Sets the value of the autoRetryCount property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setAutoRetryCount(BigInteger value)
    {
        this.autoRetryCount = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * <p/>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p/>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="FunctionName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class BusinessFunction
    {

        @XmlAttribute(name = "FunctionName", required = true)
        protected String functionName;

        /**
         * Gets the value of the functionName property.
         *
         * @return possible object is
         *         {@link String }
         */
        public String getFunctionName()
        {
            return functionName;
        }

        /**
         * Sets the value of the functionName property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setFunctionName(String value)
        {
            this.functionName = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * <p/>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p/>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="deletedDate" use="required" type="{}JavaDateType" />
     *       &lt;attribute name="deletionReason" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;maxLength value="100"/>
     *             &lt;minLength value="0"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class DeletionDetails
    {

        @XmlAttribute(name = "deletedDate", required = true)
        protected String deletedDate;
        @XmlAttribute(name = "deletionReason", required = true)
        protected String deletionReason;

        /**
         * Gets the value of the deletedDate property.
         *
         * @return possible object is
         *         {@link String }
         */
        public String getDeletedDate()
        {
            return deletedDate;
        }

        /**
         * Sets the value of the deletedDate property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setDeletedDate(String value)
        {
            this.deletedDate = value;
        }

        /**
         * Gets the value of the deletionReason property.
         *
         * @return possible object is
         *         {@link String }
         */
        public String getDeletionReason()
        {
            return deletionReason;
        }

        /**
         * Sets the value of the deletionReason property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setDeletionReason(String value)
        {
            this.deletionReason = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * <p/>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p/>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="deviceType" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="deviceCapabilities" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class DeviceDetails
    {

        @XmlAttribute(name = "deviceType", required = true)
        protected BigInteger deviceType;
        @XmlAttribute(name = "deviceCapabilities")
        @XmlSchemaType(name = "anySimpleType")
        protected String deviceCapabilities;

        /**
         * Gets the value of the deviceType property.
         *
         * @return possible object is
         *         {@link BigInteger }
         */
        public BigInteger getDeviceType()
        {
            return deviceType;
        }

        /**
         * Sets the value of the deviceType property.
         *
         * @param value allowed object is
         *              {@link BigInteger }
         */
        public void setDeviceType(BigInteger value)
        {
            this.deviceType = value;
        }

        /**
         * Gets the value of the deviceCapabilities property.
         *
         * @return possible object is
         *         {@link String }
         */
        public String getDeviceCapabilities()
        {
            return deviceCapabilities;
        }

        /**
         * Sets the value of the deviceCapabilities property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setDeviceCapabilities(String value)
        {
            this.deviceCapabilities = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * <p/>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p/>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="deliveryStatus" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class ScriptDeliveryStatus
    {

        @XmlAttribute(name = "deliveryStatus", required = true)
        protected String deliveryStatus;

        /**
         * Gets the value of the deliveryStatus property.
         *
         * @return possible object is
         *         {@link String }
         */
        public String getDeliveryStatus()
        {
            return deliveryStatus;
        }

        /**
         * Sets the value of the deliveryStatus property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setDeliveryStatus(String value)
        {
            this.deliveryStatus = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * <p/>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p/>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="atc" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="transactionDate" use="required" type="{}JavaDateType" />
     *       &lt;attribute name="scriptBytes" use="required" type="{http://www.w3.org/2001/XMLSchema}hexBinary" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class TransactionDetails
    {

        @XmlAttribute(name = "atc", required = true)
        protected String atc;
        @XmlAttribute(name = "transactionDate", required = true)
        protected String transactionDate;
        @XmlAttribute(name = "scriptBytes", required = true)
        @XmlJavaTypeAdapter(HexBinaryAdapter.class)
        @XmlSchemaType(name = "hexBinary")
        protected byte[] scriptBytes;

        /**
         * Gets the value of the atc property.
         *
         * @return possible object is
         *         {@link String }
         */
        public String getAtc()
        {
            return atc;
        }

        /**
         * Sets the value of the atc property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setAtc(String value)
        {
            this.atc = value;
        }

        /**
         * Gets the value of the transactionDate property.
         *
         * @return possible object is
         *         {@link String }
         */
        public String getTransactionDate()
        {
            return transactionDate;
        }

        /**
         * Sets the value of the transactionDate property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setTransactionDate(String value)
        {
            this.transactionDate = value;
        }

        /**
         * Gets the value of the scriptBytes property.
         *
         * @return possible object is
         *         {@link String }
         */
        public byte[] getScriptBytes()
        {
            return scriptBytes;
        }

        /**
         * Sets the value of the scriptBytes property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setScriptBytes(byte[] value)
        {
            this.scriptBytes = value;
        }

    }

}
