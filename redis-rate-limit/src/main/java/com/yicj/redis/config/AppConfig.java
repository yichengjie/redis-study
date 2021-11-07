package com.yicj.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class AppConfig {

    @Bean
    public DefaultRedisScript<Long> limitRedisScript() {
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Long.class);
        ClassPathResource classPathResource = new ClassPathResource("redis/limit.lua");
        defaultRedisScript.setScriptSource(new ResourceScriptSource(classPathResource));
        return defaultRedisScript;
    }

}
