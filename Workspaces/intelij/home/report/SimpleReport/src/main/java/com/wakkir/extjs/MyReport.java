package com.wakkir.extjs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/** Created with IntelliJ IDEA. User: wakkir Date: 23/12/12 Time: 02:39 To change this template use File | Settings |
 * File Templates. */
public class MyReport
{
    @JsonProperty
    private boolean		success;

    @JsonProperty
    private int			totalCount;

    @JsonProperty
    private int			totalColumn;

    @JsonProperty
    private String	    message;

    @JsonSerialize(include= JsonSerialize.Inclusion.NON_DEFAULT)
    private List<String>	titles;

    @JsonSerialize(include= JsonSerialize.Inclusion.NON_DEFAULT)
    private List<String>	footers;

    @JsonProperty
    private MetaData	metaData;

    @JsonProperty
    private List<MyColumn>	columns;

    @JsonProperty
    private List<Map<String, String>>	data;

    @JsonProperty
    private Map<String, Map<String, BigDecimal>>	totals;


    public MyReport()
    {
        metaData = new MetaData();
        data = new ArrayList<Map<String, String>>();
        columns = new ArrayList<MyColumn>();
        footers = new ArrayList<String>();
        titles = new ArrayList<String>();
        totals =new HashMap<String,Map<String, BigDecimal>>();
    }

    public MetaData getMetaData()
    {
        return metaData;
    }

    public void setMetaData(MetaData metaData)
    {
        this.metaData = metaData;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalColumn() {
        return totalColumn;
    }

    public void setTotalColumn(int totalColumn) {
        this.totalColumn = totalColumn;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public List<Map<String, String>> getData()
    {
        return data;
    }

    public void addData(Map<String, String> data)
    {
        if(this.data==null)
            this.data=new  ArrayList<Map<String, String>>();

        this.data.add(data);
    }

    public List<MyColumn> getColumns()
    {
        return columns;
    }

    public void addColumn(MyColumn column)
    {
        if(this.columns==null)
            this.columns=new   ArrayList<MyColumn>();

        this.columns.add(column);
    }

    public List<String> getFooters() {
        return footers;
    }

    public void addFooter(String footer)
    {
        if(this.footers==null)
            this.footers=new   ArrayList<String>();

        this.footers.add(footer);
    }

    public List<String> getTitles()
    {
        return titles;
    }

    public void addTitle(String title)
    {
        if(this.titles==null)
            this.titles=new   ArrayList<String>();

        this.titles.add(title);
    }

    public Map<String, Map<String, BigDecimal>> getTotals() {
        return totals;
    }

    public void setTotals(Map<String, Map<String, BigDecimal>> totals) {
        this.totals = totals;
    }
}
