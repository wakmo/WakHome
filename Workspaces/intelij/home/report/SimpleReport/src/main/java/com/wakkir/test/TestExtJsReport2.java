package com.wakkir.test;


import com.wakkir.extjs.*;
import com.wakkir.report.Constant;
import com.wakkir.util.DecimalFormatter;
import com.wakkir.util.FeedbackUtils;
import org.apache.fop.apps.*;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.codehaus.jackson.map.ObjectMapper;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 16/12/12
 * Time: 01:19
 * To change this template use File | Settings | File Templates.
 */
//http://elvishsu66.blogspot.co.uk/2011/05/dynamic-gridpanel-for-extjs-4.html
public class TestExtJsReport2
{
    private final static Logger logger=Logger.getLogger(TestExtJsReport2.class);
    private static FeedbackUtils fu=new    FeedbackUtils(logger);

    @SuppressWarnings("unused")
    private static Template my_report_body_html_tmpl;
    private static Template my_report_body_wordml_tmpl;
    private static Template my_report_body_fo_tmpl;
    @SuppressWarnings("unused")
    private static String appContext="myreport";


    public void runReport()
    {

        MyReport report=new MyReport() ;
        int NumberOfRows=1000;
        int NumberOfColumns=8;

        //------------start : METADATA------------------------------------------

        MetaData metaData=new MetaData();
        metaData.setIdProperty("id");
        metaData.setTotalProperty("totalCount");
        metaData.setSuccessProperty("success");
        metaData.setRoot("data");

        for (int x=0;x<NumberOfColumns;x++)   //Adding cells
        {
            MyField field=new MyField();
            field.setIndex(x);
            field.setName(MyDefault.COLUMN_KEY_PREFIX+x);
            field.setMapping(MyDefault.DATA_KEY_PREFIX+ x);

            MyColumn column= field.getColumn();
            column.setIndex(x);
            column.setHeader("H"+x);
            column.setDataIndex(field.getName());//"column"+x);
            //column.setSortable(true);

            if(x%6==0)
            {
                column.setDataType("group");
            }
            if(x%6==1)
            {
                column.setAlign("right");
                column.setRenderer("change");
                //column.setXtype("numbercolumn");
                column.setFormat("0.000");
                column.setDataType("number");
            }
            if(x%6==2)
            {

            }
            if(x%6==3)
            {
                column.setAlign("right");
                column.setRenderer("pctChange");
                //column.setXtype("numbercolumn");
                column.setFormat("0.000");
                column.setDataType("number");
            }
            if(x%6==4)
            {
                column.setAlign("right");
                column.setFormat("USD###,###.000");
                column.setDataType("number");


            }
            if(x%6==5)
            {
                field.setType("int");

                column.setAlign("center");
                column.setRenderer("render2APMDate");
                column.setDataType("date");
            }

            report.getColumns().add(column);
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
        Map<String,BigDecimal> overallTotal=new HashMap<String, BigDecimal>();
        for (int y=0;y<NumberOfRows;y++)   //Adding rows
        {
            Map<String,String> data=new HashMap<String, String>();

            for(MyField field: metaData.getFields())
            {
                String dataKey=field.getMapping();
                String dataValue= getDataValue(field,y);
                String formattedDataValue= getFormattedDataValue(dataValue,field,y);
                data.put(dataKey,formattedDataValue);
                if("number".equalsIgnoreCase(field.getColumn().getDataType()))
                {
                    BigDecimal tol=overallTotal.get(dataKey);
                    tol=tol!=null?tol : new BigDecimal(0);
                    overallTotal.put(dataKey,tol.add(new BigDecimal(dataValue)));
                }
            }
            report.getData().add(data);
        }
        report.getTotals().put("AllTotal",overallTotal);
        //------------end : ROWDATA------------------------------------------

        report.addFooter("Foort11") ;
        report.addFooter("Foort12") ;

        report.addTitle("Title1") ;

        //------------start : JSON------------------------------------------

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
        path="C:\\App\\sunApp\\glassfish\\domains\\domain1\\applications\\j2ee-modules\\apmgui\\pages\\mydcolumn";
        fu.writeFile(path,"datax.json",str);
        //utils.writeFile("E:\\Wakkir\\workspaces\\intellij11\\AppHome\\output\\report","myjason.rows",rows.toString());
        //System.out.println("myjason--->\n"+ jsonObject);
        //System.out.println("myjason--->\n"+ rows.getDataRows());

        //----------------------------------------------------
        try
        {
            my_report_body_html_tmpl=fu.getTemplate(appContext,"report_html_body.vm");
            my_report_body_wordml_tmpl=fu.getTemplate(appContext,"report_wordml_body.vm");
            my_report_body_fo_tmpl=fu.getTemplate(appContext,"report_fo_body.vm");

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
            context.put(Constant.TOTALS, report.getTotals());

            StringWriter writer = new StringWriter();
            my_report_body_html_tmpl.merge(context, writer);
            //logger.debug("File Data:"+writer.toString());
            fu.writeFile("C:\\Wakkir\\Workspaces\\intelij\\AppHome\\output\\report","report3.html",writer.toString());

            StringWriter writer2 = new StringWriter();
            my_report_body_wordml_tmpl.merge(context, writer2);
            //logger.debug("File Data:"+writer.toString());
            fu.writeFile("C:\\Wakkir\\Workspaces\\intelij\\AppHome\\output\\report","report3.xml",writer2.toString());

            StringWriter writer3 = new StringWriter();
            my_report_body_fo_tmpl.merge(context, writer3);
            //logger.debug("File Data:"+writer.toString());
            fu.writeFile("C:\\Wakkir\\Workspaces\\intelij\\AppHome\\output\\report","report3.fo",writer3.toString());

            File out = new File("C:\\Wakkir\\Workspaces\\intelij\\AppHome\\output\\report\\report3.pdf");
            FileOutputStream fos = new FileOutputStream(out);
            try
            {

                fos.write(createPDFFile(writer3.toString()));
            }
            finally
            {
                fos.close();
            }


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

        TestExtJsReport2 tt=new TestExtJsReport2();

        double start=System.currentTimeMillis();//*Math.pow(10,6)+System.nanoTime();
        tt.runReport();
        double end=System.currentTimeMillis();//*Math.pow(10,6)+System.nanoTime();
        System.out.println("timespends in ms : "+ (end-start));///Math.pow(10,6));



    }

    String getDataValue(MyField field,int rowId)
    {
        String value=null;
        if("string".equalsIgnoreCase(field.getColumn().getDataType()))
        {
            value=rowId+": Data Value of ("+field.getMapping()+")";
        }
        else if("number".equalsIgnoreCase(field.getColumn().getDataType()))
        {
            value=""+((field.getIndex()+1.0)*10000.0/(field.getIndex()+rowId+2.0));
        }
        else if("boolean".equalsIgnoreCase(field.getColumn().getDataType()))
        {
            value="true";
        }
        else if("date".equalsIgnoreCase(field.getColumn().getDataType()))
        {
            //value=""+((rowId+10)*(field.getIndex()-10));
            value=""+System.currentTimeMillis();

        }
        else if("group".equalsIgnoreCase(field.getColumn().getDataType()))
        {
            //value=""+((rowId+10)*(field.getIndex()-10));
            value="groupColumn";

        }
        else
        {
             value="valueeeeee";
        }

        return  value;

    }

    String getFormattedDataValue(String value,MyField field,int rowId)
    {

        if("string".equalsIgnoreCase(field.getColumn().getDataType()))
        {

        }
        else if("number".equalsIgnoreCase(field.getColumn().getDataType()))
        {

            if(field.getColumn().getFormat()!=null && field.getColumn().getFormat().length()>0)
                value=DecimalFormatter.getValue(field.getColumn().getFormat(),value,Locale.GERMANY);//new Locale("de", "GB"));
        }
        else if("boolean".equalsIgnoreCase(field.getColumn().getDataType()))
        {

        }
        else if("date".equalsIgnoreCase(field.getColumn().getDataType()))
        {

        }
        else if("group".equalsIgnoreCase(field.getColumn().getDataType()))
        {

        }
        else
        {

        }

        return  value;

    }

    private byte[] convertFO2PDF(String foFileContents)
    {

        FopFactory fopFactory = FopFactory.newInstance();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] bytes = null;

        try
        {
            //fopFactory.setUserConfig(new File(Reporter.getReportConfigDirectory() + File.separatorChar + "config" + File.separatorChar + "fo-conf" + File.separatorChar + "fop.xconf"));
            //fopFactory.setFontBaseURL(Reporter.getReportConfigDirectory() + File.separatorChar + "config" + File.separatorChar + "fo-conf");
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

            // configure foUserAgent as desired

            // Setup output stream. Note: Using BufferedOutputStream
            // for performance reasons (helpful with FileOutputStreams).

            // Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Setup JAXP using identity transformer
            TransformerFactory factory = TransformerFactory.newInstance();

            Transformer transformer = factory.newTransformer(); // identity
            // transformer

            StringReader sr = new StringReader(foFileContents);
            // Setup input stream
            Source src = new StreamSource(sr);

            // Resulting SAX events (the generated FO) must be piped through to
            // FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            transformer.transform(src, res);

            bytes = out.toByteArray();
            out.flush();

        }
        catch (IOException e)
        {
            logger.error("", e);
        }
        catch (TransformerException e)
        {
            logger.error("", e);
        }
        catch (Exception e)
        {
            logger.error("", e);
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                logger.error("", e);
            }
        }

        return bytes;
    }


