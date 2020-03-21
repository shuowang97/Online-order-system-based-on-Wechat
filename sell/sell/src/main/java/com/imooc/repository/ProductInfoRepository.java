package com.imooc.repository;

import com.imooc.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    //查询已上架的商品
    List<ProductInfo> findByProductStatus(Integer productStatus);

    List<ProductInfo> findByCategoryType(Integer categoryType);
}
