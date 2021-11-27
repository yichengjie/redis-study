package com.yicj.redis.util;

import com.yicj.redis.SerialApplication;
import com.yicj.redis.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SerialApplication.class)
public class RedisTemplateTest {

    private String TOKEN_FORMAT = "token_%s" ;
    private Integer TOKEN_EXPIRE = 60 * 60 * 24;

    @Autowired
    private RedisUtil redisUtil ;

    @Test
    public void set(){
        redisUtil.set("username","test");
    }

    @Test
    public void get(){
        String username = redisUtil.get("username");
        log.info("username : {}", username);
    }

    @Test
    public void setUserDto(){
        UserDto userDto = new UserDto() ;
        userDto.setUsername("zhang-san");
        userDto.setAge(18);
        userDto.setAddress("BJS");
        String token = this.getUUId() ;
        String key = String.format(TOKEN_FORMAT, token) ;
        redisUtil.set(key, userDto, TOKEN_EXPIRE);
    }

    @Test
    public void getUserDto(){
        String token = this.getUUId() ;
        String key = String.format(TOKEN_FORMAT, token) ;
        UserDto userDto = redisUtil.get(key, UserDto.class, TOKEN_EXPIRE);
        log.info("user dto : {}", userDto);
    }

    private String getUUId(){
        return "hello10011" ;
    }

}
