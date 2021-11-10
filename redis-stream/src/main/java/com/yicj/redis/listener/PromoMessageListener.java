package com.yicj.redis.listener;

import cn.hutool.core.bean.BeanUtil;
import com.yicj.redis.constants.CommonConstant;
import com.yicj.redis.enums.RedisKey;
import com.yicj.redis.model.PromoUserTaskDTO;
import com.yicj.redis.service.PromoPosterService;
import com.yicj.redis.utils.CommonUtil;
import com.yicj.redis.utils.RedisLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;
import java.util.Map;

@Slf4j
@Component
public class PromoMessageListener implements StreamListener<String, MapRecord<String, String, String>> {

    @Autowired
    private StringRedisTemplate stringRedisTemplate ;
    @Autowired
    private PromoPosterService promoPosterService ;
    @Autowired
    private RedisLockUtil redisLockUtil ;


    @Override
    public void onMessage(MapRecord<String, String, String> entries) {
        log.info("接受到来自redis的消息");
        String streamName = CommonConstant.PROMO_POSTER_TASK_STREAM_KEY ;
        String messageId = entries.getId().getValue();
        Map<String, String> map = entries.getValue();
        PromoUserTaskDTO promoUserVo = new PromoUserTaskDTO();
        BeanUtil.fillBeanWithMap(map, promoUserVo,true) ;
        System.out.println("promoUserVo : "+ promoUserVo);
        String promoId = promoUserVo.getPromoId();
        String userCode = promoUserVo.getUserCode();
        String keyTemplate = RedisKey.PROMOTION_GEN_EXECUTING.getKey() ;
        String key = String.format(keyTemplate, promoId, userCode);
        try {
            this.doOnMessage(key, promoId, userCode);
        }catch (Exception e){
            // 异常抛出后，后续任务将会全部取消
            log.error("处理消息出错!!!!!", e);
        }finally {
            stringRedisTemplate.opsForStream().delete(streamName, messageId) ;
        }
    }

    private void doOnMessage(String key,String promoId, String userCode){
        String reqId = CommonUtil.uuid();
        boolean flag = redisLockUtil.lock(key, reqId, 300);
        if (!flag){
            log.warn("有任务正在生成 promoId : {}, userCode: {}", promoId, userCode);
            return;
        }
        // 执行生成海报操作...
        try {
            promoPosterService.gen(promoId, userCode) ;
        }finally {
            redisLockUtil.unlock(key, reqId) ;
        }
    }

}