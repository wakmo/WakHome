package com.wakkir.spring.process;

import com.wakkir.spring.model.DbObject;
import com.wakkir.spring.model.RequestObject;
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
public class BatchProcessor
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
        System.out.println("BatchProcessor processing...");

        HashMap<String, Object> myMap = new HashMap<String, Object>();
        myMap.put("myInteger",new Integer(222));
        myMap.put("myString",new String("This if for BatchProcessor & inPayload is "+(String)inPayload));

        List<Object> workflowActionResponses=(List<Object>)standardWorkflow.processWorkflow("action2_action",myMap) ;

        //Message outMessage=generateMessage(inHeaders, obj);

        List<Message> outMessages = new ArrayList<Message>();

        for (Object workflowActionResponse : workflowActionResponses)
        {
             List actionResponses= (List)workflowActionResponse;

             for (Object actionResponse : actionResponses)
             {
                 if(actionResponse instanceof RequestObject)
                 {
                     Message outMessage = generateMessage(inHeaders, (RequestObject)actionResponse);
                     outMessages.add(outMessage);
                 }

                 if(actionResponse instanceof DbObject)
                 {
                     Message outMessage = generateMessage(inHeaders, (DbObject)actionResponse);
                     outMessages.add(outMessage);
                 }
             }
        }

        System.out.println("<********************************************************<");
        return outMessages;

    }

    private Message<String> generateMessage(MessageHeaders headers, RequestObject sourceData)
    {
        String stringMessage="{"+sourceData.getId()+" | "+sourceData.getName()+"}";

        System.out.println("message body : " + stringMessage);

        return MessageBuilder.withPayload(stringMessage)
                .copyHeaders(headers)
                .build();
    }

    private Message<String> generateMessage(MessageHeaders headers, DbObject sourceData)
    {
        String stringMessage="{"+sourceData.getId()+" | "+sourceData.getName()+"}";

        System.out.println("message body : " + stringMessage);

        return MessageBuilder.withPayload(stringMessage)
                .copyHeaders(headers)
                .build();
    }

}
