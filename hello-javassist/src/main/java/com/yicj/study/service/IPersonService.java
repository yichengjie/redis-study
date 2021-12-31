package com.yicj.study.service;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-31 10:33
 **/
public interface IPersonService {
    void setName(String name) ;
    String getName() ;
    void printName() ;
}
