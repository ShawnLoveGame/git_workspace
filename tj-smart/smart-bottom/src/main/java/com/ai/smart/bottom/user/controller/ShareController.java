package com.ai.smart.bottom.user.controller;

import com.ai.smart.bottom.base.BaseController;
import com.ai.smart.bottom.user.service.ActivitySoService;
import com.ai.smart.bottom.user.service.ActivityUserRelService;
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
@RestController
@RequestMapping("/share")
@Api(value = "/share", description = "分享接口")
public class ShareController extends BaseController {

    @Autowired
    private WeService weService;

    @Autowired
    private ActivityUserRelService activityUserRelService;

    @Autowired
    private ActivitySoService activitySoService;

    @ApiOperation(notes = "/getwxacodeunlimit", value = "生成的小程序码，永久有效，数量暂无限制")
    @RequestMapping(value = "/getwxacodeunlimit", method = RequestMethod.POST)
    public GlobalResponse getwxacodeunlimit(String path) {
        return weService.getwxacodeunlimit(getUserId(), path);
    }

    @ApiOperation(notes = "/bindUser", value = "分享绑定用户关系")
    @RequestMapping(value = "/bindUser", method = RequestMethod.POST)
    public GlobalResponse bindUser(String parentId) {
        return activityUserRelService.bindUser(parentId,getUserId());
    }

    @ApiOperation(notes = "/findActiviySoPage", value = "分页查询活动轮播信息")
    @RequestMapping(value = "/findActiviySoPage", method = RequestMethod.POST)
    public GlobalResponse findActiviySoPage(Integer pageNo,Integer pageSize){
        return activitySoService.findActiviySoPage(pageNo,pageSize);
    }

    @ApiOperation(notes = "/findActiviySoPageByUserId", value = "分页查询分享红包信息")
    @RequestMapping(value = "/findActiviySoPageByUserId", method = RequestMethod.POST)
    public GlobalResponse findActiviySoPageByUserId(Integer pageNo,Integer pageSize){
        return activitySoService.findActiviySoPageByUserId(pageNo,pageSize,getUserId());
    }
}
