
package net.aconite.affina.espinterface.xmlmapping.sem;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;


/**
 * Identifies individual card in acquiring system
 * <p/>
 * <p>Java class for CardIdType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="CardIdType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="PAN" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="PANSequence" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardIdType")
public class CardIdType
{

    @XmlAttribute(name = "PAN", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger pan;
    @XmlAttribute(name = "PANSequence", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger panSequence;

    /**
     * Gets the value of the pan property.
     *
     * @return possible object is
     *         {@link BigInteger }
     */
    public BigInteger getPAN()
    {
        return pan;
    }

    /**
     * Sets the value of the pan property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setPAN(BigInteger value)
    {
        this.pan = value;
    }

    /**
     * Gets the value of the panSequence property.
     *
     * @return possible object is
     *         {@link BigInteger }
     */
    public BigInteger getPANSequence()
    {
        return panSequence;
    }

    /**
     * Sets the value of the panSequence property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setPANSequence(BigInteger value)
    {
        this.panSequence = value;
    }

}
