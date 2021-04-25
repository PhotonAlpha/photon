package com.ethan.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {
	@Bean
	@LoadBalanced //此注解会开启restTemplate负载均衡能力
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
