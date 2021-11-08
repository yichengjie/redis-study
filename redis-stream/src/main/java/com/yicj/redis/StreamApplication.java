package com.yicj.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class StreamApplication {
    /**
     * 1. 创建stream
     * XADD promoPosterTaskStream * promoId dd77ddb7a5334111babd741888b279ad userCode 10010
     * 2. 创建分组
     * XGROUP CREATE promoPosterTaskStream promoPosterTaskGroup 0
     * 3. 查看stream信息
     * XINFO STREAM promoPosterTaskStream
     * 4. 查看group信息
     * XINFO GROUPS promoPosterTaskStream
     */
    public static void main(String[] args) {
        SpringApplication.run(StreamApplication.class, args) ;
    }
}
