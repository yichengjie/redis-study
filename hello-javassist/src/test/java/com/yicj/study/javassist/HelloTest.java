package com.yicj.study.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import org.junit.Test;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-29 14:46
 **/
public class HelloTest {

    // 读取和输出字节码
    @Test
    public void test1() throws Exception {
        ClassPool pool = ClassPool.getDefault() ;
        // 会从classpath中查询该类
        String name = "com.yicj.study.controller.HelloController";
        CtClass cc = pool.get(name);
        // 设置HelloController的父类
        cc.setSuperclass(pool.get("com.yicj.study.controller.BaseController"));
        // HelloController到该目录中
        cc.writeFile("D:\\");
        //输出成二进制格式
        //byte[] b=cc.toBytecode();
        //输出并加载class 类，默认加载到当前线程的ClassLoader中，也可以选择输出的ClassLoader。
        //Class clazz=cc.toClass();
    }
}
