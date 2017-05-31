package com.he.im.controller.user;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;
import com.he.im.service.user.UserService;

/**
 * 卖家（商家）用户相关操作接口
 * @author Hybo
 *
 */
@Controller
public class SellerController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/seller/registerOne")
	@ResponseBody
	public String registerOneSeller(HttpServletRequest request,HttpServletResponse response){
		String result = null;
		try {
			String pidStr = request.getParameter("pid");
			String shopnameStr = request.getParameter("shopname");
			String signStr = request.getParameter("sign");
			
			TreeMap<String, String> param = new TreeMap<>();
			param.put("sign", signStr);
			param.put("shopname", shopnameStr);
			param.put("pid", pidStr);
			
			Map<String, Object> addOneSeller = this.userService.addOneSeller(param);
			
			result = JSONUtils.toJSONString(addOneSeller);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
