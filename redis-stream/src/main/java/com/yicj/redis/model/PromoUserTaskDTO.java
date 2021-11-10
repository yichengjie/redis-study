package com.yicj.redis.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class PromoUserTaskDTO implements Serializable {
    private String promoId ;
    private String userCode ;
    private String width;

    public PromoUserTaskDTO(){
        this.width = "280" ;
    }

    public PromoUserTaskDTO(String promoId,String userCode){
        this(promoId, userCode, "280") ;
    }

    public PromoUserTaskDTO(String promoId,String userCode, String width){
        this.promoId = promoId ;
        this.userCode = userCode ;
        if (width != null && width.matches("\\d+")) {
            this.width = width ;
        }else {
            this.width = "280" ;
        }
    }
}
