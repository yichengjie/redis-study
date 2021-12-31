package com.yicj.study.proxy;

import org.springframework.aop.framework.ProxyFactory;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-30 10:24
 **/
public class ProxyFactoryTest {

    public void test1(){
       /* // 实例化代理类工厂
        ProxyFactory factory = new ProxyFactory();
        //设置父类，ProxyFactory将会动态生成一个类，继承该父类
        factory.setSuperclass(TestProxy.class);

        //设置过滤器，判断哪些方法调用需要被拦截
        factory.setFilter(new MethodFilter() {
            @Override
            public boolean isHandled(Method m) {
                return m.getName().startsWith("get");
            }
        });
        Class<?> clazz = factory.createClass();
        TestProxy proxy = (TestProxy) clazz.newInstance();
        ((ProxyObject)proxy).setHandler(new MethodHandler() {
            @Override
            public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
                //拦截后前置处理，改写name属性的内容
                //实际情况可根据需求修改
                System.out.println(thisMethod.getName() + "被调用");
                try {
                    Object ret = proceed.invoke(self, args);
                    System.out.println("返回值: " + ret);
                    return ret;
                } finally {
                    System.out.println(thisMethod.getName() + "调用完毕");
                }
            }
        });
        proxy.setName("Alvin");
        proxy.setValue("1000");
        proxy.getName();
        proxy.getValue();*/
    }
}
