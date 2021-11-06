package com.yicj.queue.util;

import com.alibaba.fastjson.JSON;
import com.yicj.queue.common.DelayJob;
import com.yicj.queue.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Author: yicj1
 * @ClassName: DQUtilTest
 * @Date: 2021/10/9 16:16
 * @Description: //TODO
 * @Version: V1.0
 **/
@Slf4j
public class DQUtilTest {

    @Test
    public void buildKey(){
        String key = DQUtil.buildKey("com", "test");
        log.info("key : {}", key);
    }

    @Test
    public void test1(){
        //{"className":"com.yicj.queue.model.UserInfo","context":{},"entity":{"addr":"bjs","name":"test"}}
        DelayJob<?> job = new DelayJob<>(new UserInfo("test", "bjs")) ;
        String content = JSON.toJSONString(job);
        log.info("content : {}", content);


    }
}
