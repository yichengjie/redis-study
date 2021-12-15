package com.yicj.list.component;

import com.yicj.list.anno.TaskLock;
import com.yicj.list.constants.RedisConstant;
import com.yicj.list.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RedisConsumeTask {
    @Autowired
    private OrderService orderService;

    @TaskLock(RedisConstant.CONSUME_REDIS_LIST)
    @Scheduled(cron = "0/10 * * * * ?")
    public void consumeMqList() {
        System.out.println("========= 执行==========");
        orderService.consumeMqList();
    }
}