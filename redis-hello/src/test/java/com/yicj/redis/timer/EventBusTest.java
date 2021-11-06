package com.yicj.redis.timer;

import com.google.common.eventbus.EventBus;
import org.junit.Test;

/**
 * @Author: yicj1
 * @ClassName: EventBusTest
 * @Date: 2021/10/9 14:12
 * @Description: //TODO EventBus单元测试
 * @Version: V1.0
 **/
public class EventBusTest {

    @Test
    public void test1(){
        // 定义一个EventBus对象，因为我这里是测试，才这样写的。实际你应该定义一个单例获取其他的方式
        EventBus eventBus = new EventBus("test");
        // 注册监听者
        // eventBus.register(new OrderEventListener());
        // 发布消息
        //eventBus.post(new OrderMessage("order content"));
    }

}
