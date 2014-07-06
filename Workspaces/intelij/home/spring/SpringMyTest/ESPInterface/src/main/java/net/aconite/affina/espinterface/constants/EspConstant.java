/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.constants;

/**
 * @author wakkir.muzammil
 */
public class EspConstant
{
    //from Affina to Esp
    public final static String CARD_SETUP_ALERT = "CardSetupAlert";
    public final static String STAGE_SCRIPT_ALERT = "StageScriptAlert";

    //from Esp to Sem
    public final static String CARD_SETUP_REQUEST = "CardSetupRequest";
    public final static String STAGE_SCRIPT_REQUEST = "StageScriptRequest";
    public final static String SCRIPT_STATUS_RESPONSE = "ScriptStatusResponse";

    //from Sem to Esp
    public final static String STAGE_SCRIPT_RESPONSE = "StageScriptResponse";
    public final static String CARD_SETUP_RESPONSE = "CardSetupResponse";

    public final static String PROGRESS_MESSAGE = "ProgressMessage";
    public final static String ERROR_MESSAGE = "ErrorMessage";

    //--------------------------------------------
    public final static String MQ_MESSAGE_TYPE = "espMessageType";
    public final static String MQ_MESSAGE_HASHCODE = "espMessageHashcode";
    public final static String JMS_TEXT_MESSAGE = "jms_text";
    public final static String JMS_BYTES_MESSAGE = "jms_bytes";

    //------Security Related Constant-----------------------------------
    public final static String SC_ENCRYPTED_MESSAGE = "encryptedMessage";
    public final static String SC_DECRYPTED_MESSAGE = "decryptedMessage";

    //------Velocity Template Related Constant-----------------------------------
    public final static String VT_CURRENT_TIME = "currentTime";
    public final static String VT_RESPONSE_TYPE = "responseType";
    public final static String VT_TRACKING_REFERENCE = "trackingReference";
    public final static String VT_STATUS = "status";
    public final static String VT_ERROR_DATA = "errorData";
    public final static String VT_ERROR_DESCRIPTION = "errorDescription";
    public final static String VT_ERROR_CODE = "errorCode";

    //------Database Table Related Constant-----------------------------------
    public final static String DB_AE_TRACKING_ID = "aeTrackingId";


    //------Utils Related Constant-----------------------------------
    public final static String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public final static String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public final static String DATE_FORMAT_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd hh:mm:ss";
    public final static String DATE_FORMAT_YYYY_MM_DD_HHMMSSmmm = "yyyy-MM-dd hh:mm:ss.mmm";
    public final static String DATE_FORMAT_YYYY_MM_DD_HHMMSSmmmZ = "yyyy-MM-dd hh:mm:ss.mmmZ";
    //Atom (ISO 8601)
    public final static String DATE_FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.mmmZ";

}
