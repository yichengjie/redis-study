package com.yicj.redis.controller;


import com.yicj.redis.anno.LimitAnnotation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @LimitAnnotation("/index")
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @LimitAnnotation(value = "/hello-get",seconds = 1, num = 1)
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
