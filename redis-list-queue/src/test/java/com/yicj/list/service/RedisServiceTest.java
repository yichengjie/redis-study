package com.yicj.list.service;

import com.yicj.list.ListQueueApplication;
import com.yicj.list.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-15 10:47
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ListQueueApplication.class)
public class RedisServiceTest {

    @Autowired
    private OrderService redisService ;

    @Test
    public void publish(){
        OrderDTO dto = new OrderDTO();
        dto.setId(1);
        dto.setCreateTime(new Date());
        dto.setMoney("12.34");
        dto.setOrderNo("orderNo1");
        List<String> list = redisService.publish(dto);
        list.forEach(item -> log.info("====> item : {}", item));
    }
}
