package com.yicj.redis.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisMessageListener implements MessageListener {

    @Autowired
    private StringRedisTemplate redisTemplate;

    //key 过期时调用
    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("onPMessage pattern " + pattern + " " + " " + message);
        String channel = new String(message.getChannel());
        String body = (String) redisTemplate.getValueSerializer().deserialize(message.getBody());
        log.info("channel : {}, body: {}", channel, body);
    }
}