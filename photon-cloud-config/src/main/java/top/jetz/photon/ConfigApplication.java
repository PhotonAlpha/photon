package top.jetz.photon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import top.jetz.photon.security.EncryptionConfig;


@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigServer
@Import({EncryptionConfig.class})
@RefreshScope
public class ConfigApplication implements CommandLineRunner{
    
    @Autowired
    ApplicationContext appCtx;
    
    public static void main(String[] args) {
//        SpringApplication.run(ConfigApplication.class, args);
        new SpringApplicationBuilder().sources(ConfigApplication.class).run(args);
//        new SpringApplicationBuilder()
//            .environment(new EncryptableEnvironment(new StandardEnvironment(), new MyEncryptablePropertyDetector()))
//            .sources(ConfigApplication.class).run(args);
    }
    
    
    @Value("${spring.cloud.config.server.git.password}")
    private String password;
    
    @Override
    public void run(String... args) throws Exception {
        Environment environment = appCtx.getBean(Environment.class);
        System.out.println("Environment's password: {}" + environment.getProperty("spring.cloud.config.server.git.password"));
        System.out.println("MyService's password: {}" + password);
        System.out.println("Done!");
    }
    
}
