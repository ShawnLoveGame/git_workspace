package com.he.exam.service.user;

import com.he.exam.dao.exam.*;
import com.he.exam.exception.BusinessException;
import com.he.exam.model.ModelResult;
import com.he.exam.model.exam.*;
import com.he.exam.model.exam.dto.DepartmentDTO;
import com.he.exam.model.exam.dto.ExamUserDTO;
import com.he.exam.model.exam.dto.UserGroupDTO;
import com.he.exam.model.exam.dto.UserWinDTO;
import com.he.exam.util.CommonUtil;
import com.he.exam.util.DateLocationUtil;
import com.he.exam.util.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by he on 2017/3/28.
 */
@Service("userService")
public class UserService {

    @Resource private UserGroupMapper userGroupMapper;
    @Resource private DepartmentMapper departmentMapper;
    @Resource private ExamUserMapper examUserMapper;
    @Resource private ExamUserAnswerMapper examUserAnswerMapper;
    @Resource private ExamInfoMapper examInfoMapper;
    @Resource private UserWinMapper userWinMapper;

    private Log log = LogFactory.getLog(getClass());

    public Pagination<UserGroup> findGroupWithPg(UserGroupDTO dto) {
        Pagination<UserGroup> pg = new Pagination<UserGroup>();

        UserGroupExample example = new UserGroupExample();
        example.setStart(dto.getStart());
        example.setRows(dto.getRows());
        example.setNeedCount(true);
        UserGroupExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(dto.getGroupName())){
            criteria.andGroupNameNotLike(dto.getGroupName());
        }
        int count = userGroupMapper.countByExample(example);
        pg.setTotal(count);
        List<UserGroup> groupList = userGroupMapper.selectByExample(example);
        pg.setRows(groupList);
        return pg;
    }

    public ModelResult saveGroupInfo(UserGroup userGroup) {
        if(userGroup.getId() == null ){
            userGroupMapper.insert(userGroup);
        }else{
            userGroupMapper.updateByPrimaryKeySelective(userGroup);
        }
        return new ModelResult(true);
    }


    public List<UserGroup> findGroupList() {

        UserGroupExample example = new UserGroupExample();
        List<UserGroup> groupList = userGroupMapper.selectByExample(example);
        return groupList;
    }


    public Pagination<Department> findDepartmentWithPg(DepartmentDTO dto) {
        Pagination<Department> pg = new Pagination<Department>();

        DepartmentExample example = new DepartmentExample();
        example.setStart(dto.getStart());
        example.setRows(dto.getRows());
        example.setNeedCount(true);
        DepartmentExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(dto.getDepartmentName())){
            criteria.andDepartmentNameIsNotNull().andDepartmentNameLike(dto.getDepartmentName());
        }
        if(dto.getGroupId() != null ){
            criteria.andGroupIdEqualTo(dto.getGroupId());
        }
        int count = departmentMapper.countByExample(example);
        pg.setTotal(count);
        if(count == 0 ){
            pg.setRows(new ArrayList<Department>());
            return pg;
        }
        List<Department> departmentList = departmentMapper.selectByExample(example);
        List<Long> gids = CommonUtil.getValueList(departmentList, "groupId");

        UserGroupExample groupExample = new UserGroupExample();
        UserGroupExample.Criteria groupExampleCriteria = groupExample.createCriteria();
        groupExampleCriteria.andIdIn(gids);
        List<UserGroup> groupList = userGroupMapper.selectByExample(groupExample);
        Map<Long, UserGroup> objectMap = CommonUtil.listforConcurrentMap(groupList, "id", null);
        for(Department department : departmentList){
            if(objectMap.get(department.getGroupId()) != null ){
                department.setGroupName(objectMap.get(department.getGroupId()).getGroupName());
            }
        }
        pg.setRows(departmentList);
        return pg;
    }

    public ModelResult saveDepartmentInfo(Department department) {
        if(department.getId() == null ){
            departmentMapper.insert(department);
        }else{
            departmentMapper.updateByPrimaryKeySelective(department);
        }
        return new ModelResult(true);
    }

    public Department findDepartmentById(Long id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    public Pagination<ExamUser> findUserWithPg(ExamUserDTO dto) {
        Pagination<ExamUser> pg = new Pagination<ExamUser>();

        ExamUserExample example  = new ExamUserExample();
        example.setRows(dto.getRows());
        example.setStart(dto.getStart());
        example.setNeedCount(true);
        ExamUserExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(dto.getUserName())){
            criteria.andUserNameLike(dto.getUserName());
        }
        if(dto.getDepartmentId() != null ){
            criteria.andDepartmentIdEqualTo(dto.getDepartmentId());
        }
        int count = examUserMapper.countByExample(example);
        pg.setTotal(count);
        if(count == 0){
            pg.setRows(new ArrayList<ExamUser>());
            return pg;
        }
        List<ExamUser> examUserList = examUserMapper.selectByExample(example);
        List<Long> valueList = CommonUtil.getValueList(examUserList, "departmentId");

        DepartmentExample departmentExample = new DepartmentExample();
        DepartmentExample.Criteria de = departmentExample.createCriteria();
        de.andIdIn(valueList);
        List<Department> departmentList = departmentMapper.selectByExample(departmentExample);
        Map<Long, Department> objectMap = CommonUtil.listforConcurrentMap(departmentList, "id", null);


        //判断是否已经考试过
        List<Long> userIds = CommonUtil.getValueList(examUserList, "id");
        ExamUserAnswerExample answerExample = new ExamUserAnswerExample();
        ExamUserAnswerExample.Criteria an = answerExample.createCriteria();
        an.andUserIdIn(userIds);
        List<ExamUserAnswer> userAnswers = examUserAnswerMapper.selectByExample(answerExample);
        Map<Long, List<ExamUserAnswer>> listMap = CommonUtil.listforListConcurrentMap(userAnswers, "userId", null);

        for(ExamUser user : examUserList){
            if(user.getDepartmentId() != null && objectMap.get(user.getDepartmentId()) != null ){
                user.setDepartmentName(objectMap.get(user.getDepartmentId()).getDepartmentName());
            }

            if(listMap.get(user.getId()) != null &&  listMap.get(user.getId()).size() > 0){
                user.setHasExam(1);
            }else{
                user.setHasExam(0);
            }
        }
        pg.setRows(examUserList);
        return pg;
    }

    public List<Department> findDepartmentList() {

        DepartmentExample departmentExample = new DepartmentExample();
        List<Department> departmentList = departmentMapper.selectByExample(departmentExample);

        return departmentList;
    }

    public Pagination<ExamUserAnswer> findUserExamWithPg(ExamUserDTO dto) {
        Pagination<ExamUserAnswer> pg = new Pagination<ExamUserAnswer>();
        ExamUserAnswerExample answerExample = new ExamUserAnswerExample();
        answerExample.setStart(dto.getStart());
        answerExample.setRows(dto.getRows());
        answerExample.setNeedCount(true);
        answerExample.setOrderByClause("submit_time desc");
        ExamUserAnswerExample.Criteria criteria = answerExample.createCriteria();
        criteria.andUserIdEqualTo(dto.getUserId());

        int count = examUserAnswerMapper.countByExample(answerExample);
        pg.setTotal(count);
        if(count == 0 ){
            pg.setRows(new ArrayList<ExamUserAnswer>());
            return pg;
        }
        List<ExamUserAnswer> answerList = examUserAnswerMapper.selectByExample(answerExample);
        pg.setRows(answerList);
        //
        ExamUser examUser = examUserMapper.selectByPrimaryKey(dto.getUserId());
        Department department = departmentMapper.selectByPrimaryKey(examUser.getDepartmentId());
        UserGroup userGroup = userGroupMapper.selectByPrimaryKey(department.getGroupId());

        for(ExamUserAnswer answer : answerList){
            answer.setUserName(examUser.getUserName());
            answer.setDepartmentName(department.getDepartmentName());
            answer.setGroupName(userGroup.getGroupName());
        }
        return pg;
    }

    /**
     * 查询首页数据
     * @return
     */
    public ModelResult findWelComeData() {
        ModelResult result = new ModelResult(true);
        ExamUserExample example = new ExamUserExample();
        int totaluser = examUserMapper.countByExample(example);
        //参与总人数
        int totalExamUser = examUserAnswerMapper.countUserAnswer();

        result.addAttribute("totaluser" , totaluser);
        result.addAttribute("totalExamUser" , totalExamUser);

        //所有分组及对应的
        UserGroupExample example1 = new UserGroupExample();
        List<UserGroup> userGroups = userGroupMapper.selectByExample(example1);
        if (userGroups.size() > 0) {
            List<Long> gids = CommonUtil.getValueList(userGroups, "id");

            DepartmentExample departmentExample = new DepartmentExample();
            DepartmentExample.Criteria criteria1 = departmentExample.createCriteria();
            criteria1.andGroupIdIn(gids);
            List<Department> departmentList = departmentMapper.selectByExample(departmentExample);
            //获取每个分组的得分情况
            List<Department> recordList = examUserAnswerMapper.findUserDepartmentOrder();
            Map<Long,Department> dmap = CommonUtil.listforConcurrentMap(recordList , "id" , null );

            for(Department department : departmentList){
                if(dmap.get(department.getId()) != null ){
                    department.setTotalScore(dmap.get(department.getId()).getTotalScore());
                }else{
                    department.setTotalScore(0);
                }
            }
            Collections.sort(departmentList, new Comparator<Department>() {
                public int compare(Department o1, Department o2) {
                    return o2.getTotalScore().compareTo(o1.getTotalScore());
                }
            });

            Map<Long, List<Department>> objectListMap = CommonUtil.listforListConcurrentMap(departmentList, "groupId", null);
            for (UserGroup group : userGroups) {
                if (objectListMap.get(group.getId()) != null) {
                    group.setDepartmentList(objectListMap.get(group.getId()));
                }
            }
        }
        result.addAttribute("groupList" , userGroups);

        //查询当天中奖名单
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date currentDate = new Date();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String before = DateLocationUtil.getSpecifiedDayBefore(sdf2.format(currentDate), 1);
        try {
            Date parse = sdf2.parse(before);
            String format = sdf.format(parse);
            UserWinExample userWinExample = new UserWinExample();
            UserWinExample.Criteria criteria = userWinExample.createCriteria();
            criteria.andWinTimeEqualTo(format);
            List<UserWin> userWins = userWinMapper.selectByExample(userWinExample);
            //
            buildUserWinInfo(userWins);
            result.addAttribute("userWins" , userWins);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  result;
    }

    public Pagination<UserWin> findUserWinWithPg(UserWinDTO dto) {

        Pagination<UserWin> pg = new Pagination<UserWin>();


        UserWinExample userWinExample = new UserWinExample();
        UserWinExample.Criteria criteria = userWinExample.createCriteria();
        userWinExample.setStart(dto.getStart());
        userWinExample.setRows(dto.getRows());
        userWinExample.setNeedCount(true);

        if(StringUtils.isNotBlank(dto.getStartTime()) ){
            criteria.andWinTimeGreaterThanOrEqualTo(dto.getStartTime());
        }
        if(StringUtils.isNotBlank(dto.getEndTime())){
            criteria.andWinTimeLessThanOrEqualTo(dto.getEndTime());
        }
        int count = userWinMapper.countByExample(userWinExample);
        pg.setTotal(count);
        List<UserWin> userWins = userWinMapper.selectByExample(userWinExample);
        buildUserWinInfo(userWins);
        pg.setRows(userWins);
        return pg;
    }

    private void buildUserWinInfo(List<UserWin> userWins){
        if(userWins.size() > 0 ){
            List<Long> userIds = CommonUtil.getValueList(userWins, "userId");
            ExamUserExample userExample = new ExamUserExample();
            ExamUserExample.Criteria ue = userExample.createCriteria();
            ue.andIdIn(userIds);
            List<ExamUser> userList = examUserMapper.selectByExample(userExample);
            Map<Long, ExamUser> objectMap = CommonUtil.listforConcurrentMap(userList, "id", null);
            for(UserWin win : userWins){
                if(objectMap.get(win.getUserId()) != null ){
                    ExamUser user = objectMap.get(win.getUserId());
                    win.setUserName(StringUtils.isNotBlank(user.getUserName()) ? user.getUserName() :"无名（未知用户）");
                    if(user.getDepartmentId() != null ){
                        Department department = departmentMapper.selectByPrimaryKey(user.getDepartmentId());
                        win.setDepartmentName(department.getDepartmentName());
                    }
                }
            }
        }
    }

    /**
     * 计算每日 中奖用户
     */
    public void calculateDailyWin() {
        try {
            Date current = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String format = sdf.format(current);
            //查询前一天的答题情况
            String before = DateLocationUtil.getSpecifiedDayBefore(format, 1);
            String startTime  = before + " 00:00:00";

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
            Date date = DateUtils.addDays(current, -1);
            String format1 = sdf2.format(date);

            UserWinExample winExample = new UserWinExample();
            UserWinExample.Criteria criteria = winExample.createCriteria();
            criteria.andWinTimeEqualTo(format1);
            List<UserWin> userWins1 = userWinMapper.selectByExample(winExample);
            if(userWins1.size() > 0){
                BusinessException.throwMessage(format1 + "已计算奖励");
            }
            List<ExamUserAnswer> answerList  = examUserAnswerMapper.findUserExamAnswerListByTime(startTime);
            if(answerList.size() > 0){
                //参与答题的用户列表
                List<Long> userIds = CommonUtil.getValueList(answerList, "userId");
                List<Long> tempList = getRandomFromArray(userIds, 3);
                List<UserWin> userWins = new ArrayList<UserWin>();
                for(Long tmp : tempList ){
                    UserWin win = new UserWin();
                    win.setUserId(tmp);
                    win.setWinTime(format1);
                    win.setOpTime(new Date());
                    userWins.add(win);
                }
                userWinMapper.batchInsertUserWin(userWins);
            }
        } catch (Exception e) {
            log.error("计算中奖名单失败" , e);
        }
    }

    public List<Long>  getRandomFromArray(List<Long> array, int count) {
        List<Long> result = new ArrayList<Long>();
        boolean r[] = new boolean[array.size()];
        Random random = new Random();
        int m = count; // 要随机取的元素个数
        if (m > array.size() || m < 0) {
            return array;
        }
        int n = 0;
        while (true) {
            int temp = random.nextInt(array.size());
            if (!r[temp]) {
                if (n == m) // 取到足量随机数后退出循环
                    break;
                n++;
                System.out.println("得到的第" + n + "个随机数为：" + array.get(temp));
                result.add(array.get(temp));
                r[temp] = true;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Date current = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(current);
        //查询前一天的答题情况
        String before = DateLocationUtil.getSpecifiedDayBefore(format, 1);
        String startTime  = before + " 00:00:00";
        System.out.println(startTime);
    }
}
