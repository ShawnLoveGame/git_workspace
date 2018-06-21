package com.ai.smart.bottom.vo;

import cn.binarywang.wx.miniapp.bean.WxMaWxcodeLimit;
import cn.binarywang.wx.miniapp.util.json.WxMaGsonBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WxMaWxcodeLimitVo extends WxMaWxcodeLimit {

    private boolean is_hyaline;

    public static WxMaWxcodeLimitVo fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaWxcodeLimitVo.class);
    }
}
