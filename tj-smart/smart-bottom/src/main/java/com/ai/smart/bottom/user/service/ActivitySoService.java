package com.ai.smart.bottom.user.service;

import com.ai.smart.bottom.user.mapper.BottomActivitySoMapper;
import com.ai.smart.bottom.user.mapper.BottomMaUserMapper;
import com.ai.smart.bottom.user.model.BottomActivitySo;
import com.ai.smart.bottom.user.model.BottomMaUser;
import com.ai.smart.common.base.GlobalResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ActivitySoService {

    @Autowired
    private BottomActivitySoMapper bottomActivitySoMapper;

    @Autowired
    private BottomMaUserMapper bottomMaUserMapper;

    /**
     * 判断当前用户红包是否超过10次
     * @param userId
     * @return
     */
    public boolean checkTenRed(Long userId){
        List<BottomActivitySo> activiySoPageByUserId = bottomActivitySoMapper.findActiviySoPageByUserId(userId);
        return activiySoPageByUserId.size()<10?false:true;
    }

    public GlobalResponse updateByPrimaryKeySelective(BottomActivitySo so){
        bottomActivitySoMapper.updateByPrimaryKeySelective(so);
        return GlobalResponse.success("修改成功");
    }


    /**
     * 创建订单
     * @param so
     * @return
     */
    public BottomActivitySo createSo(BottomActivitySo so){
        bottomActivitySoMapper.insert(so);
        return so;
    }

    /**
     * 首页轮播红包分页查询
     * @param pageNo
     * @param pageSize
     * @return
     */
    public GlobalResponse findActiviySoPage(Integer pageNo, Integer pageSize){
        try {
            PageHelper.startPage(pageNo,pageSize);
            List<BottomActivitySo> byIv = bottomActivitySoMapper.findByIv();
            if (CollectionUtils.isNotEmpty(byIv)){
                List<Long> ids = byIv.stream().map(BottomActivitySo::getUserId).collect(Collectors.toList());
                List<BottomMaUser> bottomMaUsers = bottomMaUserMapper.batchFindByIds(ids);
                bottomMaUsers.forEach(a->{
                    a.setNickName(a.getNickName());
                });
                Map<Long, BottomMaUser> collect = bottomMaUsers.stream().collect(Collectors.toMap(BottomMaUser::getId, o -> o));
                byIv.forEach(a->{
                    a.setBottomMaUser(collect.get(a.getUserId()));
                });
            }
            return GlobalResponse.success(byIv);
        } catch (Exception e) {
            log.error("findActiviySoPage exception",e);
        }
        return GlobalResponse.success(Lists.newArrayList());
    }

    /**
     * 分页查询分享列表
     * @param pageNo
     * @param pageSize
     * @param userId
     * @return
     */
    public GlobalResponse findActiviySoPageByUserId(Integer pageNo, Integer pageSize,Long userId){
        PageHelper.startPage(pageNo,pageSize);
        List<BottomActivitySo> byIv = bottomActivitySoMapper.findActiviySoPageByUserId(userId);
        PageInfo<BottomActivitySo> pageInfo = new PageInfo<>(byIv);
        BigDecimal bg = new BigDecimal("0");
        // 获取对应的用户信息
        if (CollectionUtils.isNotEmpty(byIv)){
            List<Long> ids = byIv.stream().map(BottomActivitySo::getSubUserId).collect(Collectors.toList());
            List<BottomMaUser> bottomMaUsers = bottomMaUserMapper.batchFindByIds(ids);
            bottomMaUsers.forEach(a->{
                 a.setNickName(a.getNickName());
            });
            Map<Long, BottomMaUser> collect = bottomMaUsers.stream().collect(Collectors.toMap(BottomMaUser::getId, o -> o));
            byIv.forEach(a->{
                a.setBottomMaUser(collect.get(a.getSubUserId()));
            });
            for (int i=0;i< byIv.size();i++){
                bg = bg.add(byIv.get(i).getAmount());
            }
        }
        long total = pageInfo.getTotal();
        HashMap<Object, Object> remap = Maps.newHashMap();
        remap.put("total",total);
        remap.put("tamount",bg);
        remap.put("list",byIv);
        return GlobalResponse.success(remap);
    }

    public static String unicode(String source){
        StringBuffer sb = new StringBuffer();
        char [] source_char = source.toCharArray();
        String unicode = null;
        for (int i=0;i<source_char.length;i++) {
            unicode = Integer.toHexString(source_char[i]);
            if (unicode.length() <= 2) {
                unicode = "00" + unicode;
            }
            sb.append("\\u" + unicode);
        }
        System.out.println(sb);
        return sb.toString();
    }
}
