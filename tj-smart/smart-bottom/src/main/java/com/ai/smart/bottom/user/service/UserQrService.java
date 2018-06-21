package com.ai.smart.bottom.user.service;

import com.ai.smart.bottom.user.mapper.BottomUserQrMapper;
import com.ai.smart.bottom.user.model.BottomUserQr;
import com.ai.smart.common.base.GlobalResponse;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserQrService {

    @Autowired
    private BottomUserQrMapper bottomUserQrMapper;

    /**
     * 查询用户的二维码信息
     * @param userId
     * @return
     */
    public BottomUserQr findByUserId(Long userId){
        return bottomUserQrMapper.findByUserId(userId);
    }

    /**
     * 存储图片地址
     * @param bottomUserQr
     * @return
     */
    public GlobalResponse saveBottomUserQr(BottomUserQr bottomUserQr){
        BottomUserQr byUserId = bottomUserQrMapper.findByUserId(bottomUserQr.getUserId());
        if (byUserId != null && !Strings.isNullOrEmpty(bottomUserQr.getUserQrUrl())){
            return GlobalResponse.success(byUserId);
        }else{
            bottomUserQrMapper.insert(bottomUserQr);
            return GlobalResponse.success(bottomUserQr);
        }
    }
}
