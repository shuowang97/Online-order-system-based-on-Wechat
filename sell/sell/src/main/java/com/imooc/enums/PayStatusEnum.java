package com.imooc.enums;

import lombok.Getter;

/**
 * Created by Administrator on 2019/7/22.
 */
@Getter
public enum PayStatusEnum implements CodeEnum{
    WAIT(0, "等待支付"),
    SUCCESS(1, "已支付"),
    ;


    private Integer code;
    private String msg;

    PayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
