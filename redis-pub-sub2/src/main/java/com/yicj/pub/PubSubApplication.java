package com.yicj.pub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-16 10:33
 **/
@SpringBootApplication
public class PubSubApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ct = SpringApplication.run(PubSubApplication.class, args);
        StringRedisTemplate template = ct.getBean(StringRedisTemplate.class);
    }
}
