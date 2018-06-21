package com.ai.smart.bottom.helper;

import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.common.helper.DateHelper;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Slf4j
@Component
@Configuration
public class PostSmsHelper {

    @Value("${sms.sourceid}")
    private String smsSourceid;
    @Value("${sms.sourcekey}")
    private String smsSourceKey;
    @Value("${smsurl}")
    private String smsurl;


    /**
     * 发送短信
     *
     * @param userip
     * @return
     */
    public GlobalResponse send(String userip, String serialNumber) {
        try {
            StringBuffer httpHead = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            httpHead.append("<SendSMSCodeRequest>");
            UUID uuid = UUID.randomUUID();
            httpHead.append("<MSGID>" + uuid.toString().replaceAll("-", "") + "</MSGID>");
            httpHead.append("<SystemTime>" + DateHelper.converToStringDate(new Date(), "yyyyMMddHHmmssSSS") + "</SystemTime>");
            httpHead.append("<Vesion>1.0</Vesion>");
            httpHead.append("<SourceID>" + smsSourceid + "</SourceID>");
            httpHead.append("<AppId>3</AppId>");
            httpHead.append("<FuncType>2</FuncType>");
            httpHead.append("<MobileNumber>" + serialNumber + "</MobileNumber>");
            httpHead.append("<UserIP>" + userip + "</UserIP>");
            httpHead.append("</SendSMSCodeRequest>");

            byte[] xmlbyte = httpHead.toString().getBytes("UTF-8");
            URL url = new URL(smsurl + "/UmcWeb/s?func=SendSMSCodeRequest");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(20 * 1000);
            conn.setDoOutput(true);//允许输出
            conn.setUseCaches(false);//不使用Cache
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");//维持长连接
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Length", String.valueOf(xmlbyte.length));
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
            outStream.write(xmlbyte);//发送xml数据
            outStream.flush();
            outStream.close();

            //解析返回来的xml消息体
            byte[] msgBody = null;
            //if (conn.getResponseCode() != 200) throw new RuntimeException("请求url失败");
            InputStream is = conn.getInputStream();//获取返回数据

            byte[] temp = new byte[1024];
            int n = 0;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((n = is.read(temp)) != -1) {
                bos.write(temp, 0, n);
            }
            msgBody = bos.toByteArray();
            bos.close();
            is.close();
            String returnXml = new String(msgBody, "UTF-8").trim();
            JSONObject jsonObject = xml2JSON(returnXml.getBytes("UTF-8"));
            // 解析xml
            JSONArray jsonArray = JSONObject.fromObject(jsonObject.get("Envelope")).getJSONArray("Body");
            jsonObject = JSONObject.fromObject(jsonArray.get(0));
            jsonObject = JSONObject.fromObject(jsonObject.getJSONArray("SendSMSCodeResponse").get(0));
            Object resultCode = jsonObject.get("ResultCode");
            String[] split = resultCode.toString().split("\"");
            if (Objects.equals(split[1], "0")) {
                return GlobalResponse.success("验证码发送成功");
            }else{
                log.error(returnXml);
            }
            conn.disconnect();
            return  GlobalResponse.fail(returnXml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  GlobalResponse.fail("验证码发送失败");
    }

    /**
     * 校验手机验证码是否正确
     *
     * @param serialNumber
     * @param verCode
     * @param ip
     * @return
     */
    public GlobalResponse checkSmsVerCode(String serialNumber, String verCode, String ip) {
        try {
            StringBuffer httpHead = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            httpHead.append("<CheckSMSCodeRequest>");
            UUID uuid = UUID.randomUUID();
            httpHead.append("<MSGID>" + uuid.toString().replaceAll("-", "") + "</MSGID>");
            httpHead.append("<SystemTime>" + DateHelper.converToStringDate(new Date(), "yyyyMMddHHmmssSSS") + "</SystemTime>");
            httpHead.append("<Vesion>1.0</Vesion>");
            httpHead.append("<SourceID>" + smsSourceid + "</SourceID>");
            httpHead.append("<AppId>3</AppId>");
            httpHead.append("<FuncType>2</FuncType>");
            httpHead.append("<MobileNumber>" + serialNumber + "</MobileNumber>");
            httpHead.append("<Smscode>" + verCode + "</Smscode>");
            httpHead.append("<UserIP>" + ip + "</UserIP>");
            httpHead.append("</CheckSMSCodeRequest>");

            byte[] xmlbyte = httpHead.toString().getBytes("UTF-8");
            URL url = new URL(smsurl + "/UmcWeb/s?func=CheckSMSCodeRequest");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(20 * 1000);
            conn.setDoOutput(true);//允许输出
            conn.setUseCaches(false);//不使用Cache
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");//维持长连接
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Length", String.valueOf(xmlbyte.length));
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
            outStream.write(xmlbyte);//发送xml数据
            outStream.flush();
            outStream.close();

            //解析返回来的xml消息体
            byte[] msgBody = null;
            //if (conn.getResponseCode() != 200) throw new RuntimeException("请求url失败");
            InputStream is = conn.getInputStream();//获取返回数据

            byte[] temp = new byte[1024];
            int n = 0;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((n = is.read(temp)) != -1) {
                bos.write(temp, 0, n);
            }
            msgBody = bos.toByteArray();
            bos.close();
            is.close();
            conn.disconnect();
            String returnXml = new String(msgBody, "UTF-8").trim();
            JSONObject jsonObject = xml2JSON(returnXml.getBytes("UTF-8"));
            // 解析xml
            JSONArray jsonArray = JSONObject.fromObject(jsonObject.get("Envelope")).getJSONArray("Body");
            jsonObject = JSONObject.fromObject(jsonArray.get(0));
            jsonObject = JSONObject.fromObject(jsonObject.getJSONArray("CheckSMSCodeResponse").get(0));
            Object resultCode = jsonObject.get("ResultCode");
            String[] split = resultCode.toString().split("\"");
            if (Objects.equals(split[1], "0")) {
                return GlobalResponse.success("验证成功");
            } else {
                log.error(returnXml);
                if (Objects.equals(split[1], "91")) {
                    return GlobalResponse.fail("验证码验证失败／或过期");
                }
                if (Objects.equals(split[1], "91")) {
                    return GlobalResponse.fail("验证码验证失败／或过期");
                }
                if (Objects.equals(split[1], "92")) {
                    return GlobalResponse.fail("手机号码非法");
                }
                if (Objects.equals(split[1], "25")) {
                    return GlobalResponse.fail("系统流量限制");
                }
                if (Objects.equals(split[1], "26")) {
                    return GlobalResponse.fail("同一用户认证过于频繁");
                }
                if (Objects.equals(split[1], "28")) {
                    return GlobalResponse.fail("与上次登陆地点不一致，两次登陆地点不一样");
                }
                if (Objects.equals(split[1], "98")) {
                    return GlobalResponse.fail("获取短信内容达到每天最大限制");
                }
                if (Objects.equals(split[1], "99")) {
                    return GlobalResponse.fail("系统错误");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return GlobalResponse.fail("验证失败");
    }

    public static JSONObject xml2JSON(byte[] xml) throws JDOMException, IOException {
        JSONObject json = new JSONObject();
        InputStream is = new ByteArrayInputStream(xml);
        SAXBuilder sb = new SAXBuilder();
        org.jdom2.Document doc = sb.build(is);
        Element root = doc.getRootElement();
        json.put(root.getName(), iterateElement(root));
        return json;
    }

    private static JSONObject iterateElement(Element element) {
        List node = element.getChildren();
        Element et = null;
        JSONObject obj = new JSONObject();
        List list = null;
        for (int i = 0; i < node.size(); i++) {
            list = new LinkedList();
            et = (Element) node.get(i);
            if (et.getTextTrim().equals("")) {
                if (et.getChildren().size() == 0)
                    continue;
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(iterateElement(et));
                obj.put(et.getName(), list);
            } else {
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(et.getTextTrim());
                obj.put(et.getName(), list);
            }
        }
        return obj;
    }
}
