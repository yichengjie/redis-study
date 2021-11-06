package com.yicj.queue.anno;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: yicj1
 * @ClassName: DelayJobProcessor
 * @Date: 2021/10/9 14:49
 * @Description: //TODO
 * @Version: V1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface DelayJobProcessor {

}
