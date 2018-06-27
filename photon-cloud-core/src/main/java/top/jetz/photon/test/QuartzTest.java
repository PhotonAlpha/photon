package top.jetz.photon.test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import top.jetz.photon.quartz.SchedulerActuator;

public class QuartzTest {
	public static void main(String[] args) {
		SchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler = null;
		
		try {
			scheduler = factory.getScheduler();
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime time1 = LocalDateTime.now();
			LocalDateTime time2 = LocalDateTime.now().plusSeconds(5);
			LocalDateTime time3 = LocalDateTime.now().plusSeconds(25);
			String current = time1.format(dtf);
			String plustime = time2.format(dtf);
			System.out.println(current);
			System.out.println(plustime);
			
			LocalDateTime dateTime = LocalDateTime.parse(plustime, dtf);
			Date triggerStartTime = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
			Date triggerStartTime2 = Date.from(LocalDateTime.parse(time3.format(dtf), dtf).atZone(ZoneId.systemDefault()).toInstant());
			
			//create a job
			JobDetail job = JobBuilder.newJob(SchedulerActuator.class)
				.withIdentity("restart", "appmonitor")
				.usingJobData("appId", LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
				.build();
			//define a trigger 
			Trigger trigger = TriggerBuilder.newTrigger().forJob(job)
				.startAt(triggerStartTime)
				.build();
			
			scheduler.scheduleJob(job, trigger);
			scheduler.start();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//create a job
			JobDetail job2 = JobBuilder.newJob(SchedulerActuator.class)
				.withIdentity("restart", "appmonitor")
				.usingJobData("appId", LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
				.build();
			//define a trigger 
			Trigger trigger2 = TriggerBuilder.newTrigger().forJob(job2)
				.startAt(triggerStartTime2)
				.build();
			System.out.println("<<<<<<<<<<<<");
			System.out.println(scheduler.checkExists(job2.getKey()));
			scheduler.scheduleJob(job2, trigger2);
			
//			scheduler.s
//			scheduler.shutdown(true);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}
	
}
