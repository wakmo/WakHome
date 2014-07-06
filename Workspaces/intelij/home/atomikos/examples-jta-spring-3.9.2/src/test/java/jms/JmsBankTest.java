package jms;

import com.atomikos.jms.extra.MessageDrivenContainer;
import jdbc.Bank;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.MapMessage;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:jms/config.xml")
public class JmsBankTest {

	@Autowired
	@Qualifier("bank")
	Bank bank;

	@Autowired
	@Qualifier("messageDrivenContainer")
	MessageDrivenContainer pool;

	private static BrokerService broker;

	@BeforeClass
	public static void startBroker() throws Exception {

		System.out.println("Starting broker on " + BrokerService.DEFAULT_PORT);
		//broker = new BrokerService();
		//broker.addConnector("tcp://localhost:" + BrokerService.DEFAULT_PORT);
		//broker.start();
	}
	

	@Test
	public void orderWithdrawal() throws Exception {
		int account = 10;
		int amount=100;

		bank.checkTables();

		long balance = bank.getBalance ( account );
        System.out.println ( "Balance of account "+account+" is: " + balance );

		String url = "tcp://localhost:" + BrokerService.DEFAULT_PORT;
		ActiveMQQueue queue = new ActiveMQQueue();
		queue.setPhysicalName("BANK_QUEUE");
		ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
		cf.setBrokerURL(url);
		QueueConnection c = cf.createQueueConnection();

		QueueSession session = c.createQueueSession(true, 0);
		QueueSender sender = session.createSender(queue);
		MapMessage m = session.createMapMessage();

		m.setIntProperty("account", account);
		m.setIntProperty("amount", 100);
		sender.send(m);
		session.commit();
		session.close();
		c.close();

		Thread.sleep(1000);
		long  balance2 = bank.getBalance ( account );
        System.out.println ( "New balance of account "+account+" is: " + balance );
        assertEquals(balance2,balance-amount );

	}

	@AfterClass
	public static void stopBroker() throws Exception {
		//broker.stop();
	}
}
