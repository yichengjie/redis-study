package com.yci.pub;

import com.alibaba.fastjson.JSON;
import com.yicj.pub.PubSub2Application;
import com.yicj.pub.dto.PromoUserTaskDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-16 18:45
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PubSub2Application.class)
public class RedisTemplateTest {

    private String taskListKey = "task:list:key" ;

    @Autowired
    private StringRedisTemplate redisTemplate ;

    @Test
    public void addTasks(){
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        List<PromoUserTaskDTO> list = this.initTaskList();
        list.stream()
                .map(JSON::toJSONString)
                .forEach(item -> listOperations.rightPush(taskListKey, item));
    }

    @Test
    public void remove(){
        //{"promoId":"promoId[8]","userCode":"userCode9","width":"280"}
        PromoUserTaskDTO dto = new PromoUserTaskDTO() ;
        dto.setPromoId("promoId[3]");
        dto.setUserCode("userCode4");
        dto.setWidth("280");
        String content = JSON.toJSONString(dto);
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.remove(taskListKey,1, content) ;
    }

    private List<PromoUserTaskDTO> initTaskList(){
        List<PromoUserTaskDTO> list = new ArrayList<>() ;
        for (int i = 0 ; i < 10 ; i ++){
            PromoUserTaskDTO dto = new PromoUserTaskDTO();
            dto.setPromoId("promoId["+i+"]");
            dto.setWidth("280");
            dto.setUserCode("userCode" + (i+1));
            list.add(dto) ;
        }
        return list ;
    }
}
