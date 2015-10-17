package com.wakkir.validation.xml;

import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import java.io.*;
import java.net.URL;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
/*
import nu.xom.Builder;
import nu.xom.ParsingException;
*/
import org.xml.sax.InputSource;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
/**
 * User: wakkir
 * Date: 21/01/14
 * Time: 21:50
 */
public class XMLValidator
{
    private static String xmlE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<CardSetupResponse>\n" +
            "    <TrackingReference>13121221212</TrackingReference>\n" +
            "    <Status>STATUS_OK</Status>\n" +
            "</CardSetupResponse>";

    private static String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<CardSetupResponse>\n" +
            "    <TrackingReference>235235235235</TrackingReference>\n" +
            "    <status>STATUS_OK</status>\n" +
            "</CardSetupResponse>";
    
    private static String xmlS = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<ScriptStatusUpdate source=\"Perm\" target=\"Affina\" datePublished=\"1399900723234\" scriptUpdateStatus=\"STAGED\" scriptSequenceNumber=\"15\" autoRetryCount=\"0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
            "	<TrackingReference>0130000126_staged_04</TrackingReference>\n" +
            "	<Card PAN=\"0130000126\" PANSequence=\"02\" expirationYear=\"2018\" expirationMonth=\"02\"/>\n" +
            "	<BusinessFunction FunctionName=\"Change eId Status\"/>\n" +
            "	<ScriptOrder>603</ScriptOrder>\n" +
            "	<ScriptDataItem name=\"PKI Status\" value=\"inactive\"/>\n" +
            "</ScriptStatusUpdate>";

    
    public void validateWithDomParser() throws ParserConfigurationException, IOException, SAXException
    {
        //http://onjavahell.blogspot.co.uk/2009/04/how-to-validate-xml-document-from-xml_12.html

        //XML parsing
        DocumentBuilderFactory docBuilderfactory = DocumentBuilderFactory.newInstance();
        docBuilderfactory.setValidating(true);
        docBuilderfactory.setNamespaceAware(true);
        DocumentBuilder builder = docBuilderfactory.newDocumentBuilder();

        InputStream is = new ByteArrayInputStream(xml.getBytes());
        Document xmlDocument = builder.parse(is);
        xmlDocument.getDocumentElement().normalize();
        
        SchemaFactory schemaFactory1 = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema1;
        
        if(true)
        {
            URL xsdUrlAll = this.getClass().getResource("/xsd/net/aconite/affina/espinterface/xmlmapping/sem/AllInOneIn.xsd");
            schema1= schemaFactory1.newSchema(xsdUrlAll);
        }
        else
        {
            URL xsdUrlA = this.getClass().getResource("/xsd/net/aconite/affina/espinterface/xmlmapping/sem/ScriptStatusResponse.xsd");
            URL xsdUrlB = this.getClass().getResource("/xsd/net/aconite/affina/espinterface/xmlmapping/sem/CardSetupResponse.xsd");
            URL xsdUrlC = this.getClass().getResource("/xsd/net/aconite/affina/espinterface/xmlmapping/sem/StageScriptResponse.xsd");
      
            String W3C_XSD_TOP_ELEMENT ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" elementFormDefault=\"qualified\">\n"
                + "<xs:include schemaLocation=\"" +xsdUrlA.getPath() +"\"/>\n"
                + "<xs:include schemaLocation=\"" +xsdUrlB.getPath() +"\"/>\n"
                + "<xs:include schemaLocation=\"" +xsdUrlC.getPath() +"\"/>\n"
                +"</xs:schema>";
            schema1= schemaFactory1.newSchema(new StreamSource(new StringReader(W3C_XSD_TOP_ELEMENT)));
        }
              
        //XSD parsing
        //File xsd = new File("MyTest/src/main/resources/xsd/CardSetupResponse.xsd");
        //File xsd = new File("MyTest/src/main/resources/xsd/x.xsd");
        //SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        //Schema schema = schemaFactory.newSchema(xsd);

        //Validation
        javax.xml.validation.Validator validator = schema1.newValidator();
        Source source = new DOMSource(xmlDocument);
        validator.validate(source);
    }
    
