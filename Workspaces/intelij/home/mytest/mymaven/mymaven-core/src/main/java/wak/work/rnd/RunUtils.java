/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.work.rnd;

import wak.work.rnd.helper.DFConstant;
import wak.work.rnd.helper.DataCollectionException;
import wak.work.rnd.helper.DFUtilitiesException;
import wak.work.rnd.helper.ByteArrayUtilities;
import wak.work.rnd.helper.DFUtilities;
import wak.work.rnd.helper.AffinaEspUtilException;
import wak.work.rnd.helper.SecurityDataTranslationException;
import wak.work.rnd.helper.AffinaEspUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author wakkir.muzammil
 */
public class RunUtils
{

    private static final Logger logger = LoggerFactory.getLogger(RunUtils.class);
    public static final String DEFAULT_DATA_LIST_SEPERATOR = ",";

    private void toHex()
    {
        Object obj = (Object) ("16");
        String myString = Integer.toHexString(Integer.parseInt(String.valueOf(obj)));
        System.out.println("InHex1  : " + myString);

        if (myString.length() % 2 != 0)
        {
            myString = "0" + myString;
        }
        System.out.println("InHex2  : " + myString);
    }

    private String getValidDataValueList(String dataValueList, String listSeperator)
    {
        //dataValueList="a1,b1,c1";
        //dataValueList=",a2,b2,c2";
        //dataValueList="a3,b3,c3,";
        //dataValueList=",a4,b4,c4,";
        dataValueList = "xdfsdfsd,,,,,";

        if (dataValueList == null || dataValueList.trim().length() == 0)
        {
            System.err.println("Data Value List is null or empty");
        }

        if (listSeperator == null || listSeperator.trim().length() == 0)
        {
            listSeperator = DEFAULT_DATA_LIST_SEPERATOR;
        }

        String dataValueList2beValidated = dataValueList.trim();
        int firstIndex = dataValueList2beValidated.indexOf(listSeperator);
        int lastIndex = dataValueList2beValidated.lastIndexOf(listSeperator);

        System.out.println("firstIndex>" + firstIndex);
        System.out.println("lastIndex>" + lastIndex);
        System.out.println("length>" + dataValueList2beValidated.length());
        boolean isAddPrefix = false;
        boolean isAddSuffix = false;

        String validDataValueList = dataValueList2beValidated;
        if (firstIndex != 0)
        {
            isAddPrefix = true;
        }
        if (validDataValueList.length() > 0 && lastIndex < (validDataValueList.length() - 1))
        {
            isAddSuffix = true;
        }

        if (isAddPrefix && isAddSuffix)
        {
            validDataValueList = listSeperator.concat(validDataValueList).concat(listSeperator);
        }
        else if (isAddPrefix && !isAddSuffix)
        {
            validDataValueList = listSeperator.concat(validDataValueList);
        }
        else if (!isAddPrefix && isAddSuffix)
        {
            validDataValueList = validDataValueList.concat(listSeperator);
        }


        return validDataValueList;
    }

    private void testEndDateTime()
    {
        Calendar cal = Calendar.getInstance();

        //cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        //cal.setTime(new Date());
        cal.set(2017, 12, 1);
        //cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
        cal.add(Calendar.MONTH, 1);

        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, -1);

        System.out.println("date1 : " + cal.toString());
        System.out.println("TimeInMillis : " + cal.getTimeInMillis());
        System.out.println("TimeInMillis2 : " + (cal.getTime()).getTime());
        System.out.println("HOUR:" + cal.get(Calendar.HOUR));
        System.out.println("HOUR_OF_DAY:" + cal.get(Calendar.HOUR_OF_DAY));
        System.out.println("YEAR:" + cal.get(Calendar.YEAR));
        String str = "0" + String.valueOf(cal.get(Calendar.MONTH) + 1);
        System.out.println("MONTH:" + str.substring(str.length() - 2, str.length()));
        System.out.println("DATE:" + cal.get(Calendar.DATE));


        long ltime = cal.getTimeInMillis() - 0;

