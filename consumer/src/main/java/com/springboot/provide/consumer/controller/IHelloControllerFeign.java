package com.springboot.provide.consumer.controller;

import com.springboot.provide.consumer.config.FeignLogConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * com.springboot.provide.consumer.controller
 *
 * @author Jin
 * @Date 13:02 2021/2/6
 */
@FeignClient(name = "product", configuration = FeignLogConfig.class)
public interface IHelloControllerFeign {

    @GetMapping("/hello")
    String hello(@RequestParam("name") String name);
}
