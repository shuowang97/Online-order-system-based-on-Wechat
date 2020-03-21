package com.imooc.service.impl;

import com.imooc.config.WechatAccountConfig;
import com.imooc.dto.OrderDTO;
import com.imooc.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
@Slf4j

public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig accountConfig;

    @Override
    public void orderStatus(OrderDTO orderDTO) {

        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
        wxMpTemplateMessage.setToUser("oe14juMgyIXYQHkxbXllWto9IuHc");
        List<WxMpTemplateData> dataList = Arrays.asList(
                new WxMpTemplateData("first","请记得收货"),
                new WxMpTemplateData("keyword1","微信点餐（商家名）"),
                new WxMpTemplateData("keyword2","18875143268"),
                new WxMpTemplateData("keyword3",orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4",orderDTO.getOrderStatusEnum().getMsg()),
                new WxMpTemplateData("keyword5", "￥" + orderDTO.getOrderAmount()),
                new WxMpTemplateData("remark","欢迎再次光临")
                );
        wxMpTemplateMessage.setData(dataList);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            log.error("【微信模板消息】发送失败，{}", e);
        }
    }

    @Override
    public void newOrder(OrderDTO orderDTO) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setToUser("oe14juMgyIXYQHkxbXllWto9IuHc");
        wxMpTemplateMessage.setTemplateId(accountConfig.getTemplateId().get("newOrder"));

        List<WxMpTemplateData> dataList = Arrays.asList(
                new WxMpTemplateData("first","请确认信息"),
                new WxMpTemplateData("keyword1","微信点餐（商户名）"),
                new WxMpTemplateData("keyword2",orderDTO.getBuyerName()),
                new WxMpTemplateData("keyword3",orderDTO.getBuyerPhone()),
                new WxMpTemplateData("keyword4",orderDTO.getBuyerAddress()),
                new WxMpTemplateData("remark","若有问题，请及时联系")
        );
        wxMpTemplateMessage.setData(dataList);

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            log.error("【新建订单】出现异常，{}", e);
        }

    }
}
