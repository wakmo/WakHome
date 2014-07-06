
package net.aconite.affina.espinterface.xmlmapping.sem;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ScriptType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ScriptType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="scriptCommands">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="scriptCommand" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="dataItems">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                                       &lt;element name="dataItem" type="{}TagType"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="CLA" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="INS" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="P1" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="P2" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="validPeriod">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="startDate" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="endDate" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="businessFunctionID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="businessFunctionGroup" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="scriptType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="validDevices" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="priority" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="autoRestageLimit" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScriptType", propOrder = {
        "scriptCommands",
        "validPeriod"
})
public class ScriptType
{

    @XmlElement(required = true)
    protected ScriptType.ScriptCommands scriptCommands;
    @XmlElement(required = true)
    protected ScriptType.ValidPeriod validPeriod;
    @XmlAttribute(name = "businessFunctionID", required = true)
    protected String businessFunctionID;
    @XmlAttribute(name = "businessFunctionGroup", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger businessFunctionGroup;
    @XmlAttribute(name = "scriptType", required = true)
    protected String scriptType;
    @XmlAttribute(name = "validDevices", required = true)
    protected BigInteger validDevices;
    @XmlAttribute(name = "priority", required = true)
    protected BigInteger priority;
    @XmlAttribute(name = "autoRestageLimit", required = true)
    protected BigInteger autoRestageLimit;

    /**
     * Gets the value of the scriptCommands property.
     *
     * @return possible object is
     *         {@link ScriptType.ScriptCommands }
     */
    public ScriptType.ScriptCommands getScriptCommands()
    {
        return scriptCommands;
    }

    /**
     * Sets the value of the scriptCommands property.
     *
     * @param value allowed object is
     *              {@link ScriptType.ScriptCommands }
     */
    public void setScriptCommands(ScriptType.ScriptCommands value)
    {
        this.scriptCommands = value;
    }

    /**
     * Gets the value of the validPeriod property.
     *
     * @return possible object is
     *         {@link ScriptType.ValidPeriod }
     */
    public ScriptType.ValidPeriod getValidPeriod()
    {
        return validPeriod;
    }

    /**
     * Sets the value of the validPeriod property.
     *
     * @param value allowed object is
     *              {@link ScriptType.ValidPeriod }
     */
    public void setValidPeriod(ScriptType.ValidPeriod value)
    {
        this.validPeriod = value;
    }

    /**
     * Gets the value of the businessFunctionID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBusinessFunctionID()
    {
        return businessFunctionID;
    }

    /**
     * Sets the value of the businessFunctionID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBusinessFunctionID(String value)
    {
        this.businessFunctionID = value;
    }

    /**
     * Gets the value of the businessFunctionGroup property.
     *
     * @return possible object is
     *         {@link BigInteger }
     */
    public BigInteger getBusinessFunctionGroup()
    {
        return businessFunctionGroup;
    }

    /**
     * Sets the value of the businessFunctionGroup property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setBusinessFunctionGroup(BigInteger value)
    {
        this.businessFunctionGroup = value;
    }

    /**
     * Gets the value of the scriptType property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getScriptType()
    {
        return scriptType;
    }

    /**
     * Sets the value of the scriptType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setScriptType(String value)
    {
        this.scriptType = value;
    }

    /**
     * Gets the value of the validDevices property.
     *
     * @return possible object is
     *         {@link BigInteger }
     */
    public BigInteger getValidDevices()
    {
        return validDevices;
    }

    /**
     * Sets the value of the validDevices property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setValidDevices(BigInteger value)
    {
        this.validDevices = value;
    }

    /**
     * Gets the value of the priority property.
     *
     * @return possible object is
     *         {@link BigInteger }
     */
    public BigInteger getPriority()
    {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setPriority(BigInteger value)
    {
        this.priority = value;
    }

    /**
     * Gets the value of the autoRestageLimit property.
     *
     * @return possible object is
     *         {@link BigInteger }
     */
    public BigInteger getAutoRestageLimit()
    {
        return autoRestageLimit;
    }

    /**
     * Sets the value of the autoRestageLimit property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setAutoRestageLimit(BigInteger value)
    {
        this.autoRestageLimit = value;
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
     *       &lt;sequence>
     *         &lt;element name="scriptCommand" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="dataItems">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence maxOccurs="unbounded" minOccurs="0">
     *                             &lt;element name="dataItem" type="{}TagType"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="CLA" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="INS" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="P1" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="P2" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "scriptCommand"
    })
    public static class ScriptCommands
    {

        @XmlElement(required = true)
        protected List<ScriptType.ScriptCommands.ScriptCommand> scriptCommand;

        /**
         * Gets the value of the scriptCommand property.
         * <p/>
         * <p/>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the scriptCommand property.
         * <p/>
         * <p/>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getScriptCommand().add(newItem);
         * </pre>
         * <p/>
         * <p/>
         * <p/>
         * Objects of the following type(s) are allowed in the list
         * {@link ScriptType.ScriptCommands.ScriptCommand }
         */
        public List<ScriptType.ScriptCommands.ScriptCommand> getScriptCommand()
        {
            if (scriptCommand == null)
            {
                scriptCommand = new ArrayList<ScriptType.ScriptCommands.ScriptCommand>();
            }
            return this.scriptCommand;
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
         *       &lt;sequence>
         *         &lt;element name="dataItems">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence maxOccurs="unbounded" minOccurs="0">
         *                   &lt;element name="dataItem" type="{}TagType"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="CLA" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="INS" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="P1" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="P2" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "dataItems"
        })
        public static class ScriptCommand
        {

            @XmlElement(required = true)
            protected ScriptType.ScriptCommands.ScriptCommand.DataItems dataItems;
            @XmlAttribute(name = "CLA", required = true)
            protected String cla;
            @XmlAttribute(name = "INS", required = true)
            protected String ins;
            @XmlAttribute(name = "P1", required = true)
            protected String p1;
            @XmlAttribute(name = "P2", required = true)
            protected String p2;

            /**
             * Gets the value of the dataItems property.
             *
             * @return possible object is
             *         {@link ScriptType.ScriptCommands.ScriptCommand.DataItems }
             */
            public ScriptType.ScriptCommands.ScriptCommand.DataItems getDataItems()
            {
                return dataItems;
            }

            /**
             * Sets the value of the dataItems property.
             *
             * @param value allowed object is
             *              {@link ScriptType.ScriptCommands.ScriptCommand.DataItems }
             */
            public void setDataItems(ScriptType.ScriptCommands.ScriptCommand.DataItems value)
            {
                this.dataItems = value;
            }

            /**
             * Gets the value of the cla property.
             *
             * @return possible object is
             *         {@link String }
             */
            public String getCLA()
            {
                return cla;
            }

            /**
             * Sets the value of the cla property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setCLA(String value)
            {
                this.cla = value;
            }

            /**
             * Gets the value of the ins property.
             *
             * @return possible object is
             *         {@link String }
             */
            public String getINS()
            {
                return ins;
            }

            /**
             * Sets the value of the ins property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setINS(String value)
            {
                this.ins = value;
            }

            /**
             * Gets the value of the p1 property.
             *
             * @return possible object is
             *         {@link String }
             */
            public String getP1()
            {
                return p1;
            }

            /**
             * Sets the value of the p1 property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setP1(String value)
            {
                this.p1 = value;
            }

            /**
             * Gets the value of the p2 property.
             *
             * @return possible object is
             *         {@link String }
             */
            public String getP2()
            {
                return p2;
            }

            /**
             * Sets the value of the p2 property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setP2(String value)
            {
                this.p2 = value;
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
             *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
             *         &lt;element name="dataItem" type="{}TagType"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "dataItem"
            })
            public static class DataItems
            {

                protected List<TagType> dataItem;

                /**
                 * Gets the value of the dataItem property.
                 * <p/>
                 * <p/>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the dataItem property.
                 * <p/>
                 * <p/>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getDataItem().add(newItem);
                 * </pre>
                 * <p/>
                 * <p/>
                 * <p/>
                 * Objects of the following type(s) are allowed in the list
                 * {@link TagType }
                 */
                public List<TagType> getDataItem()
                {
                    if (dataItem == null)
                    {
                        dataItem = new ArrayList<TagType>();
                    }
                    return this.dataItem;
                }

            }

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
     *       &lt;attribute name="startDate" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="endDate" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class ValidPeriod
    {

        @XmlAttribute(name = "startDate", required = true)
        protected String startDate;
        @XmlAttribute(name = "endDate", required = true)
        protected String endDate;

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

    }

}
