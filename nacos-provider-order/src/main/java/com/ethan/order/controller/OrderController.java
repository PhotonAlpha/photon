package com.ethan.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ethan.common.vo.CommonResult;
import com.ethan.order.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: Ethan
 * @Date: 02/4月/2021 16:35
 */
@RestController
@Slf4j
public class OrderController {
	@Value("${server.port}")
	private String port;
	@Value("${service-url.nacos-user-service}")
	private String serverUrl;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private PaymentFeignService paymentFeignService;

	@GetMapping(value = "/consumer/payment")
	@SentinelResource(value = "payment")
	public CommonResult payment() {
		log.info("OrderController****:{}", port);
		return paymentFeignService.createPayment();
	}

}
