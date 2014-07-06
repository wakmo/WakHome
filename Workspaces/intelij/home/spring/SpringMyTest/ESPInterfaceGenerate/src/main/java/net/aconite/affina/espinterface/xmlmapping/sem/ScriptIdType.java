
package net.aconite.affina.espinterface.xmlmapping.sem;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;


/**
 * Identifies the script being applied
 * <p/>
 * <p>Java class for ScriptIdType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ScriptIdType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="scriptSequenceNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="requestID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="autoRetryCount" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScriptIdType")
public class ScriptIdType
{

    @XmlAttribute(name = "scriptSequenceNumber", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger scriptSequenceNumber;
    @XmlAttribute(name = "requestID", required = true)
    protected String requestID;
    @XmlAttribute(name = "autoRetryCount", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger autoRetryCount;

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
     * Gets the value of the requestID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getRequestID()
    {
        return requestID;
    }

    /**
     * Sets the value of the requestID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRequestID(String value)
    {
        this.requestID = value;
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

}
