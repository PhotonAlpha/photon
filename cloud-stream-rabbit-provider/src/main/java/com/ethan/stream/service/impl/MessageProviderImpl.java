package com.ethan.stream.service.impl;

import com.ethan.stream.service.IMessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Author: Ethan
 * @Date: 28/3月/2021 17:47
 */
@Slf4j
@EnableBinding(Source.class)//定义消息推送的管道
public class MessageProviderImpl implements IMessageProvider {
	@Resource
	private MessageChannel output; //消息发送通道

	@Override
	public String send() {
		String serial = UUID.randomUUID().toString();
		output.send(MessageBuilder.withPayload(serial).build());
		log.info("******serial:{}", serial);
		return serial;
	}
}
