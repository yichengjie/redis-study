package com.yicj.redis.timer;

import com.yicj.redis.RedisPubSubApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: yicj1
 * @ClassName: TaskTest
 * @Date: 2021/10/9 10:54
 * @Description: //TODO 任务测试
 * @Version: V1.0
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisPubSubApp.class)
public class TaskTest {

    public static final String _TOPIC = "__keyevent@0__:expired"; // 订阅频道名称

    @Autowired
    private RedisTemplate<String,Object> redisTemplate ;


    private void doTask() {

    }

}
