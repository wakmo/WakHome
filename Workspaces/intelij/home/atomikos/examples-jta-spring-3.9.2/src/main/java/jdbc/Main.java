package jdbc;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import util.SpringUtils;

public class Main
{
    public static void main ( String[] args )
    throws Exception
    {
        //InputStream is = new FileInputStream("config.xml");
        FileSystemResource fileSystemResource=new FileSystemResource(SpringUtils.getSpringPath("jdbc\\config.xml"));
        XmlBeanFactory factory = new XmlBeanFactory(fileSystemResource);
        Bank bank = ( Bank ) factory.getBean ( "bank" );

	bank.checkTables();
        long balance = bank.getBalance ( 10 );
        System.out.println ( "Balance of account 10 is: " + balance );
        System.out.println ( "Withdrawing 100 of account 10..." );
        bank.withdraw ( 10 , 100 );
        balance = bank.getBalance ( 10 );
        System.out.println ( "New balance of account 10 is: " + balance );

        System.exit ( 0 );

    }


}
