package com.small.fruitandvagetablemallspringboot.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ty.alipay")
@Data
public class AliPayProperties {
    private String appId; //小程序的appid
    private String appPrivateKey; //小程序的秘钥
    private String alipayPublicKey; //订阅信息模板id
    private String notifyUrl; //订阅信息模板id
}
