package com.he.exam.webapp.service.exam;

import com.he.exam.webapp.dao.exam.*;
import com.he.exam.webapp.exception.BusinessException;
import com.he.exam.webapp.model.exam.*;
import com.he.exam.webapp.model.userCenter.UserLoginVo;
import com.he.exam.webapp.utils.CommonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by he on 2017/3/29.
 */
@Service("examUserService")
public class ExamUserService {

    private Log log = LogFactory.getLog(getClass());

    @Resource private ExamUserMapper examUserMapper;
    @Resource private ExamInfoMapper examInfoMapper;
    @Resource private ExamUserAnswerMapper examUserAnswerMapper;
    @Resource private UserGroupMapper userGroupMapper;
    @Resource private DepartmentMapper departmentMapper;

    public void toAnswerQuestion(Long departmentId, UserLoginVo endUser) {
        ExamUser examUser = examUserMapper.selectByPrimaryKey(endUser.getUserId());
        if(examUser == null ){

        }
        //更新用户所在组织
        examUser.setDepartmentId(departmentId);
        examUserMapper.updateByPrimaryKeySelective(examUser);


        Date currentDate = new Date();
        SimpleDateFormat sdg = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdg.format(currentDate);
        List<ExamUserAnswer> answers = examUserAnswerMapper.findUserAnswerByTime(examUser.getId() , format);
        if(answers.size() > 0){
            BusinessException.throwMessageWithCode("-1" , "您今天已经答过题，请明天再来");
        }
    }

    public ExamUser findUserInfoById(Long userId) {
        ExamUser examUser = examUserMapper.selectByPrimaryKey(userId);
        //查询当天的答题情况
        //每天只能答题一次
        Date currentDate = new Date();
        SimpleDateFormat sdg = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdg.format(currentDate);
        List<ExamUserAnswer> answers = examUserAnswerMapper.findUserAnswerByTime(userId , format);
        if(answers.size() > 0){
            examUser.setExamUserAnswer(answers.get(0));
        }
        return examUser;
    }

    public Map<String, Object> getUserCenterData(Long userId) {
        Map<String, Object> resultMap = new HashMap<String,Object>();
        //总人数
        int totalUserAnswer = examUserAnswerMapper.countTotalUserAnswer();
        resultMap.put("totalUsers" , totalUserAnswer);
        ExamUser examUser = examUserMapper.selectByPrimaryKey(userId);
        List<Department> recordList = examUserAnswerMapper.findUserDepartmentOrder(null);
        int cuNum = 1;
        for(int i = 0 ;i < recordList.size() ; i++ ){
            Department map = recordList.get(i);
            if(examUser.getDepartmentId().equals(map.getId())){
                cuNum = i+1;
                break;
            }
        }
        resultMap.put("cuNum" , cuNum);
        //分组
        UserGroupExample example = new UserGroupExample();
        List<UserGroup> userGroups = userGroupMapper.selectByExample(example);
        resultMap.put("userGroups" , userGroups);
        return resultMap;
    }

    public List<Department> findGroupDepartmentData(Long groupId) {
        DepartmentExample example  = new DepartmentExample();
        DepartmentExample.Criteria de = example.createCriteria();
        de.andGroupIdEqualTo(groupId);
        List<Department> departmentList = departmentMapper.selectByExample(example);
        List<Long> dids = CommonUtil.getValueList(departmentList, "id");
        List<Department> recordList = examUserAnswerMapper.findUserDepartmentOrder(dids);
        Map<Long,Department> dmap = CommonUtil.listforConcurrentMap(recordList , "id" , null );

        for(Department department : departmentList){
            if(dmap.get(department.getId()) != null ){
                department.setTotalScore(Integer.valueOf(dmap.get(department.getId()).getTotalScore()));
            }else{
                department.setTotalScore(0);
            }
        }
        Collections.sort(departmentList, new Comparator<Department>() {
            public int compare(Department o1, Department o2) {
                return o2.getTotalScore().compareTo(o1.getTotalScore());
            }
        });
        return departmentList;

    }

    public Boolean checkCurrentUserHasAnswer(Long userId){
        if(userId == null ){
            return false;
        }
        //每天只能答题一次
        Date currentDate = new Date();
        SimpleDateFormat sdg = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdg.format(currentDate);
        List<ExamUserAnswer> answers = examUserAnswerMapper.findUserAnswerByTime(userId , format);
        return answers.size() > 0 ?true : false;
    }
}
