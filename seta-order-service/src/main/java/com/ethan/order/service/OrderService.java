package com.ethan.order.service;

import com.ethan.order.dao.OrderDao;
import com.ethan.order.entity.Order;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @className: OrderService
 * @author: Ethan
 * @date: 22/4/2021
 **/
@Service
@Slf4j
public class OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private StorageService storageService;
    @Resource
    private AccountService accountService;


    //1. 新建订单
    @GlobalTransactional(name = "fsp_create_order", rollbackFor = Exception.class)
    public void createOrder(Order order) {
        // 1. 开始新建订单
        log.info("-->开始新建订单");
        order.setStatus(0);
        orderDao.save(order);
        // 2. 扣减库存
        log.info("-->订单微服务开始调用库存，做扣减");
        storageService.decrease(order.getProductId(), order.getCount());

        // 3. 扣减 账户余额
        log.info("-->订单微服务开始扣除账户余额，做扣减");
        accountService.decrease(order.getUserId(), order.getMoney(), order.getTimeout());

        //4. 修改订单状态从0 到 1
        log.info("-->开始修改订单状态");
        updateOrder(order.getId(), 1);


        log.info("-->下单结束");
    }
    //2. 修改订单状态，从0改为1
    public Order updateOrder(Long orderId, Integer status) {
        Optional<Order> optionalOrder = orderDao.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(1);
            return orderDao.save(order);

        } else {
            return null;
        }
    }
}
