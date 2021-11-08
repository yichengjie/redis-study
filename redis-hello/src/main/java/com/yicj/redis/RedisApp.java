package com.yicj.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: yicj1
 * @ClassName: RedisApp
 * @Date: 2021/10/9 10:01
 * @Description: //TODO 启动类
 * @Version: V1.0
 **/
@EnableScheduling
@SpringBootApplication
public class RedisApp {

    public static void main(String[] args) {

        SpringApplication.run(RedisApp.class, args) ;
    }
}
