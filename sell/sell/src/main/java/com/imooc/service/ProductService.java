package com.imooc.service;

import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProductService {

    //查询一个
    ProductInfo findOne(String productId);
    //查询全部上架商品 买家端
    List<ProductInfo> findUpAll();
    //根据商品类目查询商品
    List<ProductInfo> findByCategoryType(Integer categoryType);
    //查询所有商品 要分页！ 卖家端  使用了Pageable的话 返回值为Page的对象
    Page<ProductInfo> findAll(Pageable pageable);
    //增加or修改
    ProductInfo save(ProductInfo productInfo);
    //加库存
    void increaseStock(List<CartDTO> cartDTOList);
    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);
    //上架
    ProductInfo onSale(String productId);
    //下架
    ProductInfo offSale(String productId);
}
