package com.yicj.study.javassist;

import com.yicj.study.service.BizService;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.junit.Test;

import java.util.HashMap;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-30 09:54
 **/
public class BizServiceTest {

    @Test
    public void main() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass clazz = pool.getOrNull("com.yicj.study.service.BizService");
        CtMethod bizProcessMethod = clazz.getDeclaredMethod("bizProcess");
        StringBuffer sb = new StringBuffer() ;
        sb.append("{") ;
        sb.append("com.yicj.study.interceptor.BizServiceInterceptor.preProcess($1) ;") ;
        sb.append("}") ;
        bizProcessMethod.insertBefore(sb.toString());
        // --
        BizService bizService = new BizService() ;
        bizService.bizProcess(new HashMap());
    }
}
