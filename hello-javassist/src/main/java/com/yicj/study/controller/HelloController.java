package com.yicj.study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-29 14:48
 **/
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(String name, String addr){

        return "hello : " + name ;
    }
}
