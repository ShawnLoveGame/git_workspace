package com.ai.smart.bottom.meal.service;

import com.ai.smart.bottom.common.service.ThirdService;
import com.ai.smart.bottom.enums.RedisBottomKeyEnum;
import com.ai.smart.bottom.filter.LoginUser;
import com.ai.smart.bottom.helper.SmsHelper;
import com.ai.smart.bottom.meal.mapper.BottomMealSoMapper;
import com.ai.smart.bottom.meal.model.BottomMeal;
import com.ai.smart.bottom.meal.model.BottomMealSo;
import com.ai.smart.bottom.user.mapper.BottomMaUserMapper;
import com.ai.smart.bottom.user.model.BottomActivitySo;
import com.ai.smart.bottom.user.model.BottomActivityUserRel;
import com.ai.smart.bottom.user.model.BottomMaUser;
import com.ai.smart.bottom.user.service.ActivitySoService;
import com.ai.smart.bottom.user.service.ActivityUserRelService;
import com.ai.smart.bottom.vo.MealYwSubVo;
import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.common.helper.AssertHelper;
import com.ai.smart.common.helper.DateHelper;
import com.ai.smart.common.helper.json.JsonHelper;
import com.ai.smart.common.helper.redis.RedisHelper;
import com.fasterxml.jackson.databind.JavaType;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MealSoService {

    @Autowired
    private BottomMealSoMapper bottomMealSoMapper;

    @Autowired
    private BottomMaUserMapper bottomMaUserMapper;

    @Autowired
    private MealService mealService;

    @Autowired
    private RedisHelper redisHelper;

    @Autowired
    private ThirdService thirdService;

    @Autowired
    private ActivityUserRelService activityUserRelService;

    @Autowired
    private ActivitySoService activitySoService;

    @Autowired
    private SmsHelper smsHelper;

    private static final String QD="25";

    //4g飞享套餐
    private static List<String> ncodeList = Lists.newArrayList();
    //4g自选套餐
    private static List<String> nzcodeList = Lists.newArrayList();

    private static List<String> rlist = Lists.newArrayList();
    static {
        ncodeList.add("gl_4g_gprsflysharenew138");
        ncodeList.add("gl_4ggx_138");
        ncodeList.add("gl_4g_gprsflysharenew158");
        ncodeList.add("gl_4g_gprsflysharenew18");
        ncodeList.add("gl_4g_gprsflysharenew238");
        ncodeList.add("gl_4ggx_238");
        ncodeList.add("gl_4g_gprsflysharenew268");
        ncodeList.add("gl_4g_gprsflysharenew28");
        ncodeList.add("gl_4g_gprsflysharenew338");
        ncodeList.add("gl_4ggx_338");
        ncodeList.add("gl_4g_gprsflysharenew38_2");
        ncodeList.add("gl_4g_gek");
        ncodeList.add("gl_4g_gprsflysharenew48_2");
        ncodeList.add("gl_4g_gprsflysharenew588");
        ncodeList.add("gl_4ggx_588");
        ncodeList.add("gl_4g_gprsflysharenew58");
        ncodeList.add("gl_4g_gprsflysharenew58_2");
        ncodeList.add("gl_4g_gprsflysharenew88");
        ncodeList.add("gl_4g_gprsflysharenew88_2");
        ncodeList.add("gl_4gvn_gprsflyshare_138");
        ncodeList.add("gl_4gvn_gprsflyshare_238");
        ncodeList.add("gl_4gvn_gprsflyshare_338");
        ncodeList.add("gl_4gvn_gprsflyshare_588");
        ncodeList.add("gl_4gvn_gprsflyshare_458");
        ncodeList.add("gl_4gvn_gprsflyshare_368");
        ncodeList.add("gl_4gvn_gprsflyshare_268");
        ncodeList.add("gl_4gvn_gprsflyshare_188");
        ncodeList.add("gl_4gvn_gprsflyshare_158");
        ncodeList.add("gl_4ggx_98");
        ncodeList.add("gl_4gtc_98");
        ncodeList.add("gl_4gtc_688");
        ncodeList.add("gl_4ggx_688");
        ncodeList.add("gl_4g_gprsflyshare458");
        ncodeList.add("gl_4g_gprsflyshare368");
        ncodeList.add("gl_4g_gprsflyshare268");
        ncodeList.add("gl_4g_gprsflyshare188");
        ncodeList.add("gl_4g_gprsflyshare158");
        ncodeList.add("gl_4ggx_158");
        ncodeList.add("gl_4gtc_158");
        ncodeList.add("gl_4g_gprsflysharenew8_18n");
        ncodeList.add("gl_4g_gprsflysharenew18_18n");
        ncodeList.add("gl_4g_gprsflysharenew38_18n");
        ncodeList.add("gl_4g_gprsflysharenew58_18");
        ncodeList.add("gl_4g_gprsflysharenew88_18");
        ncodeList.add("gl_4g_gprsflysharenew138_18");
        ncodeList.add("gl_4g_gprsflysharenew188_18");
        ncodeList.add("gl_4g_gprsflysharenew238_18");
        ncodeList.add("gl_4g_gprsflysharenew288_18");
        ncodeList.add("gl_4g_gprsflysharenew388_18");
        ncodeList.add("gl_4g_gprsflysharenew588_18");
        ncodeList.add("gl_4g_gprsflasharenew18b_18n");
        ncodeList.add("gl_4g_gprsflysharenew18_18");
        ncodeList.add("gl_4g_gprsflysharenew38_18");

        nzcodeList.add("gl_4gvn_gprsbaseshare_100");
        nzcodeList.add("gl_4gvn_gprsbasesharenew_100");
        nzcodeList.add("gl_4gvn_gprsbaseshare_130");
        nzcodeList.add("gl_4gvn_gprsbasesharenew_130");
        nzcodeList.add("gl_4gvn_gprsbaseshare_180");
        nzcodeList.add("gl_4gvn_gprsbasesharenew_180");
        nzcodeList.add("gl_4gvn_gprsbasesharenew_20");
        nzcodeList.add("gl_4gvn_gprsbasesharenew_280");
        nzcodeList.add("gl_4gvn_gprsbasesharenew_30");
        nzcodeList.add("gl_4gvn_gprsbaseshare_40");
        nzcodeList.add("gl_4gvn_gprsbasesharenew_40");
        nzcodeList.add("gl_4gvn_gprsbasesharenew_50");
        nzcodeList.add("gl_4gvn_gprsbaseshare_50");
        nzcodeList.add("gl_4gvn_gprsbaseshare_70");
        nzcodeList.add("gl_4gvn_gprsbasesharenew_70");
        nzcodeList.add("gl_4g_voicenew168");
        nzcodeList.add("gl_4g_voice168");
        nzcodeList.add("gl_4g_voicenew18_2");
        nzcodeList.add("gl_4g_voicenew238");
        nzcodeList.add("gl_4g_voice238");
        nzcodeList.add("gl_4g_voicenew28_2");
        nzcodeList.add("gl_4g_voicenew28");
        nzcodeList.add("gl_4g_voicenew328");
        nzcodeList.add("gl_4g_voice328");
        nzcodeList.add("gl_4g_voicenew408");
        nzcodeList.add("gl_4g_voice408");
        nzcodeList.add("gl_4g_voicenew48_2");
        nzcodeList.add("gl_4g_voice48");
        nzcodeList.add("gl_4g_voicenew48");
        nzcodeList.add("gl_4g_voicenew58");
        nzcodeList.add("gl_4g_voice58");
        nzcodeList.add("gl_4g_voicenew88");
        nzcodeList.add("gl_4g_voice88");

        rlist.add("pip_main_qgbxl169");
        rlist.add("pip_main_nolim109");
        rlist.add("gl_bdbxl_tc");
        rlist.add("gl_bdbxl_tcn");
        rlist.add("pip_main_nolim199");
        rlist.add("pip_main_qgbxl159");
        rlist.add("gl_4gvn_4gqgbxl169");
        rlist.add("pip_main_qgbxl198");
        rlist.add("gl_4gqgbxl238");
        rlist.add("pip_main_qgbxl239");
        rlist.add("pip_main_qgbxl289");
        rlist.add("pip_main_qgbxl389");
        rlist.add("gl_4gqgbxl98");
        rlist.add("pip_main_qgbxl99");
        rlist.add("pip_main_zxjh288");
        rlist.add("pip_main_zxjh388");
        rlist.add("pip_main_zxjh588");
        rlist.add("gl_4grwytc198");
        rlist.add("gl_4grwytc78");
        rlist.add("gl_4grwytc98");
        rlist.add("gl_rwy_tcc");
        rlist.add("gl_rwy_tca");
        rlist.add("gl_rwy_tcb");
        rlist.add("pip_gprs_nolim1");
        rlist.add("pip_gprs_nolim6");
        rlist.add("pip_gprs_nolim7");
        rlist.add("pip_gprs_nolim4");
        rlist.add("pip_gprs_nolim5");
        rlist.add("pip_qgbxl_138pack");
        rlist.add("pip_qgbxl_58pack");
        rlist.add("pip_qgbxl_88pack");
    }

    /**
     * migu权益领取
     * @param user
     * @return
     */
    public GlobalResponse getMiguSo(LoginUser user){
        boolean isGet = redisHelper.canBeSubmit(RedisBottomKeyEnum.BOTTOM_MIGU_SO_SUBMIT_.name()+user.getSerialNumber(), "isGet", 3);
        if (!isGet){
            return GlobalResponse.fail("您操作太频繁了哦!");
        }
        AssertHelper.notBlank(user.getSerialNumber(),"手机号码为空");
        // 获取用户当前资费信息
        GlobalResponse currentMeal = thirdService.findCurrentMeal(user.getSerialNumber());
        if (!currentMeal.isSuccess()){
            return currentMeal;
        }
        JavaType jt = JsonHelper.constructParametricType(List.class,MealYwSubVo.class);
        List<MealYwSubVo> detailinfo = JsonHelper.fromJson(JsonHelper.toJson(currentMeal.getData()), jt);
        if (CollectionUtils.isEmpty(detailinfo)){
            return GlobalResponse.fail("未查询到对应的资费信息，无法第一时间为您办理~您可以拨打10086人工客服进行咨询~我们会尽快帮您解决！");
        }
        int month = DateHelper.getMonth(new Date());
        String pcode = "";
        if (month == 6){
            pcode = "gl_ncode_260";
        }
        if (month == 7){
            pcode = "gl_ncode_261";
        }
        String finalPcode = pcode;
        MealYwSubVo mealYwSubVo = detailinfo.stream().filter(a -> Objects.equals(finalPcode, a.getITEMID())).findFirst().orElse(null);
        if (mealYwSubVo != null){
            return GlobalResponse.fail("您已经办理过当前权益哦！");
        }
        // 判断是否办理过不限量
        MealYwSubVo sub = detailinfo.stream().filter(a -> rlist.contains(a.getITEMID())).findFirst().orElse(null);
        if (sub == null){
            return GlobalResponse.fail("您还没有办理不限量套餐或不限量叠加包，暂不能领取该权益哦~");
        }
        BottomMealSo bottomMealSo = saveMiguSo(String.valueOf(month), pcode,"9.9元/月", user);
        GlobalResponse globalResponse = thirdService.addMealByNcode(pcode, user.getSerialNumber(), "2");
        if (globalResponse.isSuccess()){
            bottomMealSo.setStatus(1);
            bottomMealSoMapper.updateStatusById(bottomMealSo.getId());
            return globalResponse;
        }else{
            return GlobalResponse.fail("您已经办理过当前权益哦！");
        }
    }

    /**
     * 办理套餐业务
     * @param user
     * @return
     */
    public GlobalResponse createMealSo(String ncode, LoginUser user){
        try {
            boolean isGet = redisHelper.canBeSubmit(RedisBottomKeyEnum.BOTTOM_MEAL_SO_SUBMIT_.name()+user.getSerialNumber(), "isGet", 3);
            if (!isGet){
                return GlobalResponse.fail("您操作太频繁了哦!");
            }
            AssertHelper.notBlank(ncode,"套餐ncode为空");
            AssertHelper.notBlank(user.getSerialNumber(),"手机号码为空");
            // 获取用户当前资费信息
            GlobalResponse currentMeal = thirdService.findCurrentMeal(user.getSerialNumber());
            if (!currentMeal.isSuccess()){
                return currentMeal;
            }
            JavaType jt = JsonHelper.constructParametricType(List.class,MealYwSubVo.class);
            List<MealYwSubVo> detailinfo = JsonHelper.fromJson(JsonHelper.toJson(currentMeal.getData()), jt);
            if (CollectionUtils.isEmpty(detailinfo)){
                return GlobalResponse.fail(" 本业务与您的服务/业务可能互斥，无法第一时间为您办理~您可以拨打10086人工客服进行咨询~我们会尽快帮您解决！");
            }
            // 不限量叠加包
            if (Objects.equals(ncode,"1")){
                // 判断资费主体信息
                List<MealYwSubVo> sublist = detailinfo.stream().filter(a -> ncodeList.contains(a.getITEMID()) || nzcodeList.contains(a.getITEMID())).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(sublist)){
                    return GlobalResponse.fail("本业务与您的服务/业务可能互斥，无法第一时间为您办理~ 您可以拨打10086人工客服进行咨询~我们会尽快帮您解决！");
                }
                Map<String, String> current = getCurrent(sublist.get(0));
                if (!Strings.isNullOrEmpty(current.get("amount"))){
                    BigDecimal bigDecimal = new BigDecimal(current.get("amount"));
                    if (bigDecimal.compareTo(new BigDecimal("138")) > -1){
                        ncode = "pip_qgbxl_138pack";
                    }else if(bigDecimal.compareTo(new BigDecimal("88")) > -1){
                        ncode = "pip_qgbxl_88pack";
                    }else if(bigDecimal.compareTo(new BigDecimal("58")) > -1){
                        ncode = "pip_qgbxl_58pack";
                    }else{
                        return GlobalResponse.fail("本业务与您的服务/业务可能互斥，无法第一时间为您办理~ 您可以拨打10086人工客服进行咨询~我们会尽快帮您解决！");
                    }
                }else{
                    return GlobalResponse.fail("本业务与您的服务/业务可能互斥，无法第一时间为您办理~您可以拨打10086人工客服进行咨询~我们会尽快帮您解决！");
                }
            }
            // 判断当前是否办理过该套餐
            String finalNcode = ncode;
            MealYwSubVo first = detailinfo.stream().filter(a -> Objects.equals(a.getITEMID(), finalNcode)).findFirst().orElse(null);
            if (first != null){
                return GlobalResponse.fail("本业务与您的服务/业务可能互斥，无法第一时间为您办理~您可以拨打10086人工客服进行咨询~我们会尽快帮您解决！");
            }
            BottomMeal mealByNcode = mealService.findMealByNcode(ncode);
            AssertHelper.notNull(mealByNcode,"该套餐不存在或已经失效");
            BottomMealSo mealSo = new BottomMealSo();
            mealSo.setMealId(mealByNcode.getId());
            mealSo.setMealName(mealByNcode.getMealName());
            mealSo.setMealTitle(mealByNcode.getMealTitle());
            mealSo.setMealPrice(mealByNcode.getMealPrice());
            mealSo.setOffType(mealByNcode.getOffType());
            mealSo.setCreateTime(new Date());
            mealSo.setSerialNum(user.getSerialNumber());
            mealSo.setStatus(0);
            mealSo.setUserId(user.getId());
            mealSo.setNcode(mealByNcode.getNcode());
            bottomMealSoMapper.insert(mealSo);
            // 主套餐
            if (Objects.equals(mealByNcode.getIsMain(),1)){
                //169 套餐  99 套餐   判断当月是否有资费主体变更
                GlobalResponse gr = thirdService.checkIsMealOnMonth(user.getSerialNumber());
                if (!gr.isSuccess()){
                    return GlobalResponse.fail("本业务与您的服务/业务可能互斥，无法第一时间为您办理~您可以拨打10086人工客服进行咨询~我们会尽快帮您解决！");
                }
                // 是否有资费主体变更
                boolean flag = (boolean)gr.getData();
                if (flag){
                    return GlobalResponse.fail(" 本业务与您的服务/业务可能互斥，无法第一时间为您办理~您可以拨打10086人工客服进行咨询~我们会尽快帮您解决！");
                }
                String param = "";
                if(Objects.equals(ncode,"pip_main_qgbxl169")){
                    param = "gl_4gvn_4gqgbxl169";
                }
                if(Objects.equals(ncode,"pip_main_qgbxl99")){
                    param = "gl_4gvn_4gqgbxl99";
                }
                // 业务转移
                GlobalResponse globalResponse = thirdService.changeMeal(ncode, user.getSerialNumber(),mealByNcode.getOffType().toString(),param);
                if (globalResponse.isSuccess()){
                    bottomMealSoMapper.updateStatusById(mealSo.getId());
                    // 办理世界杯
                    int month = DateHelper.getMonth(new Date());
                    String pcode = "";
                    if (month == 6){
                        pcode = "gl_ncode_260";
                    }
                    if (month == 7){
                        pcode = "gl_ncode_261";
                    }
                    BottomMealSo bottomMealSo = saveMiguSo(String.valueOf(month), pcode,"9.9元/月", user);
                    //立即生效
                    globalResponse = thirdService.addMealByNcode(pcode, user.getSerialNumber(),"2");
                    if (globalResponse.isSuccess()){
                        bottomMealSo.setStatus(1);
                        bottomMealSoMapper.updateStatusById(bottomMealSo.getId());
                    }
                    doLargessFee(user.getId(),new BigDecimal("10"),ncode);
                    return GlobalResponse.success("办理成功！");
                }
            }else{
                // 新增业务
                GlobalResponse globalResponse = thirdService.addMealByNcode(ncode, user.getSerialNumber(),mealByNcode.getOffType().toString());
                if (globalResponse.isSuccess()){
                    bottomMealSoMapper.updateStatusById(mealSo.getId());
                    BottomMealSo bottomMealSo = saveMiguSo("咪咕视频畅看套餐7天免费体验特权","gl_mgwdcp_qyb", "0元/月", user);
                    //立即生效
                    globalResponse = thirdService.addMealByNcode("gl_mgwdcp_qyb", user.getSerialNumber(),"2");
                    if (globalResponse.isSuccess()){
                        bottomMealSo.setStatus(1);
                        bottomMealSoMapper.updateStatusById(bottomMealSo.getId());
                    }
                    doLargessFee(user.getId(),new BigDecimal("3"),ncode);
                    return GlobalResponse.success("办理成功！");
                }

            }
            return GlobalResponse.fail("本业务与您的服务/业务可能互斥，无法第一时间为您办理~您可以拨打10086人工客服进行咨询~我们会尽快帮您解决！");
        } catch (Exception e) {
            log.error("createMealSo exception:{}",user.getSerialNumber(),e);
        }
        return GlobalResponse.fail("本业务与您的服务/业务可能互斥，无法第一时间为您办理~您可以拨打10086人工客服进行咨询~我们会尽快帮您解决！");
    }

    /**
     * 咪咕订单
     * @param month
     * @param price
     * @param user
     * @return
     */
    public BottomMealSo saveMiguSo(String month,String ncode,String price,LoginUser user){
        BottomMealSo mealSo = new BottomMealSo();
        mealSo.setMealId(-1L);
        mealSo.setMealName("咪咕视频畅看套餐（标准版）");
        mealSo.setNcode(ncode);
        mealSo.setMealTitle(month);
        mealSo.setMealPrice(price);
        mealSo.setOffType(2);
        mealSo.setCreateTime(new Date());
        mealSo.setSerialNum(user.getSerialNumber());
        mealSo.setStatus(0);
        mealSo.setUserId(user.getId());
        bottomMealSoMapper.insert(mealSo);
        return mealSo;
    }

    /**
     * 处理红包赠费操作
     * @param amount
     * @return
     */
    @Transactional
    public GlobalResponse doLargessFee(Long userId,BigDecimal amount,String ncode){
        // 红包下单处理
        BottomActivityUserRel currentIv = activityUserRelService.getCurrentIv(userId);
        if (currentIv == null){
            return GlobalResponse.success("当前没有绑定信息");
        }
        BottomMaUser bottomMaUser = bottomMaUserMapper.selectByPrimaryKey(userId);
        if (activitySoService.checkTenRed(userId)){
            String redisJson = redisHelper.get(RedisBottomKeyEnum.BOTTOM_SMS_TAG_.name() + bottomMaUser.getSerialNum());
            if (!Strings.isNullOrEmpty(redisJson)){
                smsHelper.sendSms(bottomMaUser.getSerialNum(),"尊敬的用户：您参与重庆移动“发红包 赚赏金”活动推荐的用户数已达上限。感谢您对重庆移动的支持，更多优惠可关注重庆移动官方微信点击我的优惠查看。【中国移动】");
                redisHelper.setex(RedisBottomKeyEnum.BOTTOM_SMS_TAG_.name() + bottomMaUser.getSerialNum(),60*60*24*30*2,"isSend");
            }
            return GlobalResponse.success("您参与重庆移动“发红包 赚赏金”领取红包次数已达上限");
        }

        // 生成订单
        BottomActivitySo bottomActivitySo = new BottomActivitySo();
        bottomActivitySo.setAmount(amount);
        bottomActivitySo.setCreateTime(new Date());
        bottomActivitySo.setNcode(ncode);
        bottomActivitySo.setSerialNumber(bottomMaUser.getSerialNum());
        bottomActivitySo.setUserId(userId);
        bottomActivitySo.setStatus(0);
        activitySoService.createSo(bottomActivitySo);
        String yyyyMMdd = DateHelper.converToStringDate(new Date(), "yyyyMMdd");
        long l = bottomActivitySo.getId() + 10000000;
        String formNum = yyyyMMdd+QD+l;
        String notes = "不限量世界杯活动赠费";
        String subjectId = "";
        GlobalResponse globalResponse = thirdService.largessFee(formNum, bottomMaUser.getSerialNum(), amount, notes, subjectId);
        if (globalResponse.isSuccess()){
            smsHelper.sendSms(bottomMaUser.getSerialNum(),"尊敬的用户：您通过重庆移动“发红包 赚赏金”活动成功办理不限量业务，本次瓜分的"+amount+"元赏金已赠送到账。赠送话费有效期12个月，不抵扣历史欠费及滞纳金，不抵扣国际/港澳台漫游及长途费、不抵扣电子商务、梦网sp、手机投注等代收费。【中国移动】 ");
            bottomActivitySo.setStatus(1);
        }
        bottomActivitySo.setRemark(JsonHelper.toJson(globalResponse));
        activitySoService.updateByPrimaryKeySelective(bottomActivitySo);
        // 给父节点上账
        Long parentUserId = currentIv.getParentUserId();
        BottomMaUser parentUser = bottomMaUserMapper.selectByPrimaryKey(parentUserId);
        if (activitySoService.checkTenRed(parentUserId)){
            String redisJson = redisHelper.get(RedisBottomKeyEnum.BOTTOM_SMS_TAG_.name() + parentUser.getSerialNum());
            if (!Strings.isNullOrEmpty(redisJson)){
                smsHelper.sendSms(parentUser.getSerialNum(),"尊敬的用户：您参与重庆移动“发红包 赚赏金”活动推荐的用户数已达上限。感谢您对重庆移动的支持，更多优惠可关注重庆移动官方微信点击我的优惠查看。【中国移动】");
                redisHelper.setex(RedisBottomKeyEnum.BOTTOM_SMS_TAG_.name() + parentUser.getSerialNum(),60*60*24*30*2,"isSend");
            }
            return GlobalResponse.success("您参与重庆移动“发红包 赚赏金”领取红包次数已达上限");
        }

        // 生成订单
        BottomActivitySo pso = new BottomActivitySo();
        pso.setAmount(amount);
        pso.setCreateTime(new Date());
        pso.setNcode(ncode);
        pso.setSerialNumber(parentUser.getSerialNum());
        pso.setUserId(parentUser.getId());
        pso.setStatus(0);
        pso.setSubUserId(userId);
        activitySoService.createSo(pso);
        long pl = pso.getId() + 10000000;
        String pformNum = yyyyMMdd+QD+pl;
        String pnotes = "不限量世界杯活动赠费";
        String psubjectId = "";
        GlobalResponse pres = thirdService.largessFee(pformNum, parentUser.getSerialNum(), amount, pnotes, psubjectId);
        if (pres.isSuccess()){
            pso.setStatus(1);
            smsHelper.sendSms(parentUser.getSerialNum(),"尊敬的用户：您参与重庆移动“发红包 赚赏金”活动推荐的尾号"+bottomMaUser.getSerialNum().substring(8)+"的用户已成功办理不限量业务，本次瓜分的"+amount+"元赏金已赠送到账。赠送话费有效期12个月，不抵扣历史欠费及滞纳金，不抵扣国际/港澳台漫游及长途费、不抵扣电子商务、梦网sp、手机投注等代收费。");
        }
        pso.setRemark(JsonHelper.toJson(pres));
        activitySoService.updateByPrimaryKeySelective(pso);
        // 生效绑定关系
        activityUserRelService.updateStatus(currentIv);
        return GlobalResponse.success("赠费操作");
    }


    /**
     * 解析资费
     * @param mealYwSubVo
     * @return
     */
    public Map<String,String>  getCurrent(MealYwSubVo mealYwSubVo){
        String itemid = mealYwSubVo.getITEMID();
        //解析itemid 判断资费档次
        String amount = "";
        String type = "";
        //4G飞享/自选套餐
        if(itemid.contains("gl_4g_gprsflysharenew")){
            amount = itemid.split("gl_4g_gprsflysharenew")[1].split("_")[0];
            type = "gprsfly";
        }else if(itemid.contains("gl_4g_gprsflyshare")){
            amount = itemid.split("gl_4g_gprsflyshare")[1].split("_")[0];
            type = "gprsfly";
        }else if(itemid.contains("gl_4gvn_gprsflyshare_")){
            amount = itemid.split("gl_4gvn_gprsflyshare_")[1].split("_")[0];
            type = "gprsfly";
        }else if(itemid.contains("gl_4gvn_gprsbaseshare_")){
            amount = itemid.split("gl_4gvn_gprsbaseshare_")[1].split("_")[0];
            type = "gprsfly";
        }else if(itemid.contains("gl_4gvn_gprsbasesharenew_")){
            amount = itemid.split("gl_4gvn_gprsbasesharenew_")[1].split("_")[0];
            type = "gprsfly";
        }else if(itemid.contains("gl_4g_gprsflasharenew")){
            amount = itemid.split("gl_4g_gprsflasharenew")[1].split("_")[0].split("b")[1];
            type = "gprsfly";
        }else if(itemid.contains("gl_4ggx_")){
            amount = itemid.split("gl_4ggx_")[1].split("_")[0];
            type = "gprsfly";
        }else if(itemid.contains("gl_4gtc_")){
            amount = itemid.split("gl_4gtc_")[1].split("_")[0];
            type = "gprsfly";
        }else if(itemid.contains("gl_4g_voicenew")){
            amount = itemid.split("gl_4g_voicenew")[1].split("_")[0];
            type = "voice";
        }else if(itemid.contains("gl_4g_voice")){
            amount = itemid.split("gl_4g_voice")[1].split("_")[0];
            type = "voice";
        }else if(itemid.contains("gl_4g_gek")){
            amount = "38";
            type = "gprsfly";
        }
        Map<String, String> re = Maps.newHashMap();
        re.put("amount",amount);
        re.put("type",type);
        return re;
    }


}
