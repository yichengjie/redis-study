package com.yicj.redis.timer;

import com.yicj.redis.RedisPubSubApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.Instant;
import java.util.Set;

/**
 * @Author: yicj1
 * @ClassName: DelayQueueTest
 * @Date: 2021/10/9 10:34
 * @Description: //TODO 阻塞队列测试
 * @Version: V1.0
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisPubSubApp.class)
public class DelayQueueTest {
    // zset key
    private static final String _KEY = "myTaskQueue";
    @Autowired
    private RedisTemplate<String,Object> redisTemplate ;

    @Test
    public void instant(){
        Instant nowInstant = Instant.now();
        log.info("===> last value : {}",  nowInstant.plusSeconds(-1).getEpochSecond());
        log.info("===> now value {}", nowInstant.getEpochSecond());
    }

    @Test
    public void delayQueue() throws InterruptedException {
        // 30s 后执行
        long delayTime = Instant.now().plusSeconds(30).getEpochSecond();
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        zSet.add(_KEY, "order_1",delayTime)  ;
        // 继续添加测试数据
        zSet.add(_KEY, "order_2", Instant.now().plusSeconds(2).getEpochSecond());
        zSet.add(_KEY, "order_3", Instant.now().plusSeconds(2).getEpochSecond());
        zSet.add(_KEY, "order_4",Instant.now().plusSeconds(7).getEpochSecond());
        zSet.add(_KEY, "order_5", Instant.now().plusSeconds(10).getEpochSecond());
        // 开启定时任务队列
        //doDelayQueue(zSet);
    }

    /**
     * 定时任务队列消费
     */
    private void doDelayQueue(ZSetOperations<String, Object> zSet) throws InterruptedException {
        while (true) {
            // 当前时间
            Instant nowInstant = Instant.now();
            long lastSecond = nowInstant.plusSeconds(-1).getEpochSecond(); // 上一秒时间
            long nowSecond = nowInstant.getEpochSecond();
            // 查询当前时间的所有任务
            Set<Object> objects = zSet.rangeByScore(_KEY, lastSecond, nowSecond);
            for (Object item : objects) {
                // 消费任务
                log.info("===> 消费：{}" , item);
            }
            // 删除已经执行的任务
            zSet.removeRangeByScore(_KEY, lastSecond, nowSecond);
        }
    }
}
