package com.ethan.order.dao;

import com.ethan.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @className: StorageDao
 * @author: Ethan
 * @date: 20/4/2021
 **/
public interface OrderDao extends JpaRepository<Order, Long> {
}
