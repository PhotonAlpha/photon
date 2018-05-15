package top.jetz.phonton.security;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

@Configuration
public class ResolverConfig {
    
    @Bean(name="encryptablePropertyResolver")
    @Primary
    EncryptablePropertyResolver encryptablePropertyResolver(Environment environment) {
        return new JwtkEncryptablePropertyResolver(environment);
    }
}
