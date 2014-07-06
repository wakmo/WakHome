/* Copyright 2008 Alin Dreghiciu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.atomikos.osgi.sample.web.internal;

import java.util.logging.Logger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.atomikos.osgi.sample.AccountManager;

/**
 * Web Ui. We need a Ui to interact with OSGi bundles...
 * 
 * @author Pascal Leclercq
 * 
 */
public final class Activator implements BundleActivator {

	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(Activator.class
			.getName());

	private AccountManagerServiceTracker accountManagerServiceTracker;
	private HttpServiceTracker httpServiceTracker;

	public void start(BundleContext context) {
		LOGGER.info("starting webui...");
		accountManagerServiceTracker = new AccountManagerServiceTracker(context);
		accountManagerServiceTracker.open();

		Controller controller = new Controller(
				(AccountManager) accountManagerServiceTracker.getService());
		httpServiceTracker = new HttpServiceTracker(context, controller);

		httpServiceTracker.open();

	}

	public void stop(BundleContext context) {
		LOGGER.info("stopping webui...");
		httpServiceTracker.close();
		accountManagerServiceTracker.close();
	}
}
