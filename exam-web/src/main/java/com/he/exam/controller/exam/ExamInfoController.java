package com.he.exam.controller.exam;

import com.he.exam.controller.BaseController;
import com.he.exam.exception.BusinessException;
import com.he.exam.framework.aop.ArchivesLog;
import com.he.exam.model.ModelResult;
import com.he.exam.model.exam.ExamInfo;
import com.he.exam.model.exam.dto.ExamInfoDTO;
import com.he.exam.service.exam.ExamInfoService;
import com.he.exam.util.Pagination;
import com.he.exam.util.json.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by he on 2017/3/28.
 */
@Controller
@RequestMapping("/exam")
public class ExamInfoController extends BaseController {


    @Resource
    private ExamInfoService examInfoService;

    @RequestMapping("/intoExamPage")
    public String intoExamPage() {

        return "exam/examPage";
    }

    @RequestMapping("/findExamInfoWithPg")
    @ResponseBody
    public Pagination<ExamInfo> findExamInfoWithPg(ExamInfoDTO examInfoDTO) {

        return examInfoService.findExamInfoWithPg(examInfoDTO);
    }


    @RequestMapping("/saveExamInfo")
    @ResponseBody
    @ArchivesLog(operationType = "save", operationName = "保存试卷")
    public ModelResult saveExamInfo(ExamInfo examInfo) {
        return examInfoService.saveExamInfo(examInfo);
    }

    @RequestMapping("/findExamInfoById")
    @ResponseBody
    public ExamInfo findExamInfoById(Long id) {
        return examInfoService.findExamInfoById(id);
    }


    @RequestMapping("/saveEditExamInfo")
    @ResponseBody
    @ArchivesLog(operationType = "save", operationName = "新增编辑试卷")
    public ModelResult saveEditExamInfo(String jsonStr) {
        ModelResult result = new ModelResult();
        ExamInfo examInfo = JsonUtils.fromJson(jsonStr, ExamInfo.class);
        try {
            examInfoService.saveEditExamInfo(examInfo);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("保存试题失败", e);
            result.setSuccess(false);
            if (e instanceof BusinessException) {
                result.setMessage(e.getMessage());
            } else {
                result.setMessage("保存试题失败");
            }
        }
        return result;
    }
}
