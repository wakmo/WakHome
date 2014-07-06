/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.jaxb;

import net.aconite.affina.espinterface.xmlmapping.sem.*;
import org.junit.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.math.BigInteger;

/**
 * @author wakkir.muzammil
 */
public class Object2XMLTest
{

    public Object2XMLTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void CardSetupRequestObject2XML()
    {
        CardSetupRequest request = new CardSetupRequest();
        request.setTrackingReference("12121212121212");

        CardType cardType = new CardType();
        cardType.setExpirationDate("1385855999999");
        cardType.setPAN("0129002312");
        cardType.setPANSequence("01");
        request.setCard(cardType);

        AppType appType = new AppType();
        appType.setApplicationType("Master");
        appType.setApplicationVersion("1.0");
        request.setApplication(appType);

        try
        {

            JAXBContext jaxbContext = JAXBContext.newInstance(CardSetupRequest.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            StringWriter xmlWriter = new StringWriter();
            //marshaller.marshal(updateRequest, xmlWriter);
            //requestXMLString = xmlWriter.getBuffer().toString();
            //xmlList.add(requestXMLString);

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            //jaxbMarshaller.marshal(CardSetupRequest, file);
            jaxbMarshaller.marshal(request, System.out);

        }
        catch (JAXBException e)
        {
            e.printStackTrace();
        }

    }

    @Test
    public void StageScriptRequestObject2XML()
    {
        StageScriptRequest request = new StageScriptRequest();
        request.setTrackingReference("4525234552525");

        CardType cardType = new CardType();
        cardType.setExpirationDate("1385855999999");
        cardType.setPAN("0129002312");
        cardType.setPANSequence("01");
        request.setCard(cardType);

        StageScriptRequest.BusinessFunction bs = new StageScriptRequest.BusinessFunction();
        bs.setFunctionName("Function One");
        request.setBusinessFunction(bs);

        StageScriptRequest.Action ac = new StageScriptRequest.Action();
        ac.setEndDate("20");
        ac.setRestageAutomatically(BigInteger.ONE);
        ac.setStartDate("0");
        request.setAction(ac);

        NVPType nvp1 = new NVPType();
        nvp1.setName("MyName");
        nvp1.setValue("MyValue");

        request.getScriptDataItem().add(nvp1);

        try
        {

            JAXBContext jaxbContext = JAXBContext.newInstance(StageScriptRequest.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            StringWriter xmlWriter = new StringWriter();
            //marshaller.marshal(updateRequest, xmlWriter);
            //requestXMLString = xmlWriter.getBuffer().toString();
            //xmlList.add(requestXMLString);

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            //jaxbMarshaller.marshal(CardSetupRequest, file);
            jaxbMarshaller.marshal(request, System.out);

        }
        catch (JAXBException e)
        {
            e.printStackTrace();
        }

    }
}
