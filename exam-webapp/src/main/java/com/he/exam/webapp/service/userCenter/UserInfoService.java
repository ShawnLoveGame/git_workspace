package com.he.exam.webapp.service.userCenter;

import com.he.exam.webapp.client.SoaResult;
import com.he.exam.webapp.dao.exam.ExamUserMapper;
import com.he.exam.webapp.model.Result;
import com.he.exam.webapp.model.exam.ExamUser;
import com.he.exam.webapp.model.exam.ExamUserExample;
import com.he.exam.webapp.model.userCenter.param.UserParam;
import com.he.exam.webapp.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userInfoService")
public class UserInfoService extends BaseService{
	
	private static Logger log = LoggerFactory.getLogger(UserInfoService.class);
	
	@Resource
	private LoginService loginService;
	@Resource
	private CookiesService cookiesService;
	
	
	public SoaResult findUserInfoByDTOJson(String json){
		return null;
	}

	@Resource private ExamUserMapper examUserMapper;
	
	
	/**
	 * 获取用户信息
	 * @param userId
	 * @return
	 */
	public Result getUserInfo(Long userId){
		Result result = new Result();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", userId);
			SoaResult sr = new SoaResult();
			result.setSuccess(sr.isSuccess());
			result.setMessage(sr.getMessage());
			return result;
		} catch (Exception e) {
			log.error("获取用户信息异常",e);
			result.setSuccess(false);
			return result;
		}
	}


	public ExamUser loginForThird(UserParam up) {

		ExamUserExample example = new ExamUserExample();
		ExamUserExample.Criteria criteria = example.createCriteria();
		criteria.andOpenidEqualTo(up.getOpenid());
		List<ExamUser> examUsers = examUserMapper.selectByExample(example);
		if(examUsers.size() == 0){
			ExamUser user = new ExamUser();
			user.setHeadPic(up.getHeadpic());
			user.setNickName(up.getNickname());
			user.setOpenid(up.getOpenid());
			user.setSex(up.getSex());
			user.setUserName(up.getUserName());
			examUserMapper.insert(user);
			return user;
		}else{
			ExamUser user = examUsers.get(0);
			user.setHeadPic(up.getHeadpic());
			user.setNickName(up.getNickname());
			user.setOpenid(up.getOpenid());
			user.setSex(up.getSex());
			user.setUserName(up.getUserName());
			examUserMapper.updateByPrimaryKeySelective(user);
			return examUsers.get(0);
		}
	}
}
