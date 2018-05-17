package top.jetz.photon.security;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableEncryptableProperties
@PropertySource(name = "EncryptedProperties", value = "classpath:config/quartz.properties")
public class ResolverConfig {

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(SecretKeyEnum.KEY.getReverseVal());
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
    
    @Bean(name = "encryptablePropertyDetector")
    public EncryptablePropertyDetector encryptablePropertyDetector() {
        return new CustomerEncryptablePropertyDetector();
    }
    
    public static void main(String[] args) {
		EncryptionConfig config = new EncryptionConfig();
		StringEncryptor resolver = config.stringEncryptor();
		System.out.println(resolver.encrypt(""));
		System.out.println(resolver.encrypt(""));
	}
	
	private static class CustomerEncryptablePropertyDetector implements EncryptablePropertyDetector {
		private static final String PREFIX = "^.^${";
		private static final String SUFFIX = "}";
		@Override
		public boolean isEncrypted(String property) {
			if (property != null && property.startsWith(PREFIX) && property.endsWith(SUFFIX)) {
	            return true;
	        }
			return false;
		}

		@Override
		public String unwrapEncryptedValue(String property) {
			String newValue = property.substring(PREFIX.length(), property.indexOf(SUFFIX));
			return newValue;
		}
	}
	
    
//    @Bean(name="encryptablePropertyResolver")
//    EncryptablePropertyResolver encryptablePropertyResolver(Environment environment) {
//        return new JwtkEncryptablePropertyResolver(environment);
//    }
}

@Configuration
public class SchedulerConfiguration {
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
//		propertiesFactoryBean.setLocation(new ClassPathResource("/config/quartz.properties"));
//		propertiesFactoryBean.afterPropertiesSet();
//		Properties properties = propertiesFactoryBean.getObject();
		return properties;
    }
}
