package com.yicj.redis.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisLockUtil {

    private final DefaultRedisScript<Long> unlockScript ;
    private final StringRedisTemplate stringRedisTemplate;

    public RedisLockUtil(DefaultRedisScript<Long> unlockScript, StringRedisTemplate stringRedisTemplate) {
        this.unlockScript = unlockScript;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    // 加锁
    public boolean lock(String key, String reqId, long timeout) {
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, reqId, timeout, TimeUnit.SECONDS);
        if (result == null){
            return false ;
        }
        return result ;
    }

    // 解锁
    public boolean unlock(String key, String reqId) {
        Long result = stringRedisTemplate.execute(unlockScript, Arrays.asList(key), reqId) ;
        return result != null && result == 1;
    }
}