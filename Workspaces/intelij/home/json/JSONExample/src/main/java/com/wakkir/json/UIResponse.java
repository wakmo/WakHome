package com.wakkir.json;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 16/12/12
 * Time: 00:27
 * To change this template use File | Settings | File Templates.
 */
public class UIResponse
{
    private JSONObject summary=new JSONObject();

    public UIResponse()
    {

    }

    public JSONObject getSummary()
    {
        return summary;
    }

    public void setSummary(JSONObject summary)
    {
        this.summary = summary;
    }

    public void addSummary(String key, Object value)
    {
        if(key!=null && key.length()>0)
            summary.element(key,value);
    }

    public void addSummary(String key, Object value,boolean isAddNull)
    {
        if(isAddNull)
            addSummary(key, value);
        else if(value!=null)
            addSummary(key, value);
    }

    public void removeSummary(String key)
    {
        summary.discard(key);
    }

    public Object getValueByKey(String key)
    {
        return summary.get(key);
    }

    public String getJson()
    {
        return summary.toString();
    }

    @Override
    public String toString()
    {
        return summary.toString();
    }
}
