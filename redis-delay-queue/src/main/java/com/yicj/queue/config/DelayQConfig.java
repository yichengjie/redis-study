package com.yicj.queue.config;

import com.yicj.queue.consumer.DelayQConsumer;
import com.yicj.queue.consumer.impl.DelayQConsumerImpl;
import com.yicj.queue.producer.DelayQProducer;
import com.yicj.queue.producer.impl.BoundedDelayQProducer;
import com.yicj.queue.producer.impl.DelayQProducerImpl;
import com.yicj.queue.util.PooledRedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;

/**
 * @Author: yicj1
 * @ClassName: DelayQConfig
 * @Date: 2021/10/9 14:49
 * @Description: //TODO
 * @Version: V1.0
 **/
@Slf4j
@Configuration
public class DelayQConfig {
    /**
     * 交互事件超时结束任务延迟队列
     */
    public static final String DELAY_QUEUE_INTERACT_EVENT_AUTO_CLOSE = "interact_event_auto_close";
    @Resource
    private PooledRedisClient pooledRedisClient;
    @Value(value = "${business-card.delayqueue.namespace:business-card}")
    private String namespace;

    @Bean
    public DelayQProducer delayQProducer() {
        DelayQProducerImpl delayQProducer = new DelayQProducerImpl();
        delayQProducer.setNamespace(namespace);
        delayQProducer.setPooledRedisClient(pooledRedisClient);
        BoundedDelayQProducer boundedProducer = new BoundedDelayQProducer();
        boundedProducer.setDelegate(delayQProducer);
        return boundedProducer;
    }

    @Bean
    public DelayQConsumer delayQConsumer() {
        DelayQConsumerImpl consumer = new DelayQConsumerImpl();
        consumer.setInitWithQueue(DELAY_QUEUE_INTERACT_EVENT_AUTO_CLOSE);
        consumer.setNamespace(namespace);
        consumer.setPooledRedisClient(pooledRedisClient);
        return consumer;
    }
}

