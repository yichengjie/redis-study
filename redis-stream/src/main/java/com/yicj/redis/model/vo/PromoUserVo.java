package com.yicj.redis.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PromoUserVo implements Serializable {
    private String msgId ;
    private String promoId ;
    private String userCode ;
}
