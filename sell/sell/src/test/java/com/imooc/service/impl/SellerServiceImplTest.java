package com.imooc.service.impl;

import com.imooc.dataobject.SellerInfo;
import com.imooc.repository.SellerInfoRepository;
import com.imooc.service.SellerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceImplTest {

    @Autowired
    private SellerService sellerService;

    private static final String openid = "avc";

    @Test
    public void findSellInfoByOpenid() throws Exception {
        SellerInfo result = sellerService.findSellInfoByOpenid(openid);
        Assert.assertEquals(openid, result.getOpenid());
    }

}