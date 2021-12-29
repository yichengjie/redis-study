package com.yicj.study.javassist;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: redis-study
 * @description: 访问类实例变量
 * @author: yicj1
 * @create: 2021-12-29 15:52
 **/
@Slf4j
public class VisitClassInstance {


    // 获取类的简单信息
    @Test
    public void test01() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.yicj.study.model.Person");
        // 得到字节码
        byte[] bytes = cc.toBytecode();
        log.info("info : {}", Arrays.toString(bytes));
        log.info("类名称: {}", cc.getName());
        log.info("类简要名称: {}", cc.getSimpleName());
        log.info("父类名称: {}", cc.getSuperclass());
        log.info("接口名称: {}", cc.getInterfaces());
        log.info("方法: {}", cc.getMethods());
    }

    // 新生成一个方法
    @Test
    public void test02() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.yicj.study.model.Person");
        // 方式1
        //CtMethod cm = CtMethod.make("public String getName(){return name ;}", cc);
        // 方式2
        CtMethod cm = new CtMethod(CtClass.intType, "add", new CtClass[]{CtClass.intType, CtClass.intType}, cc) ;
        cm.setModifiers(Modifier.PUBLIC); // 访问范围
        cm.setBody("{return $1 + $2 ;}");
        // cc.removeMethod(cm); // 删除一个方法
        cc.addMethod(cm);
        // 通过反射调用方法
        Class<?> clazz = cc.toClass();
        Object obj = clazz.newInstance();
        Method m = clazz.getDeclaredMethod("add", int.class, int.class);
        Object result = m.invoke(obj, 2, 3);
        log.info("add result : {}", result);
    }

    // 修改已有的方法
    @Test
    public void test03() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.yicj.study.model.User");
        CtMethod cm = cc.getDeclaredMethod("hello", new CtClass[]{pool.get("java.lang.String")}) ;
        cm.insertBefore("System.out.println(\"调用前\") ;");
        cm.insertAt(20, "System.out.println(\"20\");");//行号
        cm.insertAfter("System.out.println(\"调用后\");");

        // 通过反射调用方法
        Class<?> clazz = cc.toClass();
        Object obj = clazz.newInstance();
        Method m = clazz.getDeclaredMethod("hello", String.class);
        Object result = m.invoke(obj, "张三");
        log.info("result : {}", result);
    }

    // 修改已有属性
    @Test
    public void test04() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.yicj.study.model.User");
        // 属性
        CtField cf = new CtField(CtClass.intType, "age", cc) ;
        cf.setModifiers(Modifier.PRIVATE);
        cc.addField(cf);
        // 增加相应的get set方法
        cc.addMethod(CtNewMethod.getter("getAge",cf));
        cc.addMethod(CtNewMethod.setter("setAge",cf));
        //访问属性
        Class<?> clazz = cc.toClass();
        Object obj = clazz.newInstance();
        Field field = clazz.getDeclaredField("age");
        log.info("field : {}", field);
        Method m = clazz.getDeclaredMethod("setAge", int.class);
        m.invoke(obj, 16) ;

        Method m2 = clazz.getDeclaredMethod("getAge", null);
        Object result = m2.invoke(obj, null);
        log.info("result : {}", result);
    }


    // 操作构造方法
    @Test
    public void test05() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.yicj.study.model.Person");
        CtConstructor[] cons = cc.getConstructors();
        for (CtConstructor con: cons){
            log.info("con : {}", con);
        }
    }


    @Test
    public void test06() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.yicj.study.model.Person");
        CtMethod hello = cc.getDeclaredMethod("hello", new CtClass[]{pool.get("java.lang.String")});
        System.out.println(hello);
        CtMethod cm = cc.getDeclaredMethod("hello");
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        System.out.println(cm);
        if (attr == null) {
            // exception
        }
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        List<String> list = new ArrayList<>();
        //paramNames即参数名
        String[] paramNames = new String[cm.getParameterTypes().length];
        for (int i = 0; i < paramNames.length; i++){
            String variableName = attr.variableName(i + pos);
            if ("this".equals(variableName)) {
                continue;
            }
            list.add(variableName);
        }
        log.info("variableName :{}", list);
    }

}
