package wak.work.cryptogram.graem.securecrypto;

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: growles
 * Date: 12-Feb-2009
 * Time: 11:39:14
 */
public class HsmType
{
    private static final Map<String, HsmType> NAME_MAP = new HashMap<String, HsmType>();

    public static final HsmType RG7000_HSM_TYPE = new HsmType("Thales RG 7000", "RG7000", 7000);
    public static final HsmType CM250_HSM_TYPE = new HsmType("Thales CM 250", "CM250", 250);
    public static final HsmType RG8000_HSM_TYPE = new HsmType("Thales RG 8000", "RG8000", 8000);
    public static final HsmType PS9000_HSM_TYPE = new HsmType("Thales PS 9000", "PS9000", 9000);


    private final String name;
    private final String shortName;
    private final int typeCode;

    private HsmType(String name, String shortName, int typeCode)
    {
        this.name = name;
        this.shortName = shortName;
        this.typeCode = typeCode;

        NAME_MAP.put(name, this);
    }

    public static String[] getHsmNames()
    {
        String[] hsmNames = new String[NAME_MAP.size()];
        hsmNames = NAME_MAP.keySet().toArray(hsmNames);
        Arrays.sort(hsmNames);

        return hsmNames;
    }

    public static HsmType getHsmType(String hsmTypeName) throws IllegalArgumentException
    {
        if (!NAME_MAP.containsKey(hsmTypeName))
        {
            throw new IllegalArgumentException("Invalid HSM Type String: " + hsmTypeName);
        }

        return NAME_MAP.get(hsmTypeName);
    }

    public String getName()
    {
        return name;
    }

    public String getShortName()
    {
        return shortName;
    }

    public int getTypeCode()
    {
        return typeCode;
    }

    public boolean equals(HsmType hsmType)
    {
        return hsmType.typeCode == typeCode;
    }

    public String toString()
    {
        return name;
    }
}
