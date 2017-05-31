package com.he.im.controller.user;

import com.he.im.controller.BaseController;
import com.he.im.exception.BusinessException;
import com.he.im.model.ModelResult;
import com.he.im.model.profire.OfUserDTO;
import com.he.im.model.profire.UserLoginVo;
import com.he.im.service.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by he on 2017/3/31.
 */
@Controller
@RequestMapping("/im/user")
public class UserController extends BaseController{

	private Log log = LogFactory.getLog(getClass());
	@Resource
	private UserService userService;


	@RequestMapping("/changePwdData")
	@ResponseBody
	public ModelResult changePwdData(OfUserDTO dto , HttpServletRequest request){
		ModelResult result = new ModelResult();
		try {
			UserLoginVo operator = getBackendOperator(request);
			dto.setId(operator.getUserId());

			userService.changePwdData(dto);
			result.setSuccess(true);
		} catch (Exception e) {
			log.error("update pwd error" , e);
			result.setSuccess(false);
			if(e instanceof BusinessException){
				result.setMessage(e.getMessage());
			}else{
				result.setMessage("update error");
			}
		}
		return result;
	}



	/**
	 * 获取  对话用户信息
	 * 没有当前用户 则新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getImInfo", method = RequestMethod.POST)
	@ResponseBody
	public ModelResult getImInfo(HttpServletRequest request) {
		String userName = request.getParameter("userName");
		return userService.getImInfo(userName);
	}





}
