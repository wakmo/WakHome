
package net.aconite.affina.espinterface.xmlmapping.sem;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * Identifies individual card in SCMS
 * <p/>
 * <p>Java class for CardIdentificationType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="CardIdentificationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="CIN" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="IIN" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardIdentificationType")
public class CardIdentificationType
{

    @XmlAttribute(name = "CIN", required = true)
    protected String cin;
    @XmlAttribute(name = "IIN", required = true)
    protected String iin;

    /**
     * Gets the value of the cin property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCIN()
    {
        return cin;
    }

    /**
     * Sets the value of the cin property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCIN(String value)
    {
        this.cin = value;
    }

    /**
     * Gets the value of the iin property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getIIN()
    {
        return iin;
    }

    /**
     * Sets the value of the iin property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIIN(String value)
    {
        this.iin = value;
    }

}
