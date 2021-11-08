package com.yicj.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAspectJAutoProxy
@SpringBootApplication
public class CurrentLimitApp {

    public static void main(String[] args) {

        SpringApplication.run(CurrentLimitApp.class, args) ;
    }
}
