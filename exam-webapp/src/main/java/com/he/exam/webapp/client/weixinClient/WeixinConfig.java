package com.he.exam.webapp.client.weixinClient;


/**
 * 
 * 微信接口配置
 * 
 * @author daizy
 *
 */
public class WeixinConfig {

	
	/**
	 * 获取access token
	 */
	public final static String WX_APP_GET_TOKEN ="https://api.weixin.qq.com/cgi-bin/token";
	
	public final static String WX_APP_JS_TICKET="https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	
	/**
	 * 获取用户基本信息（包括UnionID机制）
	 */
	public final static String WX_APP_GET_USER_URL = "https://api.weixin.qq.com/cgi-bin/user/info";
	
	/**
	 * 创建二维码ticket
	 */
	public final static String WX_APP_POST_ERWEICODE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
	
	/**
	 * 通过ticket换取二维码
	 */
	public final static String WX_APP_GET_ERWEITICKET_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
	
	
	/**
	 * 网页授权
	 * 通过code换取网页授权access_token
	 */
	public final static String WX_APP_GET_OAUTH_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	/**
	 * 网页授权
	 * 网页授权access_token
	 * 
	 * 刷新access_token
	 */
	public final static String WX_APP_GET_OAUTH_REFRESH_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
	
	
	/**
	 * 网页授权获取用户信息
	 */
	public final static String WX_APP_GET_OAUTH_USERINFO="https://api.weixin.qq.com/sns/userinfo";
	
	/**
	 * 验证网页授权的token是否有效
	 */
	public final static String WX_APP_GET_OAUTH_CHECK="https://api.weixin.qq.com/sns/auth";

}
