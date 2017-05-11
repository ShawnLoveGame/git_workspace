package com.he.exam.webapp.service.weixinCenter;

import com.he.exam.webapp.client.HttpClient;
import com.he.exam.webapp.client.RedisEnum;
import com.he.exam.webapp.client.payClient.WXUtil;
import com.he.exam.webapp.client.weixinClient.WeixinConfig;
import com.he.exam.webapp.model.Result;
import com.he.exam.webapp.model.exam.ExamUser;
import com.he.exam.webapp.model.userCenter.param.UserParam;
import com.he.exam.webapp.model.weixinCenter.WXBaseModel;
import com.he.exam.webapp.model.weixinCenter.WXOauthTokenModel;
import com.he.exam.webapp.model.weixinCenter.WXUserInfoModel;
import com.he.exam.webapp.service.BaseService;
import com.he.exam.webapp.service.userCenter.CookiesService;
import com.he.exam.webapp.service.userCenter.UserInfoService;
import com.he.exam.webapp.utils.json.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("commonService")
public class CommonService extends BaseService{
	
	@Value("${qrcodePath}")
	private String qrcodePath;
	
	
	private  Log log = LogFactory.getLog(getClass());
	
	@Resource
	private CookiesService cookiesService;
	@Value("${mainDomain}")
	private String mainDomain;

	@Resource private UserInfoService userInfoService;
	
	/**
	 * 获取jssdk信息
	 * @param url
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result findJsSdk(String url, Long ...userId){
		Result result = new Result();
		try {
			Map<String, String> map = WXUtil.shaSign(getJsticket(),url);
			if(userId != null){
				String ss =  URLEncoder.encode(mainDomain+"/wxOauthServlet?id="+userId);
				String linkUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WX_APP_ID+"&redirect_uri="+ss+"&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
				result.addAttribute("linkUrl", linkUrl);
			}
			map.put("appId", WX_APP_ID);
			result.addAttribute("jssdk", map);
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取jsticket
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getJsticket() throws Exception {
		String ticket = redisUtils.get(RedisEnum.REDIS_WEIXIN_APP_JSTICKET_.getKey());
		if(StringUtils.isNotBlank(ticket)){
			return ticket;
		}
		String acessToken = getAccessToken();
		HashMap<String, String> pramas = new HashMap<String, String>();
		pramas.put("access_token", acessToken);
		pramas.put("TYPE", "jsapi");
		String result = HttpClient.doGet(WeixinConfig.WX_APP_JS_TICKET, pramas);
		Map<String, String> re = JsonUtils.fromJson(result, Map.class);
		String nToken = re.get("ticket");
		redisUtils.setex(RedisEnum.REDIS_WEIXIN_APP_JSTICKET_.getKey(), RedisEnum.REDIS_WEIXIN_APP_JSTICKET_.getTime(), nToken);
		return nToken;
	}
	
	
	/***
	 * 
	 * 获取普通微信的access_token
	 * 
	 **/
	@SuppressWarnings("unchecked")
	public String getAccessToken() throws Exception {
		String token = redisUtils.get(RedisEnum.REDIS_WEIXIN_APP_TOKEN_.getKey());
		if(StringUtils.isNotBlank(token)){
			return token;
		}
		HashMap<String, String> pramas = new HashMap<String, String>();
		pramas.put("grant_type", "client_credential");
		pramas.put("appid", WX_APP_ID);
		pramas.put("secret", WX_APP_SECRET);
		String result = HttpClient.doGet(WeixinConfig.WX_APP_GET_TOKEN, pramas);
		Map<String, String> re = JsonUtils.fromJson(result, Map.class);
		String nToken = re.get("access_token");
		if(StringUtils.isNotBlank(nToken)){
			redisUtils.setex(RedisEnum.REDIS_WEIXIN_APP_TOKEN_.getKey(), RedisEnum.REDIS_WEIXIN_APP_TOKEN_.getTime(), nToken);
		}
		return nToken;
	}
	
