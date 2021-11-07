package com.yicj.redis.enums;

import lombok.Getter;

@Getter
public enum RedisKey {
    CURRENT_LIMITING_KEY("current_limiting:user:%s:%s", 1, "redis限流key") ;

    private String key ;
    private Integer expire ;
    private String desc ;

    RedisKey(String key, Integer expire, String desc){
        this.key = key ;
        this.expire = expire ;
        this.desc = desc ;
    }
}
