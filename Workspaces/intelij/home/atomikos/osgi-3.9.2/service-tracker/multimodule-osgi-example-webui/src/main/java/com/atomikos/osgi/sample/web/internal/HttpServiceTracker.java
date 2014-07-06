package com.atomikos.osgi.sample.web.internal;


import java.util.logging.Logger;

import javax.servlet.ServletException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.osgi.util.tracker.ServiceTracker;


public class HttpServiceTracker extends ServiceTracker {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(HttpServiceTracker.class.getName());


	private static final String ALIAS = "/";

	private final Controller controller;
	public HttpServiceTracker(BundleContext context, Controller controller) {
		super(context, HttpService.class.getName(), null);
		this.controller=controller;
	}


	public Object addingService(ServiceReference reference) {
		HttpService httpService = (HttpService) context.getService(reference);
		LOGGER.info("Adding HttpService : "+httpService);
		 // create a default context to share between registrations
        final HttpContext httpContext = httpService.createDefaultHttpContext();
        // register the controller servlet

        try {
			httpService.registerServlet(
			    ALIAS,                            // alias
			    controller,   // registered servlet
			    null,                     // init params
			    httpContext                     // http context
			);
		} catch (ServletException e) {
			LOGGER.warning(e.getMessage());
		} catch (NamespaceException e) {
			LOGGER.warning(e.getMessage());
		}




		return httpService;
	}

	public void removedService(ServiceReference reference, Object service) {
		HttpService httpService = (HttpService) service;
		LOGGER.info("Removing HttpService : "+httpService);
		httpService.unregister(ALIAS);
		context.ungetService(reference);


	}

}