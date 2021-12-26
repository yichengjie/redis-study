package com.yicj.hello.interrupted;

import java.util.concurrent.locks.ReentrantLock;

public class Example42 extends Thread {
    public static void main(String args[]) throws Exception {
        final ReentrantLock lock1 = new ReentrantLock();
        final ReentrantLock lock2 = new ReentrantLock();
        Thread thread1 = new Thread() {
            public void run() {
                deathLock(lock1, lock2);
            }
        };
        Thread thread2 = new Thread() {
            public void run() {
                // 注意，这里在交换了一下位置
                deathLock(lock2, lock1);
            }
        };
        System.out.println("Starting thread...");
        thread1.start();
        thread2.start();
        Thread.sleep(20);
        System.out.println("Interrupting thread...");
        thread1.interrupt();
        thread2.interrupt();
        System.out.println("Stopping application...");
    }
    //Example5试着去中断处于死锁状态的两个线程，但这两个线在收到任何中断信号后抛出异常，
    // 所以interrupt()方法可以中断死锁线程
    static void deathLock(ReentrantLock lock1, ReentrantLock lock2) {
        try {
            lock1.lockInterruptibly();
            Thread.sleep(1);// 不会在这里死掉
            try{
                lock2.lockInterruptibly();
                System.out.println(Thread.currentThread());
            }catch (InterruptedException e) {
                lock2.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }finally{
            lock1.unlock();
        }

    }

}