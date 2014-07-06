package com.wakkir.util;


import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir.muzammil
 * Date: 19/06/12
 * Time: 15:02
 * To change this template use File | Settings | File Templates.
 */
public class FeedbackUtils  implements IFeedbackUtils
{
    private final Logger logger;
    private StreamingResultSetEnabledJdbcTemplate jdbcTemplate;

    private final boolean isQueryExternalized =false;
    private final boolean isTemplateExternalized =false;

    private String apmHomeDirectory;
    private String resourcesDirectory;
    private String templateDirectory;
    private String sqlDirectory;
    private String outputDirectory;
    private String inputDirectory;

    //============================================================================
    public FeedbackUtils(Logger logger)
    {
        this.logger = logger;
        logger.debug("FeedbackUtils instance created");
    }

    public FeedbackUtils(Logger logger, StreamingResultSetEnabledJdbcTemplate jdbcTemplate)
    {
        this.logger = logger;
        this.jdbcTemplate=jdbcTemplate;
        try
        {
            apmHomeDirectory=this.getAPMHome();
            resourcesDirectory=apmHomeDirectory+File.separator+Constant.RESOURCE_FOLDER;
            outputDirectory=apmHomeDirectory+File.separator+Constant.OUTPUT_FOLDER;
            inputDirectory=apmHomeDirectory+File.separator+Constant.INPUT_FOLDER;

            templateDirectory=apmHomeDirectory+File.separator+Constant.RESOURCE_FOLDER+File.separator+Constant.TEMPLATE_FOLDER;
            sqlDirectory=apmHomeDirectory+File.separator+Constant.RESOURCE_FOLDER+File.separator+Constant.SQL_FOLDER;

        }
        catch(FeedbackUtilException e)
        {
            logger.error("Error in APM home ",e);
        }
        logger.debug("FeedbackUtils instance created");
    }

    //=============================================================================
    // Handling  Data into file
    //=============================================================================

    public boolean writeFile(String outputFileDestination, String fileName, String payload)
    {
        logger.debug("FileGroupDestination:" + outputFileDestination+", FileName:"+fileName);
        //logger.debug("Payload:"+payload);

        boolean isErrorWhileWrite=false;

        //String outputFileDestination = fileGroupDestination == null ? outputFilesDirectory : fileGroupDestination;
        //String outputFileDestination = fileGroupDestination;

        FileWriter fileWritter=null;
        BufferedWriter bufferWritter=null;
        try
        {
            File theDir = new File(outputFileDestination);
            // if the directories do not exist, create them
            if (!theDir.exists())
            {
                if (theDir.mkdirs())
                {
                    logger.debug("DIR created : " + outputFileDestination);
                }
            }

            File file = new File(outputFileDestination, fileName.trim());

            // if file doesnt exists, then create it
            if (!file.exists())
            {
                file.createNewFile();
                logger.info("File created : " + file.getAbsoluteFile());
            }
            fileWritter = new FileWriter(file.getAbsoluteFile(), true);
            bufferWritter = new BufferedWriter(fileWritter);

            bufferWritter.write(payload);
            isErrorWhileWrite=false;
        }
        catch (IOException e)
        {
            isErrorWhileWrite=true;
            logger.error("writeFile : IOException while writing to the file..", e);
        }
        finally
        {
            try
            {
                if(bufferWritter!=null)
                    bufferWritter.close();
            }
            catch (IOException e)
            {
                logger.warn("writeFile : IOException while closing a BufferedWriter connection..",e);
            }
            try
            {
                if(fileWritter!=null)
                    fileWritter.close();
            }
            catch (IOException e)
            {
                logger.warn("writeFile : IOException while closing a FileWriter connection..",e);
            }
        }
        return  isErrorWhileWrite;

    }
    //=============================================================================
    public boolean renameFile(String outputFileDestination, String oldFileName,  String newFileName)
    {
        //logger.debug("FileGroupDestination:" + fileGroupDestination+", FileName:"+fileName+", \nPayload:"+payload);

        boolean isErrorWhileRename=false;


        File newfile = new File(outputFileDestination, newFileName.trim());
        File oldfile = new File(outputFileDestination, oldFileName.trim());
        // if file doesnt exists, then create it
        if (oldfile.exists())
        {
            if(oldfile.renameTo(newfile))
            {
                logger.info("File renamed : from " +oldfile.getAbsoluteFile()+" to "+newfile.getAbsoluteFile());
                isErrorWhileRename=false;
            }
            else
            {
                logger.warn("File rename failed : from " +oldfile.getAbsoluteFile()+" to "+newfile.getAbsoluteFile());
                isErrorWhileRename=true;
            }
        }
        else
        {
            logger.warn("File " +oldfile.getAbsoluteFile()+" doesn't exist ");
            isErrorWhileRename=true;
        }
        return  isErrorWhileRename;

    }
    //=============================================================================
    // Handling General Utilities
    //=============================================================================
    public Timestamp getCurrentDate()
    {
        return new Timestamp(new Date().getTime());
    }

