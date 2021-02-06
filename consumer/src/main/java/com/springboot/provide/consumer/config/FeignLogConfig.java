package com.springboot.provide.consumer.config;

import feign.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * com.springboot.provide.consumer.config
 * feign打印日志类型
 * @author Jin
 * @Date 17:51 2021/2/6
 */
@Slf4j
@Configuration
public class FeignLogConfig {

    /**
     *  feign 的日志记录
     * @return
     */
    @Bean
    Logger.Level feignLogger(){
        log.info("feign输出full级别日志:[ 记录请求和响应的头信息，正文和元数据]");
        return Logger.Level.FULL;
    }
}
