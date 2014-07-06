package com.atomikos.osgi.sample;

import static org.ops4j.pax.exam.CoreOptions.felix;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;
import static org.ops4j.pax.exam.CoreOptions.webProfile;
import static org.ops4j.pax.exam.OptionUtils.combine;
import static org.ops4j.pax.exam.spi.PaxExamRuntime.createContainer;
import static org.ops4j.pax.exam.spi.PaxExamRuntime.createTestSystem;

import java.io.IOException;

import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.TimeoutException;

public class OSGiContainer {
	private static Option[] config() {

		return CoreOptions
				.options(
						// @formatter:off
						felix(),
						mavenBundle("javax.transaction",
								"com.springsource.javax.transaction", "1.1.0"),
						mavenBundle("org.apache.derby", "derby", "10.8.1.2"),
						mavenBundle("com.atomikos", "transactions-osgi")
								.versionAsInProject(),
						mavenBundle("com.atomikos",
								"service-tracker-osgi-example-db-axt")
								.versionAsInProject(),
						mavenBundle("com.atomikos",
								"service-tracker-osgi-example-api")
								.versionAsInProject(),
						mavenBundle("com.atomikos",
								"service-tracker-osgi-example-impl")
								.versionAsInProject(),
						mavenBundle("com.atomikos",
								"service-tracker-osgi-example-webui")
								.versionAsInProject(),
						mavenBundle("com.atomikos", "transactions-jmx")
								.versionAsInProject().noStart(),
						mavenBundle("com.atomikos", "atomikos-licensing")
								.versionAsInProject().noStart()
				// @formatter:on
				);
	}

	public static void main(String[] args) throws TimeoutException, IOException {
		String license_folder = System
				.getProperty("com.atomikos.icatch.license_folder");
		if (license_folder == null) {
			System.err
					.println("must specify com.atomikos.icatch.license_folder to a folder containing a valid license");
			System.exit(1);
		}
		createContainer(
				createTestSystem(combine(config(), webProfile(),
						systemProperty("com.atomikos.icatch.license_folder")
								.value(license_folder)))).start();
	}
}
