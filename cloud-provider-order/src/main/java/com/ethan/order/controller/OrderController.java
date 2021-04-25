package com.ethan.order.controller;

import com.ethan.common.vo.CommonResult;
import com.ethan.loadbalancer.ILoadBalancer;
import com.ethan.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("api")
public class OrderController {
	// public static final String API_PAYMENT = "http://localhost:8081/";
	public static final String API_PAYMENT = "http://cloud-provider-payment/";
	private final OrderService orderService;
	private final RestTemplate restTemplate;

	public OrderController(OrderService orderService, RestTemplate restTemplate) {
		this.orderService = orderService;
		this.restTemplate = restTemplate;
	}


	@Autowired
	private ILoadBalancer loadBalancer;
	@Resource


	@GetMapping("/consumer/payment/{serial}")
	public ResponseEntity<CommonResult> createPayment(@PathVariable("serial") String serial) {
		CommonResult commonResult = restTemplate.getForObject(API_PAYMENT + "api/payment/" +serial, CommonResult.class);
		return ResponseEntity.ok(commonResult);
	}

	@GetMapping("/consumer/payments")
	public ResponseEntity<CommonResult> listPayments() {
		CommonResult success = restTemplate.getForObject(API_PAYMENT + "api/payments", CommonResult.class);
		return ResponseEntity.ok(success);
	}
}
