package com.yicj.pub.listener;

import org.springframework.stereotype.Component;

@Component
public class MessageReceiveHandler {

  public void messagePush(String message){
      System.out.println("----------收到消息了message："+message);
  }
}