package com.he.exam.controller.exam;

import com.he.exam.controller.BaseController;
import com.he.exam.framework.aop.ArchivesLog;
import com.he.exam.model.ModelResult;
import com.he.exam.model.exam.QuestionLibrary;
import com.he.exam.model.exam.dto.QuestionLibraryDTO;
import com.he.exam.service.exam.QuestionService;
import com.he.exam.util.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by he on 2017/3/23.
 */
@Controller
@RequestMapping("/exam")
public class QuestionsLibrayController extends BaseController {


    @Resource private QuestionService questionService;

    @RequestMapping("/intoLibraryPage")
    public String intoLibraryPage(){
        return "exam/library";
    }


    @RequestMapping("/findLibraryWithPg")
    @ResponseBody
    public Pagination<QuestionLibrary> findLibraryWithPg(QuestionLibraryDTO dto){

        return questionService.findLibraryWithPg(dto);

    }

    @RequestMapping("/saveLibraryInfo")
    @ResponseBody
    @ArchivesLog(operationType="save" , operationName = "题库保存")
    public ModelResult saveLibraryInfo(QuestionLibrary library){

        return questionService.saveLibraryInfo(library);

    }


    @RequestMapping("/findAllLibraryList")
    @ResponseBody
    public List<QuestionLibrary> findAllLibraryList(){
        return questionService.findAllLibraryList();

    }
}
