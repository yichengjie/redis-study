package com.yicj.list.service.impl;

import com.alibaba.fastjson.JSON;
import com.yicj.list.constants.RedisConstant;
import com.yicj.list.dto.OrderDTO;
import com.yicj.list.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-15 10:44
 **/
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public List<String> publish(OrderDTO orderDTO) {
        String jsonContent = JSON.toJSONString(orderDTO);
        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
        //leftPush和rightPop对应，左边入队，右边出队
        listOperations.leftPush(RedisConstant.MQ_ORDER_LIST, jsonContent);
        //因为出队是阻塞读取的，所以上一步入队后，数据立刻就被驱走了，下一步size=0
        Long size = listOperations.size(RedisConstant.MQ_ORDER_LIST);
        List<String> list = new ArrayList<>();
        if (size != null && size > 0) {
            list = listOperations.range(RedisConstant.MQ_ORDER_LIST, 0, size - 1);
        }
        return list;
    }


    @Override
    public void consumeMqList() {
        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
        //0时间，表示阻塞永久
        //待机一小时后，再次发消息，消费不了了，阻塞有问题啊。还得轮寻啊
        //String s = listOperations.rightPop(RedisConstant.MQ_LIST, 0L, TimeUnit.SECONDS);
        String s = listOperations.rightPop(RedisConstant.MQ_ORDER_LIST);
        if (s == null) {
            return;
        }
        log.info("{} = {}", RedisConstant.MQ_ORDER_LIST, s);
        OrderDTO dto = JSON.parseObject(s, OrderDTO.class);
        log.info("dto = {}", dto);
    }
}
