package com.yicj.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(String name, String addr){
        String formatContent = "name : %s, addr: %s" ;
        return String.format(formatContent, name, addr) ;
    }
}
