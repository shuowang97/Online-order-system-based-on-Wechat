package com.imooc.service.impl;

import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.BuyerService;
import com.imooc.service.OrderService;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.channels.SelectableChannel;


@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderid) {

        OrderDTO orderDTO = checkOrderOwner(openid, orderid);
        return orderDTO;
    }

    @Override
    public OrderDTO cancel(String openid, String orderid) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderid);
        if(orderDTO == null){
            log.error("【取消订单】查不到该订单,orderid={}", orderid);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        OrderDTO result = orderService.cancel(orderDTO);
        return result;
    }

    //此函数为了判断订单的openid与查询订单的用户是否为同一openid
    private OrderDTO checkOrderOwner(String openid, String orderid){

        OrderDTO orderDTO = orderService.findOne(orderid);
        if(orderDTO == null){
            return null;
        }

        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("【查询订单】订单openid不一致, orderid={}, orderDTO={}", openid, orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }

        return orderDTO;
    }
}
