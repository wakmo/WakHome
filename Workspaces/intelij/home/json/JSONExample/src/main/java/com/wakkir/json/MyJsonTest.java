package com.wakkir.json;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 15/12/12
 * Time: 21:02
 * To change this template use File | Settings | File Templates.
 * http://json-lib.sourceforge.net/snippets.html#JavaJSON
 */
public class MyJsonTest
{
    void CreatingJSONObjectScratch()
    {
        JSONObject jsonObject = new JSONObject()
                .element( "string", "JSON" )
                .element( "integer", "1" )
                .element( "double", "2.0" )
                .element( "boolean", "true" );

    }

    void CreatingJSONObjectJSONformattedstring()
    {
        String str = "{'string':'JSON', 'integer': 1, 'double': 2.0, 'boolean': true}";
        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( str );

        System.out.println("jsonObject.getString--->"+ jsonObject.getString("string"));
    }

    void CreatingJSONObjectMap()
    {
        Map map = new HashMap();
        map.put( "string", "JSON" );
        map.put( "integer", "1" );
        map.put( "double", "2.0" );
        map.put( "boolean", "true" );
        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(map);

        System.out.println("jsonObject.getString--->"+ jsonObject.getString("string"));
        System.out.println("jsonObject.getInt--->"+ jsonObject.getInt("integer"));
        System.out.println("jsonObject.getDouble--->"+ jsonObject.getDouble("double"));
        System.out.println("jsonObject.getBoolean--->"+ jsonObject.getBoolean("boolean"));
        System.out.println("myjason--->\n"+ jsonObject);

        map.put( "name", "Wakkir" );
        JSONObject jsonObject2 = (JSONObject) JSONSerializer.toJSON(map);
        System.out.println("myjason--->\n"+ jsonObject2);

        map.put( "name", "Nadiya" );
        JSONObject jsonObject3 = (JSONObject) JSONSerializer.toJSON(map);
        System.out.println("myjason--->\n"+ jsonObject3);

        //map.put( "name", null );
        JSONObject jsonObject4 = (JSONObject) JSONSerializer.toJSON(map);
        System.out.println("myjason--->\n"+ jsonObject4);

        map.put( "", null );
        JSONObject jsonObject5 = (JSONObject) JSONSerializer.toJSON(map);
        System.out.println("myjason--->\n"+ jsonObject5);

        Pojo pojo = new Pojo();
        pojo.setName("Waaaaa");
        pojo.setAge( 23 );
        //bean.setDooble( 2.0d );
        //bean.setBool( true );

        map.put( "POJO", pojo );
        JSONObject jsonObject6 = (JSONObject) JSONSerializer.toJSON(map);
        System.out.println("myjason--->\n"+ jsonObject6);
        System.out.println("jsonObject.getString--->"+ jsonObject6.getString("POJO"));
        JSONObject jsonObjectx = (JSONObject) JSONSerializer.toJSON( jsonObject6.getString("POJO") );
        System.out.println("jsonObject.getString--->"+ jsonObjectx.getString("name"));
    }

    void CreateJSONObjectJavaBean()
    {
        MyJavaBean bean = new MyJavaBean();
        bean.setString( "JSON" );
        bean.setInteger( 1 );
        bean.setDooble( 2.0d );
        bean.setBool( true );

        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( bean );

        System.out.println("jsonObject.getString--->"+ jsonObject.getString("string"));
    }
    void testUIResponse()
    {
       UIResponse response=new UIResponse();
       response.addSummary("name","Wakkir");
        response.addSummary("ss","rtetr");
       response.addSummary("xcx","sdfsdf");
        System.out.println(response.getSummary());
        response.addSummary("age","34");
        System.out.println(response.getValueByKey("name"));
        response.addSummary("name","Nadiya");
        response.removeSummary("xcx");
        System.out.println(response.getSummary());

    }

    void testMy()
    {
       List<Pojo> pojos=new ArrayList<Pojo>();
        //pojos.add(new Pojo("ada",23));
        //pojos.add(new Pojo("wert",54));
        //pojos.add(new Pojo("dfdf",76));
        //pojos.add(new Pojo("yuytk",35));

        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(pojos.toArray());

        System.out.println("jsonObject.getString--->"+ jsonObject);
    }

    public static void main(String[] args)
    {
        MyJsonTest test=new MyJsonTest();
        //test.CreatingJSONObjectJSONformattedstring();
        //test.CreateJSONObjectJavaBean();
        //test.CreatingJSONObjectMap();
        //test.testUIResponse();
        test.testMy();

    }

}



class MyJavaBean
{
    private String string;
    private int integer;
    private double dooble;
    private boolean bool;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getInteger() {
        return integer;
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }

    public double getDooble() {
        return dooble;
    }

    public void setDooble(double dooble) {
        this.dooble = dooble;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    // getters & setters
}
