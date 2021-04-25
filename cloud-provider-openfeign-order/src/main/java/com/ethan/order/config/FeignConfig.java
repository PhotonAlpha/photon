package com.ethan.order.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * #  配置Feign日志级别
 *
 * @Author: Ethan
 * @Date: 31/1月/2021 23:01
 */
@Configuration
public class FeignConfig {
	@Bean
	public Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
}
