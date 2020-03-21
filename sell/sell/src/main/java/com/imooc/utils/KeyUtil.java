package com.imooc.utils;

import java.util.Random;

public class KeyUtil {

    /*生成唯一的主键 格式为时间+随机数*/
    /*使用Synchronized关键字 避免生成重复的random*/
    public static synchronized String getUniqueKey(){
        Random random = new Random();
        /*利用Random生成6位随机数*/
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }
}
