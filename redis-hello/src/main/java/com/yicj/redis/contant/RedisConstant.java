package com.yicj.redis.contant;

public interface RedisConstant {
    // 推广码生产redis缓存
    String PROMO_GEN_QRCODE_KEY = "promo:gen:qrcode:%s" ;//promo:gen:qrCode:推广id
    // 海报合成redis缓存
    String PROMO_GEN_POSTER_KEY = "promo:gen:poster:%s" ; //promo:gen:poster:推广码
}
