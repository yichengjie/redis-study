package com.yicj.redis.config;

import com.yicj.redis.constants.CommonConstant;
import com.yicj.redis.listener.PromoMessageListener;
import com.yicj.redis.utils.CommonUtil;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.time.Duration;


@Configuration
public class RedisStreamConfig {

    @Autowired
    private PromoMessageListener streamListener;

    @Bean
    public Subscription subscription(RedisConnectionFactory factory){
        var options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .build();
        var listenerContainer = StreamMessageListenerContainer.create(factory,options);
        String groupName = CommonConstant.PROMO_POSTER_TASK_GROUP_KEY ;
        String streamName = CommonConstant.PROMO_POSTER_TASK_STREAM_KEY;
        // 流配置
        StreamOffset<String> myStream = StreamOffset.create(streamName, ReadOffset.lastConsumed());
        // 组与消费者配置
        Consumer consumer = Consumer.from(groupName, "consumerA");
        // 将流与消费者整合到一起
        var subscription = listenerContainer.receiveAutoAck(consumer, myStream, streamListener);
        listenerContainer.start();
        return subscription;
    }
}