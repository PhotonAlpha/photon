package top.jetz.phonton;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
//@RefreshScope
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
    
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.cloud.config.uri}")
    private String config;
    @Value("${spring.datasource.username}")
    private String username;
//    cloud:
//        config:
//          uri: ${CONFIG_SERVER_URI:http://localhost:8888}
    
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String getParameter() {
        return url+"\n"+config+"\n"+username;
    }
}
