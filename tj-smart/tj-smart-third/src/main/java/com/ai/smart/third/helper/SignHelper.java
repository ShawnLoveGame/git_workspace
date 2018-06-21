package com.ai.smart.third.helper;

import com.ai.smart.common.helper.MD5Helper;
import com.ai.smart.common.helper.json.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
public class SignHelper {


    /**
     * @param params         参数信息
     * @param signKey        签名Key
     * @return 签名字符串
     */
    public static String createSign(Map<String, SortedMap<String,String>> params, String signKey, boolean ignoreSignType) {
        SortedMap<String, SortedMap<String,String>> sortedMap = new TreeMap<>(params);
        for (String key : sortedMap.keySet()) {
            SortedMap value = params.get(key);
            if (value != null && value.size() > 0 && ignoreSignType){
                if ("mac".equals(value.get("mac"))){
                    value.remove("mac");
                    sortedMap.put(key,value);
                }
            }
        }
        // md5处理
        String sjson = MD5Helper.MD5Encode(JsonHelper.toJson(sortedMap), "UTF-8", true);
        return createHmacSha256Sign(sjson, signKey);
    }

    private static String createHmacSha256Sign(String message, String key) {
        try {
            Mac sha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            sha256.init(secretKeySpec);
            byte[] bytes = sha256.doFinal(message.getBytes());
            return Hex.encodeHexString(bytes).toUpperCase();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }


}
