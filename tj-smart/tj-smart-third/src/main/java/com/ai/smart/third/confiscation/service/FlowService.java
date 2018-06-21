package com.ai.smart.third.confiscation.service;


import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.common.helper.AssertHelper;
import com.ai.smart.common.helper.MockHelper;
import com.ai.smart.common.helper.json.JsonHelper;
import com.ai.smart.third.confiscation.vo.MyPackageVo;
import com.ai.smart.third.helper.SvcHelper;
import com.ai.smart.third.vo.ProAcVo;
import com.ai.smart.third.vo.Uip100006;
import com.ai.smart.third.vo.Uip100008;
import com.fasterxml.jackson.databind.JavaType;
import com.linkage.appframework.data.DataMap;
import com.linkage.appframework.data.IData;
import com.linkage.appframework.data.IDataset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class FlowService {

    @Autowired
    private MockHelper mockHelper;
    @Autowired
    private LoggingService loggingService;


    /**
     * 流量包办理
     * @param serialNum
     * @param packageCode
     * @return
     */
    public GlobalResponse handlePackage(String serialNum, String packageCode,String efftType){
        AssertHelper.notBlank(serialNum,"手机号码为空");
        AssertHelper.notBlank(packageCode,"套餐编码");
        if(mockHelper.isMock()){
            return GlobalResponse.success("true");
        }
        try{
            IData param = new DataMap();
            param.put("TELNUM",serialNum);
            param.put("NCODE",packageCode);
            param.put("STYPE","ADD");
            param.put("EFFTYPE",efftType);
            param.put("NOTES","套餐办理");
            GlobalResponse globalResponse = SvcHelper.postSvc("100045", param, "新增业务办理");
            loggingService.writeLogInfo(serialNum,"套餐办理",globalResponse.getData());
            if(globalResponse.isSuccess()){
                String json = JsonHelper.toJson(globalResponse.getData());
                JavaType jt = JsonHelper.constructParametricType(List.class,HashMap.class);
                List<HashMap> list= JsonHelper.fromJson(json,jt);
                HashMap hashMap = list.get(0);
                if (hashMap.get("X_RESULTCODE").toString().equals("0")){
                    return GlobalResponse.success("业务办理成功");
                }else{
                    return GlobalResponse.fail(hashMap.get("X_RESULTINFO").toString());
                }
            }
            return  globalResponse;
        }catch(Exception e){
            log.error("addMealByNcode exception",e);
        }
        return GlobalResponse.fail("业务办理失败");
    }

    /**
     * 流量包退订
     * @param serialNum
     * @param packageCode
     * @return
     */
    public GlobalResponse unsubscribePackage(String serialNum,String packageCode){
        AssertHelper.notBlank(serialNum,"手机号码为空");
        AssertHelper.notBlank(packageCode,"流量包编码为空");
        if (mockHelper.isMock()){
            return GlobalResponse.success(true);
        }
        try{
            IData param = new DataMap();
            param.put("TELNUM",serialNum);
            param.put("NCODE",packageCode);
            param.put("STYPE","DEL");
            param.put("EFFTYPE","");
            param.put("NOTES","套餐办理");
            GlobalResponse globalResponse = SvcHelper.postSvc("100045", param, "流量包退订");
            loggingService.writeLogInfo(serialNum,"流量包退订",globalResponse.getData());
            if(globalResponse.isSuccess()){
                String json = JsonHelper.toJson(globalResponse.getData());
                JavaType jt = JsonHelper.constructParametricType(List.class,HashMap.class);
                List<HashMap> list= JsonHelper.fromJson(json,jt);
                HashMap hashMap = list.get(0);
                if (hashMap.get("X_RESULTCODE").toString().equals("0")){
                    return GlobalResponse.success("业务办理成功");
                }else{
                    return GlobalResponse.fail(hashMap.get("X_RESULTINFO").toString());
                }
            }
            return GlobalResponse.fail("流量包退订失败");
        }catch(Exception e){
            log.error("unsubscribePackage exception"+e);
            return GlobalResponse.fail("正在加进对接中。。。。");
        }
    }

    /**
     * 查询订购的流量包
     * @param serialNum
     * @return
     */
    public GlobalResponse findMyPackage(String serialNum){
        AssertHelper.notBlank(serialNum,"手机号码为空");
        List<MyPackageVo> list1=new ArrayList<>();
        if (mockHelper.isMock()){
            MyPackageVo mp=new MyPackageVo();
            MyPackageVo mp1=new MyPackageVo();
            MyPackageVo mp2=new MyPackageVo();
            MyPackageVo mp3=new MyPackageVo();

            mp.setIsUse(0);
            mp.setPackageName("国内流量月包");
            mp.setPackagePrice(new BigDecimal(10.00));
            mp.setPackageSize(100);
            mp.setLastPackage(0);
            mp.setEndTime(new Date());
            mp.setPackageCode("100009");

            mp1.setIsUse(0);
            mp1.setPackageName("国内流量半年包");
            mp1.setPackagePrice(new BigDecimal(60.00));
            mp1.setPackageSize(1024);
            mp1.setLastPackage(0);
            mp1.setEndTime(new Date());
            mp1.setPackageCode("1000079");

            mp2.setIsUse(0);
            mp2.setPackageName("国内流量月包");
            mp2.setPackagePrice(new BigDecimal(10.00));
            mp2.setPackageSize(100);
            mp2.setLastPackage(0);
            mp2.setEndTime(new Date());
            mp2.setPackageCode("1000018");

            mp3.setIsUse(0);
            mp3.setPackageName("国内流量月包");
            mp3.setPackagePrice(new BigDecimal(20.00));
            mp3.setPackageSize(300);
            mp3.setLastPackage(0);
            mp3.setEndTime(new Date());
            mp3.setPackageCode("100005");

            list1.add(mp);
            list1.add(mp1);
            list1.add(mp2);
            list1.add(mp3);
            return GlobalResponse.success(list1);
        }
        try{
            IData param = new DataMap();
            param.put("TELNUM",serialNum);
            //查询结果类型  ServiceAndPrivilege查优惠和服务
            //Service 查服务  其他都查优惠
            param.put("FLAG","ServiceAndPrivilege");
            param.put("QRYNEXTMONTH",0);//查询结果的生效类型  0本月生效  1下月及以后生效
            //param.put("privid","");//写入此字段则FLAG和QRYNEXTMONTH失效，只查所有的优惠订购记录
            GlobalResponse globalResponse = SvcHelper.postSvc("100006",param,"查询订购的流量包");
            loggingService.writeLogInfo(serialNum,"查询订购的流量包",globalResponse.getData());
            if(globalResponse.isSuccess()) {
                String json = JsonHelper.toJson(globalResponse.getData());
                JavaType javaType = JsonHelper.constructParametricType(List.class, Uip100006.class);
                List<Uip100006> list = JsonHelper.fromJson(json, javaType);
                return GlobalResponse.success(list);
            }
            return GlobalResponse.fail("正在加进对接中。。。。");

        }catch(Exception e){
            log.error("findMyPackage exception"+e);
            return GlobalResponse.fail("正在加进对接中。。。。");
        }
    }
}
