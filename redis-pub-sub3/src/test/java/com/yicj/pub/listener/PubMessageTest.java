package com.yicj.pub.listener;

import com.yicj.pub.PubSub3Application;
import com.yicj.pub.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-16 11:07
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PubSub3Application.class)
public class PubMessageTest {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate ;

    @Test
    public void test1(){
        String channel1 = "fullDataUpload";
        String channel2 = "analysis";
        User user1 = new User();
        user1.setPhone("18675830623");
        user1.setName("刘大");

        User user2 = new User();
        user2.setPhone("17856232365");
        user2.setName("李二");

        redisTemplate.convertAndSend(channel1, user1);

        redisTemplate.convertAndSend(channel2,user2);
    }
}
