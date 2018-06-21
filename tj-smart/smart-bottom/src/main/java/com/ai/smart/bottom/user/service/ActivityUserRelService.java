package com.ai.smart.bottom.user.service;

import com.ai.smart.bottom.enums.RedisBottomKeyEnum;
import com.ai.smart.bottom.user.mapper.BottomActivitySoMapper;
import com.ai.smart.bottom.user.mapper.BottomActivityUserRelMapper;
import com.ai.smart.bottom.user.model.BottomActivitySo;
import com.ai.smart.bottom.user.model.BottomActivityUserRel;
import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.common.helper.AssertHelper;
import com.ai.smart.common.helper.DateHelper;
import com.ai.smart.common.helper.redis.RedisHelper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ActivityUserRelService {

    @Autowired
    private BottomActivityUserRelMapper bottomActivityUserRelMapper;

    @Autowired
    private BottomActivitySoMapper bottomActivitySoMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisHelper redisHelper;

    /**
     * 绑定用户关系
     * @param parentId
     * @param userId
     * @return
     */
    public GlobalResponse bindUser(String parentId,Long userId){
        boolean isGet = redisHelper.canBeSubmit(RedisBottomKeyEnum.BOTTOM_SHARE_TAG_.name()+parentId, "isGet", 2);
        if (!isGet){
            return GlobalResponse.fail("您操作太频繁了哦!");
        }
        GlobalResponse userInfo = userService.findUserInfo(Long.valueOf(parentId));
        HashMap<Object, Object> re = Maps.newHashMap();
        AssertHelper.notBlank(parentId,"分享人id不能为空");
        if (Objects.equals(Long.valueOf(parentId),userId)){
            return GlobalResponse.fail("同用户不可绑定", -100);
        }
        //1.校验是否已经被邀请
        BottomActivityUserRel param = new BottomActivityUserRel();
        param.setUserId(userId);
        List<BottomActivityUserRel> byUserIdAndParentId = bottomActivityUserRelMapper.findByUserIdAndParentId(param);
        if (CollectionUtils.isNotEmpty(byUserIdAndParentId)){
            // 判断当天是否已经领取过红包
            List<BottomActivitySo> byUserId = bottomActivitySoMapper.findByUserId(userId);
            if (CollectionUtils.isNotEmpty(byUserId)){
                BottomActivitySo bottomActivitySo = byUserId.stream().filter(a -> checkIsCurrDay(a.getCreateTime())).findFirst().orElse(null);
                if (bottomActivitySo != null){
                    return GlobalResponse.fail("当天已经领取过话费红包哦！",-200);
                }
            }
            BottomActivityUserRel bottomActivityUserRel = byUserIdAndParentId.stream().filter(a -> Objects.equals(a.getStatus(), 0)).findFirst().orElse(null);
            if (bottomActivityUserRel != null){
                if (Objects.equals(Long.valueOf(parentId),bottomActivityUserRel.getParentUserId())){
                    if (!checkIsVial(bottomActivityUserRel.getBindTime())){
                        // 判断时间  --> 是否还原时间
                        bottomActivityUserRel.setParentUserId(Long.valueOf(parentId));
                        bottomActivityUserRel.setBindTime(new Date());
                        bottomActivityUserRelMapper.updateByPrimaryKeySelective(bottomActivityUserRel);
                    }
                    re.put("parentUser",userInfo.getData());
                    re.put("activityInfo",bottomActivityUserRel);
                    return GlobalResponse.success(re);
                }else{
                    // 失效 之前的绑定关系
                    bottomActivityUserRel.setStatus(2);
                    bottomActivityUserRelMapper.updateByPrimaryKeySelective(bottomActivityUserRel);
                }
            }
        }
        BottomActivityUserRel bottomActivityUserRel = new BottomActivityUserRel();
        bottomActivityUserRel.setBindTime(new Date());
        bottomActivityUserRel.setParentUserId(Long.valueOf(parentId));
        bottomActivityUserRel.setUserId(userId);
        bottomActivityUserRel.setStatus(0);
        bottomActivityUserRelMapper.insert(bottomActivityUserRel);

        re.put("parentUser",userInfo.getData());
        re.put("activityInfo",bottomActivityUserRel);
        return GlobalResponse.success(re);
    }

    /**
     * 获取当前生效的绑定关系
     * @param userId
     * @return
     */
    public BottomActivityUserRel getCurrentIv(Long userId){
        BottomActivityUserRel param = new BottomActivityUserRel();
        param.setUserId(userId);
        List<BottomActivityUserRel> byUserIdAndParentId = bottomActivityUserRelMapper.findByUserIdAndParentId(param);
        if (CollectionUtils.isEmpty(byUserIdAndParentId)){
            return null;
        }
        BottomActivityUserRel bottomActivityUserRel = byUserIdAndParentId.stream().filter(a -> Objects.equals(a.getStatus(), 0)
                && checkIsVial(a.getBindTime())).findFirst().orElse(null);
        if (bottomActivityUserRel != null && checkIsVial(bottomActivityUserRel.getBindTime())){
           return bottomActivityUserRel;
        }
        return null;
    }

    public GlobalResponse updateStatus(BottomActivityUserRel bottomActivityUserRel){
        bottomActivityUserRel.setStatus(1);
        bottomActivityUserRelMapper.updateByPrimaryKeySelective(bottomActivityUserRel);
        return GlobalResponse.success("修改状态成功");
    }


    /**
     * 校验是否为3天内的邀请
     * @param time
     * @return
     */
    public boolean checkIsVial(Date time){
        Date date = new Date();
        Date addDayTime = DateHelper.getAddDayTime(time, 3);
        if (addDayTime.after(date)){
            return true;
        }
        return false;
    }

    /**
     * 校验是否为当天
     * @param time
     * @return
     */
    public boolean checkIsCurrDay(Date time){
        String yyyyMMdd = DateHelper.converToStringDate(time, "yyyyMMdd");
        String now = DateHelper.converToStringDate(new Date(), "yyyyMMdd");
        return Objects.equals(yyyyMMdd,now)?true:false;
    }
}
