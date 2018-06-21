package com.ai.smart.bottom.common.service;

import com.ai.smart.bottom.common.mapper.ComSectionMapper;
import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.common.helper.AssertHelper;
import com.ai.smart.common.helper.MockHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 号段服务
 */
@Slf4j
@Service
public class ComSectionService {

    @Autowired
    private MockHelper mockHelper;

    @Autowired
    private ThirdService thirdService;


    /**
     * 校验手机号码号段是否正确
     * @param sectionNum
     * @return
     */
    public GlobalResponse checkSectionNum(String sectionNum){
        AssertHelper.notBlank(sectionNum,"号码为空");
        if (mockHelper.isMock()){
            return GlobalResponse.success("验证成功");
        }
        return thirdService.checkIsQcyd(sectionNum);
    }
}
