/**  
 * Copyright © 2018 personal. All rights reserved.
 * @Description: //TODO
 * @author: qiangcao  
 * @date: Jun 27, 2018 10:42:06 AM
 * @version: V1.0  
 */
package top.jetz.photon.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import top.jetz.photon.constant.SecretKeyEnum;


/**
 * The Class JasyptEncryptionConfig.
 * 自定义加密
 * 方法一： jasyptStringEncryptor 和 encryptablePropertyDetector 结合使用
 * 方法二： 自定义encryptablePropertyResolver
 */
@Configuration
@EnableEncryptableProperties
@PropertySource(name = "EncryptedProperties", value = "classpath:quartz/quartz.properties")
public class JasyptEncryptionConfig {
    private static final Logger logger = LoggerFactory.getLogger(JasyptEncryptionConfig.class);
    /**
     * String encryptor.
     *
     * @return the string encryptor
     */
    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(SecretKeyEnum.KEY.getVal());
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
    
    /**
     * Encryptable property detector.
     *
     * @return the encryptable property detector
     */
    @Bean(name = "encryptablePropertyDetector")
    public EncryptablePropertyDetector encryptablePropertyDetector() {
        return new CustomerEncryptablePropertyDetector();
    }
    
    /**
     * The Class CustomerEncryptablePropertyDetector.
     */
    private static class CustomerEncryptablePropertyDetector implements EncryptablePropertyDetector {
        
        /** The Constant PREFIX. */
        private static final String PREFIX = "$^.^";
        
        /** The Constant SUFFIX. */
//      private static final String SUFFIX = "}";
        @Override
        public boolean isEncrypted(String property) {
            if (property != null && property.startsWith(PREFIX) ) {
                return true;
            }
            return false;
        }

        @Override
        public String unwrapEncryptedValue(String property) {
            String newValue = property.substring(PREFIX.length(), property.length());
            logger.debug("开始解析："+newValue);
            return newValue;
        }
    }
    
    
    /*@Bean(name="encryptablePropertyResolver")
    EncryptablePropertyResolver encryptablePropertyResolver(Environment environment) {
        return new JasyptEncryptablePropertyResolver(environment);
    }*/
}
