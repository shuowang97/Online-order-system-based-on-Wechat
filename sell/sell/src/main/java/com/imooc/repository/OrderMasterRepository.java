package com.imooc.repository;

import com.imooc.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    /*按照买家openid查订单，并且分页*/
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

}
