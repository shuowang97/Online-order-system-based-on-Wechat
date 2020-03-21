package com.imooc.enums;


import lombok.Getter;



@Getter
public enum ResultEnum {

    SUCCESS(0, "成功"),
    PARAM_INCORRECT(1, "参数不正确"),

    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "库存不足"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    ORDERDETAIL_NOT_EXIST(13, "订单详情不存在"),
    ORDER_STATUS_ERROR(14, "订单状态错误"),
    ORDER_UPDATE_ERROR(15, "订单更新错误"),
    ORDER_DETAIL_EMPTY(16, "订单详情为空"),
    ORDER_PAY_STATUS_ERROR(17, "支付状态错误"),
    CART_EMPTY_ERROR(18, "购物车为空"),
    ORDER_OWNER_ERROR(19, "该订单不属于当前用户"),
    WECHAT_MP_ERROR(20, "微信公众账号方面错误"),

    ORDER_CANCEL_SUCCESS(22, "订单取消成功"),
    ORDER_FINISH_SUCCESS(23, "订单已完结"),
    PRODUCT_STATUS_ERROR(24, "商品状态错误"),
    PRODUCT_ONSALE_SUCCESS(25, "商品上架成功"),
    PRODUCT_OFFSALE_SUCCESS(26, "商品下架成功"),
    PRODUCT_SUBMIT_SUCCESS(27, "商品提交成功"),
    CATEGORY_SUBMIT_SUCCESS(28, "新类目创建成功"),
    LOGIN_FAIL(29, "登录失败，登录信息不正确"),
    LOGOUT_SUCCESS(30, "登出成功"),
    LOGIN_ERROR(31, "您尚未登录或登录身份已失效"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
