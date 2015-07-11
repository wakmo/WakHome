package wak.work.cryptogram.graem.securecrypto;

import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;

import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyAlgorithms;
import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyComponents;
import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyTypes;
import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyUsage;
import wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKeyUsages;
import wak.work.cryptogram.graem.securecrypto.xmlinterface.generated.XMLSCIKeyData;
import wak.work.cryptogram.graem.securecrypto.xmlinterface.generated.XMLSCIKeyUsage;

public class SCIKeyExporter
{
    private static final Logger log = Logger.getLogger(SCIKeyExporter.class);

    private static final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    private static final URL schemaUrl = SCIKeyExporter.class.getResource("/com/platform7/securecrypto/xmlinterface/xsd/SCIKeyData.xsd");

    public static void exportKey(OutputStream os, SCIKey keyToExport) throws SecureCryptoException
    {
        log.trace("enter: exportKey(OutputStream os, SCIKey keyToExport)");

        try
        {
            wak.work.cryptogram.graem.securecrypto.generickeyvalue.SCIKey key = keyToExport.extractTransportableKey();

            XMLSCIKeyData kd = new XMLSCIKeyData();

            kd.setMKIdentifier(key.getMKIdentifier());
            kd.setSMType(key.getSMType());
            kd.setComponent(SCIKeyComponents.translate(key.getComponent()));
            kd.setKeyType(SCIKeyTypes.translate(key.getKeyType()));
            kd.setCryptoEngineVersion(Integer.toString(key.getCryptoEngineVersion()));
            kd.setSMExtension(key.getSMExtension());
            kd.setSCIKeyValue(key.getKeyValue());

            // AF-890 Now typesafe
            List<XMLSCIKeyUsage> kuList = kd.getKeyUsage();
            SCIKeyUsage[] kuArray = key.getKeyUsage();
            for (SCIKeyUsage sku : kuArray)
            {
                log.trace("Adding new key usage to list");
                XMLSCIKeyUsage ku = new XMLSCIKeyUsage();
                ku.setAlgorithm(SCIKeyAlgorithms.translate(sku.getAlgorithm()));
                ku.setUsage(SCIKeyUsages.translate(sku.getUsage()));
                kd.setKeySize(key.getKeySize());
                kuList.add(ku);
            }

            // 
            // AF-890 SW JAXB2 revamp
            // 
            // log.trace("About to call kd.validate()");
            // kd.validate();
            // log.trace("About to call kd.marshal(os)");
            // kd.marshal(os);
            log.trace("About to marshal XMLSCIKeyData...");
            JAXBContext jaxbContext = JAXBContext.newInstance(XMLSCIKeyData.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // JAXB2 validation takes a different form, and relies on the schema being associated
            // with the marshaller. If a schema supplied, then validation takes place
            // during marshalling

            Schema schema = SCIKeyExporter.schemaFactory.newSchema(SCIKeyExporter.schemaUrl);
            marshaller.setSchema(schema);
            marshaller.setEventHandler(new ValidationEventHandler()
            {
                public boolean handleEvent(ValidationEvent event)
                {
                    // Basically if a warning or error occurs and event is raised
                    // All we want to do is alert the fact that validation has failed
                    log.trace(event);
                    return false;
                }
            });

            marshaller.marshal(kd, os);
            log.trace("Marshalling completed.");
        }
        catch (Throwable x)
        {
            log.warn("Failed to export generic key", x);
            throw new SecureCryptoException("Cannot export generic key", x);
        }

        log.trace("exit: exportKey(OutputStream os, SCIKey keyToExport)");
    }
}
