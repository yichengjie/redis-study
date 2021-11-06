package com.yicj.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PubSubBean {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void publish(String key, String value) {
        redisTemplate.execute((RedisCallback<Object>) redisConnection -> {
            redisConnection.publish(key.getBytes(), value.getBytes());
            return null;
        });
    }

    public void subscribe(MessageListener messageListener, String key) {
        redisTemplate.execute((RedisCallback<Object>) redisConnection -> {
            redisConnection.subscribe(messageListener, key.getBytes());
            return null;
        });
    }
}