package top.jetz.phonton.security;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;

public class JwtkEncryptablePropertyResolver extends StandardEnvironment implements EncryptablePropertyResolver {
    private final String password = "PassWoooooooorld";
    private final String prefix = "$^.^";
    private final String suffix = "";
    
    private final PooledPBEStringEncryptor encryptor;
    
    public JwtkEncryptablePropertyResolver() {
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

    public JwtkEncryptablePropertyResolver(Environment environment) {
        
        
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
