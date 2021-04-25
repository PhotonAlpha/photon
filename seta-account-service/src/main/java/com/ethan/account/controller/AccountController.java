package com.ethan.account.controller;

import com.ethan.account.service.AccountService;
import com.ethan.common.vo.CommonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @className: OrderController
 * @author: Ethan
 * @date: 22/4/2021
 **/
@RestController
public class AccountController {
    @Resource
    private AccountService accountService;

    @PostMapping("/account/decrease")
    public CommonResult decrease(@RequestParam("userId") Long userId, @RequestParam("price") BigDecimal price, @RequestParam("timeout") Boolean timeout) throws InterruptedException {
        if (timeout) {
            TimeUnit.SECONDS.sleep(20);
        }
        boolean success = accountService.decrease(userId, price);
        return new CommonResult(200, "更新账户：" + success);
    }
}
