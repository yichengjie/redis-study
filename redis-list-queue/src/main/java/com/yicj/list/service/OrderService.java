package com.yicj.list.service;


import com.yicj.list.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    List<String> publish(OrderDTO orderDTO) ;

    void consumeMqList() ;
}
