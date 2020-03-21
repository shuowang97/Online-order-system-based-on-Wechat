package com.imooc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.dataobject.OrderDetail;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import com.imooc.utils.EnumUtil;
import com.imooc.utils.serializer.Date2LongSerializer;
import lombok.Data;
import org.hibernate.criterion.Order;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * data transfer object 数据传输对象 为了加入orderDetailList这个对象
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)  //默认不输出值为null的属性 如输出订单列表时 OrderDetailList 为Null
//这个注解可以在配置文件里 替换
public class OrderDTO {

    /*订单id*/
    private String orderId;

    /*买家名*/
    private String buyerName;

    /*买家手机号*/
    private String buyerPhone;

    /*买家地址*/
    private String buyerAddress;

    /*买家微信openid*/
    private String buyerOpenid;

    /*订单总金额*/
    private BigDecimal orderAmount;

    /*订单状态，默认为新下单*/
    private Integer orderStatus;

    /*支付状态，默认为未支付*/
    private Integer payStatus;

    /*创建时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /*更新时间*/
    /*JsonSerialize实现快速将Date类型转换成Long类型 /1000 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /*一个订单可以对应多个订单商品*/
    private List<OrderDetail> orderDetailList;

    @JsonIgnore  //输出orderDTO时 可以不显示该对象
    public OrderStatusEnum getOrderStatusEnum(){

        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){

        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
