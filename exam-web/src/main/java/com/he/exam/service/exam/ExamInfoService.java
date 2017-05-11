package com.he.exam.service.exam;

import com.he.exam.dao.exam.ExamDetailMapper;
import com.he.exam.dao.exam.ExamInfoMapper;
import com.he.exam.dao.exam.QuestionsMapper;
import com.he.exam.exception.AlikAssert;
import com.he.exam.exception.BusinessException;
import com.he.exam.model.ModelResult;
import com.he.exam.model.exam.*;
import com.he.exam.model.exam.dto.ExamInfoDTO;
import com.he.exam.util.Pagination;
import com.he.exam.util.PaginationUtil;
import com.he.exam.util.StringUtils;
import com.he.exam.util.json.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by he on 2017/3/28.
 */
@Service("examInfoService")
public class ExamInfoService {

    private Log log = LogFactory.getLog(getClass());
    @Resource
    private ExamInfoMapper examInfoMapper;
    @Resource
    private ExamDetailMapper examDetailMapper;
    @Resource
    private QuestionsMapper questionsMapper;

    public Pagination<ExamInfo> findExamInfoWithPg(ExamInfoDTO examInfoDTO) {

        Pagination<ExamInfo> pg = new Pagination<ExamInfo>();
        ExamInfoExample examInfoExample = new ExamInfoExample();
        examInfoExample.setStart(examInfoDTO.getStart());
        examInfoExample.setRows(examInfoDTO.getRows());
        examInfoExample.setNeedCount(true);

        ExamInfoExample.Criteria criteria = examInfoExample.createCriteria();
        if (StringUtils.isNotBlank(examInfoDTO.getExamTitle())) {
            criteria.andExamTitleLike(examInfoDTO.getExamTitle());
        }
        if (examInfoDTO.getExamStatus() != null) {
            criteria.andExamStatusEqualTo(examInfoDTO.getExamStatus());
        }

        int count = examInfoMapper.countByExample(examInfoExample);
        pg.setTotal(count);
        if (count == 0) {
            pg.setRows(new ArrayList<ExamInfo>());
            return pg;
        }
        List<ExamInfo> examInfos = examInfoMapper.selectByExample(examInfoExample);
        pg.setRows(examInfos);
        return pg;

    }

    public ModelResult saveExamInfo(ExamInfo examInfo) {
        examInfoMapper.updateByPrimaryKeySelective(examInfo);
        return new ModelResult(true);
    }

    public ExamInfo findExamInfoById(Long id) {
        ExamInfo examInfo = examInfoMapper.selectByPrimaryKey(id);
        if (StringUtils.isNotBlank(examInfo.getExamContent())) {
            List<ExamContent> contentList = JsonUtils.fromJson(examInfo.getExamContent(), JsonUtils.constructParametricType(List.class, ExamContent.class));
            examInfo.setContentList(contentList);
        }
        return examInfo;
    }

    @Transactional
    public void saveEditExamInfo(ExamInfo examInfo) throws Exception {
        AlikAssert.isNotBlank(examInfo.getExamTitle() , "考试名称不能为空");
        AlikAssert.isNotBlank(examInfo.getExamEndTimeStr() , "答题时间不能为空");
        AlikAssert.isNotBlank(examInfo.getExamStartTimeStr() , "答题时间不能为空");
        examInfo.setOpTime(new Date());
        List<ExamContent> contentList = examInfo.getContentList();
        if (contentList.size() == 0) {
            BusinessException.throwMessageWithCode("-1", "试卷题目构成不能为空");
        }
        int totalScore = 0;
        int rowlevel = 1;
        for (ExamContent content : contentList) {
            content.setRuleLevel(rowlevel);
            totalScore += content.getTotalNum() * content.getSingleScore();
            rowlevel++;
        }
        examInfo.setTotalScore(totalScore);
        examInfo.setExamContent(JsonUtils.toJson(contentList));
        Boolean isEdit = false;

        //时间
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date StartTime = sdf.parse(examInfo.getExamStartTimeStr());
        Date endTime = sdf.parse(examInfo.getExamEndTimeStr());
        examInfo.setExamStartTime(StartTime);
        examInfo.setExamEndTime(endTime);

        if (examInfo.getId() == null) {
            examInfo.setIsDeleted(0);
            examInfoMapper.insert(examInfo);
        } else {
            isEdit = true;
            examInfoMapper.updateByPrimaryKeySelective(examInfo);
        }

        //
        //删除已经存在的试卷详情
        if (isEdit) {
            ExamDetailExample detailExample = new ExamDetailExample();
            ExamDetailExample.Criteria criteria = detailExample.createCriteria();
            criteria.andExamIdEqualTo(examInfo.getId());
            examDetailMapper.deleteByExample(detailExample);
        }
        List<ExamDetail> detailList = new ArrayList<ExamDetail>();
        int i = 1;
        for (ExamContent content : contentList) {
            QuestionsExample questionsExample = new QuestionsExample();
            questionsExample.setOrderByClause("RAND() limit " + content.getTotalNum());
            QuestionsExample.Criteria criteria = questionsExample.createCriteria();
            criteria.andQuestionTypeEqualTo(content.getQuetionType());
            List<Questions> questionss = questionsMapper.selectByExample(questionsExample);
            if (questionss.size() > 0) {
                for (Questions questions : questionss) {
                    ExamDetail detail = new ExamDetail();
                    detail.setDetailIndex(i);
                    detail.setRuleLevel(content.getQuetionType());
                    detail.setExamId(examInfo.getId());
                    detail.setQuestionId(questions.getId());
                    detail.setRecord(content.getSingleScore());
                    i++;
                    detailList.add(detail);
                }
            }
        }
        if (detailList.size() > 0) {
            List<List<ExamDetail>> split = PaginationUtil.split(detailList, 50);
            for (List<ExamDetail> details : split) {
                examDetailMapper.batchInsertDetail(details);
            }
        }
    }
}
