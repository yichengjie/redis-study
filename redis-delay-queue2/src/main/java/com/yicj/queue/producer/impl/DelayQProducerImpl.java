package com.yicj.queue.producer.impl;

import com.alibaba.fastjson.JSON;
import com.yicj.queue.common.DelayJob;
import com.yicj.queue.producer.DelayQProducer;
import com.yicj.queue.util.DQUtil;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;
import java.time.Duration;
import java.time.Instant;

/**
 * @Author: yicj1
 * @ClassName: DelayQueueProducerImpl
 * @Date: 2021/10/9 14:40
 * @Description: //TODO
 * @Version: V1.0
 **/
@Data
public class DelayQProducerImpl implements DelayQProducer {

    private String namespace;

    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void submit(String queue, DelayJob<?> job, Duration delayed) {
        double score = Instant.now().toEpochMilli() + delayed.toMillis();
        redisTemplate.opsForZSet().add(DQUtil.buildKey(namespace, queue), JSON.toJSONString(job), score) ;
    }

    @Override
    public boolean update(String queue, DelayJob<?> job, Duration delayed) {
        String jobString = JSON.toJSONString(job);
        Double oldScore = redisTemplate.opsForZSet().score(DQUtil.buildKey(namespace, queue), jobString) ;
        if (oldScore == null) {
            return false;
        } else {
            double newScore = Instant.now().toEpochMilli() + delayed.toMillis();
            redisTemplate.opsForZSet().add(DQUtil.buildKey(namespace, queue), JSON.toJSONString(job), newScore) ;
            return true;
        }
    }

    @Override
    public void cancel(String queue, DelayJob<?> job) {
        redisTemplate.opsForZSet().remove(DQUtil.buildKey(namespace, queue), JSON.toJSONString(job)) ;
    }

    @Override
    public int getQueueSize(String queue) {
        return redisTemplate.opsForZSet().zCard(DQUtil.buildKey(namespace, queue)).intValue() ;
    }
}
