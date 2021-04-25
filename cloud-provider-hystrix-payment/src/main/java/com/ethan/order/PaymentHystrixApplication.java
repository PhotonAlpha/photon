package com.ethan.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.ethan")
@EnableJpaRepositories(basePackages = "com.ethan.order.dao")
@EntityScan("com.ethan.common.entity")
// @EnableEurekaClient
// eureka zookeeper necos 等配置启用注解
@EnableDiscoveryClient
@EnableCircuitBreaker
public class PaymentHystrixApplication {
	public static void main(String[] args) {
		SpringApplication.run(PaymentHystrixApplication.class, args);
	}
}
