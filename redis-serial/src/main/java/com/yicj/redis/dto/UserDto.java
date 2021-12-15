package com.yicj.redis.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private String username ;
    private Integer age ;
    private String address ;
}
