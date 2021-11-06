package com.yicj.queue.consumer;

/**
 * @Author: yicj1
 * @ClassName: XXTest
 * @Date: 2021/10/13 9:35
 * @Description: //TODO
 * @Version: V1.0
 **/
public class XXTest {

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(20000);
        while (true){
            test1() ;
        }
    }

    public static void test1(){
        try {
            byte [] bytes = new byte[102400] ;
        }catch (Exception e){
            e.printStackTrace();
        }
        int count = 100 ;
        for (int i = 0 ; i < 100; i ++){
            count ++ ;
        }
    }
}
