package com.ai.smart.bottom.boot.ma;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Configuration
public class WxMaPayHelper {

    private WxPayService wxPayService;

    private WxPayConfig wxPayConfig;

    @Value("${wechat_miniapp_appid}")
    private String wechat_miniapp_appid;

    @Value("${wechat_mchid}")
    private String wechat_mchid;
    @Value("${wechat_mchkey}")
    private String wechat_mchkey;
    @Value("${wechat_mchkeypath}")
    private String wechat_mchkeypath;

    public WxPayService getInstance() {
       return wxPayService;
    }

    @PostConstruct
    public void config() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(wechat_miniapp_appid);
        payConfig.setMchId(wechat_mchid);
        payConfig.setMchKey(wechat_mchkey);
        payConfig.setKeyPath(wechat_mchkeypath);
        payConfig.setNotifyUrl("smart/pay/notifyOrder");
        payConfig.setSignType("MD5");
        wxPayConfig = payConfig;
        wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(wxPayConfig);
    }

}
