
package net.aconite.affina.espinterface.xmlmapping.sem;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * Identifies individual card in SEM
 * <p/>
 * <p>Java class for CardType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="CardType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="PAN" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="PANSequence" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ExpirationDate" use="required" type="{}JavaDateType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardType")
public class CardType
{

    @XmlAttribute(name = "PAN", required = true)
    protected String pan;
    @XmlAttribute(name = "PANSequence", required = true)
    protected String panSequence;
    @XmlAttribute(name = "ExpirationDate", required = true)
    protected String expirationDate;

    /**
     * Gets the value of the pan property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPAN()
    {
        return pan;
    }

    /**
     * Sets the value of the pan property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPAN(String value)
    {
        this.pan = value;
    }

    /**
     * Gets the value of the panSequence property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPANSequence()
    {
        return panSequence;
    }

    /**
     * Sets the value of the panSequence property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPANSequence(String value)
    {
        this.panSequence = value;
    }

    /**
     * Gets the value of the expirationDate property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getExpirationDate()
    {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setExpirationDate(String value)
    {
        this.expirationDate = value;
    }

}
