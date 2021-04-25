package com.ethan.stream.controller;

import com.ethan.stream.service.IMessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Ethan
 * @Date: 02/4月/2021 13:53
 */
@RestController
public class SendMessageController {
	@Resource
	private IMessageProvider messageProvider;

	@GetMapping("/sendMessage")
	public String sendMessage() {
		return messageProvider.send();
	}
}
