package com.atomikos.osgi.sample.internal;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

import com.atomikos.osgi.sample.AccountManager;
import com.atomikos.osgi.sample.internal.dao.jdbc.AccountDaoImpl;
import com.atomikos.osgi.sample.internal.impl.AccountManagerImpl;
 

public class DataSourceTracker extends ServiceTracker {
	
	private final AccountManagerImpl accountManager = new AccountManagerImpl();
	private final AccountDaoImpl accountDao = new AccountDaoImpl();
	
	private int finderCount = 0;
	private ServiceRegistration registration = null;
	
	public DataSourceTracker(BundleContext context) {
		super(context, DataSource.class.getName(), null);
		accountManager.setAccountDao(accountDao);
	}
	
	private boolean registering = false; 
	public Object addingService(ServiceReference reference) { 
		DataSource dataSource = (DataSource) context.getService(reference); 
		try {
			checkTables(dataSource);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		accountDao.setDataSource(dataSource); 
 
		synchronized(this) { 
			finderCount ++; 
			if (registering) 
				return dataSource; 
			registering = (finderCount == 1); 
			if (!registering) 
				return dataSource; 
		} 
 
		ServiceRegistration reg = context.registerService( 
				AccountManager.class.getName(), accountManager, null); 
 
		synchronized(this) { 
			registering = false; 
			registration = reg; 
		} 
 
		return dataSource; 
	} 
 
	public void removedService(ServiceReference reference, Object service) { 
		DataSource dataSource = (DataSource) service; 
		System.out.println(dataSource);
		accountDao.setDataSource(null); 
		context.ungetService(reference); 
 
		ServiceRegistration needsUnregistration = null; 
		synchronized(this) { 
			finderCount --; 
			if (finderCount == 0) { 
				needsUnregistration = registration; 
				registration = null; 
			} 
		} 
 
		if(needsUnregistration != null) { 
			needsUnregistration.unregister(); 
		} 
	} 
private void checkTables(DataSource dataSource) throws Exception {

		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (Exception noConnect) {
			noConnect.printStackTrace();
			System.err.println("Failed to connect.");
			System.err
					.println("PLEASE MAKE SURE THAT DERBY IS INSTALLED AND RUNNING");
			throw noConnect;
		}

		try {

			Statement s = conn.createStatement();
			try {
				s.executeQuery("select * from Accounts");
			} catch (SQLException ex) {
				// table not there => create it
				System.err.println("Creating Accounts table...");
				s.executeUpdate("create table Accounts ( "
						+ " account VARCHAR ( 20 ), owner VARCHAR(300), balance DECIMAL (19,0) )");
				for (int i = 0; i < 100; i++) {
					s.executeUpdate("insert into Accounts values ( "
							+ "'account" + i + "' , 'owner" + i + "', 10000 )");
				}
			}
			s.close();
		} catch (Exception e) {
			//error = true;
			throw e;
		} finally {
			if (conn != null)
				conn.close();

		}

		// That concludes setup

	}
}