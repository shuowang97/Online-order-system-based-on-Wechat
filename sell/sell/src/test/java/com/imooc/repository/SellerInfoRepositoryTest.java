package com.imooc.repository;

import com.imooc.dataobject.SellerInfo;
import com.imooc.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository repository;

    @Test
    public void findByOpenId() throws Exception {
        SellerInfo result = repository.findByOpenid("avc");
        Assert.assertEquals("avc", result.getOpenid());
    }

    @Test
    public void save(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.getUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("avc");
        SellerInfo result = repository.save(sellerInfo);
        Assert.assertNotNull(result);
    }

}