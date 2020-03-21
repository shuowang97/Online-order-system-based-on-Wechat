package com.imooc.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class ProductForm {

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

    //类目编号  关联商品信息表和类目表！！！
    private Integer categoryType;

}
