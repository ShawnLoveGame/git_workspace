package com.ai.smart.bottom.helper;

import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.common.helper.DateHelper;
import com.ai.smart.common.helper.MockHelper;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * 短信
 */
@Slf4j
@Component
public class SmsHelper {

    private static final String SOURCEKEY = "H3chl7P8q6vCAodX";
    //10.191.110.50:80

    @Autowired
    private MockHelper mockHelper;

//    public static void main(String[] args) {
////        {
////            "body": {
////            "msisdn": "15123020544",
////                    "smsContent": "短信内容"
////        },
////            "header": {
////            "sourceid": "051003",
////                    "systemtime": "20180611195138104",
////                    "mac": "a2d9c1b442b301510261863bc9f672436d2e7a9e0affaf0f9a7172ea3b1d6387"
////        }
////        }
//
//        JSONObject body = new JSONObject();
//        body.put("msisdn", "15123020544");
//        body.put("smscontent", "短信内容");
//        JSONObject header = new JSONObject();
//        header.put("systemtime", "20180611195138104");
//        header.put("sourceid", "051003");
//        System.out.println(MacUtils.toJson(MacUtils.sort(body), MacUtils.sort(header)));
//        System.out.println(MacUtils.hmacsha256("H3chl7P8q6vCAodX", MacUtils.toJson(MacUtils.sort(body), MacUtils.sort(header))));
//
//    }

    public  static void sendSms(String serailNum,String content){
       // sendSms("18571726873","测试短信下发");
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            String url = "http://10.191.110.50:80/nativeintf/groupunit/sendSms";
            HttpPost httpPost = new HttpPost(url);

            // 设置请求的header
            httpPost.addHeader("Content-Type", "application/json;charset=utf-8");

            JSONObject body = new JSONObject();
            body.put("msisdn", serailNum);
            body.put("smsContent", content);
            JSONObject header = new JSONObject();
            header.put("systemtime", DateHelper.converToStringDate(new Date(), "yyyyMMddHHmmssSSS"));
            header.put("sourceid", "051003");

            //System.out.println("加密参数："+MacUtils.toJson(MacUtils.sort(body), MacUtils.sort(header)));
            header.put("mac", MacUtils.hmacsha256(SOURCEKEY, MacUtils.toJson(MacUtils.sort(body), MacUtils.sort(header))));
            // 设置请求的参数
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("header",header);
            jsonParam.put("body",body);

            log.error("请求参数："+jsonParam.toString());
            StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);

            // 执行请求
            HttpResponse response = httpClient.execute(httpPost);
            String json2 = EntityUtils.toString(response.getEntity(), "utf-8");
            JSONObject jsonObject = JSONObject.fromObject(json2);
            // 打印执行结果
            log.error("发送短信",jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
//     * 下发短信
//     * @param serailNum
//     * @param content
//     * @return
//     */
//    public  static GlobalResponse sendSms(String serailNum,String content){
////        if (mockHelper.isMock()){
////            return GlobalResponse.success(true);
////        }
//        JSONObject body = new JSONObject();
//        body.put("msisdn", serailNum);
//        body.put("smsContent", content);
//        JSONObject header = new JSONObject();
//        header.put("systemtime", DateHelper.converToStringDate(new Date(), "yyyyMMddHHmmssSSS"));
//        header.put("sourceid", "051003");
//        header.put("mac", MacUtils.hmacsha256(SOURCEKEY, MacUtils.toJson(MacUtils.sort(body), MacUtils.sort(header))));
//        JSONObject jsonParam = new JSONObject();
//        jsonParam.put("header",header);
//        jsonParam.put("body",body);
//        GlobalResponse globalResponse = HttpHelper.postSmsJson("http://10.191.110.50:80/nativeintf/groupunit/sendSms", jsonParam.toString());
//        if (globalResponse.isSuccess()){
//            Map map = JsonHelper.fromJson(JsonHelper.toJson(globalResponse.getData()), Map.class);
//            Object header1 = map.get("header");
//            Map<String,String> map1 = JsonHelper.fromJson(JsonHelper.toJson(header1), Map.class);
//            if (!Objects.equals(map1.get("resultcode"),"104000")){
//                log.error("发送短信异常：["+serailNum+"]"+JsonHelper.toJson(globalResponse));
//            }
//        }else{
//            log.error("发送短信异常：["+serailNum+"]"+JsonHelper.toJson(globalResponse));
//        }
//        return globalResponse;
//    }

}
