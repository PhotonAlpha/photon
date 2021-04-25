package com.ethan.storage.controller;

import com.ethan.common.vo.CommonResult;
import com.ethan.storage.service.StorageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @className: StorageController
 * @author: Ethan
 * @date: 25/4/2021
 **/
@RestController
public class StorageController {
    @Resource
    private StorageService storageService;

    @PostMapping("/storage/decrease")
    public CommonResult decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count) {
        boolean success = storageService.decrease(productId, count);
        return new CommonResult(200, "更新库存：" + success);
    }
}
