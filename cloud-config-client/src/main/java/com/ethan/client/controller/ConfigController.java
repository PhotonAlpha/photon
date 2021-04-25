package com.ethan.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Ethan
 * @Date: 21/3月/2021 16:19
 */
@RestController
/**
 * 设置具备刷新能力
 */
@RefreshScope
public class ConfigController {
	@Value("${config.info:default}")
	private String configInfo;
	@Value("${server.port}")
	private String serverPort;
	@GetMapping("config-info")
	public String getConfigInfo() {
		return "server port:" + serverPort + " config:" + configInfo;
	}
}
