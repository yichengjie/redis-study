package com.yicj.redis.utils;

import com.yicj.redis.StreamApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StreamApplication.class)
public class RedisLockUtilTest {

    @Autowired
    private RedisLockUtil redisLockUtil ;

    @Test
    public void lock() throws InterruptedException {
        String key = "username" ;
        String reqId = CommonUtil.uuid() ;
        boolean lock = redisLockUtil.lock(key, reqId, 100);
        Thread.sleep(300);
        boolean unlockFlag = redisLockUtil.unlock(key, reqId);
        log.info("unlock flag : {}", unlockFlag);
    }

}
