package com.ai.smart.third.helper;

import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.common.helper.json.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * webservice 调用入口
 */
@Slf4j
public class CxfHelper {

    /**
     * 动态调用方式
     */
//    public static GlobalResponse sendWebService(String wsdlUrl,String methodName,String ...param) {
//        // 创建动态客户端
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient(wsdlUrl);
//        Object[] objects = new Object[0];
//        try {
//            objects = client.invoke(methodName, param);
//            System.out.println("返回数据:" + objects[0]);
//            return GlobalResponse.success(JsonHelper.toJson(objects[0]));
//        } catch (java.lang.Exception e) {
//            log.error("sendWebService exception:{},{}",wsdlUrl,param,e);
//        }
//        return GlobalResponse.fail("调用失败");
//    }
//
//    /**
//     * 需要用户名密码调用
//     * @param wsdlUrl
//     * @param userName
//     * @param pwd
//     * @param methodName
//     * @param param
//     * @return
//     */
//    public static GlobalResponse sendWebService(String wsdlUrl,String userName,String pwd,String methodName,String ...param) {
//        // 创建动态客户端
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient(wsdlUrl);
//        // 需要密码的情况需要加上用户名和密码
//        client.getOutInterceptors().add(new ClientLoginInterceptor(userName,pwd));
//        Object[] objects = new Object[0];
//        try {
//            objects = client.invoke(methodName, param);
//            return GlobalResponse.success(JsonHelper.toJson(objects[0]));
//        } catch (java.lang.Exception e) {
//            log.error("sendWebService exception:{},{}",wsdlUrl,param,e);
//        }
//        return GlobalResponse.fail("调用失败");
//    }
}
