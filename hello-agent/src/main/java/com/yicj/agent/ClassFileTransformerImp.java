package com.yicj.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.springframework.util.StringUtils;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ClassFileTransformerImp implements ClassFileTransformer {

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if ("com/yicj/web/adapter/MsgServiceAdapter".equals(className)) {
            try {
                System.out.println("类名:" + className);
                ClassPool cPool = new ClassPool(true);
                //设置class文件的位置，实际运用时应替换为相对路径
                cPool.insertClassPath("D:\\code\\study\\redis-study\\hello-web\\target\\classes");
                //获取该class对象
                CtClass cClass = cPool.get("com.yicj.web.adapter.MsgServiceAdapter");
                //获取到对应的方法
                CtMethod cMethod = cClass.getDeclaredMethod("sendMsg");
                //通过insertAt可引用局部变量。
                cMethod.insertAt(19, "{url = \"http://localhost:8187/v1/toSendSmsBySingle\";}");
                //替换原有的文件，实际运用时应替换为相对路径
                cClass.writeFile("D:\\code\\study\\redis-study\\hello-web\\target\\classes");
                System.out.println("=======修改完成=========");
            } catch (Exception e) {
                //ignore error
                e.printStackTrace();
            }
        }
        return null;
    }
}