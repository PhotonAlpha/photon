package com.ethan.order.service;

import com.ethan.common.vo.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


@Component
@FeignClient(value = "nacos-provider-payment", fallback = PaymentFeignFallbackService.class)
public interface PaymentFeignService {
	@GetMapping("/payment")
	CommonResult createPayment();

}
