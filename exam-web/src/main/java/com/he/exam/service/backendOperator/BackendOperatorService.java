package com.he.exam.service.backendOperator;

import com.he.exam.constant.KeysEnum;
import com.he.exam.dao.exam.BackOperatorMapper;
import com.he.exam.exception.AlikAssert;
import com.he.exam.exception.BusinessException;
import com.he.exam.model.ModelResult;
import com.he.exam.model.backoperator.BackOperator;
import com.he.exam.model.backoperator.dto.BackendOperatorDTO;
import com.he.exam.model.backoperator.vo.BackOperatorVO;
import com.he.exam.util.codec.CipherUtils;
import com.he.exam.util.shared.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by he on 2016/12/5.
 */
@Service("backendOperatorService")
public class BackendOperatorService {

    private Log log = LogFactory.getLog(getClass());


    @Resource
    private BackOperatorMapper backOperatorMapper;
    @Resource
    private RedisUtils redisUtils;


    @Value("${redis.url}")
    private String redisUrl;

    @Transactional
    public BackOperatorVO login(BackendOperatorDTO backendOperatorDTO) {
        // 参数校验
        AlikAssert.isNotNull(backendOperatorDTO, "参数错误");
        AlikAssert.isNotBlank(backendOperatorDTO.getPassword(), "密码不能为空");
        AlikAssert.isNotBlank(backendOperatorDTO.getUserName(), "用户名不能为空");

        // 检验验证码信息
        if (!backendOperatorDTO.getValidateCode().toUpperCase().equals(backendOperatorDTO.getSessionValidateCode())) {
            BusinessException.throwMessage("验证码错误");
        }

        BackOperatorVO backendOperator = null;
        try {
            // 查找用户信息
            backendOperator = backOperatorMapper.findBackendOperatorByName(backendOperatorDTO.getUserName());
        } catch (Exception e) {
            log.error("backendOperatorMapper.findBackendOperatorByName is error ",e);
            BusinessException.throwMessage("系统异常，请稍后再试");
        }

        if (backendOperator == null) {
            BusinessException.throwMessage("用户不存在");
        }
        // 页面密码MD5加密
        String passwordMd5 = CipherUtils.MD5Encode(backendOperatorDTO.getPassword());
        if (!backendOperator.getPassword().equals(passwordMd5)) {
            BusinessException.throwMessage("密码错误");
        }
        // 登录成功后更新用户信息
        backendOperatorDTO.setUpdateBy(backendOperator.getId());
        backOperatorMapper.updateBackendOperatorAfterLogin(backendOperatorDTO);
        return backendOperator;
    }

    public BackOperatorVO findBackendOperatorByDTO(BackendOperatorDTO backendOperatorDTO) {
        AlikAssert.isNotNull(backendOperatorDTO, "参数对象不能为空！");
        AlikAssert.isNotBlank(backendOperatorDTO.getUserName(), "用户名不能为空！");
        return backOperatorMapper.findBackendOperatorByDTO(backendOperatorDTO);
    }



    public List<BackOperatorVO> findBackUserWithPg(BackendOperatorDTO backendOperatorDTO) {
        List<BackOperatorVO> list = backOperatorMapper.findBackUserWithPg(backendOperatorDTO);
        return list;
    }

    public int countFindBackUserWithPg(BackendOperatorDTO backendOperatorDTO) {
        return backOperatorMapper.countFindBackUserWithPg(backendOperatorDTO);
    }

    public BackOperatorVO findBackUserInfoById(Long id) {
        return backOperatorMapper.selectByPrimaryKey(id);
    }



    public void clearRedis() {
        try {
            String host = redisUrl.split(":")[0];
            String port = redisUrl.split(":")[1];
            Jedis jedis = new Jedis(host , Integer.valueOf(port) );
            Set<String> keys = jedis.keys(KeysEnum.BACKEND_NORMAL_FUNCTION.getKey()+"_*" );
            for(String key : keys){
                jedis.del(key);
            }
            jedis.del(KeysEnum.BACKEND_SUPER_FUNCTION.getKey());
        } catch (Exception e) {
            log.error("删除缓存异常",e);
        }

    }

    public ModelResult resetPwd(BackendOperatorDTO backendOperatorDTO) {

        AlikAssert.isNotBlank(backendOperatorDTO.getPassword(), "密码不能为空！");
        AlikAssert.isNotBlank(backendOperatorDTO.getNewPassword(), "新密码不能为空！");
        AlikAssert.isNotBlank(backendOperatorDTO.getConfirmNewPassword(), "重复密码不能为空！");

        ModelResult result = new ModelResult();
        try {
            BackOperatorVO backOperatorVO = backOperatorMapper.selectByPrimaryKey(backendOperatorDTO.getId());
            if(backOperatorVO == null ){
                BusinessException.throwMessage("无效用户");
            }
            String pwd = CipherUtils.MD5Encode(backendOperatorDTO.getPassword());
            if(!pwd.equals(backOperatorVO.getPassword())){
                BusinessException.throwMessage("输入的原密码有误");
            }
            String newpwd = CipherUtils.MD5Encode(backendOperatorDTO.getNewPassword());
            if(!pwd.equals(newpwd)){
                BusinessException.throwMessage("新密码不能和旧密码一样");
            }
            if(!backendOperatorDTO.getNewPassword().equals(backendOperatorDTO.getConfirmNewPassword())){
                BusinessException.throwMessage("重复新密嘛不一致");
            }
            // 修改密码，封装入参
            BackOperator backendOperator = new BackOperator();
            backendOperator.setId(backendOperatorDTO.getId());
            backendOperator.setUpdateBy(backendOperatorDTO.getUpdateBy());
            backendOperator.setPassword(CipherUtils.MD5Encode(backendOperatorDTO.getNewPassword()));

            String password = backOperatorVO.getRecentlyPassword();
            if(StringUtils.isNotBlank(password)){
                String[] split = password.split(",");
                if(split.length < 3){
                    password = password + "," + CipherUtils.MD5Encode(backendOperatorDTO.getNewPassword());
                }else{
                    StringBuilder sb = new StringBuilder();
                    for(int i = 1  ; i < split.length ; i++){
                        sb.append(split[i]).append(",");
                    }
                    sb.append(CipherUtils.MD5Encode(backendOperatorDTO.getNewPassword()));
                    password = sb.toString();
                }
            }
            backendOperator.setRecentlyPassword(password);
            // 更新密码
            int flag = backOperatorMapper.updateByPrimaryKeySelective(backendOperator);
            if (flag <= 0) {
                BusinessException.throwMessageWithCode("-4", "修改密码失败！");
            }
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("修改密码异常" ,e);
            result.setSuccess(false);
            if(e instanceof  BusinessException){
                result.setMessage(e.getMessage());
            }else{
                result.setMessage("操作失败");
            }
        }
        return result;
    }

}
