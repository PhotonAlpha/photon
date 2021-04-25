package com.ethan.order.service;

import com.ethan.common.entity.CommonPayment;
import com.ethan.order.dao.PaymentRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
	@Resource
	private PaymentRepository paymentRepository;

	@Override
	public CommonPayment createPayment() {
		CommonPayment commonPayment = new CommonPayment();
		commonPayment.setSerial(UUID.randomUUID() + ":" + Thread.currentThread().getName());
		log.info("插入数据成功 {}", Thread.currentThread().getName());
		return commonPayment;
	}

	@Override
	@HystrixCommand(fallbackMethod = "createPaymentTimeOutHandler", commandProperties = {
			@HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "2000")
	})
	public CommonPayment createPaymentTimeOut() throws InterruptedException {
		int timeout = 3;
		TimeUnit.SECONDS.sleep(timeout);
		CommonPayment commonPayment = new CommonPayment();
		commonPayment.setSerial(UUID.randomUUID() + ":" + Thread.currentThread().getName());
		log.info("超时插入数据成功 {}", Thread.currentThread().getName());
		return commonPayment;
	}

	public CommonPayment createPaymentTimeOutHandler() {
		CommonPayment commonPayment = new CommonPayment();
		log.info("插入数据失败 {}", Thread.currentThread().getName());
		commonPayment.setSerial("插入数据失败" + UUID.randomUUID() + ":" + Thread.currentThread().getName());
		return commonPayment;
	}

	@Override
	public List<CommonPayment> getPayments() {
		PageRequest pageRequest = PageRequest.of(0, 20);
		Page<CommonPayment> paymentPage = paymentRepository.findAll(pageRequest);
		return paymentPage.getContent();
	}
}
