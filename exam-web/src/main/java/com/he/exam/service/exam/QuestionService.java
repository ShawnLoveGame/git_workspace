package com.he.exam.service.exam;

import com.he.exam.dao.exam.QuestionLibraryMapper;
import com.he.exam.dao.exam.QuestionsMapper;
import com.he.exam.exception.AlikAssert;
import com.he.exam.exception.BusinessException;
import com.he.exam.model.ModelResult;
import com.he.exam.model.exam.*;
import com.he.exam.model.exam.dto.QuestionLibraryDTO;
import com.he.exam.model.exam.dto.QuestionsDTO;
import com.he.exam.util.CommonUtil;
import com.he.exam.util.Pagination;
import com.he.exam.util.PaginationUtil;
import com.he.exam.util.StringUtils;
import com.he.exam.util.json.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by he on 2017/3/24.
 */
@Service("questionService")
public class QuestionService {

    private Log log = LogFactory.getLog(getClass());

    @Resource
    private QuestionLibraryMapper questionLibraryMapper;
    @Resource
    private QuestionsMapper questionsMapper;

    public Pagination<QuestionLibrary> findLibraryWithPg(QuestionLibraryDTO dto) {

        Pagination<QuestionLibrary> pg = new Pagination<QuestionLibrary>();
        int count = questionLibraryMapper.countFindLibraryWithPg(dto);
        pg.setTotal(count);
        if (count == 0) {
            pg.setRows(new ArrayList<QuestionLibrary>());
            return pg;
        }
        List<QuestionLibrary> list = questionLibraryMapper.findLibraryWithPg(dto);
        pg.setRows(list);
        return pg;
    }


    public List<QuestionLibrary> findAllLibraryList() {

        QuestionLibraryExample example = new QuestionLibraryExample();
        QuestionLibraryExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        return questionLibraryMapper.selectByExample(example);
    }

    public ModelResult saveLibraryInfo(QuestionLibrary library) {
        ModelResult result = new ModelResult(true);

        library.setUpdateTime(new Date());
        if (library.getId() == null) {
            library.setCreateTime(new Date());
            library.setIsDeleted(0);
            library.setLibraryStatus(0);
            questionLibraryMapper.insert(library);
        } else {
            questionLibraryMapper.updateByPrimaryKeySelective(library);
        }
        return result;
    }


    public Pagination<Questions> findQuestionWithPg(QuestionsDTO dto) {

        Pagination<Questions> pg = new Pagination<Questions>();
        QuestionsExample questionsExample = new QuestionsExample();

        questionsExample.setStart(dto.getStart());
        questionsExample.setRows(dto.getRows());
        questionsExample.setNeedCount(true);
        questionsExample.setOrderByClause("answer_time desc");
        QuestionsExample.Criteria criteria1 = questionsExample.createCriteria();

        if (org.apache.commons.lang3.StringUtils.isNotBlank(dto.getQuestionContent())) {
            criteria1.andQuestionContentLike(dto.getQuestionContent());
        }
        if (dto.getQuestionType() != null) {
            criteria1.andQuestionTypeEqualTo(dto.getQuestionType());
        }
        if (dto.getStatus() != null) {
            criteria1.andStatusEqualTo(dto.getStatus());
        }
        criteria1.andIsDeletedEqualTo(0);
        int count = questionsMapper.countByExample(questionsExample);
        pg.setTotal(count);
        if (count == 0) {
            pg.setRows(new ArrayList<Questions>());
            return pg;
        }
        List<Questions> list = questionsMapper.selectByExample(questionsExample);
        List<Long> libIds = CommonUtil.getValueList(list, "libraryId");

        pg.setRows(list);

        return pg;
    }

    public Questions findQuestionById(Long id) {
        Questions questions = questionsMapper.selectByPrimaryKey(id);
        if (questions != null) {
            if (StringUtils.isNotBlank(questions.getQuestionOptions())) {
                List<QuestionOption> optionList = JsonUtils.fromJson(questions.getQuestionOptions(), JsonUtils.constructParametricType(List.class, QuestionOption.class));
                questions.setOptionList(optionList);
            }
        }
        return questions;
    }

