package net.aconite.affina.espinterface.demo;

import net.aconite.affina.espinterface.context.ESPShutDownHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * User: Wakkir.Muzammil
 * Date: 26/11/13
 * Time: 16:30
 */
public class MyHelloDemo
{
    private static final Logger logger = LoggerFactory.getLogger(MyHelloDemo.class);


    public void initServer()
    {
        ConfigurableApplicationContext espContext = new ClassPathXmlApplicationContext("/META-INF/spring/application-context.xml");
        /*ConfigurableApplicationContext espContext = new ClassPathXmlApplicationContext(
                "/META-INF/spring/application-context.xml",
                "/META-INF/spring/app-config.xml",
                "/META-INF/spring/database-config.xml",
                "/META-INF/spring/jms-config.xml",
                "/META-INF/spring/properties-config.xml",
                "/META-INF/spring/spring-integration-config.xml",
                "/META-INF/spring/spring-config.xml");*/
        //ConfigurableApplicationContext espContext = new FileSystemXmlApplicationContext("/ESPInterface/src/main/resources/META-INF/spring/application-context.xml");
        //AbstractApplicationContext espContext = new ClassPathXmlApplicationContext("/META-INF/spring/application-context.xml",MyHelloDemo.class);


        espContext.registerShutdownHook();

        ESPShutDownHook hook = new ESPShutDownHook(espContext);

        Runtime.getRuntime().addShutdownHook(hook);

        //IESPServices espService = (ESPServices) espContext.getBean("espServices");

        //espService.startServer();
        //AffinaTOPLinkSessionManager sm = (AffinaTOPLinkSessionManager) AppConfig.getBean("sessionManager_pma");
        //UnitOfWork uow = sm.getUnitOfWork();
        
        /*
        Persistent p = new AbstractPersistentDAO()
        {

            @Override
            public void doTransactionalWork() 
            {
                Vector sfs= getObjectByName("333001000001", SoftCard.class, "cardId");
                //throw new UnsupportedOperationException("Not supported yet.");
            }
            
            
        };
        p.doTransactionAndCommit();
        */
        
        
        /*
        //SimpleMailMessage  msg = (SimpleMailMessage) context.getBean("templateMessage");
        //JavaMailSenderImpl mailSender = (JavaMailSenderImpl) context.getBean("mailSender");
        VelocityEngine velocityEngine = (VelocityEngine) espContext.getBean("velocityEngine");

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

        String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/emailBody.vm","UTF-8", props);

        System.out.println("body={}"+ body);

        String body2 = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/cardSetupResponse.vm","UTF-8", props);

        System.out.println(body2);
        */

        //Properties appProperties = espContext.getBean("appProperties", Properties.class);
    }

    public static void main(String[] args)
    {
        System.out.println("Hello World");
        new MyHelloDemo().initServer();
    }


}
