
package net.aconite.affina.espinterface.xmlmapping.sem;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * identifies source and target systems
 * <p/>
 * <p>Java class for HeaderType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="HeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="source" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="target" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="datePublished" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderType")
public class HeaderType
{

    @XmlAttribute(name = "source", required = true)
    protected String source;
    @XmlAttribute(name = "target", required = true)
    protected String target;
    @XmlAttribute(name = "datePublished", required = true)
    protected String datePublished;

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

}
