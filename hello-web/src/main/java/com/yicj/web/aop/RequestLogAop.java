package com.yicj.web.aop;

import com.google.common.collect.Lists;
import com.yicj.web.util.AopUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 打印log
 * @author lipo
 * @version v1.0
 * @date 2019-10-14 14:40
 */
@Component
@Aspect
@Slf4j
public class RequestLogAop {
    /**
     * 阿里云心跳一分钟一次，写日志
     */
    private static List<String> excludeUriList = Lists.newArrayList("/capi/health/heartbeat");

    /**
     * 打印log，不能抛出异常，否则会中断请求处理，必须try。。。catch处理异常
     * @author lipo
     * @date 2019-10-16 16:53
     * @param
     * @return
     */
    @Before("execution(public * com.yicj.web.controller.*.*(..))")
    public void before(JoinPoint joinPoint) {

        try {
            HttpServletRequest request = getHttpServletRequest();
            Assert.notNull(request, "request must not be null");

            String requestUri = request.getRequestURI();
            if (excludeUriList.contains(requestUri)) {
                return;
            }

            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();

            String params = AopUtil.getParams(className, methodName, joinPoint.getArgs());
            log.info("uri = {} | params : {}", requestUri, params);

        } catch (Exception e) {
            //捕获异常，即使打印日志出错，不影响接口处理请求
            log.error(e.getMessage(), e);
        }

    }


    /**
     * 获取request
     * @author lipo
     * @date 2019-10-16 16:53
     * @param
     * @return
     */
    private HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());

        if(servletRequestAttributes == null){
            return null;
        }

        return servletRequestAttributes.getRequest();
    }

}
