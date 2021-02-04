package com.springboot.provide.consumer.controller;

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
        System.out.println("传入url:"+"http://product/test"+"?name="+name);
        return restTemplate.getForObject("http://product/test"+"?name="+name,String.class   );
    }
    // 数据传输有问题
    @PostMapping("post")
    public String post(@RequestBody Map<String,String> product){
        System.out.println("传入参数为:"+product);
        return restTemplate.getForObject("http://product/post",String.class, product);
    }
}
