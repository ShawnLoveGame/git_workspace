package com.he.exam.controller.user;

import com.he.exam.controller.BaseController;
import com.he.exam.framework.aop.ArchivesLog;
import com.he.exam.model.ModelResult;
import com.he.exam.model.exam.*;
import com.he.exam.model.exam.dto.DepartmentDTO;
import com.he.exam.model.exam.dto.ExamUserDTO;
import com.he.exam.model.exam.dto.UserGroupDTO;
import com.he.exam.model.exam.dto.UserWinDTO;
import com.he.exam.service.user.UserService;
import com.he.exam.util.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户分组
 * Created by he on 2017/3/27.
 */
@Controller
@RequestMapping("/user")
public class UserManageController extends BaseController {

    @Resource private UserService userService;


    @RequestMapping("/groupPage")
    public String intoGroupPage(){

        return "user/groupPage";
    }

    @RequestMapping("/findGroupWithPg")
    @ResponseBody
    public Pagination<UserGroup> findGroupWithPg(UserGroupDTO dto){
        return userService.findGroupWithPg(dto);
    }


    @RequestMapping("/findGroupList")
    @ResponseBody
    public List<UserGroup> findGroupList(){
        return userService.findGroupList();
    }


    @RequestMapping("/saveGroupInfo")
    @ResponseBody
    @ArchivesLog(operationType="save" , operationName="保存分组信息")
    public ModelResult saveGroupInfo(UserGroup userGroup){
        return userService.saveGroupInfo(userGroup);
    }



    @RequestMapping("/departmentPage")
    public String intoDepartmentPage(){

        return "user/departmentPage";
    }

    @RequestMapping("/findDepartmentWithPg")
    @ResponseBody
    public Pagination<Department> findDepartmentWithPg(DepartmentDTO dto){
        return userService.findDepartmentWithPg(dto);
    }


    @RequestMapping("/findDepartmentList")
    @ResponseBody
    public List<Department> findDepartmentList(){
        return userService.findDepartmentList();
    }


    @RequestMapping("/findDepartmentById")
    @ResponseBody
    public Department findDepartmentById(Long id ){
        return userService.findDepartmentById(id);
    }



    @RequestMapping("/saveDepartmentInfo")
    @ResponseBody
    @ArchivesLog(operationType="save" , operationName="保存部门信息")
    public ModelResult saveDepartmentInfo(Department department){
        return userService.saveDepartmentInfo(department);
    }




    @RequestMapping("/userManagePage")
    public String userManagePage(){

        return "user/userManagePage";
    }

    @RequestMapping("/findUserWithPg")
    @ResponseBody
    public Pagination<ExamUser> findUserWithPg(ExamUserDTO dto){
        return userService.findUserWithPg(dto);
    }

    @RequestMapping("/findUserExamByUserId")
    public String findUserExamByUserId(Long userId , HttpServletRequest  request){
        request.setAttribute("userId" , userId);
        return "user/userExamPage";
    }



    @RequestMapping("/findUserExamWithPg")
    @ResponseBody
    public Pagination<ExamUserAnswer> findUserExamWithPg(ExamUserDTO dto){
        return userService.findUserExamWithPg(dto);
    }



//    用户中奖


    @RequestMapping("/userWin")
    public String intoWinPage(){

        return "user/winPage";
    }

    @RequestMapping("/findUserWinWithPg")
    @ResponseBody
    public Pagination<UserWin> findUserWinWithPg(UserWinDTO dto){
        return userService.findUserWinWithPg(dto);
    }


}


