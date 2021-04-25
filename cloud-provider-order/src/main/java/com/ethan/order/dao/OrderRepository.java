package com.ethan.order.dao;

import com.ethan.common.entity.CommonOrder;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<CommonOrder, Long> {
}