    public void saveQuestionInfo(Questions questions) {
        AlikAssert.isNotBlank(questions.getQuestionContent(), "题干不能为空");
        AlikAssert.isNotNull(questions.getOptionList(), "试题选项不能为空");
        AlikAssert.isNotNull(questions.getAnswerTime(), "答题时间不能为空");
        List<QuestionOption> optionList = questions.getOptionList();
        if(questions.getQuestionType().intValue() != 3){
            if (optionList.size() == 0) {
                BusinessException.throwMessageWithCode("-1", "选项不能为空");
            }
            StringBuilder sb = new StringBuilder();
            for (QuestionOption option : optionList) {
                if (option.getChecked()) {
                    sb.append(option.getKey()).append(",");
                }
            }
            if (sb.length() == 0) {
                BusinessException.throwMessageWithCode("-1", "选项答案不能为空");
            }
            questions.setAnswers(sb.substring(0, sb.length() - 1));
        }
        questions.setQuestionOptions(JsonUtils.toJson(optionList));
        questions.setOpTime(new Date());
        if (questions.getId() == null) {
            questions.setIsDeleted(0);
            questionsMapper.insert(questions);
        } else {
            questionsMapper.updateByPrimaryKeySelective(questions);
        }

    }

    public ModelResult updateQuestionsInfo(Questions questions) {

        questionsMapper.updateByPrimaryKeySelective(questions);
        return new ModelResult(true);
    }

