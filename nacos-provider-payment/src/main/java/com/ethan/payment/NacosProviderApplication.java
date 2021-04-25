package com.ethan.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: Ethan
 * @Date: 02/4月/2021 16:34
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosProviderApplication {
	public static void main(String[] args) {
		// org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration
		SpringApplication.run(NacosProviderApplication.class, args);
	}
}
