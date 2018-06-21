package com.ai.smart.common.helper;

import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.common.enums.Env;
import com.ai.smart.common.helper.json.JsonHelper;
import com.google.common.base.Objects;
import org.springframework.stereotype.Component;

@Component
public class MockHelper {

    /**
     * mock 本地环境
     * @return
     */
    public boolean isMock(){
        String property = System.getProperty("user.env");
        if (Objects.equal(property, Env.LOCAL.name().toLowerCase()) ||
                Objects.equal(property, Env.DEV.name().toLowerCase())) {
            return true;
        }
        return false;
    }


}
