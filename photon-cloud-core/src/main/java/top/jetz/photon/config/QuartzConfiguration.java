/**  
 * Copyright © 2018 personal. All rights reserved.
 * @Description: //TODO
 * @author: qiangcao  
 * @date: Jun 27, 2018 11:34:16 AM
 * @version: V1.0  
 */
package top.jetz.photon.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.StreamSupport;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import top.jetz.photon.quartz.AutowiringSpringBeanJobFactory;

//quartz配置  //TO DO 移到专门的调度中心
//@Configuration
public class QuartzConfiguration {
	@Autowired
	private Environment springEnv;
	
	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) {
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) throws Exception {
//		SchedulerFactory factory = new StdSchedulerFactory();
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
//		factory.setAutoStartup(true);
		factory.setOverwriteExistingJobs(true);
		factory.setJobFactory(jobFactory);
		factory.setQuartzProperties(quartzProperties());
		return factory;
	}
	@Bean
	public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		scheduler.start();
		return scheduler;
	}
	@Bean
    public Properties quartzProperties() throws IOException {
		MutablePropertySources propSrcs = ((AbstractEnvironment)springEnv).getPropertySources();
		Properties properties = new Properties();
		StreamSupport.stream(propSrcs.spliterator(), false)
			.filter(ps -> ps instanceof EnumerablePropertySource)
			.map(ps -> ((EnumerablePropertySource<?>) ps).getPropertyNames())
			.flatMap(Arrays::<String>stream)
			.filter(key -> key.startsWith("org.quartz"))
			.forEach(propName -> properties.setProperty(propName, springEnv.getProperty(propName)));
//		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//		propertiesFactoryBean.setLocation(new ClassPathResource("/xappconfig/quartz.properties"));
//		propertiesFactoryBean.afterPropertiesSet();
//		Properties properties = propertiesFactoryBean.getObject();
		return properties;
    }
}
