package com.ai.smart.bottom.common.service;

import com.ai.smart.bottom.vo.MealYwSubVo;
import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.common.helper.MockHelper;
import com.ai.smart.common.helper.io.HttpHelper;
import com.ai.smart.common.helper.json.JsonHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 第三方接口服务
 */
@Slf4j
@Service
public class ThirdService {

    @Value("${thirdDomain}")
    private String thirdDomain;

    @Autowired
    private MockHelper mockHelper;

    /**
     * 查询当前手机号码的信息
     * @param serialNumber
     * @return
     */
    public GlobalResponse getHtcByserialNumber(String serialNumber){
        Map<String, Object> param = Maps.newHashMap();
        param.put("serialNum",serialNumber);
        return HttpHelper.postAppJson(thirdDomain+"/htc/findHtcBySerialNum", JsonHelper.toJson(param),"pass");
    }

    /**
     * 发送短信验证码
     * @param serialNumber
     * @param createBy
     * @return
     */
    public GlobalResponse sendSmsCode(String serialNumber,String createBy,String ip){
        Map<String, Object> param = Maps.newHashMap();
        param.put("serialNumber",serialNumber);
        param.put("createBy",createBy);
        param.put("ip",ip);
        return HttpHelper.postAppJson(thirdDomain+"/sms/sendSmsVerCode", JsonHelper.toJson(param),"pass");
    }

    /**
     * 校验验证码是否通过
     * @param serialNumber
     * @param smsCode
     * @param ip
     * @return
     */
    public GlobalResponse checkSmsVerCode(String serialNumber,String smsCode,String ip){
        Map<String, Object> param = Maps.newHashMap();
        param.put("serialNumber",serialNumber);
        param.put("verCode",smsCode);
        param.put("ip",ip);
        return HttpHelper.postAppJson(thirdDomain+"/sms/checkSmsVerCode", JsonHelper.toJson(param),"pass");
    }


    /**
     * 套餐办理
     * @param ncode
     * @param serialNum
     * @return
     */
    public GlobalResponse addMealByNcode(String ncode,String serialNum,String efftype){
        if (mockHelper.isMock()){
            return GlobalResponse.success(true);
        }
        Map<String, Object> param = Maps.newHashMap();
        param.put("serialNum",serialNum);
        param.put("mealCode",ncode);
        param.put("efftype",efftype);
        return HttpHelper.postAppJson(thirdDomain+"/meal/addMealByNcode", JsonHelper.toJson(param),"pass");
    }

    /**
     * 主体业务转移
     * @param ncode
     * @param serialNum
     * @param efftype
     * @return
     */
    public GlobalResponse changeMeal(String ncode,String serialNum,String efftype,String cparam){
        if (mockHelper.isMock()){
            return GlobalResponse.success(true);
        }
        Map<String, Object> param = Maps.newHashMap();
        param.put("serialNum",serialNum);
        param.put("mealCode",ncode);
        param.put("efftype",efftype);
        param.put("cparam",cparam);
        return HttpHelper.postAppJson(thirdDomain+"/meal/changeMeal", JsonHelper.toJson(param),"pass");
    }

    /**
     * 当月是否办理过主体产品变更
     * @param serialNum
     * @return
     */
    public GlobalResponse checkIsMealOnMonth(String serialNum){
        if (mockHelper.isMock()){
            return GlobalResponse.success(false);
        }
        Map<String, Object> param = Maps.newHashMap();
        param.put("serialNum",serialNum);
        return HttpHelper.postAppJson(thirdDomain+"/meal/checkIsMealOnMonth", JsonHelper.toJson(param),"pass");
    }

    /**
     * 校验是否为重庆移动号码
     * @param serialNum
     * @return
     */
    public GlobalResponse checkIsQcyd(String serialNum){
        if (mockHelper.isMock()){
            return GlobalResponse.success(true);
        }
        Map<String, Object> param = Maps.newHashMap();
        param.put("serialNum",serialNum);
        return HttpHelper.postAppJson(thirdDomain+"/htc/checkIsQcyd", JsonHelper.toJson(param),"pass");
    }

    /**
     * 获取当前手机号的资费信息
     * @param serialNum
     * @return
     */
    public GlobalResponse findCurrentMeal(String serialNum){
        if (mockHelper.isMock()){
            List<MealYwSubVo> detailinfo = Lists.newArrayList();
            MealYwSubVo mealYwSubVo = new MealYwSubVo();
            mealYwSubVo.setITEMID("-1000");
            detailinfo.add(mealYwSubVo);
            return GlobalResponse.success(detailinfo);
        }
        Map<String, Object> param = Maps.newHashMap();
        param.put("serialNum",serialNum);
        return HttpHelper.postAppJson(thirdDomain+"/meal/findCurrentMeal", JsonHelper.toJson(param),"pass");
    }

    /**
     * 赠送话费
     * @param formNum
     * @param serialNum
     * @param amount
     * @param notes
     * @param subjectId
     * @return
     */
    public GlobalResponse largessFee(String formNum, String serialNum, BigDecimal amount,String notes,String subjectId){
        if (mockHelper.isMock()){
            return GlobalResponse.success(true);
        }
        Map<String, Object> param = Maps.newHashMap();
        param.put("formNum",formNum);
        param.put("serialNum",serialNum);
        param.put("amount",amount);
        param.put("notes",notes);
        param.put("subjectId",subjectId);
        return HttpHelper.postAppJson(thirdDomain+"/recharge/largessFee", JsonHelper.toJson(param),"pass");
    }
}
