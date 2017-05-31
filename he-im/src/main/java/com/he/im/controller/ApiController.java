package com.he.im.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.he.im.model.ModelResult;
import com.he.im.service.msg.MessageService;
import com.he.im.service.user.UserService;

@Controller
@RequestMapping("/api")
public class ApiController {
	
	@Resource
    private UserService userService;
	@Resource
	private MessageService messageService;
	
	@RequestMapping("/openImHtml")
	public String intoTest(){
		return "api/imHtml";
	}
	
	@RequestMapping(value="/openIm")
	@ResponseBody
	public String openIm(String userName,HttpServletRequest request,HttpServletResponse response){
		// 设置跨域请求
		String jsonpCallback = request.getParameter("jsonpCallback");
		ModelResult mr = userService.loginForApi(userName);
		return jsonpCallback+"("+mr+")";
	}
	
	
	 /**
     * 给某个用户发消息
     * @return
     */
    @RequestMapping(value="/toSendMsg")
    @ResponseBody
    public String sendMsg(String content ,String fromName, String toName ,int type,HttpServletRequest request,HttpServletResponse response){
    	// 设置跨域请求
    	String jsonpCallback = request.getParameter("jsonpCallback");
    	ModelResult mr = messageService.sendMsg(fromName , content , toName , type,  null);
    	return jsonpCallback+"("+mr+")";
    }
    
    /**
     * 获取当前消息
     * @return
     */
    @RequestMapping(value="/getMsg",produces="application/json;charset=UTF-8")
    @ResponseBody
    public String getMsg(String fromName,String toName,HttpServletRequest request,HttpServletResponse response){
    	// 设置跨域请求
    	String jsonpCallback = request.getParameter("jsonpCallback");
    	ModelResult mr = messageService.getMsg(fromName,toName);
    	return jsonpCallback+"("+mr+")";
    }

}
