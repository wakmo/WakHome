
package net.aconite.affina.espinterface.xmlmapping.sem;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ErrorType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ErrorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="ErrorCode" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Data" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorType")
public class ErrorType
{

    @XmlAttribute(name = "ErrorCode", required = true)
    protected String errorCode;
    @XmlAttribute(name = "Description", required = true)
    protected String description;
    @XmlAttribute(name = "Data")
    protected String data;

    /**
     * Gets the value of the errorCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getErrorCode()
    {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setErrorCode(String value)
    {
        this.errorCode = value;
    }

    /**
     * Gets the value of the description property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets the value of the description property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDescription(String value)
    {
        this.description = value;
    }

    /**
     * Gets the value of the data property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getData()
    {
        return data;
    }

    /**
     * Sets the value of the data property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setData(String value)
    {
        this.data = value;
    }

}
