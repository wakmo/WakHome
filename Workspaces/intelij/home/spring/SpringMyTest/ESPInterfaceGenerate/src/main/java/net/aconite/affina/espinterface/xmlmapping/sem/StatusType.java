
package net.aconite.affina.espinterface.xmlmapping.sem;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatusType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="StatusType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="ERROR"/>
 *     &lt;enumeration value="STATUS_OK"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "StatusType")
@XmlEnum
public enum StatusType
{

    ERROR,
    STATUS_OK;

    public String value()
    {
        return name();
    }

    public static StatusType fromValue(String v)
    {
        return valueOf(v);
    }

}
