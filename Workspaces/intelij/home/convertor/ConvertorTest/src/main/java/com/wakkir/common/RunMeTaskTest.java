package com.wakkir.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.expression.ParseException;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

import com.mkyong.common.RunMeTask;

public class RunMeTaskTest
{
	
	
	/** @param args
	 * @throws Exception */
	public static void main(String[] args) throws Exception
	{
		ClassPathResource res = new ClassPathResource("mySpring-Quartz.xml");

		XmlBeanFactory ctx = new XmlBeanFactory(res);

		// get the quartzFactory bean
		Scheduler scheduler = (Scheduler) ctx.getBean("scheduleFactory");

		// get the task to run or it could have been injected
		RunMeTask runMeTask = (RunMeTask) ctx.getBean("runMeTask");

		// runMeTask.printMe();
		try
		{
			///***init method***///
			//getList of active scheduler tasks
			//loop start
				//create MethodInvokingJobDetailFactoryBean
				//create CronTriggerBean
				//assign to scheduler as scheduler.scheduleJob
			//loop end
			//start the scheduler
			
			///***update trigger***///
			//create new CronTriggerBean
			//get & set new and old trigger name
			//reschedule job
			
		 ///***Activate jobs***///
		 //resume trigger
		 //resume jobs
			
		 ///***Inactivate jobs***///
		 //pause trigger
		 //pause jobs
			
		 ///***stop***///
		 //shutdown() 
			List<ValuePair> jobargs=new ArrayList<ValuePair>(); 
			jobargs.add(new ValuePair("StartOffsetValue", new Integer(-10)));
			jobargs.add(new ValuePair("EndOffsetValue", new Integer(1)));
			
			MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
			jobDetail.setTargetObject(runMeTask);
			jobDetail.setTargetMethod("printMe");
			jobDetail.setArguments(jobargs.toArray());
			jobDetail.setGroup("JOBGROUP1");
			jobDetail.setName("JOB1");			
			jobDetail.setConcurrent(false);
			jobDetail.afterPropertiesSet();
			// create trigger
			/*SimpleTriggerBean trigger = new SimpleTriggerBean(); trigger.setBeanName(taskName);
			 * trigger.setJobDetail((JobDetail) jobDetail.getObject()); trigger.setRepeatInterval(5000);
			 * trigger.afterPropertiesSet(); scheduler.scheduleJob((JobDetail) jobDetail.getObject(), trigger); */
			CronTriggerBean cronTrigger = new CronTriggerBean();
			cronTrigger.setBeanName("CRON0001");
			cronTrigger.setGroup("TRGGROUP1");
			cronTrigger.setName("TRIG1");			
			cronTrigger.setJobName("JOB1");
			cronTrigger.setJobGroup("JOBGROUP1");
			cronTrigger.setCronExpression("0/5 * * * * ?");
			cronTrigger.afterPropertiesSet();
			
			System.out.println("Next fire : "+cronTrigger.getFireTimeAfter(new Date()));
			// scheduler.scheduleJob(jobDetail, cronTrigger);
			scheduler.scheduleJob((JobDetail) jobDetail.getObject(), cronTrigger);

			// ////////////////////////////////////////////////////////////////////
			
			MethodInvokingJobDetailFactoryBean jobDetail2 = new MethodInvokingJobDetailFactoryBean();
			jobDetail2.setTargetObject(runMeTask);
			jobDetail2.setTargetMethod("printMe2");	
			jobDetail2.setGroup("JOBGROUP2");
			jobDetail2.setName("JOB2");
			jobDetail2.setConcurrent(false);
			jobDetail2.afterPropertiesSet();
			// create trigger
			/*SimpleTriggerBean trigger = new SimpleTriggerBean(); trigger.setBeanName(taskName);
			 * trigger.setJobDetail((JobDetail) jobDetail.getObject()); trigger.setRepeatInterval(5000);
			 * trigger.afterPropertiesSet(); scheduler.scheduleJob((JobDetail) jobDetail.getObject(), trigger); */
			CronTriggerBean cronTrigger2 = new CronTriggerBean();
			cronTrigger2.setBeanName("CRON0001");
			cronTrigger2.setGroup("TRGGROUP2");
			cronTrigger2.setName("TRIG2");			
			cronTrigger2.setJobName("JOB2");
			cronTrigger2.setJobGroup("JOBGROUP2");
			cronTrigger2.setCronExpression("0/5 * * * * ?");
			cronTrigger2.afterPropertiesSet();
			
			System.out.println("2Next fire : "+cronTrigger2.getFireTimeAfter(new Date()));
			// scheduler.scheduleJob(jobDetail, cronTrigger);
			scheduler.scheduleJob((JobDetail) jobDetail2.getObject(), cronTrigger2);

			// /////////////////////////////////////////////////////////////////////

			scheduler.start();

			try
			{
				// wait 65 seconds to show job
				Thread.sleep(30L * 1000L);
				// executing...
			}
			catch (Exception e)
			{
			}
			System.out.println("----------------------------------");
			CronTriggerBean cronTrigger3 = new CronTriggerBean();
			cronTrigger3.setBeanName("CRON0002");
			cronTrigger3.setGroup("TRGGROUP2");
			cronTrigger3.setName("TRIG3");
			cronTrigger3.setJobName("JOB2");
			cronTrigger3.setJobGroup("JOBGROUP2");
			cronTrigger3.setCronExpression("0/10 * * * * ?");
			cronTrigger3.afterPropertiesSet();
			
			System.out.println("3Next fire : "+cronTrigger3.getFireTimeAfter(new Date()));
			// Trigger trg= (Trigger) scheduler.getTrigger("CRON0002", "GROUP1");
			scheduler.rescheduleJob("TRIG2", "TRGGROUP2", cronTrigger3);

		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SchedulerException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
