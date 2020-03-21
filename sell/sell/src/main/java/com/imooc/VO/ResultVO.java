package com.imooc.VO;

import lombok.Data;

/**
 * VO 视图对象
 * Http请求返回的最外层的对象
 */
@Data
public class ResultVO<T> {
    /*错误码*/
    private Integer code;
    /*提示信息*/
    private String msg;
    /*返回的具体内容*/
    private T data;
}
