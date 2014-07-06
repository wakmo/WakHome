package jms;

import jdbc.Bank;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

import com.atomikos.jms.extra.MessageDrivenContainer;
import util.SpringUtils;

public class StartBank
{
    public static void main ( String[] args )
    throws Exception
    {
        //InputStream is = new FileInputStream("config.xml");
        FileSystemResource fileSystemResource=new FileSystemResource(SpringUtils.getSpringPath("jms\\config.xml"));
        XmlBeanFactory factory = new XmlBeanFactory(fileSystemResource);
        Bank bank = ( Bank ) factory.getBean ( "bank" );
        //initialize the bank if needed
        bank.checkTables();

        //retrieve the pool; this will also start the pool as specified in config.xml
        //by the init-method attribute!
        MessageDrivenContainer pool = ( MessageDrivenContainer ) factory.getBean ( "messageDrivenContainer" );

        //Alternatively, start pool here (if not done in XML)
        //pool.start();

        System.out.println ( "Bank is listening for messages..." );
        
    }
}
