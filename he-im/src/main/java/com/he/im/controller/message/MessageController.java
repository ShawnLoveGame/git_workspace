package com.he.im.controller.message;

import com.he.im.conn.ImConnection;
import com.he.im.controller.BaseController;
import com.he.im.model.ModelResult;
import com.he.im.model.profire.UserLoginVo;
import com.he.im.service.msg.MessageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by he on 2017/4/20.
 */
@Controller
@RequestMapping("/im/message")
public class MessageController extends BaseController{

    private Log log = LogFactory.getLog(getClass());

    @Resource private MessageService messageService;

    @Resource private ImConnection imConnection;
    /**
     * 消息界面
     * @return
     */
    @RequestMapping("/toMessagePage")
    public  String toMessagePage(HttpServletRequest request){
        UserLoginVo loginVo = getBackendOperator(request);
        request.setAttribute("userInfo" , loginVo);
        return "message/socket";
    }

    @RequestMapping("/toMessagePage2")
    public  String toMessagePage2(HttpServletRequest request){
        UserLoginVo loginVo = getBackendOperator(request);
        request.setAttribute("userInfo" , loginVo);
        return "message/message";
    }

    /**
     * 给某个用户发消息
     * @param content
     * @param toAcount
     * @return
     */
    @RequestMapping("/toSendMsg")
    @ResponseBody
    public ModelResult sendMsg(String content , String toAcount ,int type,  HttpServletRequest request){
        UserLoginVo backendOperator = getBackendOperator(request);

        return  messageService.sendMsg(backendOperator.getUserName().replaceAll("@" , "+40").replaceAll(" " , "-40") , content , toAcount , type,  request);
    }
    
    @RequestMapping(value="/getMsg",produces="application/json;charset=UTF-8")
    @ResponseBody
    public ModelResult getMsg(String from,String to){
    	return messageService.getMsg(from,to);
    }


    /**
     * 当前存在的聊天对象  默认获取最近的10个人
     * @return
     */
    @RequestMapping(value="/getCurrentChat",produces="application/json;charset=UTF-8")
    @ResponseBody
    public ModelResult getCurrentChat(HttpServletRequest request){
        UserLoginVo user = getBackendOperator(request);
        return messageService.getCurrentChat(user.getUserName());
    }


    @RequestMapping("/historyPage")
    public String historyPage(){
        return "message/historyPage";
    }

    @RequestMapping(value="/findHistoryMsg",produces="application/json;charset=UTF-8")
    @ResponseBody
    public ModelResult findHistoryMsg(String toAcount , HttpServletRequest request){
        return messageService.findHistoryMsg(toAcount);
    }


    /**
     * 查询历史消息
     * @param from
     * @param request
     * @return
     */
    @RequestMapping(value="/findHistoryMsgDetail",produces="application/json;charset=UTF-8")
    @ResponseBody
    public ModelResult findHistoryMsgDetail(String from ,HttpServletRequest request){
        UserLoginVo loginVo = getBackendOperator(request);
        return messageService.findHistoryMsgDetail(from , loginVo.getUserName().replaceAll("@" , "+40").replaceAll(" " , "-40"));
    }
}
