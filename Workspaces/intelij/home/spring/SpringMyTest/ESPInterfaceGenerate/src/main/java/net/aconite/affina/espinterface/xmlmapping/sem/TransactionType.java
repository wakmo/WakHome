
package net.aconite.affina.espinterface.xmlmapping.sem;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="TransactionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="atc" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="transactionDate" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="scriptBytes" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionType")
public class TransactionType
{

    @XmlAttribute(name = "atc", required = true)
    protected String atc;
    @XmlAttribute(name = "transactionDate", required = true)
    protected String transactionDate;
    @XmlAttribute(name = "scriptBytes", required = true)
    protected String scriptBytes;

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
    public String getScriptBytes()
    {
        return scriptBytes;
    }

    /**
     * Sets the value of the scriptBytes property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setScriptBytes(String value)
    {
        this.scriptBytes = value;
    }

}
