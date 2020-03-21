package com.imooc.aspect;

import com.imooc.constant.CookieConstant;
import com.imooc.constant.RedisConstant;
import com.imooc.exception.SellerAuthorizeException;
import com.imooc.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 设置是否已登录的切入点，应排除SellerUserController，添加所有Seller开头的controller
     */

    //定义切入点                                         这里表示以seller开头的所有controller   其中SellerUserController内部已有判断 故排除
    @Pointcut("execution(public * com.imooc.controller.Seller*.*(..))"+
    "&& !execution(public * com.imooc.controller.SellerUserController.*(..))")
    public void verify(){}

    //在切入点之前进行判断登录操作 判断方法 1. 获得request 2. 获得token的值 3. 在redis里查找对应的openid
    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie.getValue() == null){
            log.warn("【登录校验】cookie中查不到token");
            throw new SellerAuthorizeException();
        }
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if(StringUtils.isEmpty(tokenValue)){
            log.warn("【登录校验】redis里查不到token");
            throw new SellerAuthorizeException();
        }
    }

}
