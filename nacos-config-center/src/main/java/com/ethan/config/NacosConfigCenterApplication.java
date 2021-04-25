package com.ethan.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: Ethan
 * @Date: 02/4月/2021 16:34
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosConfigCenterApplication {
	public static void main(String[] args) {
		SpringApplication.run(NacosConfigCenterApplication.class, args);
	}
}
