package com.imooc.service.impl;

import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import com.imooc.dataobject.OrderDetail;
import com.imooc.dataobject.OrderMaster;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    private final String BUYER_OPENID = "110110";
    private final String ORDER_ID = "1563997082111595527";

    @Test
    public void create() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("慕课网新楼");
        orderDTO.setBuyerName("廖师兄");
        orderDTO.setBuyerPhone("18875143268");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        /*购物车*/
        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail orderDetail = new OrderDetail();
        //此处orderDetail必须为数据库中已存在的数据
        orderDetail.setProductId("1234558");
        orderDetail.setProductQuantity(5);
        orderDetailList.add(orderDetail);

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductId("1234557");
        orderDetail1.setProductQuantity(1);
        orderDetailList.add(orderDetail1);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】 result={}", result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() throws Exception {

        OrderDTO result = orderService.findOne(ORDER_ID);
        log.info("【查询单个订单】, result={}", result);
        Assert.assertEquals(ORDER_ID, result.getOrderId());

    }

    @Test
    public void findList() throws Exception {

        PageRequest pageRequest = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, pageRequest);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());

    }

    @Test
    public void cancle() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCLE.getCode(), result.getOrderStatus());


    }

    @Test
    public void finish() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
    }

    @Test
    public void paid() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
    }

    @Test
    public void list() throws Exception {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());

    }
}