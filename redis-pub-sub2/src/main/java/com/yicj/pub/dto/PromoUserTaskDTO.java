package com.yicj.pub.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PromoUserTaskDTO implements Serializable {
    private String promoId ;
    private String userCode ;
    private String width = "280" ;

    public PromoUserTaskDTO(){}

    public PromoUserTaskDTO(String promoId, String userCode){
        this.promoId = promoId ;
        this.userCode = userCode ;
    }

    public PromoUserTaskDTO(String promoId, String userCode, String width){
        this.promoId = promoId ;
        this.userCode = userCode ;
        this.width = width ;
    }
}