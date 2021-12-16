package com.yicj.web.util;

import com.alibaba.fastjson.JSONObject;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.validation.BindingResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lipo
 * @version v1.0
 * @date 2019-10-14 14:40
 */
@Slf4j
public class AopUtil {

    /**
     * 私有构造器
     */
    private AopUtil() {

    }

    /**
     * 获取参数名=值, json格式
     * @param className 类名
     * @param methodName 方法名
     * @param args 参数值数组
     * @return
     * @throws Exception
     */
    public static String getParams(String className, String methodName, Object[] args) throws Exception {
        Asserts.notBlank(className, "className must not be null");
        Asserts.notBlank(methodName, "methodName must not be null");
        if (args == null || args.length == 0) {
            return null;
        }

        String[] parameterNames = getFieldsName(className, methodName);
        if (parameterNames.length == 0) {
            return null;
        }

        JSONObject jo = new JSONObject();
        for (int i = 0, len = parameterNames.length; i < len; i++) {
            Object value = args[i];

            //打印HttpServletResponse报错, getOutputStream() has already been called for this response, 不会解决, 暂时不打印就行了
            //It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
            if (args[i] instanceof HttpServletResponse || args[i] instanceof HttpServletRequest || args[i] instanceof BindingResult) {
                value = null;
            }

            jo.put(parameterNames[i], value);
        }
        return jo.toString();

    }


    private static Object deepCopy(Object obj) {
        Object cloneObj = null;
        try {
            //写入字节流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();

            //分配内存,写入原始对象,生成新对象
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);

            //返回生成的新对象
            cloneObj = ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloneObj;
    }


    /**
     * 得到方法参数的名称, 基本类型和对象的名称都能拿到
     * @param clazzName 类名
     * @param methodName 方法名
     * @return
     * @throws NotFoundException
     */
    private static String[] getFieldsName(String clazzName, String methodName) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(AopUtil.class);
        pool.insertClassPath(classPath);

        CtMethod cm;
        try {
            CtClass cc = pool.get(clazzName);
            cm = cc.getDeclaredMethod(methodName);
        } catch (NotFoundException e) {
            //继承父类BaseController的方法，解析不到，就不打印了
            return new String[0];
        }

        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
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

        return list.toArray(new String[list.size()]);
    }

}
