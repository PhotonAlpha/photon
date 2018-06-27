/**  
 * Copyright © 2018 personal. All rights reserved.
 * @Description: //TODO
 * @author: qiangcao  
 * @date: Jun 27, 2018 11:35:47 AM
 * @version: V1.0  
 */
package top.jetz.photon.quartz;

import org.quartz.JobDetail;
import org.quartz.Trigger;

public class JobScheduleModel {
	private JobDetail jobDetail;
	private Trigger trigger;
	
	public JobScheduleModel(JobDetail jobDetail, Trigger trigger) {
		this.jobDetail = jobDetail;
		this.trigger = trigger;
	}
	public JobDetail getJobDetail() {
		return jobDetail;
	}
	public void setJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}
	public Trigger getTrigger() {
		return trigger;
	}
	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}
	
}
