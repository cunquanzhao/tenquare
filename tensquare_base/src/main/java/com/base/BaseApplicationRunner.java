package com.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * Name: BaseApplicationRunner
 * User: zhaocq
 * Date: 2019/5/5 0005
 * Time: 11:13
 * Description: SpringBoot的启动类
 */
@SpringBootApplication
@EnableEurekaClient
public class BaseApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplicationRunner.class, args);
    }


    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
