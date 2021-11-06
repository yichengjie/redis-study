package com.yicj.queue.consumer;

/**
 * @Author: yicj1
 * @ClassName: DelayQConsumer
 * @Date: 2021/10/9 14:36
 * @Description: //TODO
 * @Version: V1.0
 **/
public interface DelayQConsumer {
    void subscribe(String queue);
}

