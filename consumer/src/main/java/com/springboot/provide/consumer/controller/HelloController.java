package com.springboot.provide.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * com.springboot.provide.consumer.HelloController
 *
 * @author Jin
 * @Date 12:41 2021/2/4
 */
@Slf4j
@RestController
public class HelloController {
    @Autowired
    RestTemplate restTemplate;

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate () {
        return new RestTemplate();
    }

    @GetMapping("test/{name}")
    public String test(@PathVariable("name") String name){
        // Robbon:
        // 1.调用Eureka DiscoveryClient 去获得 product  ->url地址列表
        // 2.负载均衡算法
        // 下方url解析为 http://localhost:8080/hello
        log.info("传入url:"+"http://product/test"+"?name={}",name);
        return restTemplate.getForObject("http://product/test"+"?name="+name,String.class   );
    }

    // 数据传输有问题
    @PostMapping("post")
    public String post(@RequestBody Map<String,String> product){
        log.info("传入参数为:{}",product);
        return restTemplate.getForObject("http://product/post",String.class, product);
    }
}
