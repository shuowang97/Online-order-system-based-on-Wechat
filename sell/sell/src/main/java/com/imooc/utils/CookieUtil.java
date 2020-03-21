package com.imooc.utils;

import com.imooc.constant.CookieConstant;
import org.apache.http.HttpResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


public class CookieUtil {
    //静态方法 不用生成对象 直接类名调用
    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge){
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(7200);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static Cookie get(HttpServletRequest request,
                             String name){
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if(cookieMap.containsKey(name)){
            return cookieMap.get(name);
        }
        else {
            return null;
        }
    }

    /**
     * 将cookie封装成map形式 方便get()方法实现
     * @param request
     * @return
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request){
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie: cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