    public void importQuestionToSave(MultipartFile myfile, HttpServletRequest request) {
        Sheet sheet = null;
        try {
            try {
                // OFFice 2007 2010
                XSSFWorkbook xwb = new XSSFWorkbook(myfile.getInputStream());
                sheet = xwb.getSheetAt(0);
            } catch (Exception e) {
                log.debug("WPS 老版本的OFFice====");
                // WPS 老版本的OFFice
                HSSFWorkbook hwb = new HSSFWorkbook(myfile.getInputStream());
                sheet = hwb.getSheetAt(0);
            }
            //解析excel表格
            int countRows = sheet.getPhysicalNumberOfRows();
            List<Questions> questionsList = new ArrayList<Questions>();
            for (int i = 1; i <= countRows; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                Cell cell0 = row.getCell(0);//时间
                Cell cell1 = row.getCell(1);//类型
                Cell cell2 = row.getCell(2);//题干
                Cell cell3 = row.getCell(3);//选项A
                Cell cell4 = row.getCell(4);
                Cell cell5 = row.getCell(5);//选项D
                Cell cell6 = row.getCell(6);//选项D
                Cell cell7 = row.getCell(7);//答案
                if (cell0 == null || cell1 == null || cell2 == null ||  cell7 == null ) {
                    continue;
                }
//                if(cell2== null &&  cell3 == null && cell4 == null &&  cell5 == null){
//                    continue;
//                }
                //设置excel 为文本格式

                cell0.setCellType(Cell.CELL_TYPE_STRING);
                cell1.setCellType(Cell.CELL_TYPE_STRING);
                cell2.setCellType(Cell.CELL_TYPE_STRING);
                if(cell3 != null ){
                    cell3.setCellType(Cell.CELL_TYPE_STRING);
                }
                if(cell4 != null ){
                    cell4.setCellType(Cell.CELL_TYPE_STRING);
                }
                if(cell5 != null ){
                    cell5.setCellType(Cell.CELL_TYPE_STRING);
                }
                if(cell5 != null ){
                    cell6.setCellType(Cell.CELL_TYPE_STRING);
                }
                cell7.setCellType(Cell.CELL_TYPE_STRING);

                Questions questions = new Questions();
                String answertime = StringUtils.trim(cell0.getStringCellValue());
                if(org.apache.commons.lang3.StringUtils.isBlank(answertime)){
                    //设置成当天
                    answertime = new SimpleDateFormat("yyyyMMdd").format(new Date());
                }
                questions.setAnswerTime(answertime);
                String typename = StringUtils.trim(cell1.getStringCellValue());
                if (StringUtils.isBlank(typename)) {
                    questions.setQuestionType(2);
                } else {
                    if ("单选".equals(typename)) {
                        questions.setQuestionType(1);
                    } else if("多选".equals(typename)){
                        questions.setQuestionType(2);
                    }else{
                        questions.setQuestionType(3);
                    }
                }
                String content = StringUtils.trim(cell2.getStringCellValue());
                if (StringUtils.isBlank(content)) {
                    continue;
                } else {
                    questions.setQuestionContent(content);
                }
                List<QuestionOption> optionList = new ArrayList<QuestionOption>();
                //根据类型判断
                if(questions.getQuestionType().intValue() != 3){
                    if( cell3 == null && cell4 == null  && cell5 == null  && cell6 == null ){
                        continue;
                    }
                }
                String optionA = "";
                String optionB = "";
                String optionC = "";
                String optionD = "";
                if(cell3 != null ){
                    optionA = StringUtils.trim(cell3.getStringCellValue());
                }
                if(cell4 != null ){
                    optionB = StringUtils.trim(cell4.getStringCellValue());
                }
                if(cell5 != null ){
                    optionC = StringUtils.trim(cell5.getStringCellValue());
                }
                if(cell6 != null ){
                    optionD = StringUtils.trim(cell6.getStringCellValue());
                }
                String answers = StringUtils.trim(cell7.getStringCellValue());
                if (StringUtils.isBlank(answers)) {
                    continue;
                }
                answers = answers.toUpperCase();
                if(questions.getQuestionType().intValue() != 3){
                    StringBuilder sb = new StringBuilder();
                    for(int j = 0 ; j< answers.length() ; j++ ){
                        sb.append(answers.charAt(j)).append(",");
                    }
                    if(sb.length() == 0 ){
                        continue;
                    }
                    questions.setAnswers(sb.substring(0 , sb.length() - 1));
                    if (StringUtils.isBlank(optionA) && StringUtils.isBlank(optionB) && StringUtils.isBlank(optionC) && StringUtils.isBlank(optionD)) {
                        continue;
                    }
                    if (StringUtils.isNotBlank(optionA)) {
                        QuestionOption option = new QuestionOption();
                        option.setKey("A");
                        option.setContent(optionA);
                        if (answers.contains("A")) {
                            option.setChecked(true);
                        }
                        optionList.add(option);
                    }

                    if (StringUtils.isNotBlank(optionB)) {
                        QuestionOption option = new QuestionOption();
                        option.setKey("B");
                        option.setContent(optionB);
                        if (answers.contains("B")) {
                            option.setChecked(true);
                        }
                        optionList.add(option);
                    }

                    if (StringUtils.isNotBlank(optionC)) {
                        QuestionOption option = new QuestionOption();
                        option.setKey("C");
                        option.setContent(optionC);
                        if (answers.contains("C")) {
                            option.setChecked(true);
                        }
                        optionList.add(option);
                    }

                    if (StringUtils.isNotBlank(optionD)) {
                        QuestionOption option = new QuestionOption();
                        option.setKey("D");
                        option.setContent(optionD);
                        if (answers.contains("D")) {
                            option.setChecked(true);
                        }
                        optionList.add(option);
                    }
                }else{
                    if("正确".equals(answers)){
                        questions.setAnswers("Y");
                    }else{
                        questions.setAnswers("N");
                    }
                }
                questions.setIsDeleted(0);
                questions.setQuestionOptions(JsonUtils.toJson(optionList));
                questions.setOpTime(new Date());
                questions.setStatus(0);
                questionsList.add(questions);
            }

            if (questionsList.size() > 0) {
                List<List<Questions>> split = PaginationUtil.split(questionsList, 50);
                for (List<Questions> qlist : split) {
                    questionsMapper.batchInsertQuestion(qlist);
                }
            }
        } catch (IOException e) {
            log.error("导入失败");
        }
    }

    public ModelResult batchDeleteQuestion(List<Long> ids) {

        questionsMapper.batchDeleteQuestion(ids);
        return new ModelResult(true);
    }
}