    //=============================================================================

    public Timestamp  getStartDate(String scope,int offset)
    {
        Calendar cal = getOffSetDate(scope,offset);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return new Timestamp(cal.getTime().getTime());
    }
    //=============================================================================

    public Timestamp  getEndDate(String scope,int offset)
    {
        return getStartDate(scope,offset+1);
    }
    //=============================================================================

    public String  getFormattedDate(Date date,String format)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date).toUpperCase();
    }

    //=============================================================================

    public String  translateToISO8601ColonDate(String udcDate)
    {
        if(udcDate!=null && udcDate.length()>=25)
            return udcDate.substring(0,udcDate.length()-2)+":"+udcDate.substring(udcDate.length()-2);
        else
            return   udcDate;
    }
    //=============================================================================

    public String  getEmptyIfNegative(int value)
    {
        return value<0?"":value+"";
    }
    //=============================================================================
    public String  getEmptyIfNegative(long value)
    {
        return value<0?"":value+"";
    }
    //=============================================================================

    public String  getEmptyIfNull(BigDecimal value)
    {
        return value!=null?value.toPlainString():"";
    }
    //=============================================================================

    public String  getEmptyIfNull(String value)
    {
        return value!=null?value:"";
    }
    //=============================================================================

    public boolean  isNullorEmpty(String value)
    {
        return value!=null?(value.length()>0?false:true):true;
    }
    //=============================================================================

    public String  getDefaultIfNull(String value,String defValue)
    {
        return value!=null?value:defValue;
    }

    //=============================================================================

    public String getApplicationError(Exception e,boolean isStackTrace)
    {
        if(isStackTrace)
        {
            StringWriter sWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(sWriter));
            return sWriter.getBuffer().toString();
        }
        else
            return e.getMessage();
    }

    //=============================================================================

    public String  getAPMHomeDirectory()
    {
        return  apmHomeDirectory;
    }
    //=============================================================================

    public String  getTemplateDirectory()
    {
        return  templateDirectory;
    }
    //=============================================================================

    public String  getSqlDirectory()
    {
        return  sqlDirectory;
    }
    //=============================================================================

    public String  getOutputDirectory()
    {
        return  outputDirectory;
    }
    //=============================================================================

    public String  getInputDirectory()
    {
        return  inputDirectory;
    }
    //=============================================================================
    // Handling  Velocity Templates
    //=============================================================================

    public Template getTemplate(String templateFileName) throws FeedbackUtilException
    {
        return   getTemplate(null,templateFileName,isTemplateExternalized);
    }
    //=============================================================================
    public Template getTemplate(String templateContext,String templateFileName) throws FeedbackUtilException
    {
        return   getTemplate(templateContext,templateFileName,isTemplateExternalized);
    }
    //=============================================================================
    public Template getTemplate(String templateFileName,boolean isExternalized) throws FeedbackUtilException
    {
        return   getTemplate(null,templateFileName,isExternalized);
    }
    //=============================================================================

    public Template getTemplate(String templateContext,String templateFileName,boolean isExternalized) throws FeedbackUtilException
    {
        VelocityEngine ve = new VelocityEngine();
        String templatePath;

        if(isExternalized)
        {
            templatePath=templateDirectory+(isNullorEmpty(templateContext)?"":File.separator+templateContext);
            ve.setProperty("file.resource.loader.path", templatePath);
        }
        else
        {
            //Due to strange reason File.separator doesn't work for  ClasspathResourceLoader and it always expect '/'
            //templatePath=File.separator+Constant.TEMPLATE_FOLDER+File.separator+templateContext+File.separator;
            templatePath=Constant.FILE_SEPARATOR+Constant.TEMPLATE_FOLDER+Constant.FILE_SEPARATOR+(isNullorEmpty(templateContext)?"":templateContext+Constant.FILE_SEPARATOR);
            ve.setProperty("resource.loader", "feedbackloader");
            ve.setProperty("feedbackloader.resource.loader.class", "com.wakkir.util.PrefixedClasspathResourceLoader");
            ve.setProperty("feedbackloader.resource.loader.prefix", templatePath);
        }
        logger.debug("Loading Template : [context:" + templateContext + ", path:" + templatePath + ", file:"+templateFileName+",isTemplateExternalized:"+ isExternalized +"]");

        Template template = null;
        try
        {
            ve.init();
            template = ve.getTemplate(templateFileName);
            logger.debug("Template file loaded successfully....\n[context:" + templateContext + ", path:" + templatePath + ", file:"+templateFileName+",isTemplateExternalized:"+ isExternalized +"]");
        }
        catch (Exception e)
        {
            String errMsg = "Error occurred while loading Template file ";
            logger.error(errMsg);
            throw new FeedbackUtilException(errMsg);
        }
        return template;
    }

    //=============================================================================
    /////////// Query Manager ///////////////////////////////////////////////////
    //=============================================================================

    public String getQuery(String queryFileName,String queryId) throws FeedbackUtilException
    {
        return getQuery(null,queryFileName,queryId,isQueryExternalized);
    }
    //=============================================================================

    public String getQuery(String queryContext,String queryFileName,String queryId) throws FeedbackUtilException
    {
        return getQuery(queryContext,queryFileName,queryId,isQueryExternalized);
    }
    //=============================================================================

    public String getQuery(String queryFileName,String queryId,boolean isExternalized) throws FeedbackUtilException
    {
        return getQuery(null,queryFileName,queryId,isExternalized);
    }
    //=============================================================================

    public String getQuery(String queryContext,String queryFileName,String queryId,boolean isExternalized) throws FeedbackUtilException
    {
        String filePath;
        if(isExternalized)
            filePath=sqlDirectory+File.separator+(isNullorEmpty(queryContext)?"":queryContext+File.separator);
        else
            filePath=Constant.FILE_SEPARATOR+Constant.SQL_FOLDER+Constant.FILE_SEPARATOR+(isNullorEmpty(queryContext)?"":queryContext+Constant.FILE_SEPARATOR);

        filePath=filePath+queryFileName+Constant.SQL_FILE_EXTENTION;

        logger.info("Loading sql file : \n[file:" + filePath + ", query:" + queryId + ", isQueryExternalized:"+ isExternalized +"]");
        Properties properties = readProperties(filePath, isExternalized);
        String querySelected = properties.getProperty(queryId);
        logger.debug("Selected query  : [file:" + filePath + ", query:" + queryId + ", isQueryExternalized:"+isExternalized+"]\n" + querySelected);
        return querySelected;
    }

    //=============================================================================
    /////////// Private Methods ///////////////////////////////////////////////////
    //=============================================================================

    private Properties readProperties(String xmlFileName,boolean isExternalized) throws FeedbackUtilException
    {
        Properties properties = new Properties();
        //InputStream is = this.getClass().getClassLoader().getResourceAsStream(xmlFileName);
        //InputStream is = this.getClass().getResourceAsStream("/com/test/querymanager/"+xmlFileName);
        InputStream is;
        try
        {
            if(isExternalized)
                is=new FileInputStream(xmlFileName);
            else
                is = this.getClass().getClassLoader().getResourceAsStream(xmlFileName);

            logger.debug("loading sql file : " + xmlFileName);
            properties.loadFromXML(is);
        }
        catch (IOException e)
        {
            String errMsg = "Error occurred while loading SQL file ";
            logger.error(errMsg);
            throw new FeedbackUtilException(errMsg);
        }
        return properties;
    }

    //=============================================================================

    private Calendar getOffSetDate(String scope,int offset)
    {
        Calendar cal = Calendar.getInstance();
        if("YEAR".equalsIgnoreCase(scope))
            cal.add(Calendar.YEAR, offset);
        else if("MONTH".equalsIgnoreCase(scope))
            cal.add(Calendar.MONTH, offset);
        else if("DATE".equalsIgnoreCase(scope))
            cal.add(Calendar.DATE, offset);
        else if("HOUR".equalsIgnoreCase(scope))
            cal.add(Calendar.HOUR, offset);
        else if("MINUTE".equalsIgnoreCase(scope))
            cal.add(Calendar.MINUTE, offset);
        else if("SECOND".equalsIgnoreCase(scope))
            cal.add(Calendar.SECOND, offset);
        else if("MILLISECOND".equalsIgnoreCase(scope))
            cal.add(Calendar.MILLISECOND, offset);
        else cal.add(Calendar.DATE, offset);

        return cal;
    }

    //=============================================================================

    private String  getAPMHome() throws  FeedbackUtilException
    {
        String apmHome=System.getProperty(Constant.APM_HOME);  // /opt/aconite/apm
        if(apmHome==null)
        {
            String errMsg = "APM home is NULL";
            logger.error(errMsg);
            throw new FeedbackUtilException(errMsg);
        }
        else
        {
            logger.debug("APM home is "+apmHome);
            URL url = null;
            File file = null;
            try
            {
                url = new URL(apmHome);
            }
            catch (MalformedURLException e)
            {
                String errMsg = "getAPMHome : apm.home:MalformedURLException";
                logger.error(errMsg, e);
                throw new FeedbackUtilException(errMsg, e);
            }
            try
            {
                file = new File(url.toURI());
            }
            catch (URISyntaxException e)
            {
                String errMsg = "getAPMHome : apm.home:URISyntaxException";
                logger.error(errMsg, e);
                throw new FeedbackUtilException(errMsg, e);
            }
            if(file==null)
            {
                String errMsg = "getAPMHome : APM home is not Valid";
                logger.error(errMsg);
                throw new FeedbackUtilException(errMsg);
            }
            else
                apmHome=file.getPath();
        }
        logger.info("APM home is being configured as : "+apmHome);
        return  apmHome;

    }
    //=============================================================================
}
