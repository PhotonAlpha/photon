package com.ethan.loadbalancer;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * 自定义LoadBalancer
 */
public interface ILoadBalancer {
	/**
	 *
	 * @param serviceInstances
	 * @return
	 */
	ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
