/**  
 * Copyright © 2018 personal. All rights reserved.
 * @Description: //TODO
 * @author: qiangcao  
 * @date: Jun 27, 2018 11:35:40 AM
 * @version: V1.0  
 */
package top.jetz.photon.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

public class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware{
	private static final Logger logger = LoggerFactory.getLogger(AutowiringSpringBeanJobFactory.class);
	
	private transient AutowireCapableBeanFactory beanFactory;
	
	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		beanFactory = applicationContext.getAutowireCapableBeanFactory();
	}
	
	@Override
	protected Object createJobInstance(final TriggerFiredBundle firedBundle) throws Exception {
		final Object job = super.createJobInstance(firedBundle);
		logger.info("cccccccccccccccccccccccccccccccreate job instance");
		beanFactory.autowireBean(job);
		return job;
	}
	
}
