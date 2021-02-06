package com.springboot.provide.consumer.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * com.springboot.provide.consumer.controller
 *
 * @author Jin
 * @Date 13:02 2021/2/6
 */
@FeignClient("product")
public interface IHelloControllerFeign {

    @GetMapping("/hello")
    String sayHello();
}
