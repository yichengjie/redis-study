package com.yicj.redis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-15 17:46
 **/
@RestController
@RequestMapping("/capi/health")
public class HeartBeatController {

    @GetMapping("/heartbeat")
    public String heartbeat(){
        return "success" ;
    }

}
