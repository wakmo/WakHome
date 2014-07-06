
package net.aconite.affina.espinterface.xmlmapping.sem;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TrackingReference" type="{}TrackingReferenceType"/>
 *         &lt;element name="status" type="{}StatusType"/>
 *         &lt;element name="error" type="{}ErrorType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "trackingReference",
        "status",
        "error"
})
@XmlRootElement(name = "CardSetupResponse")
public class CardSetupResponse
{

    @XmlElement(name = "TrackingReference", required = true)
    protected String trackingReference;
    @XmlElement(required = true)
    protected StatusType status;
    protected ErrorType error;

    /**
     * Gets the value of the trackingReference property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTrackingReference()
    {
        return trackingReference;
    }

    /**
     * Sets the value of the trackingReference property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTrackingReference(String value)
    {
        this.trackingReference = value;
    }

    /**
     * Gets the value of the status property.
     *
     * @return possible object is
     *         {@link StatusType }
     */
    public StatusType getStatus()
    {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value allowed object is
     *              {@link StatusType }
     */
    public void setStatus(StatusType value)
    {
        this.status = value;
    }

    /**
     * Gets the value of the error property.
     *
     * @return possible object is
     *         {@link ErrorType }
     */
    public ErrorType getError()
    {
        return error;
    }

    /**
     * Sets the value of the error property.
     *
     * @param value allowed object is
     *              {@link ErrorType }
     */
    public void setError(ErrorType value)
    {
        this.error = value;
    }

}
