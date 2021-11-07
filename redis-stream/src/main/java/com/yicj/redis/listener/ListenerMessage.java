package com.yicj.redis.listener;

import cn.hutool.core.bean.BeanUtil;
import com.yicj.redis.constants.CommonConstant;
import com.yicj.redis.model.dto.PromoUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;
import java.util.Map;

@Slf4j
@Component
public class ListenerMessage implements StreamListener<String, MapRecord<String, String, String>> {

    @Autowired
    private StringRedisTemplate stringRedisTemplate ;


    @Override
    public void onMessage(MapRecord<String, String, String> entries) {
        log.info("接受到来自redis的消息");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String streamName = CommonConstant.PROMO_POSTER_TASK_STREAM_KEY ;
        System.out.println("message id :"+entries.getId());
        System.out.println("stream : "+entries.getStream());
        Map<String, String> map = entries.getValue();
        PromoUserDTO promoUserVo = new PromoUserDTO();
        BeanUtil.fillBeanWithMap(map, promoUserVo,true) ;
        System.out.println("promoUserVo : "+ promoUserVo);
        stringRedisTemplate.opsForStream().delete(streamName,entries.getId()) ;
    }
    
}