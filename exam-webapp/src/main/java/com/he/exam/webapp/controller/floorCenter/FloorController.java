package com.he.exam.webapp.controller.floorCenter;

import com.he.exam.webapp.model.exam.ExamUser;
import com.he.exam.webapp.model.exam.UserGroup;
import com.he.exam.webapp.model.userCenter.UserLoginVo;
import com.he.exam.webapp.service.exam.ExamService;
import com.he.exam.webapp.service.exam.ExamUserService;
import com.he.exam.webapp.sso.controller.BaseController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * 
 * 首页控制器
 * 
 * @author daizy
 *
 */
@Controller
@RequestMapping("/floor/")
public class FloorController extends BaseController{

	private Log log = LogFactory.getLog(getClass());

	@Resource private ExamService examService;
	@Resource private ExamUserService examUserService;
	/**
	 * 初始化首页
	 * 
	 * @return
	 */
	@RequestMapping("toIndex")
	public ModelAndView toIndex(Model model , HttpServletRequest request){
		UserLoginVo endUser = getEndUser(request);
		//判断当前有没有答过题
		Boolean flag = examUserService.checkCurrentUserHasAnswer(endUser.getUserId());
		if(flag){
			return new ModelAndView("redirect:/exam/userCenter");
		}else{
			//获取所有的Coo
			List<UserGroup> groups = examService.findGroups();
			model.addAttribute("groupList", groups);
			//获取用户所在的单位
			ExamUser examUser = examUserService.findUserInfoById(endUser.getUserId());
			model.addAttribute("user", examUser);
			return new ModelAndView("/floor/index");
		}
	}


}
