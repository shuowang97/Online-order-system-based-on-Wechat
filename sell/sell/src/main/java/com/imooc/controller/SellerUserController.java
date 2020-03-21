package com.imooc.controller;

import com.imooc.config.ProjectUrlConfig;
import com.imooc.constant.CookieConstant;
import com.imooc.constant.RedisConstant;
import com.imooc.dataobject.SellerInfo;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.SellerService;
import com.imooc.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**卖家用户
 */
@Controller
@RequestMapping("/seller")
@Slf4j
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "openid", required = false) String openid,
                              HttpServletResponse response,
                              Map<String, Object> map){
        if(openid == null){
            map.put("msg", ResultEnum.LOGIN_ERROR.getMsg());
            map.put("url", "/sell/buyer/product/list");
            return new ModelAndView("common/error", map);
        }

//        1. 确定openid与数据库匹配
        SellerInfo sellerInfo = sellerService.findSellInfoByOpenid(openid);

        if(sellerInfo == null){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMsg());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

//        2. 设置token至redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE_TIME;
                                         //  redis 的key                                     value   过期时间    时间的单位
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);

//        3. 设置token至cookie
/*        Cookie cookie = new Cookie("token", token);
        cookie.setPath("");
        cookie.setMaxAge(7200);*/
        //这里这样写太复杂，使用CookieUtil来完成！！！！！

        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
        //用户登录时的校验方法，先用token查到cookie里对应的value值，再用Value值在redis里查出openid，如果有的话，说明已登录
        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/sell/seller/order/list"); //这里注意格式！！
    }

    @GetMapping("logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> map){
        //1. 查找cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie != null){
            //2. 清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            //3. 清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
            log.info("【清除中】");
        }
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

}
