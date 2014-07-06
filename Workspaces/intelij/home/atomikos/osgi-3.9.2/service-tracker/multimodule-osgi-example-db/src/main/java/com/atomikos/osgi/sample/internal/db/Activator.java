package com.atomikos.osgi.sample.internal.db;

import javax.sql.DataSource;

import org.apache.derby.jdbc.EmbeddedXADataSource;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.atomikos.jdbc.AtomikosDataSourceBean;

public class Activator implements BundleActivator {

	private AtomikosDataSourceBean dataSource;

	public void start(BundleContext context) throws Exception {
			System.err.println("Starting database connection pool...");
		
			EmbeddedXADataSource embeddedXADataSource = new EmbeddedXADataSource();
			embeddedXADataSource.setDatabaseName("db");
			embeddedXADataSource.setCreateDatabase("create");

			dataSource = new AtomikosDataSourceBean();
			dataSource.setUniqueResourceName("OSGI");
			dataSource.setXaDataSource(embeddedXADataSource);
			dataSource.setMaxPoolSize(10);
			dataSource.init();
			/*
			 * Eventually customize to be able to register multiple datasource...
			 * Dictionary<String, String> props=new Hashtable<String, String>();
			 * props.put("uniqueResourceName", "OSGI");
			 */
			context.registerService(DataSource.class.getName(), dataSource, null);
		
	}

	public void stop(BundleContext context) throws Exception {
		System.err.println("Stopping database connection pool...");
		dataSource.close();
	}

}
