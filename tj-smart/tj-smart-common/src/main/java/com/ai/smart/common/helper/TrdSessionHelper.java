package com.ai.smart.common.helper;

import com.ai.smart.common.helper.redis.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class TrdSessionHelper {


    @Autowired
    private RedisHelper redisHelper;

    private static final  String SMART_COMMON_TRD_SESSION_ = "SMART_COMMON_TRD_SESSION_";

    /**
     * 创建sessionId
     * @param openId
     * @return
     */
    public String buildTrdSession(String openId){
        try {
            boolean isget = redisHelper.canBeSubmit(SMART_COMMON_TRD_SESSION_ + openId, "isget", 5);
            if (isget){
                return  UUID.randomUUID().toString().replaceAll("-", "");
            }
        } catch (Exception e) {
            log.error("buildTrdSession exception：",e);
        }
        return null;
    }
}
