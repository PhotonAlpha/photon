package top.jetz.phonton;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
//@RefreshScope
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
//        new SpringApplicationBuilder().sources(ClientApplication.class).run(args);
    }
    
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.password_ma}")
    private String password_ma;
    @Value("${spring.datasource.username}")
    private String username;
//    cloud:
//        config:
//          uri: ${CONFIG_SERVER_URI:http://localhost:8888}
    
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String getParameter() {
        return url+"\n"+password_ma+"\n"+username;
    }
}
