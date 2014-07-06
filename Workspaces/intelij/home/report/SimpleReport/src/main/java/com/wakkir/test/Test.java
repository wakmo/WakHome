package com.wakkir.test;


import com.wakkir.report.*;
import com.wakkir.util.FeedbackUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import java.io.StringWriter;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 16/12/12
 * Time: 01:19
 * To change this template use File | Settings | File Templates.
 */
public class Test
{
    private final static Logger logger=Logger.getLogger(Test.class);
    private static FeedbackUtils fu=new    FeedbackUtils(logger);

    private static org.apache.velocity.Template my_report_body_tmpl;
    private static String appContext="myreport";

    public void runReport()
    {
        SimpleReporTable report=new SimpleReporTable() ;
        int NumberOfRows=2;
        int NumberOfCells=1;

        //populate DATA
        //------------------------------------------------------
        //-----------TITLE-------------------------------------------
        for (int y=0;y<1;y++)   //Adding rows
        {
            TitleRow titleRow=new TitleRow();
            //List<DataCell> row=new ArrayList<DataCell>();
            for (int x=0;x<1;x++)   //Adding cells
            {
                TitleCell cell=new TitleCell();
                cell.setData("FeedbackUtils instance created");
                cell.setX(x);
                cell.setY(y);
                cell.setColspan(NumberOfCells);
                titleRow.getCells().add(cell);
            }
            titleRow.setY(y);
            report.getTitleRows().add(titleRow);
        }
        //------------HEADER------------------------------------------
        for (int y=0;y<1;y++)   //Adding rows
        {
            HeaderRow headerRow=new HeaderRow();
            //List<DataCell> row=new ArrayList<DataCell>();
            for (int x=0;x<NumberOfCells;x++)   //Adding cells
            {
                HeaderCell cell=new HeaderCell();
                cell.setData("NAME"+x);
                cell.setX(x);
                cell.setY(y);
                headerRow.getCells().add(cell);
            }
            headerRow.setY(y);
            report.getHeaderRows().add(headerRow);
        }
        //------------DATA------------------------------------------
        for (int y=0;y<NumberOfRows;y++)   //Adding rows
        {
            DataRow dataRow=new DataRow();
            //List<DataCell> row=new ArrayList<DataCell>();
            for (int x=0;x<NumberOfCells;x++)   //Adding cells
            {
                DataCell cell=new DataCell();
                cell.setData(System.nanoTime());
                cell.setType(0);
                cell.setX(x);
                cell.setY(y);
                dataRow.getCells().add(cell);
            }
            dataRow.setY(y);
            report.getDataRows().add(dataRow);
        }

        //-----------FOOTER------------------------------------------
        for (int y=0;y<1;y++)   //Adding rows
        {
            FooterRow footerRow=new FooterRow();
            //List<DataCell> row=new ArrayList<DataCell>();
            for (int x=0;x<1;x++)   //Adding cells
            {
                FooterCell cell=new FooterCell();
                cell.setData("context:myreport, path:/template/myreport/, file:report_html_body.vm,isTemplateExternalized:false");
                cell.setX(x);
                cell.setY(y);
                cell.setColspan(NumberOfCells);
                footerRow.getCells().add(cell);
            }
            footerRow.setY(y);
            report.getFooterRows().add(footerRow);
        }

        //------------------------------------------------------
        //------------------------------------------------------
        /*
        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(report);
        Template myreporttemplate=null;
        try
        {
            my_report_body_tmpl=fu.getTemplate(appContext,"report_html_body.vm");

            VelocityContext context = new VelocityContext();
            context.put(Constant.TOTAL_RECORDS, fu.getEmptyIfNegative(report.getDataRows().size()));
            //context.put(Constant.PARAMETERS, fu.getEmptyIfNull(extractLog.getParameters()));
            //context.put(Constant.OFFSET_VALUES, fu.getEmptyIfNull(extractLog.getOffsetValues()));
            //context.put(Constant.DATA_WINDOW, fu.getEmptyIfNull(extractLog.getDataWindow()));
            context.put(Constant.DATA_ROWS, report.getDataRows());
            context.put(Constant.HEADER_ROWS, report.getHeaderRows());
            context.put(Constant.FOOTER_ROWS, report.getFooterRows());
            context.put(Constant.TITLE_ROWS, report.getTitleRows());

            StringWriter writer = new StringWriter();
            my_report_body_tmpl.merge(context, writer);
            //logger.debug("File Data:"+writer.toString());
            fu.writeFile("E:\\Wakkir\\workspaces\\intellij11\\AppHome\\output\\report","report-"+System.nanoTime()+".html",writer.toString());

        }
        catch (Exception e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        */
        //------------------------------------------------------
        //------------------------------------------------------

        //fu.writeFile("E:\\Wakkir\\workspaces\\intellij11\\AppHome\\output\\report","data-"+System.nanoTime()+".json",jsonObject.toString());
        //utils.writeFile("E:\\Wakkir\\workspaces\\intellij11\\AppHome\\output\\report","myjason.rows",rows.toString());
        //System.out.println("myjason--->\n"+ jsonObject);
        //System.out.println("myjason--->\n"+ rows.getDataRows());


    }

    public static  void main(String[] args)
    {
        System.setProperty("LOG_ROOT","E:\\Wakkir\\workspaces\\intellij11\\AppHome\\logs");

        Test tt=new Test();

        double start=System.currentTimeMillis();//*Math.pow(10,6)+System.nanoTime();
        tt.runReport();
        double end=System.currentTimeMillis();//*Math.pow(10,6)+System.nanoTime();
        System.out.println("timespends in ms : "+ (end-start));///Math.pow(10,6));



    }
}
