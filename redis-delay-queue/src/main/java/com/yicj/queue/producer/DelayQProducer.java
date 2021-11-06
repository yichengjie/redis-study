package com.yicj.queue.producer;

import com.yicj.queue.common.DelayJob;

import java.time.Duration;

/**
 * @Author: yicj1
 * @ClassName: DelayQueueProducer
 * @Date: 2021/10/9 14:38
 * @Description: //TODO
 * @Version: V1.0
 **/
public interface DelayQProducer {
    /**
     * 提交任务，若存在则更新
     */
    void submit(String queue, DelayJob<?> job, Duration delayed);

    /**
     * 更新任务，不存在返回false
     */
    boolean update(String queue, DelayJob<?> job, Duration delayed);

    /**
     * 取消任务
     */
    void cancel(String queue, DelayJob<?> job);

    /**
     * 查询任务队列长度
     */
    default int getQueueSize(String queue) {
        return 0;
    }

    String getNamespace();
}
