package com.yicj.redis.utils;

import cn.hutool.core.lang.UUID;

public class CommonUtil {

    public static String uuid(){
        return UUID.randomUUID().toString(true) ;
    }
}
