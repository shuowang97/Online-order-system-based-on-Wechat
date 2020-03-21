package com.imooc.constant;


public interface RedisConstant {

    //声明一种格式 格式为token_字符串
    String TOKEN_PREFIX = "token_%s";

    Integer EXPIRE_TIME = 7200; //2 小时
}
