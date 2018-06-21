/*
 * Project: idmp-yzapi
 * 
 * File Created at 2018年4月9日
 * 
 * Copyright 2016 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
package com.ai.smart.bottom.helper;

import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Type MacUtils.java
 * @Desc mac签名示例
 * @author
 * @date 
 * @version 
 */
public class MacUtils {
    
 
    // HMAC算法
    private static final String HMAC_ALGORITHM = "HmacSHA256";

//    public static void main(String[] args) {
//
//        JSONObject header = new JSONObject();
//        header.put("", "");
//        //...组装header报文
//        JSONObject body = new JSONObject();
//        body.put("", "");
//        //...组装body报文
//        System.out.println(hmacsha256(SOURCEKEY, toJson(sort(header), sort(body))));
//    }
    
 
    //加密示例
    public static String hmacsha256(String secret, String data) {
        Mac mac = null;
        byte[] doFinal = null;
        try {
            mac = Mac.getInstance(HMAC_ALGORITHM);
            //先对排序后的字符串进行MD5
            byte[] dataBytes = DigestUtils.md5(data);
            //对sourcekey进行MD5,得到密钥
              SecretKey secretkey = new SecretKeySpec(DigestUtils.md5(secret), HMAC_ALGORITHM);
                mac.init(secretkey);
                //HmacSHA256加密
                doFinal = mac.doFinal(dataBytes);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                
            }
            String checksum = Hex.encodeHexString(doFinal).toLowerCase();
            return checksum;
        }
    
    //组装排序后的报文
    static String toJson(JSONObject jo1, JSONObject jo2) {
        JSONObject jo = new JSONObject();
        jo.put("body", jo1);
        jo.put("header", jo2);
        return jo.toString();
    }

    //JSONObject 排序
    public static JSONObject sort(JSONObject json) {
        Set<String> iteratorKeys = json.keySet();
        SortedMap<String, Object> map = new TreeMap<String, Object>();
        for (String str : iteratorKeys) {
            map.put(str, json.get(str));
        }
        Set<String> mapkeys = map.keySet();
        JSONObject json2 = new JSONObject();
        for (String s : mapkeys) {
            json2.put(s, map.get(s));
        }
        return json2;
    }

}
