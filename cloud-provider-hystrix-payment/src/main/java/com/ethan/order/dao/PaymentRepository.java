package com.ethan.order.dao;

import com.ethan.common.entity.CommonPayment;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaymentRepository extends PagingAndSortingRepository<CommonPayment, Long> {
}
