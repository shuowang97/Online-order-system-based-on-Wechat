package com.imooc.exception;

import com.imooc.enums.ResultEnum;

import javax.print.attribute.standard.RequestingUserName;


public class SellException extends  RuntimeException {

    private Integer code;


    public SellException(ResultEnum resultEnum) {
        //RuntimeException自带msg属性
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
