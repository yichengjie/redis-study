package com.yicj.redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoUserDTO implements Serializable {
    private String promoId ;
    private String userCode ;
}
