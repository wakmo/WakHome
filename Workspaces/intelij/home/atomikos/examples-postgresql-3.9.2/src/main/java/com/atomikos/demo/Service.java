package com.atomikos.demo;

public interface Service {
	
	/**
	 * @return true if processing can be called again.
	 */
	boolean process();

}
