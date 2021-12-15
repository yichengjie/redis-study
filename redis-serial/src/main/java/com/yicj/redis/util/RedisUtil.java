package com.yicj.redis.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
public class RedisUtil implements InitializingBean {

    @Autowired
    private RedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOperations;


    /**  默认过期时长，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;

    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;

    public Long getExpire(String key){
        return redisTemplate.getExpire(key);
    }

    public void set(String key, Object value, long expire){
        valueOperations.set(key, toJson(value));
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value){
        set(key, value, DEFAULT_EXPIRE);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object, SerializerFeature... features){
        if(object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String){
            return String.valueOf(object);
        }
        return JSON.toJSONString(object, features);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }

    public <T> T getNoE(String key, Class<T> clazz) {
        try {
            return get(key, clazz, NOT_EXPIRE);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public void setNoE(String key, Object value, long expire){
        Executors.newCachedThreadPool().submit(() -> {
            try {
                valueOperations.set(key, toJson(value, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat));
                if(expire != NOT_EXPIRE){
                    redisTemplate.expire(key, expire, TimeUnit.SECONDS);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.valueOperations = redisTemplate.opsForValue() ;
    }
}
