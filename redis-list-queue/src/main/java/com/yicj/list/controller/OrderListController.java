package com.yicj.list.controller;

import com.yicj.list.dto.OrderDTO;
import com.yicj.list.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderListController {

    @Autowired
    private OrderService redisService;

    @PostMapping("/orderList/publish")
    public Object publish(@RequestBody OrderDTO dto) {
        return redisService.publish(dto);
    }
}