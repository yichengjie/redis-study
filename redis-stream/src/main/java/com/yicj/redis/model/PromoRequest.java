package com.yicj.redis.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Data
public class PromoRequest implements Serializable {

    private String promoId;
    private List<String> ownerList ;
    private Integer width = 280;
}
