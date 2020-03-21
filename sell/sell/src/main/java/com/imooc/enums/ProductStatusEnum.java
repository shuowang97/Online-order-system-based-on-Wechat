package com.imooc.enums;

import lombok.Getter;
import org.aspectj.apache.bcel.classfile.Code;
import sun.print.DialogOwner;


@Getter
public enum ProductStatusEnum implements CodeEnum{
    UP(0,"上架"),
    DOWN(1,"下架")
    ;

    private Integer code;
    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