        SimpleDateFormat dateFormat = new SimpleDateFormat(AffinaEspUtils.DATE_FORMAT_ISO8601);
        System.out.println("date-1 : " + dateFormat.format(new Date(ltime)).toUpperCase());
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        System.out.println("longtime " + ltime + " in Hex : " + Long.toHexString(ltime).toUpperCase());
        System.out.println("date-2 : " + dateFormat.format(new Date(ltime)).toUpperCase());

        System.out.println("#########");

        BigInteger bint = new BigInteger("16011F29618", 16);
        System.out.println("longtime " + bint.longValue() + " in Hex : " + Long.toHexString(bint.longValue()).toUpperCase());
        System.out.println("date-3 : " + dateFormat.format(new Date(bint.longValue())).toUpperCase());

        System.out.println("#########");
        //1512079199999
        //1512086399999

        System.out.println("date-4 : " + dateFormat.format(new Date()).toUpperCase());


    }

    private void testCurrentDateTime()
    {
        Calendar cal = Calendar.getInstance();
        //cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        cal.setTime(new Date());
        //cal.set(Calendar.HOUR_OF_DAY, 0);

        System.out.println("YEAR:" + cal.get(Calendar.YEAR));
        String str = "0" + String.valueOf(cal.get(Calendar.MONTH) + 1);
        System.out.println("MONTH:" + str.substring(str.length() - 2, str.length()));
        System.out.println("DATE:" + cal.get(Calendar.DATE));

        System.out.println("HOUR:" + cal.get(Calendar.HOUR));
        System.out.println("HOUR_OF_DAY:" + cal.get(Calendar.HOUR_OF_DAY));
        System.out.println("MINUTS:" + cal.get(Calendar.MINUTE));
        System.out.println("SECOND:" + cal.get(Calendar.SECOND));

        long ltime = cal.getTimeInMillis() - 0;

        SimpleDateFormat dateFormat = new SimpleDateFormat(AffinaEspUtils.DATE_FORMAT_YYYY_MM_DD_HHMMSSmmm);
        System.out.println("date-1 : " + dateFormat.format(new Date(ltime)).toUpperCase());

        //dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        //System.out.println("date-2 : "+ dateFormat.format(new Date(ltime)).toUpperCase());


        //System.out.println("date-3 : " + DateHelper.getDefaultPayloadHeader());

    }

    private void testString2DateTime() throws ParseException, AffinaEspUtilException
    {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat(AffinaEspUtils.DATE_FORMAT_YYYY_MM_DD_HHMMSSmmm);


        long end = AffinaEspUtils.getEndDateTime("2014/03/18", AffinaEspUtils.DATE_FORMAT_YYYY_MM_DD);

        long start = AffinaEspUtils.getStartDateTime("2014/03/28", AffinaEspUtils.DATE_FORMAT_YYYY_MM_DD);

        System.out.println("long-start : " + start);
        System.out.println("long-end : " + end);



        System.out.println("date-2-start : " + dateFormat1.format(start).toUpperCase());
        System.out.println("date-2-end : " + dateFormat1.format(end).toUpperCase());


    }

    public void mySubstring()
    {
        String i_iMAID = "00112233445566778899";
        String i_iCIN = "112233445566";
        String i_iBIN = "1234567890";
        //String myKeyData=i_iMAID.substring(8, 20);
        //System.out.println(myKeyData);


        String myCINData = i_iCIN.substring(2, 12);
        String myBINData = i_iBIN.substring(0, 6);

        System.out.println("myCINData:" + myCINData);
        System.out.println("myBINData:" + myBINData);

        String myKeyData = myBINData.substring(myBINData.length() - 2) + myCINData.substring(myCINData.length() - 10);

        System.out.println(myKeyData);
    }

    public void readDirectory()
    {
        String basePath = "";
        //basePath="C:\\Wakkir\\TEMP_IMPORT\\LK\\20140328\\OldWay\\Export\\Luottokunta";
        //basePath="C:\\JDrive\\modules\\Affina\\AffinaTestHarness\\Configuration\\LKConfig\\T9000\\SplitVersion\\create\\PMA\\Luottokunta";
        basePath = "C:\\Wakkir\\TEMP_IMPORT\\LK\\20140328\\GP\\Export\\Luottokunta";

        String inputPath = "";
        inputPath = basePath + "\\DataFunction";
        //inputPath=basePath+"\\SourcedFunction";


        File inputFilePath = new File(inputPath);
        File[] files = inputFilePath.listFiles();
        int count = 0;
        assert files != null;
        for (File file : files)
        {
            if (file.isDirectory())
            {
                //System.out.println("Source : "+file.getAbsolutePath());
                System.out.println(file.getName());
                count++;
            }
            else
            {
                count++;
            }
        }

        System.out.println("Total Renamed : " + count);

    }

    public void getDERLength() throws DataCollectionException
    {
        String i_iComment = "getDERLength";
        Object i_iLength = new String("960");
        String i_iUnit = "bits";
        String i_iDataType = "int";
        String i_oDataType = "hex";
        String i_oDataTag = "9f26";

        // Output parameters
        Object o_oDERLength = null;


        System.out.println("########## getDERLength ##########");
        System.out.println("i_iComment  : " + i_iComment);
        System.out.println("i_iLength   : " + i_iLength);
        System.out.println("i_iUnit     : " + i_iUnit);
        System.out.println("i_iDataType : " + i_iDataType);
        System.out.println("i_oDataType : " + i_oDataType);
        System.out.println("i_oDataTag  : " + i_oDataTag);


        String inputUnit = i_iUnit;
        if (i_iUnit == null || i_iUnit.trim().length() < 1)
        {
            inputUnit = DFConstant.UNIT_BYTES;
            logger.warn("iUnit is not being defined and it will set as " + inputUnit);
        }

        if (i_iLength == null)
        {
            throw new DataCollectionException("Input length cannot be null", -4001);
        }

        int inputLength = 0;

        try
        {
            String hexString = DFUtilities.convertValue2HexString(1, i_iDataType, i_iLength);
            System.out.println("hexString: " + hexString);
            inputLength = ByteArrayUtilities.convertHexStringToInt(hexString);
        }
        catch (DFUtilitiesException ex)
        {
            throw new DataCollectionException(ex.getMessage(), ex.getErrorCode(), ex);
        }

        System.out.println("inputLength: " + inputLength);

        if (inputLength < 1)
        {
            throw new DataCollectionException("Input length must be a positive interger", -4002);
        }
        else if (DFConstant.UNIT_BITS.equalsIgnoreCase(inputUnit) && inputLength % 8 != 0)
        {
            throw new DataCollectionException("Input length divided by 8 leaves non zero remainder", -4003);
        }

        if (DFConstant.UNIT_BITS.equalsIgnoreCase(inputUnit))
        {
            inputLength = inputLength / 8;
        }
        else if (DFConstant.UNIT_BYTES.equalsIgnoreCase(inputUnit))
        {
            inputLength = inputLength / 1;
        }
        else
        {
            throw new DataCollectionException("Input unit must be defined as bits or bytes", -4004);
        }

        String derLength = ByteArrayUtilities.getDERLength(inputLength);

        try
        {
            o_oDERLength = DFUtilities.convertHexString2Value(i_oDataTag, i_oDataType, derLength);
        }
        catch(DFUtilitiesException e)
        {
            System.out.println("Invalid hex value");
            e.printStackTrace();
        }

        System.out.println("o_oDERLength: " + o_oDERLength);
    }
    
    public void myDecription() throws SecurityDataTranslationException
    {
        byte[] caPublicExponentByte = ByteArrayUtilities.byteify_nospaces("010001");
        byte[] caPrivateExponentByte = ByteArrayUtilities.byteify_nospaces("82B67A8209E25A022A41F10DCAED28DFE147C955ADCFE9CB47D984C785266856991C2B4C625FA1634734B40A7A3B33F71C60907EA9E54CB5721547FA9528C8E6B4DFA2F88554F6B1D5F4C4701EC94E5B4E823F9D4E55CB4E03CB4595C506E70D99DA9FFC0E3FF3FF3244648C75936F8C40D8CFF256DD80B50908B451AA0EF961");
        
        byte[] caModulusByte = ByteArrayUtilities.byteify_nospaces("B2F52A91225A11B9CDF4642D0D0440234ECF86398CC2BA075B5A2CBF0F4A1248DB737567F228DE1AB5F27738EB61CC7298E54279D96B7B05BB5B9D9E680904574AD358C5B4C7A1B1A8B0E807FA5571801AAC35DC336CB2497760C82CFD16EC8D5BD2D69BF64388F242528A66CA178C9ABEA81BC82F5D742CAD2C77C80DF48563");
        
        byte[] encryptedDataByte = ByteArrayUtilities.byteify_nospaces("29b05e21196875e48b133c0eb2f7de79170361a3d1e46d4acf911979127e6f51cb12da8b40fd56b27b7bb723707a65b5b07f78816d1b9f104b902b17d4b9c77d118c3e8c6cb2335ecd24081c07d0cb2ae9782a7fde607a816bb891129555af8f8d9babf66b3a3f4e96bdb4dc63e942a227ee060d39d55b10be179423fc91db94");
//        byte[] clearDataByte = ByteArrayUtilities.byteify_nospaces(ByteArrayUtilities.convertStringToHex("wakkir"));
        byte[] clearDataByte = "wakkir".getBytes();
        //byte[] clearDataByte = ByteArrayUtilities.byteify_nospaces("1234");
        
        
        
        BigInteger caPrivateExponent = new BigInteger(1, caPrivateExponentByte);
        BigInteger caPublicExponent = new BigInteger(1, caPublicExponentByte);
        
        BigInteger caModulus = new BigInteger(1, caModulusByte);
        
        BigInteger encryptedData = new BigInteger(1, encryptedDataByte);
        
        BigInteger clearData = new BigInteger(1, clearDataByte);
        
        //Now do the decryption!
        BigInteger decryptedData = encryptedData.modPow(caPublicExponent, caModulus);
        byte[] decryptedDataBytes = decryptedData.toByteArray();        
        //System.out.println("decrypted Data = " + ByteArrayUtilities.stringify_nospaces(decryptedDataBytes));
        System.out.println("decrypted Data = " + new String(decryptedDataBytes));
        
        
        //Now do the decryption!
        BigInteger encryptedData2 = clearData.modPow(caPrivateExponent, caModulus);
        byte[] encryptedData2Bytes = encryptedData2.toByteArray();
        System.out.println("encrypted Data 2 = " + ByteArrayUtilities.stringify_nospaces(encryptedData2Bytes));
        
       
        //return decryptedIssuerCertBytes;
    }

    //public byte[] getDecriptedIssuerCertSignature(byte[] issuerCert,BigInteger caModulus,BigInteger caPublicExponent) throws SecurityDataTranslationException
    public void getDecriptedIssuerCertSignature() throws SecurityDataTranslationException
    {
        byte[] caPublicExponentByte = ByteArrayUtilities.byteify_nospaces("03");
        byte[] caModulusByte = ByteArrayUtilities.byteify_nospaces("8CF2A75E3C45FB4E72C2775111BC0D0C81A420C2C652A11D6AA6292D70112A16871BC616A0D1C30E1E0097A3D4EA344B1842D7EBCE96D89CC873A2843D545A4C8FF3723D9480723CD3960BBBEE32EED57D1D4EA81815CA39F27F65B537A33499C5548F58B7C96DF0F6606EBA5CDEAFD11BB48A6691CA2DC0CC8F3631A59255CB");
        byte[] issuerCert = ByteArrayUtilities.byteify_nospaces("3038A5F154AE44C683CF68DF1333FACA0AB50E3FBF967878E8413F59502B736E298F53834582FC1A5B2CFD301870BCB271F758F4670326B0968C7D16935BDCCDF1BA72F41DACF94CF4A4718F68559EF85B731C37C66F98AFA8875C478EF252993BC162658D1787E0EFD2C3D5408D0B9AA457895BAD1AAB02989A68EA4752883C");


        caModulusByte = ByteArrayUtilities.byteify_nospaces("996AF56F569187D09293C14810450ED8EE3357397B18A2458EFAA92DA3B6DF6514EC060195318FD43BE9B8F0CC669E3F844057CBDDF8BDA191BB64473BC8DC9A730DB8F6B4EDE3924186FFD9B8C7735789C23A36BA0B8AF65372EB57EA5D89E7D14E9C7B6B557460F10885DA16AC923F15AF3758F0F03EBD3C5C2C949CBA306DB44E6A2C076C5F67E281D7EF56785DC4D75945E491F01918800A9E2DC66F60080566CE0DAF8D17EAD46AD8E30A247C9F");
        issuerCert = ByteArrayUtilities.byteify_nospaces("3827EC9EF3EA9F3F238684E4F6C0F1DF8708BF197AB56EB09AE4ED41720DEFE7EF00DFB6F167BC2CCC26204EB2520E9591421BAF70786E6B8988C334C3A6F80193B320A390058DEF5096A3015F14B984D0AB63BEFF0466D19319494BE39CD140A89EE2BBB9A2F6613DD87D7B56DBCA4EF60C6C652EE33D3705A6326F8D144AB97E6D9888798ECFE19469722168C2ED8F5044F1F8D5F39070BEA25330CDB4D48CEFFE9029DA6B63E3F6FE9E8EA45DFD63");

        if (issuerCert.length != caModulusByte.length)
        {
            throw new SecurityDataTranslationException("CA Modulus cannot be used with this issuer certificate, (issuerCert.length != caModulus.length)");
        }

        BigInteger caModulus = new BigInteger(1, caModulusByte);
        BigInteger caPublicExponent = new BigInteger(1, caPublicExponentByte);

        if (issuerCert == null)
        {
            throw new SecurityDataTranslationException("Issuer Certificate cannot be null");
        }

        if (caModulus == null)
        {
            throw new SecurityDataTranslationException("CA Modulus cannot be null");
        }

        if (caPublicExponent == null)
        {
            throw new SecurityDataTranslationException("CA Public Exponent cannot be null");
        }


        BigInteger issuerCertBI = new BigInteger(1, issuerCert);

        //Now do the decryption!
        BigInteger decryptedIssuerCertBI = issuerCertBI.modPow(caPublicExponent, caModulus);

        byte[] decryptedIssuerCertBytes = decryptedIssuerCertBI.toByteArray();
        //if(logger.isDebugEnabled())
        {
            System.out.println("Decrypted cert = " + ByteArrayUtilities.stringify_nospaces(decryptedIssuerCertBytes));
        }

        if (decryptedIssuerCertBytes.length < issuerCert.length)
        {
            //Prepad with (issuerCert.length - decryptedIssuerCertBytes.length) 0x00 bytes
            byte[] temp = new byte[issuerCert.length];
            System.arraycopy(decryptedIssuerCertBytes, 0, temp, issuerCert.length - decryptedIssuerCertBytes.length, decryptedIssuerCertBytes.length);
            decryptedIssuerCertBytes = temp;
        }
        else if (decryptedIssuerCertBytes.length > issuerCert.length)
        {
            //Remove some trailing zeros
            //Number of trailing zeros to remove is: (decryptedIssuerCertBytes.length - issuerCert.length)
            byte[] temp = new byte[issuerCert.length];
            System.arraycopy(decryptedIssuerCertBytes, (decryptedIssuerCertBytes.length - issuerCert.length), temp, 0, issuerCert.length);
            decryptedIssuerCertBytes = temp;
        }

        System.out.println("decryptedIssuerCertBytes :" + ByteArrayUtilities.stringify_nospaces(decryptedIssuerCertBytes));

        //return decryptedIssuerCertBytes;
    }
    private static String xmlE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<CardSetupResponse>\n"
            + "    <TrackingReference>13121221212</TrackingReference>\n"
            + "    <Status>STATUS_OK</Status>\n"
            + "</CardSetupResponse>";
    private static String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<ScriptStatusResponse>\n"
            + "    <TrackingReference>235235235235</TrackingReference>\n"
            + "    <status>STATUS_OK</status>\n"
            + "</ScriptStatusResponse>";

    public void validateXML() throws ParserConfigurationException, IOException, SAXException
    {


        //XML parsing
        DocumentBuilderFactory docBuilderfactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = docBuilderfactory.newDocumentBuilder();

        InputStream is = new ByteArrayInputStream(xml.getBytes());
        Document xmlDocument = builder.parse(is);
        xmlDocument.getDocumentElement().normalize();

        //InputStream iss=this.getClass().getClassLoader().getResourceAsStream("/net/aconite/affina/espinterface/xmlmapping/sem/ScriptStatusResponse.xsd");
        URL xsdUrlA = this.getClass().getResource("/net/aconite/affina/espinterface/xmlmapping/sem/ScriptStatusResponse.xsd");

        SchemaFactory schemaFactory1 = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        //Schema schema1= schemaFactory1.newSchema(new StreamSource(iss));
        Schema schema1 = schemaFactory1.newSchema(xsdUrlA);


        //Validation
        javax.xml.validation.Validator validator = schema1.newValidator();
        Source source = new DOMSource(xmlDocument);
        validator.validate(source);

        System.out.println("Validated successfully");
    }

    public void CanonEqDemo()
    {
        String pattStr = "[\\p{L|Nd}]";///\u00e9gal"; // Zgal
        String[] input =
        {
            "\u00e9gal", // Zgal - this one had better match :-)
            "e\u0301gal", // e + "Combining acute accent"
            "e\u02cagal", // e + "modifier letter acute accent"
            "e'gal", // e + single quote
            "e\u00b4gal", // e + Latin-1 "acute"
        };
        Pattern pattern = Pattern.compile(pattStr, Pattern.CANON_EQ);
        for (int i = 0; i < input.length; i++)
        {
            if (pattern.matcher(input[i]).matches())
            {
                System.out.println(pattStr + " matches input " + input[i]);
            }
            else
            {
                System.out.println(pattStr + " does not match input "
                        + input[i]);
            }
        }
    }

    public void CanonEqDemo2()
    {

        // String to be scanned to find the pattern.
        //String line = "This order was placed for QT3000! OK?";
        //String line = "This order was  placed  forþ QT3000! OK?"+(char) 0;
        //String line = "1(0";
        String line = "012";//3456789ABCDEF";

        //String pattern = "(.*)(\\d+)(.*)";
        //String pattern = "[(]";
        //String pattern = "[\\x00\\x08\\x0B\\x0C\\x0E-\\x1F]";
         //String pattern = "([1-9][0-9]{0,2})$";
        //String pattern = "^([A-Fa-f0-9]{16})$";
        String pattern ="^([\\x20-\\x7F]{3})$";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(line);
        //if (m.find())
        if (m.matches())
        {
            System.out.println("MATCHES");
        }
        else
        {
            System.out.println("NO MATCH");
        }
        /*
        if (m.find())
        {
            System.out.println("Found value: " + m.group(0));
            System.out.println("Found value: " + m.group(1));
            System.out.println("Found value: " + m.group(2));
        }
        else
        {
            System.out.println("NO MATCH");
        }
        */
    }


    public static void main(String[] args) throws ParseException, AffinaEspUtilException, DFUtilitiesException, DataCollectionException, SecurityDataTranslationException
    {
        System.out.println("Starting...");
        ArrayList<String> list = new ArrayList<String>(new HashMap().values());

        RunUtils ru = new RunUtils();
        ru.myDecription();
        //ru.CanonEqDemo2();
        //ru.testEndDateTime();
        //ru.testCurrentDateTime();
        //ru.testString2DateTime();
        //ru.mySubstring();
        //ru.readDirectory();
        //String aa=",a,b,c,d,";
        //String xx=ru.getValidDataValueList(aa,null);
        //System.out.println("xx>"+xx);
        //ru.toHex();
        //ru.getDERLength();
        /*
         try
         {
         //System.out.println("xx>"+String.valueOf(null));
         //ru.getDecriptedIssuerCertSignature();


         ru.validateXML();
         }
         catch (ParserConfigurationException ex)
         {
         ex.printStackTrace();
         }
         catch (IOException ex)
         {
         ex.printStackTrace();
         }
         catch (SAXException ex)
         {
         ex.printStackTrace();
         }
         */


    }
}
