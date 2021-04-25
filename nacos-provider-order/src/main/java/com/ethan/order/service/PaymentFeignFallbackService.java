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

}
