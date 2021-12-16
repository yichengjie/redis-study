package com.yicj.pub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-16 10:48
 **/
@RestController
public class HelloController {

    @Autowired
    private StringRedisTemplate template ;

    @GetMapping("/hello")
    public String hello(){
        // 往messagepush和messagepush3两个主题发送消息
        template.convertAndSend("messagePush", "Hello message !");
        template.convertAndSend("messagePush3", "Hello message3 !");
        return "hello" ;
    }
}
