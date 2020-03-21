package com.imooc.repository;

import com.imooc.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


//                                                                 对象               主键类型
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>{
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryList);


}
