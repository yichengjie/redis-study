package com.yicj.web;

import com.yicj.web.adapter.MsgServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-16 09:49
 **/
@SpringBootApplication
public class HelloApplication implements ApplicationRunner {
    @Autowired
    private MsgServiceAdapter msgServiceAdapter ;

    public static void main(String[] args) {

        SpringApplication.run(HelloApplication.class) ;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        msgServiceAdapter.sendMsg("yicj");
    }
}
