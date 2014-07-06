package com.wakkir.test;


import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.wakkir.extjs.MyReport;
import com.wakkir.report.Constant;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.codehaus.jackson.map.ObjectMapper;

import com.wakkir.extjs.MetaData;
import com.wakkir.extjs.MyColumn;
import com.wakkir.extjs.MyField;
import com.wakkir.util.FeedbackUtils;
import org.springframework.jdbc.core.ColumnMapRowMapper;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 16/12/12
 * Time: 01:19
 * To change this template use File | Settings | File Templates.
 */
//http://elvishsu66.blogspot.co.uk/2011/05/dynamic-gridpanel-for-extjs-4.html
public class TestExtJsReport
{
    private final static Logger logger=Logger.getLogger(TestExtJsReport.class);
    private static FeedbackUtils fu=new    FeedbackUtils(logger);

    @SuppressWarnings("unused")
    private static Template my_report_body_tmpl;
    @SuppressWarnings("unused")
    private static String appContext="myreport";


    public void runReport()
    {

        MyReport report=new MyReport() ;
        int NumberOfRows=50;
        int NumberOfColumns=6;

        //------------start : METADATA------------------------------------------

        MetaData metaData=new MetaData();
        metaData.setIdProperty("id");
        metaData.setTotalProperty("totalCount");
        metaData.setSuccessProperty("success");
        metaData.setRoot("data");

        for (int x=0;x<NumberOfColumns;x++)   //Adding cells
        {
            MyField field=new MyField();
            field.setName("column"+x);
            field.setMapping("data" + x);

            if(x%6==0)
            {
                field.setType("string");
                //field.setAllowBlank(false);
                //field.setDefaultValue("");
            }
            if(x%6==1)
            {
                field.setType("int");
                //field.setAllowBlank(false);
                //field.setDefaultValue("0.00");
            }
            if(x%6==2)
            {
                field.setType("float");
                //field.setAllowBlank(false);
                //field.setDefaultValue("0.00");
            }
            if(x%6==3)
            {
                field.setType("int");
                //field.setAllowBlank(false);
                //field.setDefaultValue("");
            }
            if(x%6==4)
            {
                field.setType("int");
                //field.setAllowBlank(false);
                //field.setDefaultValue("");
            }
            if(x%6==5)
            {
                field.setType("string");
                //field.setAllowBlank(false);
                //field.setDefaultValue("");
            }
            //------start : Adding Column Details-------------------
            MyColumn column= field.getColumn();
            column.setHeader("H"+x);
            column.setDataIndex("column"+x);
            column.setSortable(true);

            if(x%6==0)
            {
                column.setAlign("left");
            }
            if(x%6==1)
            {
                column.setAlign("right");
                column.setRenderer("change");
                column.setXtype("numbercolumn");
                column.setFormat("0.00");
            }
            if(x%6==2)
            {
                column.setAlign("right");
                column.setRenderer("pctChange");
                column.setXtype("numbercolumn");
                column.setFormat("0.000");
            }
            if(x%6==3)
            {
                column.setAlign("right");
            }
            if(x%6==4)
            {
                column.setAlign("center");
                column.setRenderer("render2APMDate");
            }
            if(x%6==5)
            {
                column.setAlign("left");
            }
            report.getColumns().add(column);
            //------end : Adding Column Details-------------------

            metaData.getFields().add(field);
        }
        //----------------------------------------------------------
        report.setMetaData(metaData);
        report.setSuccess(true);
        report.setTotalCount(NumberOfRows);
        report.setTotalColumn(NumberOfColumns);
        report.setMessage("Load data");
        //------------end : METADATA------------------------------------------
        //------------start : ROWDATA------------------------------------------
        for (int y=0;y<NumberOfRows;y++)   //Adding rows
        {
            Map<String,String> data=new HashMap<String, String>();

            for (int x=0;x<NumberOfColumns;x++)   //Adding cells
            {
                if(x%6==0)
                    data.put("data"+x,x+"name name name"+y);
                if(x%6==1)
                    data.put("data"+x,(x*x+y*x-3.0)+"");
                if(x%6==2)
                    data.put("data"+x,""+((x+1.0)*100.0/(x+y+2.0)));
                if(x%6==3)
                    data.put("data"+x,x+y+"");
                if(x%6==4)
                    data.put("data"+x,""+System.currentTimeMillis());
                if(x%6==5)
                    data.put("data"+x,x+"XX"+y);

            }
            report.getData().add(data);
        }
        //------------end : ROWDATA------------------------------------------
        //------------start : COLUMNDATA------------------------------------------
        /*
        //Adding column infomation
        for (int x=0;x<NumberOfColumns;x++)   //Adding cells
        {
            MyColumn column=new MyColumn();
            column.setHeader("H"+x);
            column.setDataIndex("column"+x);
            column.setSortable(true);

            if(x%6==0)
            {
                column.setAlign("left");
            }
            if(x%6==1)
            {
                column.setAlign("right");
                column.setRenderer("change");
                column.setXtype("numbercolumn");
                column.setFormat("0.00");
            }
            if(x%6==2)
            {
                column.setAlign("right");
                column.setRenderer("pctChange");
                column.setXtype("numbercolumn");
                column.setFormat("0.000");
            }
            if(x%6==3)
            {
                column.setAlign("right");
            }
            if(x%6==4)
            {
                column.setAlign("center");
                column.setRenderer("render2APMDate");
            }
            if(x%6==5)
            {
                column.setAlign("left");
            }
            report.getColumns().add(column);
        }
        */

        /*
        for (int y=1;y<3;y++)   //Adding cells
        {
            MyColumn column2=new MyColumn();

            column2.setHeader("H"+y);
            column2.setDataIndex("Name"+y);
            column2.setSortable(true);

            if(y%6==1)
            {
                column2.setAlign("right");
                column2.setRenderer("change");
                column2.setXtype("numbercolumn");
                column2.setFormat("0.00");
            }
            if(y%6==2)
            {
                column2.setAlign("right");
                column2.setRenderer("pctChange");
                column2.setXtype("numbercolumn");
                column2.setFormat("0.000");
            }
            report.getColumns().get(1).addColumns(column2);
        }

        */
        //------------end : COLUMNDATA------------------------------------------

        report.addFooter("Foort11") ;
        report.addFooter("Foort12") ;

        report.addTitle("Title1") ;

        //------------start : JSON------------------------------------------
        /*
        //http://stackoverflow.com/questions/8025852/how-to-exclude-properties-from-bean-to-json-at-runtime
        //http://stackoverflow.com/questions/8700474/jackson-serialise-to-json-excluding-type
        PropertyFilter pf = new PropertyFilter()
        {
            public boolean apply( Object source, String name, Object value )
            {
                if( value != null && Number.class.isAssignableFrom( value.getClass() ) )
                {
                    return true;
                }
                return false;
            }
        };
        PrimitiveBean bean = new PrimitiveBean();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setJsonPropertyFilter(pf);
        JSONObject json = JSONObject.fromObject( bean, jsonConfig );
        */

        ObjectMapper mapper = new ObjectMapper();
        String str= null;
        try
        {
            str = mapper.writeValueAsString(report);
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        //------------------------------------------------------
        //------------------------------------------------------

        //System.out.println(str);
        String path="C:\\APP\\SunApp\\domains\\domain1\\applications\\j2ee-modules\\apmgui\\pages\\mydcolumn";
        fu.writeFile(path,"datax.json",str);
        //utils.writeFile("E:\\Wakkir\\workspaces\\intellij11\\AppHome\\output\\report","myjason.rows",rows.toString());
        //System.out.println("myjason--->\n"+ jsonObject);
        //System.out.println("myjason--->\n"+ rows.getDataRows());

        //----------------------------------------------------
        try
        {
            my_report_body_tmpl=fu.getTemplate(appContext,"report_html_body.vm");

            VelocityContext context = new VelocityContext();
            context.put(Constant.TOTAL_COUNTS, fu.getEmptyIfNegative(report.getTotalCount()));
            context.put(Constant.TOTAL_COLUMNS, fu.getEmptyIfNegative(report.getTotalColumn()));
            //context.put(Constant.PARAMETERS, fu.getEmptyIfNull(extractLog.getParameters()));
            //context.put(Constant.OFFSET_VALUES, fu.getEmptyIfNull(extractLog.getOffsetValues()));
            //context.put(Constant.DATA_WINDOW, fu.getEmptyIfNull(extractLog.getDataWindow()));
            context.put(Constant.META_DATA, report.getMetaData());
            context.put(Constant.DATA_ROWS, report.getData());
            context.put(Constant.COLUMNS, report.getColumns());
            context.put(Constant.FOORTERS, report.getFooters());
            context.put(Constant.TITLES, report.getTitles());

            StringWriter writer = new StringWriter();
            my_report_body_tmpl.merge(context, writer);
            //logger.debug("File Data:"+writer.toString());
            fu.writeFile("E:\\Wakkir\\workspaces\\intellij11\\AppHome\\output\\report","report3.html",writer.toString());

        }
        catch (Exception e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //----------------------------------------------------


    }

    public static  void main(String[] args)
    {
        System.setProperty("LOG_ROOT","E:\\Wakkir\\workspaces\\intellij11\\AppHome\\logs");

        TestExtJsReport tt=new TestExtJsReport();

        double start=System.currentTimeMillis();//*Math.pow(10,6)+System.nanoTime();
        tt.runReport();
        double end=System.currentTimeMillis();//*Math.pow(10,6)+System.nanoTime();
        System.out.println("timespends in ms : "+ (end-start));///Math.pow(10,6));



    }
}
