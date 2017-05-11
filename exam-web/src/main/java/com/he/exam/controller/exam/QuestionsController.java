package com.he.exam.controller.exam;

import com.he.exam.controller.BaseController;
import com.he.exam.exception.BusinessException;
import com.he.exam.framework.aop.ArchivesLog;
import com.he.exam.model.ModelResult;
import com.he.exam.model.exam.QuestionLibrary;
import com.he.exam.model.exam.Questions;
import com.he.exam.model.exam.dto.QuestionsDTO;
import com.he.exam.service.exam.QuestionService;
import com.he.exam.util.Pagination;
import com.he.exam.util.json.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by he on 2017/3/24.
 */
@Controller
@RequestMapping("/exam")
public class QuestionsController extends BaseController {


    @Resource
    private QuestionService questionService;


    @RequestMapping("/intiQuestionPage")
    public String intiQuestionPage(HttpServletRequest request) {
        return "exam/question";
    }

    @RequestMapping("/findQuestionWithPg")
    @ResponseBody
    public Pagination<Questions> findQuestionWithPg(QuestionsDTO dto) {

        return questionService.findQuestionWithPg(dto);

    }

    @RequestMapping("/findQuestionById")
    @ResponseBody
    public Questions findQuestionById(Long id) {
        return questionService.findQuestionById(id);
    }


    @RequestMapping("/saveQuestionInfo")
    @ResponseBody
    @ArchivesLog(operationType = "save", operationName = "保存试题")
    public ModelResult saveQuestionInfo(String jsonStr) {
        Questions questions = JsonUtils.fromJson(jsonStr, Questions.class);
        ModelResult result = new ModelResult();
        try {
            questionService.saveQuestionInfo(questions);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("保存试题异常", e);
            if (e instanceof BusinessException) {
                result.setMessage(e.getMessage());
            } else {
                result.setMessage("保存异常");
            }
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping("/updateQuestionsInfo")
    @ResponseBody
    @ArchivesLog(operationType = "update", operationName = "修改试题内容")
    public ModelResult updateQuestionsInfo(Questions questions) {
        return questionService.updateQuestionsInfo(questions);
    }


    @RequestMapping("/importQuestionPage")
    public String importQuestionPage(Model model) {
        List<QuestionLibrary> libraryList = questionService.findAllLibraryList();
        model.addAttribute("libraryList", libraryList);
        return "exam/importQuestionPage";
    }

    @RequestMapping("/importQuestionToSave")
    public ModelAndView importQuestionToSave(MultipartFile myfile, HttpServletRequest request) {
        questionService.importQuestionToSave(myfile, request);

        return new ModelAndView("redirect:/exam/intiQuestionPage");
    }

    @RequestMapping("/batchDeleteQuestion")
    @ResponseBody
    @ArchivesLog(operationType = "delete", operationName = "批量删除试题")
    public ModelResult batchDeleteQuestion(String idStr) {
        List<Long> ids = JsonUtils.fromJson(idStr, List.class, Long.class);
        return questionService.batchDeleteQuestion(ids);
    }
}