	/***
	 * 
	 * 获取微信的用户信息
	 * 
	 **/
	public WXUserInfoModel getWxUserInfo(String openid) throws Exception {
		//获取access_token
		String accessToken = getAccessToken();
		HashMap<String, String> pramas = new HashMap<String, String>();
		pramas.put("access_token", accessToken);
		pramas.put("openid", openid);
		pramas.put("lang", "zh_CN");
		String src = HttpClient.doGet(WeixinConfig.WX_APP_GET_USER_URL, pramas);
		WXUserInfoModel wx = JsonUtils.fromJson(src, WXUserInfoModel.class);
		return wx;
	}
	
	/***
	 * 
	 * 创建二维码ticket
	 * 
	 * 
	 * 永久二维码请求
	 * 
	 * 创建字符串形式的二维码参数
	 * 
	 **/
	@SuppressWarnings("unchecked")
	public String generateErweiCodeWithParam(String weddingId) throws Exception {
		//获取  access_token
		String accessToken = getAccessToken();
		String pramas = "{\"action_name\":\"QR_LIMIT_STR_SCENE\",\"action_info\":{\"scene\":{\"scene_str\":\""+weddingId+"\"}}}";
		String result = HttpClient.sendPost(WeixinConfig.WX_APP_POST_ERWEICODE_URL+accessToken, pramas);
		Map<String, String> re = JsonUtils.fromJson(result, Map.class);
		if(re.containsKey("errcode")){
			log.error("获取ticket异常："+JsonUtils.toJson(result));
			redisUtils.del(RedisEnum.REDIS_WEIXIN_APP_TOKEN_.getKey());
			generateErweiCodeWithParam(weddingId);
		}
		return result;
	}
	

