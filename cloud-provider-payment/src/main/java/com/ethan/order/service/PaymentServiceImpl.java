package com.ethan.order.service;

import com.ethan.common.entity.CommonPayment;
import com.ethan.order.dao.PaymentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
	@Resource
	private PaymentRepository paymentRepository;

	@Override
	public CommonPayment createPayment(String serial) {
		CommonPayment commonPayment = new CommonPayment();
		commonPayment.setSerial(serial);
		return paymentRepository.save(commonPayment);
	}

	@Override
	public List<CommonPayment> getPayments() {
		PageRequest pageRequest = PageRequest.of(0, 20);
		Page<CommonPayment> paymentPage = paymentRepository.findAll(pageRequest);
		return paymentPage.getContent();
	}
}
