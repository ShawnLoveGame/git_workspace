package com.he.exam.webapp.service.exam;

import com.he.exam.webapp.dao.exam.*;
import com.he.exam.webapp.exception.BusinessException;
import com.he.exam.webapp.model.Result;
import com.he.exam.webapp.model.exam.*;
import com.he.exam.webapp.utils.CommonUtil;
import com.he.exam.webapp.utils.json.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by he on 2017/3/29.
 */
@Service("examService")
public class ExamService {

    @Resource
    private UserGroupMapper userGroupMapper;
    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private QuestionsMapper questionsMapper;
    @Resource
    private ExamUserAnswerMapper examUserAnswerMapper;
    @Resource private ExamUserMapper examUserMapper;

    @Value("${question_num}")
    private String question_num;

    @Value("${sigle_recore}")
    private String sigle_recore;
    @Value("${num_recore}")
    private String num_recore;
    @Value("${boolean_recore}")
    private String boolean_recore;


    public List<UserGroup> findGroups() {
        UserGroupExample example = new UserGroupExample();
        UserGroupExample.Criteria criteria = example.createCriteria();
        List<UserGroup> userGroups = userGroupMapper.selectByExample(example);
        if (userGroups.size() > 0) {
            List<Long> gids = CommonUtil.getValueList(userGroups, "id");

            DepartmentExample departmentExample = new DepartmentExample();
            DepartmentExample.Criteria criteria1 = departmentExample.createCriteria();
            criteria1.andGroupIdIn(gids);
            List<Department> departmentList = departmentMapper.selectByExample(departmentExample);
            Map<Long, List<Department>> objectListMap = CommonUtil.listforListConcurrentMap(departmentList, "groupId", null);
            for (UserGroup group : userGroups) {
                if (objectListMap.get(group.getId()) != null) {
                    group.setDepartmentList(objectListMap.get(group.getId()));
                }
            }
        }
        return userGroups;

    }

    /**
     * 查询试卷 并获取当前试卷的试题
     * 查询当天可以答题的题目列表
     * @return
     */
    public Result findExamQuestionList() {
        Result result = new Result();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        QuestionsExample questionsExample = new QuestionsExample();
        questionsExample.setOrderByClause("answer_time desc");
        questionsExample.setNeedCount(true);
        questionsExample.setRows(Integer.valueOf(question_num));

        QuestionsExample.Criteria criteria2 = questionsExample.createCriteria();
        criteria2.andIsDeletedEqualTo(0);
        criteria2.andStatusEqualTo(0);
        criteria2.andAnswerTimeEqualTo(sdf.format(new Date()));
        List<Questions> questionss = questionsMapper.selectByExample(questionsExample);

        for (Questions questions : questionss) {
            if(questions.getQuestionType().intValue() != 3){
                String options = questions.getQuestionOptions();
                List<QuestionOption> optionList = JsonUtils.fromJson(options, JsonUtils.constructParametricType(List.class, QuestionOption.class));
                questions.setOptionList(optionList);
            }
        }
        result.addAttribute("questionList", questionss);
        result.setSuccess(true);
        return result;

    }

    /**
     * 提交答案
     * @param jsonStr
     * @param endUserId
     * @return
     */
    @Transactional
    public void adduserAnswerToSubmit(String jsonStr, Long endUserId) {
        ExamUserAnswer examUserAnswer = JsonUtils.fromJson(jsonStr, ExamUserAnswer.class);

        //判断当前用户是否已答题
        Date currentDate = new Date();
        SimpleDateFormat sdg = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdg.format(currentDate);
        List<ExamUserAnswer> answers = examUserAnswerMapper.findUserAnswerByTime(endUserId , format);
        if(answers.size() > 0){
            BusinessException.throwMessageWithCode("-1" , "您今天已经答过题，请明天再来");
        }


        examUserAnswer.setSubmitTime(new Date());
        //计算得分情况
        List<AnswerContentModel> contentModelList = examUserAnswer.getContentModelList();
        List<Long> qids = CommonUtil.getValueList(contentModelList, "qid");

        QuestionsExample questionsExample = new QuestionsExample();
        QuestionsExample.Criteria qc = questionsExample.createCriteria();
        qc.andIdIn(qids);
        List<Questions> questionss = questionsMapper.selectByExample(questionsExample);
        Map<Long, Questions> objectMap = CommonUtil.listforConcurrentMap(questionss, "id", null);
        int totalScore = 0;
        for(AnswerContentModel model : contentModelList){
            Questions questions = objectMap.get(model.getQid());
            String checks = questions.getAnswers();
            String[] checkstr = checks.split(",");
            List<String> ckList = new ArrayList<String>();
            for(String a : checkstr){
                ckList.add(a);
            }
            String key = model.getKey();
            String[] split = key.split(",");
            //A 存在 B 不存在
            boolean flag = true;
            List<String> userCk = new ArrayList<String>();
            for(String str : split){
                userCk.add(str);
                if(!ckList.contains(str)){//正确答案
                    flag = false;
                }
            }
            //B 存在 A不存在
            for(String str : ckList){
                if(!userCk.contains(str)){
                    flag = false;
                }
            }
            if(flag){
                if(questions.getQuestionType().intValue() == 1){
                    totalScore += Integer.valueOf(sigle_recore).intValue();
                }else if(questions.getQuestionType().intValue() == 2){
                    totalScore += Integer.valueOf(num_recore).intValue();
                }else if(questions.getQuestionType().intValue() == 3){
                    totalScore += Integer.valueOf(boolean_recore).intValue();
                }
            }
        }
        ExamUser examUser = examUserMapper.selectByPrimaryKey(endUserId);
        examUserAnswer.setUserId(endUserId);
        examUserAnswer.setDepartmentId(examUser.getDepartmentId());
        examUserAnswer.setUserRecord(totalScore);
        examUserAnswer.setAnswerContent(JsonUtils.toJson(examUserAnswer.getContentModelList()));
        examUserAnswerMapper.insert(examUserAnswer);
    }
}
