-Dspring.profiles.active=eureka2 -Xms100m -Xmx200m

```yaml
eureka:
  instance:
    hostname: localhost #eureka服务端的实例名称
  client:
    register-with-eureka: false #false 表示不向注册中心注册自己
    fetch-registry: false #fasle 表示自己就是注册中心，职责是维护实例，并不需要去检索服务
    service-url:
#      设置与eureka server 交互的地址查询服务和注册服务都需要依赖这个地址
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

集群搭建：
1. C:\Windows\System32\drivers\etc -> hosts ->  
   ```
       127.0.0.1       ethan-primary.com
       127.0.0.1       ethan-secondary.com
    ```
   
2. 修改配置，相互注册
```yaml
server:
  port: 7001

eureka:
  instance:
    hostname: ethan-primary.com #eureka服务端的实例名称
  client:
    register-with-eureka: false #false 表示不向注册中心注册自己
    fetch-registry: false #fasle 表示自己就是注册中心，职责是维护实例，并不需要去检索服务
    service-url:
#      设置与eureka server 交互的地址查询服务和注册服务都需要依赖这个地址
      defaultZone: http://ethan-secondary.com:7002/eureka/
```
3. 负载均衡
   `@LoadBalanced //此注解会开启restTemplate负载均衡能力`
   
4. 服务发现discovery
通过服务发现来获取服务信息`@EnableDiscoveryClient`
   
```
EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.
```
Eureka自我保护：某时刻一个微服务不可用了(可能因为网络故障)，Eureka不会立即清理，依旧会对该微服务的信息进行保存。属于CAP里面的AP分支
怎么禁止自我保护： 
```yaml
----------------server--------------------
  server:
    #    关闭自我保护机制，保证不可用服务及时被剔除
    enable-self-preservation: false
    eviction-interval-timer-in-ms: 2000
----------------clinet---------------------    
  instance:
   prefer-ip-address: true #访问路径可以显示IP地址
   #    Eureka客户端向服务端发送心跳的时间间隔，默认30S
   lease-expiration-duration-in-seconds: 10
   #   Eureka服务端在收到最后一次心跳后等待时间上限，单位默认90S，超时即剔除服务
   lease-renewal-interval-in-seconds: 20
```