    public void do4() throws ParserConfigurationException, IOException, SAXException
    {
        //http://onjavahell.blogspot.co.uk/2009/04/how-to-validate-xml-document-from-xml_12.html


        //XML parsing
        DocumentBuilderFactory docBuilderfactory = DocumentBuilderFactory.newInstance();
        docBuilderfactory.setValidating(true);
        docBuilderfactory.setNamespaceAware(true);
        DocumentBuilder builder = docBuilderfactory.newDocumentBuilder();

        InputStream is = new ByteArrayInputStream(xml.getBytes());
        Document xmlDocument = builder.parse(is);
        xmlDocument.getDocumentElement().normalize();
        
        InputStream iss=this.getClass().getClassLoader().getResourceAsStream("/net/aconite/affina/espinterface/xmlmapping/sem/ScriptStatusResponse.xsd");
        //URL xsdUrlA = this.getClass().getResource("/xsd/net/aconite/affina/espinterface/xmlmapping/sem/ScriptStatusResponse.xsd");
        //URL xsdUrlB = this.getClass().getResource("/xsd/net/aconite/affina/espinterface/xmlmapping/sem/CardSetupResponse.xsd");
        //URL xsdUrlC = this.getClass().getResource("/xsd/net/aconite/affina/espinterface/xmlmapping/sem/StageScriptResponse.xsd");

        SchemaFactory schemaFactory1 = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        
        Schema schema1= schemaFactory1.newSchema(new StreamSource(iss));

        //XSD parsing

        //File xsd = new File("MyTest/src/main/resources/xsd/CardSetupResponse.xsd");
        //File xsd = new File("MyTest/src/main/resources/xsd/x.xsd");
        //SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        //Schema schema = schemaFactory.newSchema(xsd);

        //Validation
        javax.xml.validation.Validator validator = schema1.newValidator();
        Source source = new DOMSource(xmlDocument);
        validator.validate(source);
    }

    public void xx() throws ParserConfigurationException, IOException, SAXException
    {

        //http://stackoverflow.com/questions/19424399/xml-validation-against-xsd-by-using-spring-java

        //new StreamSource(new File("MyTest/xsd/ScriptStatusResponse.xsd")),
                //new StreamSource(new File("MyTest/xsd/CardSetupResponse.xsd")),
                //new StreamSource(new File("MyTest/xsd/StageScriptResponse.xsd"))};

        //URL xsdUrlA = this.getClass().getResource("/ScriptStatusResponse.xsd");
        //URL xsdUrlB = this.getClass().getResource("/CardSetupResponse.xsd");
        //URL xsdUrlC = this.getClass().getResource("/StageScriptResponse.xsd");

        URL xsdUrlA = this.getClass().getResource("/net/aconite/affina/espinterface/xmlmapping/sem/ScriptStatusResponse.xsd");
        URL xsdUrlB = this.getClass().getResource("/net/aconite/affina/espinterface/xmlmapping/sem/CardSetupResponse.xsd");
        URL xsdUrlC = this.getClass().getResource("/net/aconite/affina/espinterface/xmlmapping/sem/StageScriptResponse.xsd");

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        String W3C_XSD_TOP_ELEMENT ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                        + "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" elementFormDefault=\"qualified\">\n"
                        //+ "<xs:include schemaLocation=\"" +xsdUrlA.getPath() +"\"/>\n"
                        + "<xs:include schemaLocation=\"" +xsdUrlB.getPath() +"\"/>\n"
                       // + "<xs:include schemaLocation=\"" +xsdUrlC.getPath() +"\"/>\n"
                        +"</xs:schema>";


        Schema schema = schemaFactory.newSchema(new StreamSource(new StringReader(W3C_XSD_TOP_ELEMENT), "xsdTop"));

        //XML parsing
        DocumentBuilderFactory docBuilderfactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = docBuilderfactory.newDocumentBuilder();

        InputStream is = new ByteArrayInputStream(xml.getBytes());
        Document xmlDocument = builder.parse(is);
        xmlDocument.getDocumentElement().normalize();


        javax.xml.validation.Validator validator = schema.newValidator();
        Source source1 = new DOMSource(xmlDocument);
        validator.validate(source1);

        //XSD parsing
        //File xsd = new File("src/main/resources/xsd/yourXsd.xsd");
        //File xsd = new File("MyTest/src/main/java/com/wakkir/validation/xml/test.xsd");
        //SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        //Schema schema = schemaFactory.newSchema(xsd);


        //Validation
        //javax.xml.validation.Validator validator = schema.newValidator();
        //Source source = new DOMSource(xmlDocument);
        //validator.validate(source);


    }
    
