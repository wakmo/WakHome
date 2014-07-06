package com.wakkir.extjs;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Created with IntelliJ IDEA. User: wakkir Date: 23/12/12 Time: 02:39 To change this template use File | Settings |
 * File Templates. */
public class MetaData
{
    @JsonProperty
    private String				idProperty;
    @JsonProperty
    private String				totalProperty;
    @JsonProperty
    private String				successProperty;
    @JsonProperty
    private String				root;
    @JsonProperty
    private List<MyField>	fields;

    public MetaData()
    {
        fields = new ArrayList<MyField>();
    }

    public String getIdProperty()
    {
        return idProperty;
    }

    public void setIdProperty(String idProperty)
    {
        this.idProperty = idProperty;
    }

    public String getTotalProperty()
    {
        return totalProperty;
    }

    public void setTotalProperty(String totalProperty)
    {
        this.totalProperty = totalProperty;
    }

    public String getSuccessProperty()
    {
        return successProperty;
    }

    public void setSuccessProperty(String successProperty)
    {
        this.successProperty = successProperty;
    }

    public String getRoot()
    {
        return root;
    }

    public void setRoot(String root)
    {
        this.root = root;
    }

    public List<MyField> getFields()
    {
        return fields;
    }

    public void addField(MyField field)
    {
        if(this.fields==null)
            this.fields = new ArrayList<MyField>();

        this.fields.add(field);
    }
}
