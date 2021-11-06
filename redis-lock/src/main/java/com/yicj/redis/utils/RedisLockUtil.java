package com.yicj.redis.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisLockUtil {
    private static final String UNLOCK_SCRIPT = "if redis.call('get',KEYS[1]) == ARGV[1] then " +
            "    return redis.call('del',KEYS[1]) " +
            " else " +
            "    return 0 " +
            " end ";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 枷锁
    public boolean lock(String key, String value, long timeout) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
    }

    // 解锁
    public boolean unlock(String key, String value) {
        Long result = redisTemplate.execute((RedisCallback<Long>) connection ->{
            byte[] script = UNLOCK_SCRIPT.getBytes(StandardCharsets.UTF_8);
            ReturnType returnType = ReturnType.fromJavaType(Long.class) ;
            int numKeys = 1 ;
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] valueBytes = value.getBytes(StandardCharsets.UTF_8);
            return connection.eval(script, returnType, numKeys, keyBytes, valueBytes);
        }) ;
        return result != null && result == 1;
    }
}