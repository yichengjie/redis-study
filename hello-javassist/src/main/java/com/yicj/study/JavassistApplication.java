package com.yicj.study;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-29 14:51
 **/
@Slf4j
public class JavassistApplication {

    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("com.yicj.study.javassist.PersonService");
        Object obj = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("printName");
        Object result = method.invoke(obj);
        log.info("result : {}", result);
    }
}
