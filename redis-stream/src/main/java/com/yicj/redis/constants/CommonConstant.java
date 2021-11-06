package com.yicj.redis.constants;

public interface CommonConstant {

    // 推广海报生成stream的key
    /**
     * XADD promoPosterTaskStream * msg init
     * XGROUP CREATE promoPosterTaskStream promoPosterTaskGroup 0
     */
    String PROMO_POSTER_TASK_STREAM_KEY = "promoPosterTaskStream" ;

    /**
     * XADD promoPosterTaskStream * msg init
     * XGROUP CREATE promoPosterTaskStream promoPosterTaskGroup 0
     */
    String PROMO_POSTER_TASK_GROUP_KEY = "promoPosterTaskGroup" ;

}
