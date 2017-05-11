package com.he.exam.webapp.client.payClient;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class WXUtil {
	
	public static String getNonceStr() {
		Random random = new Random();
		return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "utf-8");
	}

	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	
	/**
	 * 参数签名
	 * @param sArray
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String sign(Map<String, String> sArray,String key){
		StringBuffer sb = new StringBuffer();
		Set es  = sArray.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if(null != v && !"".equals(v)
					&& !"sign".equals(k)
					&& !"key".equals(k)){
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key="+key);
		String params = sb.toString();
		String appsign = MD5Util.MD5Encode(params, "utf-8").toUpperCase();
		System.out.println("签名参数===="+appsign);
		return appsign;
	}
	
	/**
	 * 参数签名
	 * @param sArray
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String MD5JsSDKsign(Map<String, String> sArray,String key){
		StringBuffer sb = new StringBuffer();
		Set es  = sArray.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if(null != v && !"".equals(v)
					&& !"sign".equals(k)
					&& !"key".equals(k)){
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key="+key);
		String params = sb.toString();
		System.out.println("参与签名的====="+sb);
		String appsign = MD5Util.MD5Encode(params, "utf-8").toUpperCase();
		System.out.println("签名参数===="+appsign);
		return appsign;
	}
	
	
	
	/**
	 * 参数签名
	 * @return
	 */
	public static Map<String, String> shaSign(String jsapi_ticket, String url){
		 Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = WXUtil.getNonceStr();
        String timestamp = WXUtil.getTimeStamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
		return ret;
	}
	
	private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
	
	/**
	 * map 转xml
	 * @param sArray
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String transXML(Map<String, String> sArray,String sign){
		StringBuffer sb = new StringBuffer("<xml>");
		Set es = sArray.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(k) 
					|| "body".equalsIgnoreCase(k) 
					|| "sign".equalsIgnoreCase(k) ) {
				sb.append("<"+k+">"+v+"</"+k+">");
			}else{
				sb.append("<"+k+">"+v+"</"+k+">");
			}
		}
		sb.append("<sign>"+sign+"</sign>");
		sb.append("</xml>");
		System.out.println("weixin参数===="+sb.toString());
		return sb.toString();
	}
	
	/**
	 * xml 转移 map
	 * @param xmlDoc
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> xmlElements(String xmlDoc) {
        StringReader read = new StringReader(xmlDoc);
        InputSource source = new InputSource(read);
        SAXBuilder sb = new SAXBuilder();
        Map<String, String> xmlMap = new TreeMap<String, String>();
        try {
            //通过输入源构造一个Document
            Document doc = sb.build(source);
            //取的根元素
            Element root = doc.getRootElement();
            //得到根元素所有子元素的集合
            List jiedian = root.getChildren();
            Element et = null;
            for(int i=0;i<jiedian.size();i++){
                et = (Element) jiedian.get(i);
                xmlMap.put(et.getName(), et.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlMap;
    }
	
	public static Map<String, String> parseXml(HttpServletRequest request) {
		InputStream inputStream;
		String rec = "";
		try {
			inputStream = request.getInputStream();
			rec =InputStreamTOString(inputStream,"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return xmlElements(rec);
	}
	
	/**
	 * InputStream转换成String
	 * 注意:流关闭需要自行处理
	 * @param in
	 * @param encoding 编码
	 * @return String
	 * @throws Exception
	 */
	public static String InputStreamTOString(InputStream in,String encoding) throws IOException{  
        return new String(InputStreamTOByte(in),encoding);
        
    }
	
	/**
	 * InputStream转换成Byte
	 * 注意:流关闭需要自行处理
	 * @param in
	 * @return byte
	 * @throws Exception
	 */
	public static byte[] InputStreamTOByte(InputStream in) throws IOException{  
		int BUFFER_SIZE = 4096;  
		ByteArrayOutputStream outStream = new ByteArrayOutputStream(); 
        byte[] data = new byte[BUFFER_SIZE];  
        int count = -1;  
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)  
            outStream.write(data, 0, count);  
        data = null;  
        byte[] outByte = outStream.toByteArray();
        outStream.close();
        return outByte;  
    } 
	
	
	/** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=\"" + value+"\"";
            } else {
                prestr = prestr + key + "=\"" + value + "\"&";
            }
        }
        return prestr;
    }
}
