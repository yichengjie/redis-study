package com.yicj.study.service;

import javassist.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-31 10:04
 **/
@Slf4j
public class CreatePersonService {

    //https://www.cnblogs.com/rickiyang/p/11336268.html
    public static void createPerson () throws Exception{
        ClassPool pool = ClassPool.getDefault() ;

        //1. 创建要给空类
        CtClass cc = pool.makeClass("com.yicj.study.javassist.PersonService");
        //2. 新增一个字段 private String name ;
        // 字段名称为name
        CtField param = new CtField(pool.get("java.lang.String"), "name",cc) ;
        // 访问级别是private
        param.setModifiers(Modifier.PRIVATE);
        // 初始值是"xiaoming"
        cc.addField(param,CtField.Initializer.constant("xiaoming"));
        //3. 生成getter、setter方法
        cc.addMethod(CtNewMethod.setter("setName",param));
        cc.addMethod(CtNewMethod.getter("getName", param));
        //4. 添加无参构造函数
        CtConstructor cons = new CtConstructor(new CtClass[]{},cc) ;
        cons.setBody("{name = \"xiaohong\" ;}");
        cc.addConstructor(cons);
        //5. 添加有参构造函数
        cons = new CtConstructor(new CtClass[]{pool.get("java.lang.String")}, cc) ;
        cons.setBody("$0.name=$1;");
        cc.addConstructor(cons);
        // 创建一个名为printName的方法,无参数，无返回值，输出name
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "printName", new CtClass[]{}, cc) ;
        ctMethod.setModifiers(Modifier.PUBLIC);
        ctMethod.setBody("{System.out.println(name);}");
        cc.addMethod(ctMethod);
        // 这里会将这个创建的类对象编译为.class文件
        cc.writeFile("D:\\code\\study\\redis-study\\hello-javassist\\target\\classes");
    }


    private static void addInterface() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        // 设置类路径
        pool.appendClassPath("D:\\code\\study\\redis-study\\hello-javassist\\target\\classes");
        // 获取接口
        CtClass cci = pool.get("com.yicj.study.service.IPersonService");
        // 获取上面生成的类
        CtClass cc = pool.get("com.yicj.study.javassist.PersonService");
        cc.setInterfaces(new CtClass[]{cci});
        // 以下是通过接口调用强转
        IPersonService service = (IPersonService)cc.toClass().newInstance() ;
        System.out.println(service.getName());
        service.setName("张三");
        service.printName();
    }

    private static void print() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        // 设置类路径
        pool.appendClassPath("D:\\code\\study\\redis-study\\hello-javassist\\target\\classes");
        CtClass cc = pool.get("com.yicj.study.javassist.PersonService");
        Class<?> clazz = cc.toClass();
        Method method = clazz.getDeclaredMethod("printName");
        Object result = method.invoke(clazz.newInstance());
        log.info("result : {}", result);
    }

    public static void main(String[] args) throws Exception {
        //createPerson();
        //print();
        addInterface() ;
    }
}
