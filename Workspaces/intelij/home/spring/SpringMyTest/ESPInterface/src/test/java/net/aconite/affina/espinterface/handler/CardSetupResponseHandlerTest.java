/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.handler;


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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author wakkir.muzammil
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-config.xml"})
public class CardSetupResponseHandlerTest
{
    private static final Logger logger = LoggerFactory.getLogger(CardSetupResponseHandlerTest.class.getName());

    @Resource(name = "fromSemToEspJMSChannel")
    private MessageChannel inputChannel;

    @Resource(name = "outputChannel")
    private QueueChannel outputChannel;


    private byte[] myByteMessage;


    @Before
    public void before() throws FileNotFoundException, IOException
    {
        File infile = new File("C:\\JDrive\\modules\\Affina\\AE_SEM_CS_Interface\\ESPInterface\\src\\test\\resources\\CardSetupResponse.bin");
        myByteMessage = new byte[(int) infile.length()];
        FileInputStream inputStream = new FileInputStream(infile);

        int total = 0;
        int nRead = 0;
        while ((nRead = inputStream.read(myByteMessage)) != -1)
        {

            //System.out.println(new String(buffer));
            //total += nRead;
        }
        inputStream.close();
        //System.out.println(total);                                 
    }

    @Test
    public void testByteMessage()
    {

        Message<byte[]> msg = new GenericMessage<byte[]>(myByteMessage);
        inputChannel.send(msg);

        Message hsssc = outputChannel.receive();
        logger.info(hsssc.toString());
    }


}

