package com.wakkir.extjs;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonRawValue;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import java.util.ArrayList;
import java.util.List;

/** Created with IntelliJ IDEA. User: wakkir Date: 23/12/12 Time: 02:52 To change this template use File | Settings |
 * File Templates. */
public class MyColumn
{
    //@JsonSerialize(include=Inclusion.NON_DEFAULT)  //Add values if it is not default
   //--------Ignored--------------------------
    @JsonIgnore
    private int	index;
    @JsonIgnore
    private String	dataType=MyDefault.COLUMN_TYPE;

    //------Mandotory-----------------------------
    @JsonProperty
    private String	id;
    @JsonProperty
    private String	header;
    @JsonProperty
    private String	dataIndex;
    @JsonProperty
    private String	align=MyDefault.COLUMN_ALIGN;

    //------Optional--------------------------------
    @JsonSerialize(include=Inclusion.NON_DEFAULT)
    private boolean	sortable=MyDefault.COLUMN_SORTABLE;
    @JsonSerialize(include=Inclusion.NON_DEFAULT)
    private boolean	hidden=MyDefault.COLUMN_HIDDEN;

    @JsonRawValue  //Add values without double quotes
    @JsonSerialize(include=Inclusion.NON_NULL)
    private String	renderer;	// renderer: render2APMDate,
    @JsonSerialize(include=Inclusion.NON_DEFAULT)
    private int	flex;
    @JsonSerialize(include=Inclusion.NON_NULL)
    private String	xtype; //renderer: Ext.util.Format.usMoney
    @JsonSerialize(include=Inclusion.NON_NULL)   //Add values if it is not null
    private String format; //xtype: 'numbercolumn', format:'0.00'
    @JsonSerialize(include=Inclusion.NON_DEFAULT)
    private List<MyColumn> columns;

    //-----------------------------------------

    public MyColumn()
    {
        this.columns=new ArrayList<MyColumn>();
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public String getHeader()
    {
        return header;
    }

    public void setHeader(String header)
    {
        this.header = header;
    }

    public String getDataIndex()
    {
        return dataIndex;
    }

    public void setDataIndex(String dataIndex)
    {
        this.dataIndex = dataIndex;
        this.id="idC"+dataIndex;
    }

    public String getAlign()
    {
        return align;
    }

    public void setAlign(String align)
    {
        this.align = align;
    }

    public boolean isSortable()
    {
        return sortable;
    }

    public void setSortable(boolean sortable)
    {
        this.sortable = sortable;
    }

    public int getFlex()
    {
        return flex;
    }

    public void setFlex(int flex)
    {
        this.flex = flex;
    }

    public String getRenderer()
    {
        return renderer;
    }

    public void setRenderer(String renderer)
    {
        this.renderer = renderer;
    }

    public String getXtype()
    {
        return xtype;
    }

    public void setXtype(String xtype)
    {
        this.xtype = xtype;
    }

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }


    public List<MyColumn> getColumns()
    {
        return columns;
    }

    public void addColumns(MyColumn column)
    {
        if(this.columns==null)
            this.columns=new ArrayList<MyColumn>();

        this.columns.add(column);
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
