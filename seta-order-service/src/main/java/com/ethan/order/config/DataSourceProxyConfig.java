package com.ethan.order.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

/**
 * @className: DataSourceProxyConfig
 * @author: Ethan
 * @date: 25/4/2021
 **/
@Configuration
public class DataSourceProxyConfig {
    @Bean
    @LoadBalanced //此注解会开启restTemplate负载均衡能力
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }
    /**
     * 需要将 DataSourceProxy 设置为主数据源，否则事务无法回滚
     *
     * @param druidDataSource The DruidDataSource
     * @return The default datasource
     */
    @Primary
    @Bean("dataSource")
    public DataSource dataSource(DruidDataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }

    // @Bean
    // public PlatformTransactionManager transactionManager() {
    //     JpaTransactionManager transactionManager = new JpaTransactionManager();
    //     transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
    //
    //     return transactionManager;
    // }

}
