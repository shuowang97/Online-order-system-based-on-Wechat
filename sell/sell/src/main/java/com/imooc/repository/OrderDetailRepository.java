package com.imooc.repository;

import com.imooc.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String>{

    List<OrderDetail> findByOrderId(String buyerOpenid);
}
