package com.yicj.redis.utils;

import java.util.UUID;

public class CommonUtil {


    public static String uuid(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-","") ;
    }
}
