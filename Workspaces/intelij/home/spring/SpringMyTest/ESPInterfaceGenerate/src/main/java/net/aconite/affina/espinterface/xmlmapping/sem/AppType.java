
package net.aconite.affina.espinterface.xmlmapping.sem;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * Identifies the type of application
 * <p/>
 * <p>Java class for AppType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="AppType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="applicationType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="applicationVersion" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppType")
public class AppType
{

    @XmlAttribute(name = "applicationType", required = true)
    protected String applicationType;
    @XmlAttribute(name = "applicationVersion", required = true)
    protected String applicationVersion;

    /**
     * Gets the value of the applicationType property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getApplicationType()
    {
        return applicationType;
    }

    /**
     * Sets the value of the applicationType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setApplicationType(String value)
    {
        this.applicationType = value;
    }

    /**
     * Gets the value of the applicationVersion property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getApplicationVersion()
    {
        return applicationVersion;
    }

    /**
     * Sets the value of the applicationVersion property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setApplicationVersion(String value)
    {
        this.applicationVersion = value;
    }

}
