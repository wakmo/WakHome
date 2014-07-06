package com.wakkir.common;

import java.util.Calendar;
import java.util.Date;
import org.quartz.CronTrigger;

public class CronTest
{

	/** @param args */
	public static void main(String[] args) throws Exception
	{
		Calendar cal = Calendar.getInstance();
		Date currTime = cal.getTime();
		CronTrigger tr = new CronTrigger();
		tr.setCronExpression("0 0 0 1-31/2 * ?");
		Date nextFireAt = tr.getFireTimeAfter(currTime);
		System.out.println("Current date&time: " + currTime);
		System.out.println("Next fire at: " + nextFireAt);

	}

}
