package com.ethan.payment.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ethan.common.vo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Author: Ethan
 * @Date: 02/4月/2021 16:35
 */
@RestController
@Slf4j
public class PaymentController {
	@Value("${server.port}")
	private String port;

	@GetMapping(value = "/payment")
	@SentinelResource(value = "payment", fallback = "handlerFallback", blockHandler = "handlerBlock")
	public CommonResult payment() {
		log.info("echo****:{}", port);
		String result = "Payment " + port + " Discovery " + UUID.randomUUID().toString();
		return new CommonResult(200, "success", result);
	}

	public CommonResult handlerFallback() {
		return new CommonResult(501, "failure", "handlerFallback");
	}
	public CommonResult handlerBlock() {
		return new CommonResult(502, "failure", "handlerBlock");
	}
}
