package com.imooc.service;

import com.imooc.dto.OrderDTO;


public interface BuyerService {

    /*查询单个订单*/
    OrderDTO findOrderOne(String openid, String orderid);
    /*取消订单*/
    OrderDTO cancel(String openid, String orderid);
}
