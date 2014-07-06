package com.mkyong.common;

import java.util.Date;

import com.wakkir.common.ValuePair;

public class RunMeTask
{
	public void printMe()
	{		
		System.out.println("Spring 3 + Quartz 1.8.6 ~: "+new Date());
	}
	
	public void printMe(ValuePair[] args)
	{
		ValuePair vp=args[0];
		System.out.println("Spring 3 + Quartz 1.8.6 ~: "+new Date()+" > "+vp.toString());
	}
	
	public void printMe2()
	{
		System.out.println("printMe2 : Spring 3 + Quartz 1.8.6 : "+new Date());
	}
}