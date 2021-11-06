package com.yicj.redis.controller;

import com.yicj.redis.service.PubSubBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "rest")
public class HelloController {

    @Autowired
    private PubSubBean pubSubBean;

    // 发布消息
    @GetMapping(path = "pub")
    public String pubTest(String key, String value) {
        pubSubBean.publish(key, value);
        return "over";
    }

    // 新增消费者
    @GetMapping(path = "sub")
    public String subscribe(String key, String uuid) {
        pubSubBean.subscribe((message, bytes) -> System.out.println(uuid + " ==> msg:" + message), key);
        return "over";
    }
}
