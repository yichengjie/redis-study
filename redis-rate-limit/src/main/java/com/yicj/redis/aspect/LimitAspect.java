package com.yicj.redis.aspect;


import com.yicj.redis.anno.LimitAnnotation;
import com.yicj.redis.enums.RedisKey;
import com.yicj.redis.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
public class LimitAspect {

    @Autowired
    private StringRedisTemplate redisTemplate ;
    @Autowired
    private DefaultRedisScript<Long> limitRedisScript ;

    @Pointcut("@annotation(com.yicj.redis.anno.LimitAnnotation)")
    public void pointcut() {}

    @Around("pointcut() && @annotation(anno)")
    public Object around(ProceedingJoinPoint pj, LimitAnnotation anno) throws Throwable {
        String apiAddress = anno.value();
        int seconds = anno.seconds();
        int num = anno.num();
        if (StringUtils.isNotBlank(apiAddress)){
            HttpServletRequest request = CommonUtils.getRequest();
            String ipAddress = CommonUtils.getIpAddress(request);
            log.info("api : {}, ip : {}", apiAddress, ipAddress);
            String cacheKey = String.format(RedisKey.CURRENT_LIMITING_KEY.getKey(), apiAddress, ipAddress) ;
            Long retValue = redisTemplate.execute(limitRedisScript, Arrays.asList(cacheKey), String.valueOf(seconds), String.valueOf(num));
            if (retValue != null && retValue == 1){
                return pj.proceed() ;
            }
            throw new RuntimeException("您调用过于频繁，已被限流！！！") ;
        }
        return pj.proceed() ;
    }


}
