package com.imooc.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@EnableFeignClients // Feign访问其他微服务
@EnableEurekaClient // Eureka client
@EnableHystrix  // 断路器
@EnableCircuitBreaker // 断路器
@EnableDiscoveryClient //开启微服务发现能力
@EnableHystrixDashboard // Hystrix监控
@SpringBootApplication // SpringBoot启动注解
public class SearchApplication {

    public static void main(String[] args) {

        SpringApplication.run(SearchApplication.class, args);
    }

    @Bean
    @LoadBalanced  // 负载均衡
    RestTemplate restTemplate() {

        return new RestTemplate();
    }
}
