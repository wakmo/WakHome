package com.atomikos.demo;

import javax.jms.JMSException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atomikos.demo.transformer.Counter;
import com.atomikos.jms.extra.MessageDrivenContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:beans-activemq-derby-mdc-hibernate.xml" })
public class MessageDrivenContainerTest {

	private final static Logger log = LoggerFactory.getLogger(MessageDrivenContainerTest.class);

	@BeforeClass
	public static void initialize(){
		System.setProperty("testId", "beans-activemq-derby-mdc-hibernate");
	}
	@Autowired
	private ApplicationContext context;

	@Before
	public void publishMessages() {
		Service publisher = (Service) context.getBean("publisher");
		publisher.process();
	}

	@Test
	public void balanceLaPuree() throws InterruptedException, JMSException {
		MessageDrivenContainer container = (MessageDrivenContainer) context.getBean("messageDrivenContainer");
		container.start();
		log.info("Initialization over, now processing...");
		long start = System.currentTimeMillis();


		while (Counter.counter<Counter.totalMessages && !timeout(start)) {
			Thread.sleep(200);
		}

		log.info("Processing of " + Counter.counter + " event(s) in " + (System.currentTimeMillis() - start) + " ms done, now exiting...");

		log.info("Over.");

	}

	private boolean timeout(long start) {

		return (System.currentTimeMillis()-start)>20000;
	}
}
