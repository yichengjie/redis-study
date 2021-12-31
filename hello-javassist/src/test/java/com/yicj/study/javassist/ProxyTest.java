package com.yicj.study.javassist;

import javassist.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;


/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-30 16:40
 **/
@Slf4j
public class ProxyTest {

    @Test
    public void test1() throws Exception{
        ClassPool classPool = ClassPool.getDefault();
        classPool.appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
        CtClass clazz = classPool.getCtClass("cn.com.Test");
        String method = "hello" ;

        CtMethod ctMethod = clazz.getDeclaredMethod(method);
        String newName = method + "New";
        ctMethod.setName(newName);
        CtMethod newCtMethod = CtNewMethod.copy(ctMethod, method, clazz, null);
        String type = ctMethod.getReturnType().getName();
        StringBuilder body = new StringBuilder();
        body.append("{\n System.out.println(\"Before Method Execute...\");\n");
        if(!"void".equals(type)) {
            body.append(type).append(" result = ");
        }
        body.append(newName).append("($$);\n");
        body.append("System.out.println(\"After Method Execute...\");;\n");
        if(!"void".equals(type)) {
            body.append("return result;\n");
        }
        body.append("}");
        newCtMethod.setBody(body.toString());
        clazz.addMethod(newCtMethod);
    }
}
