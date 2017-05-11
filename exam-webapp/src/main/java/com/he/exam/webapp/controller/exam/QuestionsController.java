package com.he.exam.webapp.controller.exam;

import com.he.exam.webapp.exception.BusinessException;
import com.he.exam.webapp.model.Result;
import com.he.exam.webapp.model.exam.Department;
import com.he.exam.webapp.model.exam.ExamUser;
import com.he.exam.webapp.service.exam.ExamService;
import com.he.exam.webapp.service.exam.ExamUserService;
import com.he.exam.webapp.sso.controller.BaseController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by he on 2017/3/29.
 */
@Controller
@RequestMapping("/exam")
public class QuestionsController extends BaseController{
    private Log log = LogFactory.getLog(getClass());

    @Resource
    private ExamService examService;
    @Resource private ExamUserService examUserService;

    /**
     * 开始答题 校验当天是否有试题
     * @param departmentId
     * @param request
     * @return
     */
    @RequestMapping("/toAnswerQuestion")
    @ResponseBody
    public Result toAnswerQuestion(Long departmentId , HttpServletRequest request){
        Result result = new Result();
        try {
//            UserLoginVo endUser = getEndUser(request);
//            examUserService.toAnswerQuestion(departmentId , endUser);
//            result.setSuccess(true);
            BusinessException.throwMessage("答题已结束");
        } catch (Exception e) {
            log.error("开始答题失败" , e);
            if(e instanceof BusinessException){
                result.setMessage(e.getMessage());
            }else{
                result.setMessage("答题还未开始");
            }
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 答题列表页
     * @param model
     * @return
     */
    @RequestMapping("/toquestionPage")
    public String intoQuestionPage(Model model){
        //查询试卷的考题
        Result result = examService.findExamQuestionList();
        result.rendering(model);
        return "userCenter/questionPage";
    }

    @RequestMapping("/answerToSubmit")
    @ResponseBody
    public Result answerToSubmit(String jsonStr , HttpServletRequest request){
        Result result = new Result();
        try {
//            Long endUserId = getEndUserId();
            Long endUserId = getEndUserId();
            examService.adduserAnswerToSubmit(jsonStr , endUserId);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("提交失败" , e);
            result.setSuccess(false);
            if(e instanceof BusinessException){
                result.setMessage(e.getMessage());
            }else{
                result.setMessage("提交失败");
            }
        }
        return result;
    }

    @RequestMapping("/userCenter")
    public String userCenter(Model model , HttpServletRequest request){
        Long userId = getEndUserId();
        ExamUser endUser = examUserService.findUserInfoById(userId);
        model.addAttribute("user" , endUser);
        return "userCenter/userCenter";
    }

    /**
     * 获取个人中心数据
     * @param request
     * @return
     */
    @RequestMapping("/getUserCenterData")
    @ResponseBody
    public Result getUserCenterData(HttpServletRequest request){
        Result result = new Result();
        try {
            Long userId = getEndUserId();
            Map<String,Object>  centerData = examUserService.getUserCenterData(userId);
            result.addAttribute("resultData" , centerData);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("查询个人中心数据异常" , e);
        }
        return result;
    }

    @RequestMapping("/findGroupDepartmentData")
    @ResponseBody
    public Result findGroupDepartmentData(Long groupId){
        Result result = new Result();
        try {
            List<Department> centerData = examUserService.findGroupDepartmentData(groupId);
            result.addAttribute("resultData" , centerData);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("查询个人中心数据异常" , e);
        }
        return result;
    }
}