    public void one() throws ParserConfigurationException, IOException, SAXException
    {
        /*
        try
        {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(true);
            factory.setNamespaceAware(true);

            SAXParser parser = factory.newSAXParser();
            parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");

            XMLReader reader = parser.getXMLReader();
            reader.setErrorHandler(new SimpleErrorHandler());
            reader.parse(new InputSource("contacts.xml"));
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (SAXException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        */
    }
    /*
    public void doValidate() throws SAXException, ParserConfigurationException, TransformerException, JDOMException
    {

        DOMOutputter outputter = new DOMOutputter();
        //Document document = new Document();
        //document.addContent(param.clone());
        //org.w3c.dom.Document doc = outputter.output(document);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Source xmlSource = new DOMSource(doc);
        Result outputTarget = new StreamResult(outputStream);
        TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
        InputStream xml = new ByteArrayInputStream(outputStream.toByteArray());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        // create a grammar object.
        Source[] source = {
                new StreamSource(new File("H:/ref2.xsd")),
                new StreamSource(new File("H:/ref1.xsd")),
                new StreamSource(new File("H:/ref.xsd"))};
        Schema schemaGrammar = schemaFactory.newSchema(source);
        //validator.validate(new StreamSource(xml));
    }
    */
   /*
    public void do2() throws ParserConfigurationException
    {
        // Contains the filename of the xml file
        String filename;

// Load the xml data using a namespace-aware builder (the method
// 'stream' simply opens an input stream on a file)
        Document document;
        DocumentBuilderFactory docBuilderFactory =
                DocumentBuilderFactory.newInstance();
        docBuilderFactory.setNamespaceAware(true);
        document = docBuilderFactory.newDocumentBuilder().parse(stream(filename));

// Create the schema factory
        SchemaFactory sFactory = SchemaFactory.newInstance(
                XMLConstants.W3C_XML_SCHEMA_NS_URI);

// Load the main schema
        Schema schema = sFactory.newSchema(
                new StreamSource(stream("foo.xsd")));

// Validate using main schema
        schema.newValidator().validate(new DOMSource(document));

// Get the node that is the root for the portion you want to validate
// using another schema
        Node node = getSpecialNode(document);

// Build a Document from that node
        Document subDocument = docBuilderFactory.newDocumentBuilder().newDocument();
        subDocument.appendChild(subDocument.importNode(node, true));

// Determine the schema to use using your own logic
        Schema subSchema = parseAndDetermineSchema(document);

// Validate using other schema
        subSchema.newValidator().validate(new DOMSource(subDocument));
    }
    */

    
    /*
    public void xomValidate() throws ParserConfigurationException, SAXException, ParsingException, IOException
    {   
        //http://www.edankert.com/validate.html
        
        System.setProperty("javax.xml.parsers.SAXParserFactory", "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(true);

        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        
        URL xsdUrlAll = this.getClass().getResource("/xsd/net/aconite/affina/espinterface/xmlmapping/sem/AllInOneIn.xsd");
        //factory.setSchema(schemaFactory.newSchema( new Source[] {new StreamSource("contacts.xsd")}));
        //factory.setSchema(schemaFactory.newSchema( new Source[] {new StreamSource("xsd/net/aconite/affina/espinterface/xmlmapping/sem/AllInOneIn.xsd")}));
        factory.setSchema(schemaFactory.newSchema(xsdUrlAll));

        SAXParser parser = factory.newSAXParser();
        XMLReader reader = parser.getXMLReader();
        reader.setErrorHandler(new SimpleErrorHandler());
        
        InputStream is = new ByteArrayInputStream(xml.getBytes());
        
        Builder builder = new Builder(reader);
        builder.build(is);
    }
    */
    void saxValidator() throws SAXException, ParserConfigurationException, IOException
    {        
        //http://www.edankert.com/validate.html
        
        //https://coderwall.com/p/kqsrrw
        //JDOM2: This parser does not support specification "null" version "null"
        /*
        When you have a Maven project which uses JDOM2 and you create a new SaxBuilder with new SaxBuilder() you will see a stack trace like this:

        java.lang.ExceptionInInitializerError
          at org.jdom2.input.SAXBuilder.<init>(SAXBuilder.java:338)
          at org.jdom2.input.SAXBuilder.<init>(SAXBuilder.java:221)
          at your.package.your.class.your.method(Class.java:XX)
        Caused by: java.lang.UnsupportedOperationException: This parser does not support specification "null" version "null"
          at javax.xml.parsers.SAXParserFactory.setSchema(SAXParserFactory.java:419)
          ...

        In that case you're using a SAXParserFactory that doesn't override setSchema(...). This may be a version of org.apache.xerces.jaxp.SAXParserFactoryImpl before 2.7.0.

        There are two fixes for the problem.

        Fix 1: Use a different SAXParserFactory by setting the system property javax.xml.parsers.SAXParserFactory. E.g. the factory provided by your current JDK: com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl. This may be done by starting the JVM with

        -Djavax.xml.parsers.SAXParserFactory=com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl

        or within your code

        System.setProperty("javax.xml.parsers.SAXParserFactory", "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");

        Fix 2: Update the factory you're using. In case of apache's factory update it at least to version 2.7.0.
        */
        
        System.setProperty("javax.xml.parsers.SAXParserFactory", "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");

        
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);

        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        
        URL xsdUrlAll = this.getClass().getResource("/xsd/net/aconite/affina/espinterface/xmlmapping/sem/AllInOneIn.xsd");
        
        //factory.setSchema(schemaFactory.newSchema(new Source[] {new StreamSource("CardSetupResponse.xsd")}));
        factory.setSchema(schemaFactory.newSchema(xsdUrlAll));
        
        SAXParser parser = factory.newSAXParser();

        XMLReader reader = parser.getXMLReader();
        //reader.setErrorHandler(new SimpleErrorHandler());
        
        InputStream is = new ByteArrayInputStream(xml.getBytes());
        
        reader.parse(new InputSource(is));
    }
    
    ////////////////////////////////////////////////////////////////////////////
    /*
    public static void main(String args[]) throws ParsingException
    {
        XMLValidator val = new XMLValidator();
        try
        {
            //val.validateWithDomParser();
            //val.xx();
            //val.saxValidator();
            val.xomValidate();
            
            System.out.println("done");
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (SAXException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } 
        


    }
    */
}
