/**  
 * Copyright © 2018 personal. All rights reserved.
 * @Description: //TODO
 * @author: qiangcao  
 * @date: Jun 27, 2018 11:35:47 AM
 * @version: V1.0  
 */
package top.jetz.photon.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SchedulerActuator implements Job{
	private final static Logger logger = LoggerFactory.getLogger(SchedulerActuator.class);
	
	private Long id;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobKey key = context.getJobDetail().getKey();
		TriggerKey tiggerKey = context.getTrigger().getKey();
		logger.info("trigger action!!!!!!!!!! ->id:{} -> instance:{} -> trigger key:{}", id, key, tiggerKey);
	}

	
	public void setId(Long id) {
        this.id = id;
    }
}
