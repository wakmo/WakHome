package com.atomikos.osgi.sample.web.internal;


import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import com.atomikos.osgi.sample.AccountManager;
 

public class AccountManagerServiceTracker extends ServiceTracker {
	

	
	
	
	public AccountManagerServiceTracker(BundleContext context) {
		super(context, AccountManager.class.getName(), null);

	}
	
	 
	public Object addingService(ServiceReference reference) { 
		AccountManager accountManager = (AccountManager) context.getService(reference); 
		return accountManager; 
	} 
 
	public void removedService(ServiceReference reference, Object service) { 
	//	AccountManager accountManager = (AccountManager) service; 
		context.ungetService(reference); 
		
		 
	} 
	
}