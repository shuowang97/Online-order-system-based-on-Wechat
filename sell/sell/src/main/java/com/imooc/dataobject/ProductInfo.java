package com.imooc.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imooc.enums.ProductStatusEnum;
import com.imooc.utils.EnumUtil;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息表
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo {

    @Id  //String 不自增
    private String productId;

    //名字
    private String productName;

    //单价
    private BigDecimal productPrice;

    //库存
    private Integer productStock;

    //描述
    private String productDescription;

    //小图 链接地址
    private String productIcon;

    //状态，0为正常，1为下架
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    //类目编号  关联商品信息表和类目表！！！
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }

}
