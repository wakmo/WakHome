
package net.aconite.affina.espinterface.xmlmapping.sem;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.math.BigInteger;


/**
 * <p>Java class for DeviceType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="DeviceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="deviceType" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="deviceCapabilities" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceType")
public class DeviceType
{

    @XmlAttribute(name = "deviceType", required = true)
    protected BigInteger deviceType;
    @XmlAttribute(name = "deviceCapabilities")
    protected String deviceCapabilities;

    /**
     * Gets the value of the deviceType property.
     *
     * @return possible object is
     *         {@link BigInteger }
     */
    public BigInteger getDeviceType()
    {
        return deviceType;
    }

    /**
     * Sets the value of the deviceType property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setDeviceType(BigInteger value)
    {
        this.deviceType = value;
    }

    /**
     * Gets the value of the deviceCapabilities property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getDeviceCapabilities()
    {
        return deviceCapabilities;
    }

    /**
     * Sets the value of the deviceCapabilities property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDeviceCapabilities(String value)
    {
        this.deviceCapabilities = value;
    }

}
