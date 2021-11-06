package com.yicj.queue.producer.impl;

import com.alibaba.fastjson.JSON;
import com.yicj.queue.common.DelayJob;
import com.yicj.queue.producer.DelayQProducer;
import com.yicj.queue.util.DQUtil;
import com.yicj.queue.util.PooledRedisClient;
import lombok.Data;
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

    private PooledRedisClient pooledRedisClient;

    @Override
    public void submit(String queue, DelayJob<?> job, Duration delayed) {
        double score = Instant.now().toEpochMilli() + delayed.toMillis();
        pooledRedisClient.runWithRetry(jedis -> jedis.zadd(DQUtil.buildKey(namespace, queue), score, JSON.toJSONString(job)));
    }

    @Override
    public boolean update(String queue, DelayJob<?> job, Duration delayed) {
        String jobString = JSON.toJSONString(job);
        Double oldScore = pooledRedisClient.executeWithRetry(jedis -> jedis.zscore(DQUtil.buildKey(namespace, queue), jobString));
        if (oldScore == null) {
            return false;
        } else {
            double newScore = Instant.now().toEpochMilli() + delayed.toMillis();
            pooledRedisClient.runWithRetry(jedis -> jedis.zadd(DQUtil.buildKey(namespace, queue), newScore, JSON.toJSONString(job)));
            return true;
        }
    }

    @Override
    public void cancel(String queue, DelayJob<?> job) {
        pooledRedisClient.runWithRetry(jedis -> jedis.zrem(DQUtil.buildKey(namespace, queue), JSON.toJSONString(job)));
    }

    @Override
    public int getQueueSize(String queue) {
        return pooledRedisClient.executeWithRetry(jedis -> jedis.zcard(DQUtil.buildKey(namespace, queue))).intValue();
    }
}
