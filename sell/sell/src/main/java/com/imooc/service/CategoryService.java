package com.imooc.service;

import com.imooc.dataobject.ProductCategory;

import java.util.List;


public interface CategoryService {

//    卖家端
    ProductCategory findOne(Integer categoryId);
    List<ProductCategory> findAll();
//    买家端
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
    ProductCategory save(ProductCategory productCategory);


}
