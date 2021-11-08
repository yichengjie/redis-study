package com.yicj.redis.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum RedisKey {

    PROMOTION_GEN_EXECUTING("promotion:gen:executing:%s:%s",300, "生成推广信息执行中") ;

    private String key ;
    private Integer timeout ;
    private String desc ;

    RedisKey(String key, Integer timeout, String desc){
        this.key = key ;
        this.timeout = timeout ;
        this.desc = desc ;
    }
}
