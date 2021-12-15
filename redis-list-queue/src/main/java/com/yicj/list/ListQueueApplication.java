package com.yicj.list;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @program: redis-study
 * @description: List 队列启动类
 * @author: yicj1
 * @create: 2021-12-15 10:32
 **/
// https://blog.csdn.net/mingwulipo/article/details/103195582
@EnableScheduling
@SpringBootApplication
public class ListQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(ListQueueApplication.class, args) ;
    }
}
