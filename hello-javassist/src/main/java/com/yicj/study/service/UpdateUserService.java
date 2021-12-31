package com.yicj.study.service;

import com.yicj.study.service.impl.UserService;
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

    //private UserService orgUserService = new UserService();

    //https://www.cnblogs.com/chiangchou/p/javassist.html
    //https://www.jianshu.com/p/77fa83851d8f
    public CtClass update() throws Exception{
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
        return cc ;
    }

    public void updateMain(CtClass cc) throws Exception{
        //
        Object user = cc.toClass().newInstance();
        // 调用userFly方法
        Method userFlyMethod = user.getClass().getMethod("userFly");
        userFlyMethod.invoke(user) ;
        // 调用joinFriend方法
        Method execute = user.getClass().getMethod("joinFriend");
        execute.invoke(user) ;
    }



    public  void updateMain2(CtClass cc) throws Exception{
        Class<?> clazz = cc.toClass();
        UserService service = new UserService() ;
        service.userFly();
        //
        /*System.out.println("================================");
        IUserService userService = (IUserService)clazz.newInstance();
        userService.userFly();*/
        System.out.println("==================================");
        //orgUserService.userFly();
    }

    public static void main(String[] args) throws Exception {
        UpdateUserService updater = new UpdateUserService();
        CtClass cc = updater.update();
        //updateMain(cc);
        updater.updateMain2(cc);
    }

}
