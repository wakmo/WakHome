package com.wakkir.util;

import org.apache.velocity.Template;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
public interface IFeedbackUtils
{
    public boolean writeFile(String fileGroupDestination, String fileName, String payload);
    public boolean renameFile(String outputFileDestination, String oldFileName, String newFileName);
    //-----------------------------------------
    public Timestamp getCurrentDate();
    public Timestamp getStartDate(String scope, int offset);
    public Timestamp getEndDate(String scope, int offset);
    public String    getFormattedDate(Date date, String format);
    public String  translateToISO8601ColonDate(String udcDate);
    //-----------------------------------------
    public String  getEmptyIfNegative(int value);
    public String  getEmptyIfNegative(long value);
    public String  getEmptyIfNull(BigDecimal value);
    public String  getEmptyIfNull(String value);
    public boolean isNullorEmpty(String value);
    public String  getDefaultIfNull(String value, String defValue);
    //-----------------------------------------
    public String  getApplicationError(Exception e, boolean isStackTrace);
    //------------------------------------------
    public String  getAPMHomeDirectory();
    public String  getTemplateDirectory();
    public String  getSqlDirectory();
    public String  getOutputDirectory();
    public String  getInputDirectory();
    //-------------------------------------------
    public String getQuery(String fileName, String queryId) throws FeedbackUtilException;
    public String getQuery(String queryContext, String fileName, String queryId) throws FeedbackUtilException;
    public String getQuery(String fileName, String queryId, boolean isExternalized) throws FeedbackUtilException;
    public String getQuery(String queryContext, String fileName, String queryId, boolean isExternalized) throws FeedbackUtilException;
    //-------------------------------------------
    public Template getTemplate(String templateName) throws FeedbackUtilException;
    public Template getTemplate(String templateContext, String templateName) throws FeedbackUtilException;
    public Template getTemplate(String templateName, boolean isExternalized) throws FeedbackUtilException;
    public Template getTemplate(String templateContext, String templateName, boolean isExternalized) throws FeedbackUtilException;

}
