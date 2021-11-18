package com.yicj.redis.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

public class CommonUtilTest {

    @Test
    public void ser(){
        JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer() ;
        byte[] myGroups = serializer.serialize("myGroup");
        String str = new String(myGroups);
        System.out.println(str);
    }


    @Test
    public void test1(){
        User u1 = new User("u1", Arrays.asList(new Address("addr1"), new Address("addr2"), new Address("addr3"))) ;
        User u2 = new User("u2", Arrays.asList(new Address("addr21"), new Address("addr22"), new Address("addr23"))) ;
        User u3 = new User("u3", Arrays.asList(new Address("addr31"), new Address("addr32"), new Address("addr33"))) ;
        List<User> users = Arrays.asList(u1, u2, u3) ;
        List<String> addrNames = users.stream().flatMap(u -> u.getAddrs().stream().map(Address::getName)).distinct().collect(Collectors.toList());
        System.out.println(addrNames);
    }

    @Data
    @AllArgsConstructor
    class User{
        private String username ;
        private List<Address> addrs ;
    }

    @Data
    @AllArgsConstructor
    class Address{
        private String name ;
    }
}
