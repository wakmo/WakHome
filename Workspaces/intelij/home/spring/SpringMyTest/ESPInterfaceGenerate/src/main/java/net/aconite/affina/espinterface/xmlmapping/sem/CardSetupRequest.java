
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
 *         &lt;element name="Card" type="{}CardType"/>
 *         &lt;element name="Application" type="{}AppType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "trackingReference",
        "card",
        "application"
})
@XmlRootElement(name = "CardSetupRequest")
public class CardSetupRequest
{

    @XmlElement(name = "TrackingReference", required = true)
    protected String trackingReference;
    @XmlElement(name = "Card", required = true)
    protected CardType card;
    @XmlElement(name = "Application", required = true)
    protected AppType application;

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
     * Gets the value of the card property.
     *
     * @return possible object is
     *         {@link CardType }
     */
    public CardType getCard()
    {
        return card;
    }

    /**
     * Sets the value of the card property.
     *
     * @param value allowed object is
     *              {@link CardType }
     */
    public void setCard(CardType value)
    {
        this.card = value;
    }

    /**
     * Gets the value of the application property.
     *
     * @return possible object is
     *         {@link AppType }
     */
    public AppType getApplication()
    {
        return application;
    }

    /**
     * Sets the value of the application property.
     *
     * @param value allowed object is
     *              {@link AppType }
     */
    public void setApplication(AppType value)
    {
        this.application = value;
    }

}
