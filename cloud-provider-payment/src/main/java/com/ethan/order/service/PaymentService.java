package com.ethan.order.service;

import com.ethan.common.entity.CommonPayment;

import java.util.List;

/**
 * @Author: Ethan
 * @Date: 25/1月/2021 23:28
 */
public interface PaymentService {
	CommonPayment createPayment(String serial);
	List<CommonPayment> getPayments();
}
