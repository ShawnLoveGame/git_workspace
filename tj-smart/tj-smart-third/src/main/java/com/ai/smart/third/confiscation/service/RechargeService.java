package com.ai.smart.third.confiscation.service;


import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.common.helper.AssertHelper;
import com.ai.smart.common.helper.MockHelper;
import com.ai.smart.common.helper.json.JsonHelper;
import com.ai.smart.third.helper.CQBOSSPayParamsHelper;
import com.ai.smart.third.helper.SvcHelper;
import com.ai.smart.third.remoting.NioRemotingClient;
import com.ai.smart.third.vo.Uip100012;
import com.fasterxml.jackson.databind.JavaType;
import com.linkage.appframework.data.DataMap;
import com.linkage.appframework.data.IData;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class RechargeService {

    @Autowired
    private LoggingService loggingService;

    /**
     * 手机号码缴费
     * @param serialnum
     * @param amount
     * @return100236
     */
    public GlobalResponse rechargeOnline(String serialnum, BigDecimal amount){
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("STREAMING_NUM",CQBOSSPayParamsHelper.createSerialNo());
        jsonObject.put("TEL_NUM","14723550578");
        jsonObject.put("PAY_MENT","20.0");
        jsonObject.put("GIVE_AMOUNT","2.0");
        jsonObject.put("THIRD_GIVE_AMOUNT","0");
        jsonObject.put("SCORE","10");
        jsonObject.put("PRINT_FLAG_KEY","4");
        jsonObject.put("ORDER_TIME",DateFormatUtils.format(new Date(),"yyyyMMddHHmmss"));
        String header = CQBOSSPayParamsHelper.getPackageHead(jsonObject,"2000");
        String body = CQBOSSPayParamsHelper.getPackageBody(jsonObject,"2000");
        NioRemotingClient client =new NioRemotingClient();
        Channel channel = null;
        try {
            client.start();
            //channel = client.createChannel("10.189.70.188:8699");
            channel = client.createChannel("10.191.132.30:8699");
            client.invokeOnewayImpl(channel,header+body,3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        AssertHelper.notNull(serialnum,"手机号码为空");
//        AssertHelper.notNull(amount,"充值金额为空");
//        if (amount.compareTo(new BigDecimal(0))<1){
//            return GlobalResponse.fail("充值金额不能为空");
//        }
//       if (mockHelper.isMock()){
//            return GlobalResponse.success(true);
//       }
       //TODO 在线充值接口
        return GlobalResponse.fail("正在加进对接中。。。。");
    }


    /**
     * uip赠费接口
     * @param formNum
     * @param serialNum
     * @param amount
     * @param subjectId
     * @param notes
     * @return
     */
    public GlobalResponse largessFee(String formNum,String serialNum, BigDecimal amount,String subjectId,
                                     String notes){
        AssertHelper.notBlank(formNum,"交易流水号为空");
        AssertHelper.notBlank(serialNum,"手机号码为空");
        AssertHelper.notNull(amount,"金额为空");
        //AssertHelper.notBlank(subjectId,"充值科目编码为空");
        AssertHelper.notBlank(notes,"备注为空");
        IData param = new DataMap();
        param.put("TELNUM",serialNum);
        param.put("FORMNUM",formNum);
        param.put("NOTES",notes);
        param.put("AMOUNT",amount.toString());
        //param.put("SUBJECTID",subjectId);
        GlobalResponse globalResponse = SvcHelper.postSvc("100178", param, "电子渠道赠送话费");
        loggingService.writeLogInfo(serialNum,"电子渠道赠送话费",globalResponse.getData());
        if (globalResponse.isSuccess()){
            String json=   JsonHelper.toJson(globalResponse.getData());
            JavaType jt = JsonHelper.constructParametricType(List.class,Uip100012.class);
            List<Uip100012> list = JsonHelper.fromJson(json,jt);
            return  GlobalResponse.success(list);
        }
        return globalResponse;
    }




}
