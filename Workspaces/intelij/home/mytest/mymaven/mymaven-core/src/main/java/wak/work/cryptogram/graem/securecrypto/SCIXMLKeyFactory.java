/*
*

*
*  Project:                PROJECT NAME INSERTED HERE
*
*  Type:		 %cvtype:  java %
*  Name:		 %name:   SCIKeyFactory.java %
*  Created:	 Mon Oct 20 18:21:42 2003
*  Created By:	 %created_by:  build %
*  Last modified:	 %date_modified:  %
*  CI Idenitifier:	 %full_filespec:  SCIKeyFactory.java~10:java:UKPMA#1 %
*
*  Amendment Record
*  Version 	Date        Author  		Description
*
*
*
*
*/

package wak.work.cryptogram.graem.securecrypto;

import wak.work.cryptogram.graem.securecrypto.generickeyvalue.*;
import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKey;
import wak.work.cryptogram.graem.securecrypto.xmlinterface.generated.XMLSCIKeyData;
import wak.work.cryptogram.graem.securecrypto.xmlinterface.generated.XMLSCIKeyUsage;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

public class SCIXMLKeyFactory
{
    private static final Logger log = Logger.getLogger(SCIXMLKeyFactory.class);


    private static final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    private static final URL schemaUrl = SCIKeyExporter.class.getResource("/com/platform7/securecrypto/xmlinterface/xsd/SCIKeyData.xsd");

    public static Key createSCIKey(String filename) throws SecureCryptoException
    {
        try
        {
            FileInputStream fis = new FileInputStream(filename);
            Key key = createSCIKey(fis);
            fis.close();
            return key;
        }
        catch (Throwable ex)
        {
            throw new SecureCryptoException("Cannot load key from file", ex);
        }
    }

    public static Key createSCIKey(InputStream is) throws SecureCryptoException
    {
        Key loadedKey;
        try
        {
            //
            // AF-890 SW Migrated to JAXB2
            // Validation is achieved by associating a schema with the
            // Unmarshaller
            //
            // XMLSCIKeyData kd = XMLSCIKeyData.unmarshal(is);
            // kd.validate();
            //
            JAXBContext jaxbContext = JAXBContext.newInstance(XMLSCIKeyData.class);
            Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
            Schema schema = SCIXMLKeyFactory.schemaFactory.newSchema(SCIXMLKeyFactory.schemaUrl);
            unMarshaller.setSchema(schema);
            unMarshaller.setEventHandler(new ValidationEventHandler()
            {
                public boolean handleEvent(ValidationEvent event)
                {
                    // Basically if a warning or error occurs and event is raised
                    // All we want to do is alert the fact that validation has failed
                    return false;
                }
            });
            XMLSCIKeyData kd = (XMLSCIKeyData) unMarshaller.unmarshal(is);

            // Also made this typesafe
            List<XMLSCIKeyUsage> kulist = kd.getKeyUsage();
            wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyUsage[] ku =
                    new wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyUsage[kulist.size()];
            for (int i = 0; i < kulist.size(); i++)
            {
                XMLSCIKeyUsage kuse = kulist.get(i);
                ku[i] = new SCIKeyUsage(
                        SCIKeyAlgorithms.translate(kuse.getAlgorithm()),
                        SCIKeyUsages.translate(kuse.getUsage()));
            }

            SCIKey keyObj = new SCIKeyValue(kd.getMKIdentifier(),
                            kd.getSMType(),
                            ku,
                            SCIKeyComponents.translate(kd.getComponent()),
                            SCIKeyTypes.translate(kd.getKeyType()),
                            kd.getKeySize(),
                            Integer.parseInt(kd.getCryptoEngineVersion()),
                            kd.getSMExtension(),
                            kd.getSCIKeyValue());

            loadedKey = SCIKeyFactory.getInstance().createSCIKey(keyObj);
        }
        catch (Throwable x)
        {
            log.warn("Cannot load key", x);
            throw new SecureCryptoException("Cannot load key", x);
        }

        return loadedKey;
    }
}
