
package net.aconite.affina.espinterface.xmlmapping.sem;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ScriptStatusUpdateType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="ScriptStatusUpdateType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="STAGED"/>
 *     &lt;enumeration value="DELETED"/>
 *     &lt;enumeration value="SENT"/>
 *     &lt;enumeration value="DELIVERED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "ScriptStatusUpdateType")
@XmlEnum
public enum ScriptStatusUpdateType
{

    STAGED,
    DELETED,
    SENT,
    DELIVERED;

    public String value()
    {
        return name();
    }

    public static ScriptStatusUpdateType fromValue(String v)
    {
        return valueOf(v);
    }

}
