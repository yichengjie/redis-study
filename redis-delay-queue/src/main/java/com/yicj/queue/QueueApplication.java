package com.yicj.queue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Author: yicj1
 * @ClassName: QueueApplication
 * @Date: 2021/10/9 14:51
 * @Description: //TODO
 * @Version: V1.0
 **/
// https://www.jianshu.com/p/6bddbfa52cef
@EnableAspectJAutoProxy
@SpringBootApplication
public class QueueApplication {
    public static void main(String[] args) {
        SpringApplication.run(QueueApplication.class, args) ;
    }
}
