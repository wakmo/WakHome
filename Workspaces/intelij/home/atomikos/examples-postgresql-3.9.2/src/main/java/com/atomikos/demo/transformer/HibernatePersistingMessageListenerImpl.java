package com.atomikos.demo.transformer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public class HibernatePersistingMessageListenerImpl implements MessageListener {

	private final static Logger log = LoggerFactory.getLogger(HibernatePersistingMessageListenerImpl.class);

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public void onMessage(final Message msg) {
		try {
			final Object o = ((ObjectMessage) msg).getObject();
			sessionFactory.getCurrentSession().saveOrUpdate(o);

			Counter.counter++;

			if (Counter.counter > 0 && Counter.counter % 200 == 0)
				log.info("processed " + Counter.counter + " messages");

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
