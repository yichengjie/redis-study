package com.yicj.queue.producer;

import com.yicj.queue.QueueApplication;
import com.yicj.queue.common.DelayJob;
import com.yicj.queue.config.DelayQConfig;
import com.yicj.queue.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.Duration;
import java.util.stream.IntStream;

/**
 * @Author: yicj1
 * @ClassName: DelayQProducerTest
 * @Date: 2021/10/9 17:31
 * @Description: //TODO
 * @Version: V1.0
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QueueApplication.class)
public class DelayQProducerTest {

    @Autowired
    private DelayQProducer delayQProducer ;

    @Test
    public void submit(){
        IntStream.range(1,100).forEach(item ->{
            int delay = RandomUtils.nextInt(1, 50);
            log.info("delay time : {} - {}", item, delay);
            delayQProducer.submit(DelayQConfig.DELAY_QUEUE_INTERACT_EVENT_AUTO_CLOSE, new DelayJob<>(new UserInfo("test" + item, "bjs")), Duration.ofSeconds(delay));
        }); ;
    }

}
