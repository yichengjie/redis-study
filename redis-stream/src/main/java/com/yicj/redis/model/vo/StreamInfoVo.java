package com.yicj.redis.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StreamInfoVo implements Serializable {
    private String id ;
    private Map<Object, Object> value ;
}
