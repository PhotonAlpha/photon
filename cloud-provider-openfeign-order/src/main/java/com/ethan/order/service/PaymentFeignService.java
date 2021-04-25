package com.ethan.order.service;

import com.ethan.common.vo.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


@Component
@FeignClient(value = "CLOUD-PROVIDER-PAYMENT", fallback = PaymentFeignFallbackService.class)
public interface PaymentFeignService {
	@GetMapping("/api/payment")
	CommonResult createPayment();

	@GetMapping("/api/payment/timeout")
	CommonResult createPaymentTimeout();

	@GetMapping("/api/payments")
	CommonResult listPayments();

}
