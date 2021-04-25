package com.ethan.order.controller;

import com.ethan.common.vo.CommonResult;
import com.ethan.order.entity.Order;
import com.ethan.order.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @className: OrderController
 * @author: Ethan
 * @date: 22/4/2021
 **/
@RestController
public class OrderController {
    @Resource
    private OrderService orderService;

    @PostMapping("/order")
    public CommonResult createOrder(@RequestBody Order order) {
        orderService.createOrder(order);
        return new CommonResult(200, "订单创建成功");
    }
}
