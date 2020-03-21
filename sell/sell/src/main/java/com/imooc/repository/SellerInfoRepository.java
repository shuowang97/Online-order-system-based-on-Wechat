package com.imooc.repository;

import com.imooc.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

    //这里方法名也必须严格要求Sql里写的 sql里写的openid就是Openid 写的open_id就是OpenId!!!!
    SellerInfo findByOpenid(String openid);

}
