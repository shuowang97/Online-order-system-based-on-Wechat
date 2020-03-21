package com.imooc.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@Entity
public class SellerInfo {

    @Id
    private String sellerId;

    private String username; //这里要和sql里完全对应 如果sql里写了user_name这里必须userName!!!!!

    private String password;

    private String openid;


}
