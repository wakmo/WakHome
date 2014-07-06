package org.springbyexample.email;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * User: wakkir
 * Date: 14/12/13
 * Time: 20:29
 */
public class Demo
{
    private final static Logger logger = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args)
    {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

        context.registerShutdownHook();


        //SimpleMailMessage  msg = (SimpleMailMessage) context.getBean("templateMessage");
        //JavaMailSenderImpl mailSender = (JavaMailSenderImpl) context.getBean("mailSender");
        VelocityEngine velocityEngine = (VelocityEngine) context.getBean("velocityEngine");

        //VelocityEmailSender sender = new VelocityEmailSender(velocityEngine,mailSender);

        Map<String, Object> props = new HashMap<String, Object>();
        props.put("firstName", "Joe");
        props.put("lastName", "Smith");

        props.put("responseType", "CardSetup Response");
        props.put("trackId", "ASD234234");
        //props.put("status", "SUCCESS");
        props.put("status", "ERROR");
        props.put("errorData", "errorData errorData");
        props.put("errorDesc", "errorDesc errorDesc");
        props.put("errorCode", "errorCode2323");
        props.put("lastName", "Smith");
        props.put("lastName", "Smith");


        //sender.send(msg, props);

        String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/emailBody.vm", props);

        System.out.println("body={}"+ body);

        String body2 = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/cardSetupResponse.vm", props);

        System.out.println(body2);



    }
}
