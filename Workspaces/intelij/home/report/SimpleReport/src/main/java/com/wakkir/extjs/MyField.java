package com.wakkir.extjs;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonRawValue;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/** Created with IntelliJ IDEA. User: wakkir Date: 23/12/12 Time: 02:52 To change this template use File | Settings |
 * File Templates. */
public class MyField
{
    //@JsonSerialize(include= JsonSerialize.Inclusion.NON_DEFAULT)
    //--------Ignored---------------------------
    @JsonIgnore
    private int	index;
    @JsonIgnore
    private MyColumn column;
    //@JsonIgnore
    //private boolean	allowBlank;
    //@JsonIgnore
    //private String	defaultValue;

    //------Mandotory---------------------------
    @JsonProperty
    private String	name;
    @JsonProperty
    private String	mapping;
    @JsonProperty
    private String	type=MyDefault.FIELD_TYPE;

    //------Optional---------------------------
    @JsonSerialize(include= JsonSerialize.Inclusion.NON_DEFAULT)
    private boolean useNull;
    @JsonRawValue  //Add values without double quotes
    @JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
    private String convert;
    @JsonSerialize(include= JsonSerialize.Inclusion.NON_DEFAULT)
    private String dateFormat;     //only if type is date, this must be defined  as dateFormat: 'm/d/Y' or 'timestamp'
    //-----------------------------------------

    public MyField()
    {
        column = new MyColumn();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMapping()
    {
        return mapping;
    }

    public void setMapping(String mapping)
    {
        this.mapping = mapping;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public boolean isUseNull() {
        return useNull;
    }

    public void setUseNull(boolean useNull) {
        this.useNull = useNull;
    }

    public String getConvert() {
        return convert;
    }

    public void setConvert(String convert) {
        this.convert = convert;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public MyColumn getColumn() {
        return column;
    }

    public void setColumn(MyColumn column) {
        this.column = column;
    }
}
