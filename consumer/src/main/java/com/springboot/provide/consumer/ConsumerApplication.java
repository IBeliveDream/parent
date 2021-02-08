package com.springboot.provide.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
// 开启feignClient，否则无法注入
@EnableFeignClients
@SpringBootApplication
//@ComponentScan("com.springboot.provide.consumer.controller")
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}
