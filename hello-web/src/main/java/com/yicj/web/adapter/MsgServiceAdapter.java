package com.yicj.web.adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-30 11:38
 **/
@Slf4j
@Component
public class MsgServiceAdapter {

    public void sendMsg(String msg) {
        log.info("===> MsgServiceAdapter.sendMsg : {}", msg);
        String url = "https://www.baidu.com/user/detail" ;

        log.info("===> MsgServiceAdapter.sendMsg url : {}", url);
    }
}
