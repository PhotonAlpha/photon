package com.ethan.order.controller;

import com.ethan.common.vo.CommonResult;
import com.ethan.order.service.PaymentFeignService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api")
@DefaultProperties(defaultFallback = "defaultFallbackHandler", commandProperties = {
		@HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "1000")
})
public class OrderController {
	// public static final String API_PAYMENT = "http://localhost:8081/";
	public static final String API_PAYMENT = "http://cloud-provider-payment/";
	private final PaymentFeignService paymentFeignService;

	public OrderController(PaymentFeignService paymentFeignService) {
		this.paymentFeignService = paymentFeignService;
	}

	@GetMapping("/consumer/payment")
	public ResponseEntity<CommonResult> createPayment() {
		CommonResult payment = paymentFeignService.createPayment();
		return ResponseEntity.ok(payment);
	}
	@GetMapping("/consumer/payment/timeout")
	// @HystrixCommand(fallbackMethod = "createPaymentTimeOutHandler", commandProperties = {
	// 		@HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "1000")
	// })
	@HystrixCommand
	public ResponseEntity<CommonResult> createPaymentTimeout() {
		CommonResult payment = paymentFeignService.createPaymentTimeout();
		return ResponseEntity.ok(payment);
	}
	public ResponseEntity<CommonResult> createPaymentTimeOutHandler() {
		CommonResult success = new CommonResult(500, "插入数据超时，请稍后再试！");
		return ResponseEntity.ok(success);
	}

	/**
	 * 设置全局的fallback
	 */
	public ResponseEntity<CommonResult> defaultFallbackHandler() {
		CommonResult failure = new CommonResult(500, "操作超时，请稍后再试！");
		return ResponseEntity.ok(failure);
	}

	/**
	 * 服务熔断
	 * 1. 是否开启断路器
	 * 2. 请求总数阈值： 在快照时间窗内，必须满足请求总数阈值才有资格熔断。默认为20，意味着10S内，如果该hystrix命令的调用次数不足20次，即使所有的请求都超时或其他原因失败，熔断都不会打开、
	 * 3. 快照时间窗口： 断路器是否打开需要统计一些请求和错误数据，而统计的时间范围就是快照时间窗，默认为最近的10秒
	 * 4. 错误百分比阈值： 当请求总数在快照时间窗内超过了阈值，比如发生么30次调用，如果在这30次调用中，有15次发生了超时异常，也就是超过50%的错误百分比，在默认设定50%阈值情况下，这时候就会将断路器打开。
	 * 10次访问 失败率达到60% 就断路, 在10S后尝试半开放
	 */
	@GetMapping("/consumer/payment/circuitBreaker/{id}")
	@HystrixCommand(fallbackMethod = "createPaymentCircuitBreakerHandler",
			groupKey = "strGroupCommand",
			commandKey = "strCommand",
			threadPoolKey = "strThreadPool",
			commandProperties = {
			//常用配置
			// 该属性用来设置当前断路器打开之后的休眠时间窗。休眠时间窗结束之后，会将断路器设置为“半开”状态，尝试熔断的请求命令。
			// 如果依然失败将断路器继续设置“打开”状态，
			// 如果成功就设置为“关闭”状态。
			@HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS, value = "5000"),
			// 断路器强制打开
			@HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_FORCE_OPEN, value = "false"),
			// 断路器强制关闭
			@HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_FORCE_CLOSED, value = "false"),
			// 滚动时间窗设置，该时间用于断路器判断健康度时需要收集信息的持续时间
			@HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_STATS_TIME_IN_MILLISECONDS, value = "10000"),
			// 该属性用来设置滚动时间窗统计指标信息时划分“桶”的数量，断路器在收集指标信息的时候会根据设置的时间窗长度拆分成多个“桶”来累积各度量值，
			//  每个“桶”记录了一段时间内的采集指标。
			// 比如10秒内拆分成10个“桶”收集，所以timeinMilleseconds必须能被numBuckets整除。否则会抛出异常
			@HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_STATS_NUM_BUCKETS, value = "10"),
			// 该属性用来设置对命令执行的延迟是否使用百分位数来跟踪和计算。如果设置false，那么所有的概要统计都将返回-1
			@HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_PERCENTILE_ENABLED, value = "false"),
			// 该属性用来设置百分位统计的滚动窗口的持续时间，单位为毫秒
			@HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_PERCENTILE_TIME_IN_MILLISECONDS, value = "60000"),
			// 该属性用来设置百分位统计滚动窗口中使用“桶”的数量
			@HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_PERCENTILE_NUM_BUCKETS, value = "60000"),
			// 该属性用来设置在执行过程中每个“桶”中保留的最大执行次数。如果在滚动时间窗内发生超过该设定值的执行次数，
			// 就从最初的位置开始重写。例如，100，滚动窗口为10S，若在10S内一个“桶”中发生了500次执行，
			// 那么该“桶”中只保留最后的100次执行的统计。另外，增加该值的大小将会增加内存量的消耗，并增加排序百分位数所需的计算时间。
			@HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_PERCENTILE_BUCKET_SIZE, value = "100"),
			// 设置采集影响断路器状态的健康快照（请求的成功，错误百分比）的间隔等待时间
			@HystrixProperty(name = HystrixPropertiesManager.METRICS_HEALTH_SNAPSHOT_INTERVAL_IN_MILLISECONDS, value = "500"),
			// 开启请求缓存
			@HystrixProperty(name = HystrixPropertiesManager.REQUEST_CACHE_ENABLED, value = "true"),
			// HystrixCommand的执行和事件是否打印日志到HystrixRequestLog中
			@HystrixProperty(name = HystrixPropertiesManager.REQUEST_LOG_ENABLED, value = "true"),


			@HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ENABLED, value = "true"),
			@HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD, value = "10"),
			@HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS, value = "10000"),
			@HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE, value = "60")
	},
		threadPoolProperties = {
			//设置执行命令线程池的核心线程数，该值也就是命令执行的最大并发量
				@HystrixProperty(name = HystrixPropertiesManager.CORE_SIZE, value = "10"),
			//设置线程池的最大队列大小。当设置为-1时，线程池将使用SynchronousQueue实现队列，
			// 	否则将使用LinkedBlockingQueue实现的队列
				@HystrixProperty(name = HystrixPropertiesManager.MAX_QUEUE_SIZE, value = "-1"),
				// 设置拒绝阈值。通过该参数，即使队列没有达到最大值也能拒绝请求
				// 该参数主要时对LinkedBlockingQueue队列的补充，因为LinkedBlockingQueue队列不能动态修改它的对象大小
				// 而通过该属性就可以调整拒绝请求的队列的大小了。
				@HystrixProperty(name = HystrixPropertiesManager.QUEUE_SIZE_REJECTION_THRESHOLD, value = "5")
		}
	)
	public ResponseEntity<CommonResult> createPaymentCircuitBreaker(@PathVariable("id") Long id) {

		if (id < 0) {
			throw new RuntimeException("****ID 不能是负数");
		}
		CommonResult payment = paymentFeignService.createPayment();
		return ResponseEntity.ok(payment);
	}
	public ResponseEntity<CommonResult> createPaymentCircuitBreakerHandler(@PathVariable("id") Long id) {
		CommonResult success = new CommonResult(500, "id 不能为负数，请稍后再试！" + id);
		return ResponseEntity.ok(success);
	}
}