    public byte[] createPDFFile(String foFileContents) throws IOException
    {
        File file = File.createTempFile("" + System.currentTimeMillis(), ".pdf");

        //URL url = new File(templateFilePath + PRESCRIPTION_URL).toURI().toURL();
        // creation of transform source
        //StreamSource transformSource = new StreamSource(url.openStream());
        // create an instance of fop factory
        FopFactory fopFactory = FopFactory.newInstance();
        // a user agent is needed for transformation
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        // to store output
        ByteArrayOutputStream pdfoutStream = new ByteArrayOutputStream();
        byte[] bytes = null;

        StreamSource source = new StreamSource(new StringReader(foFileContents));
        Transformer xslfoTransformer;
        try
        {
            TransformerFactory transfact = TransformerFactory.newInstance();


                xslfoTransformer = transfact.newTransformer();

            // Construct fop with desired output format
            Fop fop;
            try
            {
                fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, pdfoutStream);
                // Resulting SAX events (the generated FO)
                // must be piped through to FOP
                Result res = new SAXResult(fop.getDefaultHandler());



                // Start XSLT transformation and FOP processing

                    // everything will happen here..

                    xslfoTransformer.transform(source, res);

                    bytes = pdfoutStream.toByteArray();

                    pdfoutStream.flush();


            }
            catch (TransformerException e)
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            catch (FOPException e)
            {
                e.printStackTrace();
            }
        }
        catch (TransformerConfigurationException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (TransformerFactoryConfigurationError e)
        {
            e.printStackTrace();
        }
        finally {
            pdfoutStream.close();
        }
        return bytes;
    }
}