	/**
	 * 静默授权获取用户openId
	 * @param request
	 */
	@SuppressWarnings("deprecation")
	public void getOauthOpenId(HttpServletRequest request,HttpServletResponse response,Long orderId,Long userId){
		try {
			String openId = redisUtils.get("EXAM-WEBAPP-USEROPEN-"+userId);
			if(StringUtils.isBlank(openId)){
				String code = request.getParameter("code");
				log.error("静默授权code=="+code);
				if(StringUtils.isNotBlank(code)){
					HashMap<String, String> pramas = new HashMap<String, String>();
					pramas.put("appid", WX_APP_ID);
					pramas.put("secret", WX_APP_SECRET);
					pramas.put("code", code);
					pramas.put("grant_type", "authorization_code");
					String result = HttpClient.doGet(WeixinConfig.WX_APP_GET_OAUTH_ACCESS_TOKEN, pramas);
					log.error(JsonUtils.toJson(result));
					WXOauthTokenModel wx = JsonUtils.fromJson(result, WXOauthTokenModel.class);
					if(wx != null && StringUtils.isNotBlank(wx.getOpenid())){
						redisUtils.set("EXAM-WEBAPP-USEROPEN-"+userId,wx.getOpenid());
					}
				}else{
					String p = URLEncoder.encode(mainDomain+"/pay/payCenter/"+orderId);
					String u = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx3a644cffa182b0b4&redirect_uri="+p+"&response_type=code&scope=snsapi_base&state=13#wechat_redirect";
					response.sendRedirect(u);
				}
			}
		} catch (Exception e){
			log.error("getOauthOpenId EXCEPTION",e);
		}
	}

	
	public void getWXOauthUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String code = request.getParameter("code");
		String openId = getOauthOpenId(code);
		WXUserInfoModel wx = this.getWxUserInfo(openId);
		if(wx != null){
		    UserParam up = new UserParam();
		    up.setHeadpic(wx.getHeadimgurl());
		    up.setNickname(wx.getNickname());
		    up.setOpenid(openId);
		    up.setRegType("1");
		    up.setSex(wx.getSex());
		    up.setUserName(wx.getNickname());
			log.error("已关注静默授权-->"+JsonUtils.toJson(up));
		    ExamUser user = userInfoService.loginForThird(up);
			String uuid = UUID.randomUUID().toString();
			user.setUuid(uuid);
			cookiesService.doCookie(user, response, request);
		}else{
			UserParam up = new UserParam();
		    up.setOpenid(openId);
		    up.setRegType("1");
			log.error("未关注静默授权-->"+JsonUtils.toJson(up));
			ExamUser user = userInfoService.loginForThird(up);
			String uuid = UUID.randomUUID().toString();
			user.setUuid(uuid);
			cookiesService.doCookie(user, response, request);
		}
	}
	
	
	/**
	 * 网页授权access_token
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public String getOauthAccessToken(String code) throws Exception{
		HashMap<String, String> pramas = new HashMap<String, String>();
		pramas.put("appid", WX_APP_ID);
		pramas.put("secret", WX_APP_SECRET);
		pramas.put("code", code);
		pramas.put("grant_type", "authorization_code");
		String result = HttpClient.doGet(WeixinConfig.WX_APP_GET_OAUTH_ACCESS_TOKEN, pramas);
		WXOauthTokenModel wx = JsonUtils.fromJson(result, WXOauthTokenModel.class);
		log.error("网页授权access_token-->"+JsonUtils.toJson(wx));
		return wx.getAccess_token();
	}
	
	public String getOauthOpenId(String code) throws Exception{
		HashMap<String, String> pramas = new HashMap<String, String>();
		pramas.put("appid", WX_APP_ID);
		pramas.put("secret", WX_APP_SECRET);
		pramas.put("code", code);
		pramas.put("grant_type", "authorization_code");
		String result = HttpClient.doGet(WeixinConfig.WX_APP_GET_OAUTH_ACCESS_TOKEN, pramas);
		WXOauthTokenModel wx = JsonUtils.fromJson(result, WXOauthTokenModel.class);
		log.error("网页授权access_token-->"+JsonUtils.toJson(wx));
		return wx.getOpenid();
	}
	
	/**
	 * 刷新网页授权refresh_token
	 * @param refresh_token
	 * @return
	 */
	public String getRefreshOauthAccessToken(String refresh_token) throws Exception{
		HashMap<String, String> pramas = new HashMap<String, String>();
		pramas.put("appid", WX_APP_ID);
		pramas.put("grant_type", "refresh_token");
		pramas.put("refresh_token", refresh_token);
		String result = HttpClient.doGet(WeixinConfig.WX_APP_GET_OAUTH_REFRESH_ACCESS_TOKEN, pramas);
		WXOauthTokenModel wx = JsonUtils.fromJson(result, WXOauthTokenModel.class);
		return wx.getAccess_token();
	}
	
	
	
	/**
	 * 获取用户的基本信息
	 * oauth授权
	 * @return
	 */
	public WXUserInfoModel getOauthUserInfo(String token) throws Exception{
		Map<String, String> pramas = new HashMap<String, String>();
		pramas.put("access_token", token);
		pramas.put("openid", WX_APP_ID);
		pramas.put("lang", "zh_CN");
		String src = HttpClient.doGet(WeixinConfig.WX_APP_GET_OAUTH_USERINFO, pramas);
		log.error("获取用户的基本信息-->"+src);
		WXUserInfoModel wx = JsonUtils.fromJson(src, WXUserInfoModel.class);
		return wx;
	}
	
	/**
	 * 验证网页授权的token是否失效
	 * @param token
	 * @return
	 */
	public boolean checkOauthToken(String token) throws Exception{
		HashMap<String, String> pramas = new HashMap<String, String>();
		pramas.put("access_token", token);
		pramas.put("openid", WX_APP_ID);
		String src = HttpClient.doGet(WeixinConfig.WX_APP_GET_OAUTH_CHECK, pramas);
		WXBaseModel wx= JsonUtils.fromJson(src, WXBaseModel.class);
		if(wx.getErrmsg().equals("ok")){
			return true;
		}
		return false;
	}

	
	/**
     * 验证签名
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public  boolean checkSignature(String signature, String timestamp, String nonce){
        String[] arr = new String[]{WX_APP_TOKEN, timestamp, nonce};
        // 将 token, timestamp, nonce 三个参数进行字典排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for(int i = 0; i < arr.length; i++){
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;
         
        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行 shal 加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()): false;
    }
     
    /**
     * 将字节数组转换为十六进制字符串
     * @param digest
     * @return
     */
    private  String byteToStr(byte[] digest) {
        String strDigest = "";
        for(int i = 0; i < digest.length; i++){
            strDigest += byteToHexStr(digest[i]);
        }
        return strDigest;
    }
     
    /**
     * 将字节转换为十六进制字符串
     * @param b
     * @return
     */
    private String byteToHexStr(byte b) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(b >>> 4) & 0X0F];
        tempArr[1] = Digit[b & 0X0F];
        String s = new String(tempArr);
        return s;
    }

	
}
