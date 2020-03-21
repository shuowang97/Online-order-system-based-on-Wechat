package com.imooc.repository;

import com.imooc.dataobject.OrderDetail;
import org.hibernate.criterion.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest(){

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1234567891");
        orderDetail.setOrderId("111112");
        orderDetail.setProductIcon("http://xxxxx.jpg");
        orderDetail.setProductId("2222221");
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductPrice(new BigDecimal(1.2));
        orderDetail.setProductQuantity(2);

        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() throws Exception {

        List<OrderDetail> orderDetailList = repository.findByOrderId("11111");
        Assert.assertNotEquals(0, orderDetailList.size());

    }

}