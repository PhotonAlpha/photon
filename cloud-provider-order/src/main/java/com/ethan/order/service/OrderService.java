package com.ethan.order.service;

import com.ethan.common.entity.CommonOrder;
import com.ethan.order.dao.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderService {
	@Resource
	private OrderRepository orderRepository;

	public CommonOrder createPayment(String serial) {
		CommonOrder payment = new CommonOrder();
		payment.setOderNo(serial);
		return orderRepository.save(payment);
	}

	public List<CommonOrder> getPayments() {
		PageRequest pageRequest = PageRequest.of(0, 20);
		Page<CommonOrder> paymentPage = orderRepository.findAll(pageRequest);
		return paymentPage.getContent();
	}
}
