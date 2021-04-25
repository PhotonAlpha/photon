package com.ethan.order.service;

import com.ethan.common.vo.CommonResult;
import org.springframework.stereotype.Component;

/**
 * @Author: Ethan
 * @Date: 07/3月/2021 21:56
 */
@Component
public class PaymentFeignFallbackService implements PaymentFeignService {
	@Override
	public CommonResult createPayment() {
		return new CommonResult(500, "createPayment插入数据超时，请稍后再试！");
	}

	@Override
	public CommonResult createPaymentTimeout() {
		return new CommonResult(500, "createPaymentTimeout插入数据超时，请稍后再试！");
	}

	@Override
	public CommonResult listPayments() {
		return new CommonResult(500, "listPayments插入数据超时，请稍后再试！");
	}
}
