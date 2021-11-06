package com.yicj.queue.controller;

import com.yicj.queue.common.DelayJob;
import com.yicj.queue.config.DelayQConfig;
import com.yicj.queue.model.UserInfo;
import com.yicj.queue.producer.DelayQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Duration;

/**
 * @Author: yicj1
 * @ClassName: HelloController
 * @Date: 2021/10/9 15:17
 * @Description: //TODO
 * @Version: V1.0
 **/
@RestController
public class HelloController {
    // 延迟20分钟
    private static final Long CLOSE_POINT_TIME_OUT = 20L;

    @Autowired
    private DelayQProducer delayQProducer ;

    @GetMapping("/hello")
    public String hello(String name){
        UserInfo event = new UserInfo(name, "bjs") ;
        delayQProducer.submit(DelayQConfig.DELAY_QUEUE_INTERACT_EVENT_AUTO_CLOSE,
                new DelayJob<>(event), Duration.ofSeconds(CLOSE_POINT_TIME_OUT));
        return "hello world" ;
    }
}
