package com.yicj.redis.utils;

import com.yicj.redis.RedisLockApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisLockApplication.class)
public class RedisLocalUtilTest {

    @Autowired
    private RedisLockUtil redisLockUtil ;

    @Test
    public void lock(){
        String key = "abc" ;
        String value = CommonUtil.uuid() ;
        boolean lock = redisLockUtil.lock(key, value, 600);
        log.info("lock flag : {}", lock);
    }


    @Test
    public void unlock(){
        String key = "abc" ;
        String value = CommonUtil.uuid() ;
        boolean unlock = redisLockUtil.unlock(key, value);
        log.info("unlock flag : {}", unlock);
    }


    @Test
    public void lockAndUnlock() throws InterruptedException {
        String key = "abc" ;
        String value = CommonUtil.uuid() ;
        boolean lock = redisLockUtil.lock(key, value, 60);
        log.info("lock flag : {}", lock);
        Thread.sleep(20);
        boolean unlock = redisLockUtil.unlock(key, value);
        log.info("unlock flag : {}", unlock);
    }

}
