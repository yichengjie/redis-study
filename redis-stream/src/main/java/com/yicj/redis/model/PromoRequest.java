package com.yicj.redis.model;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class PromoRequest implements Serializable {

    private String promoId;
    private List<String> ownerList ;
}
