package com.yicj.study.javassist;

import javassist.*;
import org.junit.Test;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-29 15:29
 **/
public class CreateClassInstanceTest {

    @Test
    public void create() throws Exception {
        ClassPool pool = ClassPool.getDefault() ;
        CtClass cc = pool.makeClass("bean.User");
        // 创建属性
        CtField field01 = CtField.make("private int id ;", cc);
        CtField field02 = CtField.make("private String name ;", cc) ;
        cc.addField(field01);
        cc.addField(field02);
        // 创建方法
        CtMethod method01 = CtMethod.make("public String getName() {return name ;}", cc);
        CtMethod method02 = CtMethod.make("public void setName(String name) {this.name = name ;}", cc);
        cc.addMethod(method01);
        cc.addMethod(method02);
        // 添加有参构造函器
        //CtConstructor constructor= CtNewConstructor.make("public User(int id,String name){this.id=id; this.name=name;}",ctClass);
        //cc.addConstructor(constructor);
        CtConstructor constructor = new CtConstructor(new CtClass[]{CtClass.intType, pool.get("java.lang.String")},cc) ;
        constructor.setBody("{this.id = $1; this.name = $1;}");
        cc.addConstructor(constructor);
        // 无参构造器
        CtConstructor cons = new CtConstructor(null, cc) ;
        cons.setBody("{}");
        cc.addConstructor(cons);

        cc.writeFile("D:/workspace/TestCompiler/src");
    }
}
