package com.imooc.utils;

import com.imooc.enums.CodeEnum;


public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> EnumClass){
        for(T each: EnumClass.getEnumConstants()){
            if(each.getCode().equals(code)){
                return each;
            }
        }
        return null;
    }


}
