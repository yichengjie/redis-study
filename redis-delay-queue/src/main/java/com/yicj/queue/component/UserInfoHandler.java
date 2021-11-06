package com.yicj.queue.component;

import com.google.common.eventbus.Subscribe;
import com.yicj.queue.anno.DelayJobProcessor;
import com.yicj.queue.model.UserInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: yicj1
 * @ClassName: EventHandler
 * @Date: 2021/10/9 15:15
 * @Description: //TODO
 * @Version: V1.0
 **/
@Slf4j
@DelayJobProcessor
public class UserInfoHandler {

    @Subscribe
    public void subscribe(UserInfo event){
        // todo 到期执行逻辑
        log.info("执行添加业务 user info : {}", event);
    }
}
