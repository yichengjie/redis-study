package com.yicj.redis.utils;

import org.junit.jupiter.api.Test;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

public class CommonUtilTest {

    @Test
    public void ser(){
        JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer() ;
        byte[] myGroups = serializer.serialize("myGroup");
        String str = new String(myGroups);
        System.out.println(str);
    }
}
