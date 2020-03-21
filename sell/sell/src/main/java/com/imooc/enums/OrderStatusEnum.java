package com.imooc.enums;

import lombok.Getter;


@Getter
public enum OrderStatusEnum implements CodeEnum{
    NEW(0, "新下单"),
    FINISHED(1, "完结"),
    CANCLE(2, "已取消"),
    ;

    private Integer code;
    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
