package com.yicj.study.interceptor;

import java.util.Map;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-30 09:53
 **/
public class BizServiceInterceptor {

    public static void preProcess(Map map){
        System.out.println("preProcess ....");
        // do log
    }
}
