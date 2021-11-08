package com.yicj.redis.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PromoUserTaskVO implements Serializable {
    private String msgId ;
    private String promoId ;
    private String userCode ;
}
