package com.yicj.queue.support.lettuce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LettuceConnectionValidConfig implements InitializingBean {
    private final RedisConnectionFactory redisConnectionFactory;

    public LettuceConnectionValidConfig(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(redisConnectionFactory instanceof LettuceConnectionFactory){
            LettuceConnectionFactory c= (LettuceConnectionFactory)redisConnectionFactory;
            c.setValidateConnection(true);
        }
    }
}
