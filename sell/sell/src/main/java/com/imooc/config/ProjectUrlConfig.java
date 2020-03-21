package com.imooc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "projectUrl")
public class ProjectUrlConfig {

//    服务平台url 网页授权
    private String wechatMpAuthorize;

//    开放平台url  扫码！
    private String wechatOpenAuthorize;

//    点餐系统url
    private String sell;


}
