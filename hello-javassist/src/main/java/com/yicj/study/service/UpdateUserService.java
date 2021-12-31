package com.yicj.study.service;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;

import java.lang.reflect.Method;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-31 10:41
 **/
public class UpdateUserService {

    public static void update() throws Exception{
        ClassPool pool = ClassPool.getDefault() ;
        CtClass cc = pool.get("com.yicj.study.service.impl.UserService");
        CtMethod userFly = cc.getDeclaredMethod("userFly");
        userFly.insertBefore("System.out.println(\"起飞之前准备降落\") ;");
        userFly.insertAfter("System.out.println(\"成功落地....\") ;");
        // 新增一个方法
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "joinFriend", new CtClass[]{},cc) ;
        ctMethod.setModifiers(Modifier.PUBLIC);
        ctMethod.setBody("{System.out.println(\"i want to be your friend\") ;}");
        cc.addMethod(ctMethod);
        //
        Object user = cc.toClass().newInstance();
        // 调用userFly方法
        Method userFlyMethod = user.getClass().getMethod("userFly");
        userFlyMethod.invoke(user) ;
        // 调用joinFriend方法
        Method execute = user.getClass().getMethod("joinFriend");
        execute.invoke(user) ;
    }

    public static void main(String[] args) throws Exception {
        update();
    }

}
