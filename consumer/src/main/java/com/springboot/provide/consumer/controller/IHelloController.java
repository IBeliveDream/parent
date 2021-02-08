package com.springboot.provide.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.springboot.provide.consumer.controller
 *
 * @author Jin
 * @Date 16:41 2021/2/6
 */
@RestController
public class IHelloController {

    @Autowired
    IHelloControllerFeign iHelloControllerFeign;
//
//    @Autowired
//    RestTemplate restTemplate;

    @GetMapping("/hello")
    public String sayHello(@RequestParam("name") String name){
        return iHelloControllerFeign.hello(name);
//        return name;
    }
}
