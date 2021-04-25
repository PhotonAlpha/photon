package com.ethan.loadbalancer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Ethan
 * @Date: 3/1/2021 下午 10:00
 */
@Slf4j
@Component
public class MyLoadBalancer implements ILoadBalancer{
	private AtomicInteger atomicInteger = new AtomicInteger(0);
	public final int getAndIncrement() {
		int current;
		int next;
		do {
			current = this.atomicInteger.get();
			next = current >= Integer.MAX_VALUE ? 0 : current + 1;

		} while (!this.atomicInteger.compareAndSet(current, next));
		log.info("***第几次访问次数next:{}", next);
		return next;
	}

	@Override
	public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
		int next = getAndIncrement();
		int index = next % serviceInstances.size();
		return serviceInstances.get(index);
	}

}
