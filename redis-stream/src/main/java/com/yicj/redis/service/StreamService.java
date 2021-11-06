package com.yicj.redis.service;

import com.yicj.redis.model.vo.StreamInfoVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.util.List;
import java.util.Map;

public interface StreamService {

    String add(String streamName, Map<String,Object> value) ;

    List<StreamInfoVo> list(String streamName) ;

    StringRedisTemplate getStringRedisTemplate() ;
}
