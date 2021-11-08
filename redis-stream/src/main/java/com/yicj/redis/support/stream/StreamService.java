package com.yicj.redis.support.stream;

import com.yicj.redis.support.stream.StreamInfoVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.util.List;
import java.util.Map;

public interface StreamService {

    String add(String streamName, Map<String,Object> value) ;

    List<StreamInfoVo> list(String streamName) ;

    StringRedisTemplate getStringRedisTemplate() ;
}
