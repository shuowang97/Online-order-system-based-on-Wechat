package com.imooc.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.imooc.converter.OrderMaster2OrderDTOConverter;
import com.imooc.dataobject.OrderDetail;
import com.imooc.dataobject.OrderMaster;
import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDTO;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.repository.OrderDetailRepository;
import com.imooc.repository.OrderMasterRepository;
import com.imooc.service.OrderService;
import com.imooc.service.ProductService;
import com.imooc.service.PushMessageService;
import com.imooc.service.WebSocket;
import com.imooc.utils.KeyUtil;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import javax.xml.transform.Result;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
@Transactional   //添加事务 异常时回滚
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private WebSocket webSocket;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {

        BigDecimal orderAmount = new BigDecimal(0);
        String orderId = KeyUtil.getUniqueKey();

        /*1. 查询商品（数量，价格）*/
        for(OrderDetail orderDetail: orderDTO.getOrderDetailList()){

            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            /*2. 计算订单总价*/
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            /*订单详情入库，此处无需传入全部对象，只需要商品id以及数量*/
            /*利用系统时间加随机数生成订单id以及商品详情id*/
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);

        }
        /*3. 写入订单数据库 包括orderMaster 和 orderDetail*/
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        /*扣库存  此时要确认库存是否充足 不然取消订单*/
        List<CartDTO> cartDTOList =
                orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(),e.getProductQuantity())
        ).collect(Collectors.toList());

        productService.decreaseStock(cartDTOList);

        //推送微信信息
        pushMessageService.newOrder(orderDTO);

        //新建订单时通过websocket向卖家端传输一条消息
        webSocket.sendMessage(orderDTO.getOrderId());


        return orderDTO;

    }


    @Override
    public OrderDTO findOne(String orderId) {

        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.converter(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());

        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        /*判断订单状态*/
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】【订单状态错误】, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        /*修改订单状态*/
        orderDTO.setOrderStatus(OrderStatusEnum.CANCLE.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster saveResult = orderMasterRepository.save(orderMaster);
        if(saveResult == null){
            log.error("【取消订单】【订单更新错误】, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }

        /*返回库存*/
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】【订单中无商品详情】, orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().
                map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).
                collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        /*如果已支付，退款*/
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO 左下角快速定位未完成部分  此处为退款
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        /*判断订单状态*/
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】【订单状态错误】, orderId={}, OrderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        /*更改订单状态*/
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(result == null){
            log.error("【完结订单】【更新失败】, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }

        //推送微信模板消息    这里代表微信订单被完结的时候 推送消息 也可以新增如orderStatus之类的方法 用于新下单
        pushMessageService.orderStatus(orderDTO);


        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        /*判断订单状态*/
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【订单支付】【订单状态错误】, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        /*判断支付状态*/
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【订单支付完成】【支付状态错误】, orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        /*修改支付状态*/
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(result == null){
            log.error("【订单支付完成】【支付状态更新错误】, OrderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.converter(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }
}
