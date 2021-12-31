package com.yicj.study.service.impl;

import com.yicj.study.service.IUserService;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-31 10:40
 **/
public class UserService implements IUserService {

    @Override
    public void getUser(){
        System.out.println("get User");
    }

    @Override
    public void userFly(){
        System.out.println("oh my god, I can fly");
    }
}
