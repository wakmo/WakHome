
package net.aconite.affina.espinterface.xmlmapping.sem;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * Identifies the application for scripting
 * <p/>
 * <p>Java class for ApplicationType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ApplicationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="AID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicationType")
public class ApplicationType
{

    @XmlAttribute(name = "AID", required = true)
    protected String aid;
    @XmlAttribute(name = "type", required = true)
    protected String type;
    @XmlAttribute(name = "version", required = true)
    protected String version;

    /**
     * Gets the value of the aid property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getAID()
    {
        return aid;
    }

    /**
     * Sets the value of the aid property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAID(String value)
    {
        this.aid = value;
    }

    /**
     * Gets the value of the type property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getType()
    {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setType(String value)
    {
        this.type = value;
    }

    /**
     * Gets the value of the version property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getVersion()
    {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVersion(String value)
    {
        this.version = value;
    }

}
