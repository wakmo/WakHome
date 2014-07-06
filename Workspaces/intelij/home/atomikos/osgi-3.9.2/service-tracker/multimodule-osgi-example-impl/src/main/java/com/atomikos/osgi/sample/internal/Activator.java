package com.atomikos.osgi.sample.internal;

import javax.sql.DataSource;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.atomikos.osgi.sample.AccountManager;
import com.atomikos.osgi.sample.internal.dao.jdbc.AccountDaoImpl;
import com.atomikos.osgi.sample.internal.impl.AccountManagerImpl;

public class Activator implements BundleActivator {

	
	ServiceRegistration registration;
	public void start(BundleContext context) {
		System.err.println("Starting Account Manager Service...");
		DataSourceTracker tracker = new DataSourceTracker(context);
		
		tracker.open();
		
		DataSource dataSource=null;
		try {
			dataSource = (DataSource) tracker.waitForService(1000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		tracker.close();
		AccountDaoImpl accountDao = new AccountDaoImpl();
		accountDao.setDataSource(dataSource);
		
		AccountManagerImpl accountManager = new AccountManagerImpl();
		accountManager.setAccountDao(accountDao);
		registration= context.registerService(AccountManager.class.getName(), accountManager, null);
		 
	}
 
	public void stop(BundleContext context) {
		System.err.println("Stopping Account Manager Service...");
		
		registration.unregister();
	}
	

	

}
