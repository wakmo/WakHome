
package net.aconite.affina.espinterface.xmlmapping.affina;

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
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "trackingReference"
})
@XmlRootElement(name = "StageScriptAlert")
public class StageScriptAlert
{

    @XmlElement(name = "TrackingReference", required = true)
    protected String trackingReference;

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

}
