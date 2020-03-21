package com.imooc.service.impl;

import com.imooc.dto.OrderDTO;
import com.imooc.service.OrderService;
import com.imooc.service.PushMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private OrderService orderService;

    @Test
    public void orderStatus() throws Exception {

        OrderDTO orderDTO = orderService.findOne("1564940096606961268");
        pushMessageService.orderStatus(orderDTO);

    }

    @Test
    public void newOrder() throws Exception {

        OrderDTO orderDTO = orderService.findOne("1564940154238517162");
        pushMessageService.newOrder(orderDTO);
    }

}