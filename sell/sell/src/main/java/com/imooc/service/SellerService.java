package com.imooc.service;

import com.imooc.dataobject.SellerInfo;


public interface SellerService {

    /**
     * 通过openid查询卖家端信息
     * @param openid
     * @return
     */

    SellerInfo findSellInfoByOpenid(String openid);   //这里方法名也必须严格要求Sql里写的 sql里写的openid就是Openid 写的open_id就是OpenId!!!!
}
