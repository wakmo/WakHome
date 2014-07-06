/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.handler;


import net.aconite.affina.espinterface.data.TestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author wakkir.muzammil
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-config.xml"})
public class StageScriptAlertHandlerTest
{
    private static final Logger logger = LoggerFactory.getLogger(StageScriptAlertHandlerTest.class.getName());

    @Resource(name = "fromAffinaToEspJMSChannel")
    private MessageChannel inputChannel;

    @Resource(name = "outputChannel")
    private QueueChannel outputChannel;


    @Before
    public void before()
    {


    }

    @Test
    public void testMessage()
    {

        Message<String> msg = new GenericMessage<String>(TestData.STAGE_SCRIPT_ALERT_XML);
        inputChannel.send(msg);

        Message hsssc = outputChannel.receive();
        logger.info(hsssc.toString());
/*
        String hssscPayload = (String) hsssc.getPayload();
        assertTrue(hssscPayload.indexOf("Type=\"HandleStartServiceStateChangeNotificationRequest\"") > 0);

        assertTrue(hssscPayload.indexOf("<HandleStartServiceStateChangeNotificationRequest>") > 0);

        assertTrue(hssscPayload.indexOf("<ReferenceOwner>4.8.125.6.45</ReferenceOwner>") > 0);

        assertTrue(hssscPayload.indexOf("<Reference>VMG201308130000001</Reference>") > 0);

        assertTrue(hssscPayload.indexOf("<Operation>1</Operation>") > 0);

        // replace time dependant transaction id with ###
        String searchStr = "TransactionID=\"";
        int startIdx = hssscPayload.indexOf(searchStr);
        int endIdx = hssscPayload.indexOf("\"", startIdx + searchStr.length());
        hssscPayload = hssscPayload.replace(hssscPayload.substring(startIdx, endIdx) + "\"", searchStr + "###\"");

        assertEquals(handleStartServiceStateChangeNotificationRequest_xml, hssscPayload);

        Message dsr = outputChannel.receive();
        LOGGER.info(dsr.toString());

        String dsrPayload = (String) dsr.getPayload();

        assertTrue(dsrPayload.indexOf("Type=\"DeployServiceRequest\"") > 0);

        assertTrue(dsrPayload.indexOf("<DeployServiceRequest>") > 0);

        assertEquals(deployServiceRequest_xml, dsrPayload);
*/
    }

}

