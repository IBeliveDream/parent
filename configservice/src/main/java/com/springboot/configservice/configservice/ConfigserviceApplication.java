package com.springboot.configservice.configservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
// 开启 配置文件读取
@EnableConfigServer
@SpringBootApplication
public class ConfigserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigserviceApplication.class, args);
    }

}
