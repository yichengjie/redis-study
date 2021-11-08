package com.yicj.redis.service.impl;

import com.yicj.redis.service.PromoPosterService;
import com.yicj.redis.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class PromoPosterServiceImpl implements PromoPosterService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate ;

    @Override
    public String gen(String promoId, String userCode) {
        String key = String.format("promotion:poster:%s:%s", promoId, userCode) ;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object id = stringRedisTemplate.opsForHash().get(key, "id");
        if (id == null){
            return this.doGen(promoId, userCode) ;
        }
        log.info("promoId: {}, userCode: {} 已存在，不需要重复生成", promoId, userCode);
        return (String) id ;
    }


    public String doGen(String promoId, String userCode) {
        log.info("生成海报开始执行....");
        String key = String.format("promotion:poster:%s:%s", promoId, userCode) ;
        Map<String,String> map = new HashMap<>() ;
        String uuid = CommonUtil.uuid();
        map.put("id", uuid) ;
        map.put("promoId", promoId) ;
        map.put("userCode", userCode) ;
        stringRedisTemplate.opsForHash().putAll(key, map);
        log.info("生成海报执行完成....");
        return uuid;
    }

}
