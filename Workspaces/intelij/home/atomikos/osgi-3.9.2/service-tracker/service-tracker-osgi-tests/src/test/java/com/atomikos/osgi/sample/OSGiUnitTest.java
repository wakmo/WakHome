package com.atomikos.osgi.sample;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.ops4j.pax.exam.CoreOptions.cleanCaches;
import static org.ops4j.pax.exam.CoreOptions.felix;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;
import static org.ops4j.pax.exam.CoreOptions.webProfile;
import static org.ops4j.pax.exam.OptionUtils.combine;
import static org.ops4j.pax.exam.spi.PaxExamRuntime.createContainer;
import static org.ops4j.pax.exam.spi.PaxExamRuntime.createTestSystem;

import java.io.IOException;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.TimeoutException;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.EagerSingleStagedReactorFactory;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(EagerSingleStagedReactorFactory.class)
public class OSGiUnitTest {
	@Configuration()
	public static Option[] config() {

		return CoreOptions.options(
        	//@formatter:off	
				felix(),
            systemProperty("org.ops4j.pax.logging.DefaultServiceLog.level").value("INFO"),
            cleanCaches(),
            junitBundles(),
            mavenBundle("javax.transaction","com.springsource.javax.transaction","1.1.0"),
    		mavenBundle("org.apache.derby","derby","10.8.1.2"),
            mavenBundle("com.atomikos","transactions-osgi").versionAsInProject(),
            mavenBundle("com.atomikos","service-tracker-osgi-example-db").versionAsInProject(),
            mavenBundle("com.atomikos","service-tracker-osgi-example-api").versionAsInProject(),
            mavenBundle("com.atomikos","service-tracker-osgi-example-impl").versionAsInProject(),
            mavenBundle("com.atomikos","service-tracker-osgi-example-webui").versionAsInProject()
            //@formatter:on
				);
	}

	private static final int timeout = 30000;
	@Inject
	BundleContext ctx;

	@Test
	public void findDataSource() throws InterruptedException {
		Thread.sleep(10);
		assertThat(ctx, is(notNullValue()));
		System.out.println("BundleContext of bundle injected: " + ctx.getBundle().getSymbolicName());

		ServiceTracker tracker = new ServiceTracker(ctx, DataSource.class.getName(), null);
		tracker.open();
		DataSource dataSource = (DataSource) tracker.waitForService(timeout);

		tracker.close();
		assertNotNull(dataSource);
	}
	
	@Test
	public void findAccountManager() throws InterruptedException {
		Thread.sleep(10);
		assertThat(ctx, is(notNullValue()));
		System.out.println("BundleContext of bundle injected: " + ctx.getBundle().getSymbolicName());

		ServiceTracker tracker = new ServiceTracker(ctx, AccountManager.class.getName(), null);
		tracker.open();
		AccountManager accountManager = (AccountManager) tracker.waitForService(timeout);

		tracker.close();
		assertNotNull(accountManager);
	}
	
	@Test
	public void withdraw() throws Exception {
		assertThat(ctx, is(notNullValue()));
		System.out.println("BundleContext of bundle injected: " + ctx.getBundle().getSymbolicName());

		ServiceTracker tracker = new ServiceTracker(ctx, AccountManager.class.getName(), null);
		tracker.open();
		AccountManager accountManager = (AccountManager) tracker.waitForService(timeout);

		tracker.close();
		int accno = 50;
		long balance=accountManager.getBalance(accno);
		int amount = 10;
		accountManager.withdraw(accno, amount);
		assertEquals((balance-amount), accountManager.getBalance(accno));
	}
	
	 public static void main(String[] args) throws TimeoutException, IOException {
         createContainer(
                         createTestSystem(combine(config(),webProfile()
                                         ))).start();
 }
}
