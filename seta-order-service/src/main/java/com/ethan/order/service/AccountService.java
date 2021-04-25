package com.ethan.order.service;

import com.ethan.common.vo.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @className: OrderService
 * @author: Ethan
 * @date: 22/4/2021
 **/
@FeignClient(value = "seata-account-service")
public interface AccountService {
    @PostMapping("/account/decrease")
    CommonResult decrease(@RequestParam("userId") Long productId, @RequestParam("price") BigDecimal price, @RequestParam("timeout") Boolean timeout);
}
