package com.imooc.repository;

import com.imooc.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private String OPENID = "110110";

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123456");
        orderMaster.setBuyerName("师兄");
        orderMaster.setBuyerPhone("18875143268");
        orderMaster.setBuyerAddress("慕课网");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(2.3));

        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);

    }

    @Test
    public void findByBuyerOpenid() throws Exception {
        PageRequest pageRequest = new PageRequest(0,1);
        Page<OrderMaster> result = repository.findByBuyerOpenid(OPENID, pageRequest);
        Assert.assertNotEquals(0, result.getTotalElements());
    }

}