package com.he.exam.webapp.controller.weixinCenter;

import com.he.exam.webapp.exception.BusinessException;
import com.he.exam.webapp.model.Result;
import com.he.exam.webapp.service.weixinCenter.CommonService;
import com.he.exam.webapp.sso.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 微信控制器
 * 
 */
@Controller
@RequestMapping("/weixin")
public class WeixinController extends BaseController{
	
	@Resource
	private CommonService commonService;
	
	/**
	 * 获取token
	 * @return
	 */
	public Result getAccessToken(){
		Result result = new Result(false,"获取失败");
		try {
			String accessToken = commonService.getAccessToken();
			result.addAttribute("accessToken", accessToken);
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			BusinessException.throwMessage(e.getMessage());
		}
		return result;
	}
	
	
	
	

}
