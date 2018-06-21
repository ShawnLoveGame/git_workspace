package com.ai.smart.bottom.wechat.controller;

import com.ai.smart.bottom.wechat.service.WeService;
import com.ai.smart.common.base.GlobalResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/core")
@RestController
@Api(value = "/core",description = "小程序接口")
public class WxMaController {

    @Autowired
    private WeService weService;

    @ApiOperation(notes = "/login",value = "用户code获取session")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public GlobalResponse login(String code){
        return weService.getSessionInfo(code);
    }


}
