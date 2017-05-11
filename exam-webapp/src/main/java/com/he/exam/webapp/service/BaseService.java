package com.he.exam.webapp.service;

import com.he.exam.webapp.utils.shared.RedisUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;


public class BaseService {
	
	@Resource
	public RedisUtils redisUtils;

	@Value("${wxAppId}")
	public String WX_APP_ID;
	@Value("${wxAppSecret}")
	public String WX_APP_SECRET;
	@Value("${cookiesDomain}")
	public String cookiesDomain;
	@Value("${wxAppToken}")
	public String WX_APP_TOKEN;
	
	@Value("${mainDomain}")
	public String mainDomain;
	

	@Value("${wxKey}")
	public String wxKey;

}
