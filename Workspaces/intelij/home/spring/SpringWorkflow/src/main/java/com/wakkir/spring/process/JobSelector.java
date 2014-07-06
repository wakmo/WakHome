package com.wakkir.spring.process;

import com.wakkir.spring.model.FilterObject;
import com.wakkir.spring.service.IWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.support.MessageBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: wakkir
 * Date: 08/03/14
 * Time: 00:50
 */
public class JobSelector
{
    //private static final Logger logger = LoggerFactory.getLogger(JobSelector.class.getName());
    @Autowired
    IWorkflow standardWorkflow;

    @Splitter
    public List<Message> process(Message inMessage)
    {
        // At info level, the data recorded shall be limited to the message type
        //   and its identifier (tracking reference or service instance).
        // At debug level, the complete message shall be recorded.

        MessageHeaders inHeaders = inMessage.getHeaders();
        Object inPayload = inMessage.getPayload();

        System.out.println(">********************************************************>");
        System.out.println("JobSelector processing...");

        HashMap<String, Object> myMap = new HashMap<String, Object>();
        myMap.put("myInteger",new Integer(111));
        myMap.put("myString",new String("This if for JobSelector"));

        List<Object> objectList=(List<Object>)standardWorkflow.processWorkflow("action1_action",myMap) ;

        //Message outMessage=generateMessage(inHeaders, obj);

        List<Message> outMessages = new ArrayList<Message>();
        for (Object object : objectList)
        {
            List<FilterObject> filterObjectList= (List<FilterObject>)object;

            for (FilterObject filterObject : filterObjectList)
            {
                Message outMessage = generateMessage(inHeaders, filterObject);
                outMessages.add(outMessage);
            }
        }

        System.out.println("<********************************************************<");
        return outMessages;
    }

    private Message<String> generateMessage(MessageHeaders headers, FilterObject sourceData)
    {
        String stringMessage="{"+sourceData.getId()+" | "+sourceData.getName()+"}";

        System.out.println("message body : " + stringMessage);

        return MessageBuilder.withPayload(stringMessage)
                .copyHeaders(headers)
                .build();
    }

}
