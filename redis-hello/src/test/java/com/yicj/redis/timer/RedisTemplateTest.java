package com.yicj.redis.timer;

import com.yicj.redis.RedisApp;
import com.yicj.redis.contant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.text.html.ObjectView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApp.class)
public class RedisTemplateTest {
    @Autowired
    private RedisTemplate redisTemplate ;
    @Autowired
    private DefaultRedisScript<Long> demoRedisScript ;


    @Test
    public void genQrCode(){
        String promoId = "10011" ;
        String key = String.format(RedisConstant.PROMO_GEN_QRCODE_KEY, promoId) ;
        String userId = "user01" ;
        Boolean exist = redisTemplate.opsForSet().isMember(key, userId);
        if (!exist){
            Long add = redisTemplate.opsForSet().add(key, userId);
            log.info("add return value : {}", add);
        }else {
            log.info("已经存在了，不能再次添加。。。");
        }
    }

    @Test
    public void listAddIfAbsent(){
        String promoCode = "10000000000" ;
        String key = String.format(RedisConstant.PROMO_GEN_POSTER_KEY, promoCode) ;
        String configId1 = "config1" ;
        String configId2 = "config2" ;
        String configId3 = "config3" ;
    }

    @Test
    public void genQrCode2() throws ExecutionException, InterruptedException {
        String promoId = "10011" ;
        String key = String.format(RedisConstant.PROMO_GEN_QRCODE_KEY, promoId) ;
        String userId = "user03" ;
        List<String> keyList = new ArrayList<>() ;
        keyList.add(key) ;
        CompletableFuture<Void> cf1 = CompletableFuture.runAsync(() -> {
            Long retValue = (Long) redisTemplate.execute(demoRedisScript, keyList, userId);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("=======> return value : {}", retValue);
        });
        CompletableFuture<Void> cf2 = CompletableFuture.runAsync(() -> {
            Long retValue = (Long) redisTemplate.execute(demoRedisScript, keyList, userId);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("=======> return value : {}", retValue);
        });
        CompletableFuture<Void> cf3 = CompletableFuture.runAsync(() -> {
            Long retValue = (Long) redisTemplate.execute(demoRedisScript, keyList, userId);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("=======> return value : {}", retValue);
        });
        CompletableFuture<Void> cf4 = CompletableFuture.runAsync(() -> {
            Long retValue = (Long) redisTemplate.execute(demoRedisScript, keyList, userId);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("=======> return value : {}", retValue);
        });
        CompletableFuture<Void> cf5 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Long retValue = (Long) redisTemplate.execute(demoRedisScript, keyList, userId);
            log.info("=======> return value : {}", retValue);
        });

        CompletableFuture<Void> future = CompletableFuture.allOf(cf1, cf2, cf3, cf4, cf5).whenComplete((t,e) ->{
            log.info("=======> value : {}", t);
        }) ;
        future.get() ;
    }

}
