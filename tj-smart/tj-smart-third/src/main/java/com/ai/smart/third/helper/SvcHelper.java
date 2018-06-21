package com.ai.smart.third.helper;

import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.common.helper.json.JsonHelper;
import com.ailk.uip.base.SeqIdGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.linkage.appframework.data.DataMap;
import com.linkage.appframework.data.IData;
import com.linkage.appframework.data.IDataset;
import com.linkage.util.webservice.WebserviceHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
public class SvcHelper {

    protected static WebserviceHelper service = WebserviceHelper.getInstance();

    /**
     * svc接口接入
     * @param opCode  业务编号
     * @param content 入参
     * @param desc   描述
     * @return
     */
    public static GlobalResponse postSvc (String opCode, IData content, String desc) {
        try {
            //bsacTYWxin
            IData head = new DataMap();
            //bsacTYZTApp bsacTYWxin
            head.put("UNITID", "bsacTYWxin");//渠道编码
            head.put("ACCESSTYPE", "bsacTYWxin");//厂商编码
            head.put("TERMINALID", "BYD");//终端编码
            //请求交易时间
            head.put("REQTIME", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            //对方请求流水编号,参数为渠道编码，只能两位 E1:网厅，E4:WAP厅 E004微信

            head.put("REQFORMNUM", SeqIdGenerator.getReqFormNum("E004"));
            head.put("BIZ_CODE", "SPI0008");
            head.put("TRANS_CODE", opCode);
            head.put("DESC", desc);
            head.put("OPCODE",opCode);
            IDataset dataset = service.callWebSvc( head, content,40000);
            System.out.println("返回json："+JsonHelper.toJson(dataset));
            JavaType jt = JsonHelper.constructParametricType(List.class,Map.class);
            List<Map> relist = JsonHelper.fromJson(JsonHelper.toJson(dataset), jt);
            if (CollectionUtils.isNotEmpty(relist)){
                return GlobalResponse.success(relist);
                //HashMap uipResultVo = relist.get(0);
//                if (Objects.equals(uipResultVo.get("X_RESULTCODE").toString(),"0")){
//                    return GlobalResponse.success(uipResultVo);
//                }else{
//                    return GlobalResponse.fail(uipResultVo.get("X_RESULTINFO").toString());
//                }
            }
;           return GlobalResponse.fail("接口返回异常");
        } catch (Exception e) {
           log.error("postSvc exception:{}",opCode,e);
        }
        return GlobalResponse.fail("调用接口"+desc+"异常");
    }



}
