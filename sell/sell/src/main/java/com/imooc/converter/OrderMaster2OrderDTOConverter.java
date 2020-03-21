package com.imooc.converter;

import com.imooc.dataobject.OrderMaster;
import com.imooc.dto.OrderDTO;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class OrderMaster2OrderDTOConverter {

    public static OrderDTO converter(OrderMaster orderMaster){

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> converter(List<OrderMaster> orderMasterList){

        return orderMasterList.stream().map(e -> converter(e)).collect(Collectors.toList());
    }
}
