package com.ethan.order.controller;

import com.ethan.common.vo.CommonResult;
import com.ethan.common.entity.CommonPayment;
import com.ethan.order.service.PaymentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api")
@Slf4j
public class PaymentController {
	@Value("${server.port}")
	private String serverPort;

	private final PaymentServiceImpl paymentService;

	@Resource
	private DiscoveryClient discoveryClient;


	public PaymentController(PaymentServiceImpl paymentService) {
		this.paymentService = paymentService;
	}

	@GetMapping("/payment")
	public ResponseEntity<CommonResult> createPayment() {
		CommonPayment commonPayment = paymentService.createPayment();
		CommonResult success = new CommonResult(200, "插入数据成功，serverPort：" + serverPort, commonPayment);
		return ResponseEntity.ok(success);
	}
	@GetMapping("/payment/timeout")
	public ResponseEntity<CommonResult> createPaymentTimeout() throws InterruptedException {
		CommonPayment commonPayment = paymentService.createPaymentTimeOut();
		CommonResult success = new CommonResult(200, "插入数据成功，serverPort：" + serverPort, commonPayment);
		return ResponseEntity.ok(success);
	}

	@GetMapping("/payments")
	public ResponseEntity<CommonResult> listPayments() {
		List<CommonPayment> commonPayments = paymentService.getPayments();
		CommonResult success = new CommonResult(200, "查询数据成功，serverPort：" + serverPort, commonPayments);
		return ResponseEntity.ok(success);
	}

	@GetMapping("/payment/discovery")
	public List discovery() {
		List<String> services = discoveryClient.getServices();
		for (String service : services) {
			log.info("****element {}", service);
		}

		List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PROVIDER-PAYMENT");
		for (ServiceInstance instance : instances) {
			log.info("****instance {} {}", instance.getServiceId(), instance.getUri());
		}
		return services;
	}

	@GetMapping("/payment/feign/timeout")
	public String paymentFeignTimeout() {
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return serverPort;
	}

}
