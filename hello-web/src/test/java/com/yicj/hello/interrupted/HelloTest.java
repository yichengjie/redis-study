package com.yicj.hello.interrupted;

import org.junit.Test;

public class HelloTest {

    @Test
    public void test1(){
        Thread td = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Thread.sleep(100000L);
                } catch (InterruptedException e) {
                    System.out.println("线程是否处于中断状态" + Thread.currentThread().isInterrupted());
                    e.printStackTrace();
                    System.out.println("abc");
                }
                System.out.println("def");
            }
        });
        td.start();
        td.interrupt();
    }

    @Test
    public void test2(){
        Thread td = new Thread(new Runnable(){
            @Override
            public void run() {
                System.out.println("线程是否处于中断状态" + Thread.currentThread().isInterrupted());
            }
        });
        td.start();
        td.interrupt();
    }


}
