/**  
 * Copyright © 2018 personal. All rights reserved.
 * @Description: //TODO
 * @author: qiangcao  
 * @date: Jun 27, 2018 11:33:12 AM
 * @version: V1.0  
 */
package top.jetz.photon.config;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;

// TODO: Auto-generated Javadoc
/**
 * The Class JasyptEncryptablePropertyResolver.
 */
public class JasyptEncryptablePropertyResolver extends StandardEnvironment implements EncryptablePropertyResolver {
    
    /** The password. */
    private final String password = "PassWoooooooorld";
    
    /** The prefix. */
    private final String prefix = "$^.^";
    
    /** The suffix. */
    private final String suffix = "";
    
    /** The encryptor. */
    private final PooledPBEStringEncryptor encryptor;
    
    /**
     * Instantiates a new jasypt encryptable property resolver.
     */
    public JasyptEncryptablePropertyResolver() {
        char[] password = this.password.toCharArray();
        this.encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPasswordCharArray(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
    }

    /**
     * Instantiates a new jasypt encryptable property resolver.
     *
     * @param environment the environment
     */
    public JasyptEncryptablePropertyResolver(Environment environment) {
        
        
        char[] password = this.password.toCharArray();
        this.encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPasswordCharArray(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
    }
    
    @Override
    public String resolvePropertyValue(String value) {
        if(value != null && value.startsWith(prefix) && value.endsWith(suffix)) {
            System.out.println(value+"unwrapEncryptedValue:" + prefix.length() +" ::: " + (value.length() - suffix.length()));
            String val = value.substring(prefix.length(), (value.length() - suffix.length()));
            return encryptor.decrypt(val);
        }
        return value;
    }

}
