package com.atomikos.examples.jpa;

import static org.junit.Assert.assertNotNull;

import javax.naming.NamingException;
import javax.transaction.SystemException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atomikos.icatch.jta.UserTransactionManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/hibernate-config.xml" })
public class HibernateJpaTest {

	private final Account a1 = new Account( "account1", 50.0f);


	@Autowired
	TestDao testDao;

	@Test
	public void saveAndRetreive() throws NamingException {
		testDao.create(a1);
		Account a3 = testDao.retreive(a1.getAccount());

		assertNotNull(a3);

	}

}
