
package net.aconite.affina.espinterface.xmlmapping.sem;

import javax.xml.bind.annotation.*;
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
 *         &lt;element name="action" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="startDate" type="{}JavaDateType" />
 *                 &lt;attribute name="endDate" type="{}JavaDateType" />
 *                 &lt;attribute name="restageAutomatically" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="ScriptDataItem" type="{}NVPType"/>
 *         &lt;/sequence>
 *       &lt;/sequence>
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
        "action",
        "scriptDataItem"
})
@XmlRootElement(name = "StageScriptRequest")
public class StageScriptRequest
{

    @XmlElement(name = "TrackingReference", required = true)
    protected String trackingReference;
    @XmlElement(name = "Card", required = true)
    protected CardType card;
    @XmlElement(name = "BusinessFunction", required = true)
    protected StageScriptRequest.BusinessFunction businessFunction;
    protected StageScriptRequest.Action action;
    @XmlElement(name = "ScriptDataItem")
    protected List<NVPType> scriptDataItem;

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
     *         {@link StageScriptRequest.BusinessFunction }
     */
    public StageScriptRequest.BusinessFunction getBusinessFunction()
    {
        return businessFunction;
    }

    /**
     * Sets the value of the businessFunction property.
     *
     * @param value allowed object is
     *              {@link StageScriptRequest.BusinessFunction }
     */
    public void setBusinessFunction(StageScriptRequest.BusinessFunction value)
    {
        this.businessFunction = value;
    }

    /**
     * Gets the value of the action property.
     *
     * @return possible object is
     *         {@link StageScriptRequest.Action }
     */
    public StageScriptRequest.Action getAction()
    {
        return action;
    }

    /**
     * Sets the value of the action property.
     *
     * @param value allowed object is
     *              {@link StageScriptRequest.Action }
     */
    public void setAction(StageScriptRequest.Action value)
    {
        this.action = value;
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
     * <p>Java class for anonymous complex type.
     * <p/>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p/>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="startDate" type="{}JavaDateType" />
     *       &lt;attribute name="endDate" type="{}JavaDateType" />
     *       &lt;attribute name="restageAutomatically" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Action
    {

        @XmlAttribute(name = "startDate")
        protected String startDate;
        @XmlAttribute(name = "endDate")
        protected String endDate;
        @XmlAttribute(name = "restageAutomatically")
        protected BigInteger restageAutomatically;

        /**
         * Gets the value of the startDate property.
         *
         * @return possible object is
         *         {@link String }
         */
        public String getStartDate()
        {
            return startDate;
        }

        /**
         * Sets the value of the startDate property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setStartDate(String value)
        {
            this.startDate = value;
        }

        /**
         * Gets the value of the endDate property.
         *
         * @return possible object is
         *         {@link String }
         */
        public String getEndDate()
        {
            return endDate;
        }

        /**
         * Sets the value of the endDate property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setEndDate(String value)
        {
            this.endDate = value;
        }

        /**
         * Gets the value of the restageAutomatically property.
         *
         * @return possible object is
         *         {@link BigInteger }
         */
        public BigInteger getRestageAutomatically()
        {
            return restageAutomatically;
        }

        /**
         * Sets the value of the restageAutomatically property.
         *
         * @param value allowed object is
         *              {@link BigInteger }
         */
        public void setRestageAutomatically(BigInteger value)
        {
            this.restageAutomatically = value;
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

}
