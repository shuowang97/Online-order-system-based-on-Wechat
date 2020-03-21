package com.imooc.handler;

import com.imooc.config.ProjectUrlConfig;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellerAuthorizeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


@ControllerAdvice
@Slf4j
public class SellerExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /**
     * 拦截登陆异常
     */
    @ExceptionHandler(SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){

        //这里不能加Map《String， Object》是因为只能在Controller里才能加 这里没有页面链接设置@GetMapping

        //这里应该跳到二维码扫描页面的 "redirect:".concat(projectUrl.getSell().concat("sell/wechat/qrAuthorize")) .....
        log.warn("【进来了！】");
        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/sell/seller/login");
    }



}
