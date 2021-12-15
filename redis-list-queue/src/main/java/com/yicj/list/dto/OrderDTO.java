package com.yicj.list.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: redis-study
 * @description:
 * @author: yicj1
 * @create: 2021-12-15 10:38
 **/
@Data
public class OrderDTO implements Serializable {
    private Integer id ;
    private String name ;
    private String money ;
    private String orderNo ;
    private Date createTime ;
}
