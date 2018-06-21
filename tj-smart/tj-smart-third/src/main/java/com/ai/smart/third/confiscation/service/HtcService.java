package com.ai.smart.third.confiscation.service;

import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.common.helper.AssertHelper;
import com.ai.smart.common.helper.Date.DateUtil;
import com.ai.smart.common.helper.MockHelper;
import com.ai.smart.common.helper.json.JsonHelper;
import com.ai.smart.third.confiscation.model.ConsumeColumn;
import com.ai.smart.third.confiscation.model.CurrentCharges;
import com.ai.smart.third.confiscation.model.Score;
import com.ai.smart.third.confiscation.model.UserCenterBasicInfo;
import com.ai.smart.third.confiscation.utils.AmountUtils;
import com.ai.smart.third.helper.SvcHelper;
import com.ai.smart.third.vo.*;
import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linkage.appframework.data.DataMap;
import com.linkage.appframework.data.IData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class HtcService {

    @Autowired
    private MockHelper mockHelper;
    @Autowired
    private LoggingService loggingService;

    /**
     * 三户查询
     * @param serialNum
     * @return
     */

    public GlobalResponse findHtcBySerialNum(String serialNum){
        AssertHelper.notBlank(serialNum,"手机号码为空");
        if(mockHelper.isMock()){
            return GlobalResponse.success("true");
        }
        try{
            IData para = new DataMap();
            para.put("TELNUM",serialNum);
            GlobalResponse globalResponse =  SvcHelper.postSvc("100004",para,"三户查询");
            if(globalResponse.isSuccess()){
                String json = JsonHelper.toJson(globalResponse.getData());
                JavaType javaType = JsonHelper.constructParametricType(List.class, Uip100004.class);
                log.info(" java type >>>>>>>> "+javaType.getTypeName());
                List<Uip100004> list = JsonHelper.fromJson(json, javaType);
                loggingService.writeLogInfo(serialNum,"三户查询",globalResponse.getData());
                return GlobalResponse.success(list.get(0));
            }
            return globalResponse;
        }catch(Exception e){
            log.error("findHtcBySerialNum exception"+e);
        }
        return GlobalResponse.fail("获取用户信息[100004]异常");
    }


    /**
     * 话费查询
     * @param serialNum
     * @return
     */
    public GlobalResponse findTelFare(String serialNum){
        AssertHelper.notBlank(serialNum,"手机号码为空");
        if(mockHelper.isMock()){
            //返回Map<String,List>
            Map<String,Object> dataMap = Maps.newHashMap();

            List<ConsumeColumn> list1 = new LinkedList<>();
            List<ConsumeColumn> list2 = new LinkedList<>();
            List<ConsumeColumn> list3 = new LinkedList<>();
            List<ConsumeColumn> list4 = new LinkedList<>();
            List<ConsumeColumn> list5 = new LinkedList<>();
            List<ConsumeColumn> list6 = new LinkedList<>();
            List<ConsumeColumn> list7 = new LinkedList<>();

            list1.add(new ConsumeColumn("当月消费",110,true));
            list2.add(new ConsumeColumn("话费余额",88.55,true));
            list3.add(new ConsumeColumn("套餐及固定费用",400,true));
            list3.add(new ConsumeColumn("基本套餐费",200,false));
            list3.add(new ConsumeColumn("语音加油包",100,false));
            list3.add(new ConsumeColumn("宽带套餐包",100,false));
            list4.add(new ConsumeColumn("套餐外费用",30,true));
            list4.add(new ConsumeColumn("短信",10,false));
            list4.add(new ConsumeColumn("流量",10,false));
            list4.add(new ConsumeColumn("彩信",10,false));
            list5.add(new ConsumeColumn("增值业务费用",120,true));
            list5.add(new ConsumeColumn("咪咕定向流量包",10,false));
            list6.add(new ConsumeColumn("减免",-110,true));
            list7.add(new ConsumeColumn("消费合计",0,true));

            dataMap.put("monthConsume",list1);//当月消费
            dataMap.put("remainingCharge",list2);//话费余额
            dataMap.put("bundleConsume",list3);//套餐及固定费用
            dataMap.put("outOfBundle",list4);//套餐外费用
            dataMap.put("AddedServiceConsume",list5);//增值业务消费
            dataMap.put("reduce",list6);//减免
            dataMap.put("totalConsume",list7);//总体消费

            return GlobalResponse.success(dataMap);
        }
        try{
            //暂时不知道返回内容英文属性名  无法创建实体

            loggingService.writeLogInfo(serialNum,"话费查询","MessageContent");

        }catch(Exception e){
            log.error("findTelFare exception",e);
        }
        loggingService.writeLogInfo(serialNum,"话费查询","MessageContent");
        return GlobalResponse.fail("正在加进对接中。。。。");
    }

    /**
     * 余量查询 no
     * @param serialNum
     * @return
     */
    public GlobalResponse findMargin(String serialNum,String month)  {

        AssertHelper.notBlank(serialNum,"手机号码为空");
        if(mockHelper.isMock()){
            //模拟调用接口  返回Map<String,List>
            Map<String,List> dataMap = Maps.newHashMap();
            List<Uip100007> list=new ArrayList<>();

            Uip100007 u0=new Uip100007();
            Uip100007 u1=new Uip100007();
            Uip100007 u2=new Uip100007();
            Uip100007 u3=new Uip100007();
            Uip100007 u4=new Uip100007();
            Uip100007 u5=new Uip100007();

            u0.setType("5");
            u0.setUsed("500.49");
            u0.setTotal("1000.00");
            u0.setTfflag("1");
            u0.setRemain("500.00");
            u0.setRow("sds");
            u0.setPrivid("1");
            u0.setPriname("费用");
            u0.setDetailinfo("话费");

            u1.setType("1");
            u1.setUsed("30000");
            u1.setTotal("60000");
            u1.setTfflag("1");
            u1.setRemain("30000");
            u1.setRow("sds");
            u1.setPrivid("1");
            u1.setPriname("国内语音通话1000分钟");
            u1.setDetailinfo("国内语音通话1000分钟");


            u2.setType("1");
            u2.setUsed("20000");
            u2.setTotal("60000");
            u2.setTfflag("1");
            u2.setRemain("40000");
            u2.setRow("sds");
            u2.setPrivid("1");
            u2.setPriname("省内语音通话1000分钟");
            u2.setDetailinfo("国内语音通话1000分钟");



            u3.setType("4");
            u3.setUsed("52428800");
            u3.setTotal("209715200");
            u3.setTfflag("1");
            u3.setRemain("157286400");
            u3.setRow("sds");
            u3.setPrivid("1");
            u3.setPriname("国内流量1M包月");
            u3.setDetailinfo("国内流量1M包月");

            u4.setType("4");
            u4.setUsed("52428800");
            u4.setTotal("209715200");
            u4.setTfflag("1");
            u4.setRemain("157286400");
            u4.setRow("sds");
            u4.setPrivid("1");
            u4.setPriname("国内流量1M包月");
            u4.setDetailinfo("国内流量1M包月");

            u5.setType("2");
            u5.setUsed("500");
            u5.setTotal("1000");
            u5.setTfflag("1");
            u5.setRemain("500");
            u5.setRow("sds");
            u5.setPrivid("1");
            u5.setPriname("国内短信1000条包月");


            list.add(u0);
            list.add(u1);
            list.add(u2);
            list.add(u3);
            list.add(u4);
            list.add(u5);

            List<Uip100007> listVoice=new ArrayList<>();
            List<Uip100007> listFlow=new ArrayList<>();
            List<Uip100007> listMessage=new ArrayList<>();
            List<Uip100007> listFee=new ArrayList<>();

            List<MarginVo>  voList=new ArrayList<>();
            for (Uip100007 uip:list){
                if(uip.getType()=="5"||"5".equals(uip.getType())){//费用
                        listFee.add(uip);

                }  if(uip.getType()=="1"||"1".equals(uip.getType())){//语音
                        listVoice.add(uip);
                 }  if(uip.getType()=="4"||"4".equals(uip.getType())){//流量
                        listFlow.add(uip);
                 }  if(uip.getType()=="2"||"2".equals(uip.getType())){//短信
                        listMessage.add(uip);
                 }
             }
            Double sumTotalFee=0.0;
            Double sumRemainFee=0.0;
            Double sumUsedFee=0.0;
            for (Uip100007 uip:listFee){
                sumTotalFee+=Double.valueOf(uip.getTotal());
                sumRemainFee+=Double.valueOf(uip.getRemain());
                sumUsedFee+=Double.valueOf(uip.getUsed());

            }
            MarginVo marginVo0=new MarginVo();
            marginVo0.setSumToal(sumTotalFee);
            marginVo0.setSumRemain(sumRemainFee);
            marginVo0.setSumUsed(sumUsedFee);
            marginVo0.setList(listFee);

            Double sumTotalVoice=0.0;
            Double sumRemainVoice=0.0;
            Double sumUsedVoice=0.0;
            for (Uip100007 uip:listVoice){
                sumTotalVoice+=Double.valueOf(uip.getTotal());
                sumRemainVoice+=Double.valueOf(uip.getRemain());
                sumUsedVoice+=Double.valueOf(uip.getUsed());

            }
            MarginVo marginVo1=new MarginVo();
            marginVo1.setSumToal(sumTotalVoice);
            marginVo1.setSumRemain(sumRemainVoice);
            marginVo1.setSumUsed(sumUsedVoice);
            marginVo1.setList(listVoice);

            Double sumTotalFlow=0.0;
            Double sumRemainFlow=0.0;
            Double sumUsedFlow=0.0;
            for (Uip100007 uip:listFlow){
                sumTotalFlow+=Double.valueOf(uip.getTotal());
                sumRemainFlow+=Double.valueOf(uip.getRemain());
                sumUsedFlow+=Double.valueOf(uip.getUsed());

            }
            MarginVo marginVo2=new MarginVo();
            marginVo2.setSumToal(sumTotalFlow);
            marginVo2.setSumRemain(sumRemainFlow);
            marginVo2.setSumUsed(sumUsedFlow);
            marginVo2.setList(listFlow);

            Double sumTotalMessage=0.0;
            Double sumRemainMessage=0.0;
            Double sumUsedMessage=0.0;
            for (Uip100007 uip:listMessage){
                sumTotalMessage+=Double.valueOf(uip.getTotal());
                sumRemainMessage+=Double.valueOf(uip.getRemain());
                sumUsedMessage+=Double.valueOf(uip.getUsed());

            }
            MarginVo marginVo3=new MarginVo();
            marginVo3.setSumToal(sumTotalMessage);
            marginVo3.setSumRemain(sumRemainMessage);
            marginVo3.setSumUsed(sumUsedMessage);
            marginVo3.setList(listMessage);

            Map<String,MarginVo> map= Maps.newHashMap();
            map.put("1",marginVo0);
            map.put("2",marginVo1);
            map.put("4",marginVo2);
            map.put("5",marginVo3);
            return  GlobalResponse.success(map);
        }
        try{
            List<Uip100007> listVoice=new ArrayList<>();
            List<Uip100007> listFlow=new ArrayList<>();
            List<Uip100007> listMessage=new ArrayList<>();
            List<Uip100007> listFee=new ArrayList<>();
            IData para = new DataMap();
            para.put("TELNUM",serialNum);
            Date date=DateUtils.parseDate(month,"yyyyMMdd");
            String dateMonth=DateFormatUtils.format(date,"yyyyMM");
            para.put("CYCLE",dateMonth);
            GlobalResponse globalResponse = SvcHelper.postSvc("100007",para,"余量查询");
            loggingService.writeLogInfo(serialNum,"余量查询",globalResponse.getData());
            if(globalResponse.isSuccess()){
                String json= JsonHelper.toJson(globalResponse.getData());
                JavaType javaType = JsonHelper.constructParametricType(List.class, Uip100007.class);
                List<Uip100007> list = JsonHelper.fromJson(json, javaType);
                for (Uip100007 uip:list){
                    if(uip.getType()=="5"||"5".equals(uip.getType())){
                        listFee.add(uip);
                    }  if(uip.getType()=="1"||"1".equals(uip.getType())){
                        listVoice.add(uip);
                    }  if(uip.getType()=="4"||"4".equals(uip.getType())){
                        listFlow.add(uip);
                    }  if(uip.getType()=="2"||"2".equals(uip.getType())){
                        listMessage.add(uip);
                    }
                }
                Double sumTotalFee=0.0;
                Double sumRemainFee=0.0;
                Double sumUsedFee=0.0;
                for (Uip100007 uip:listFee){
                    sumTotalFee+=Double.valueOf(uip.getTotal());
                    sumRemainFee+=Double.valueOf(uip.getRemain());
                    sumUsedFee+=Double.valueOf(uip.getUsed());

                }
                MarginVo marginVo0=new MarginVo();
                marginVo0.setSumToal(sumTotalFee);
                marginVo0.setSumRemain(sumRemainFee);
                marginVo0.setSumUsed(sumUsedFee);
                marginVo0.setList(listFee);

                Double sumTotalVoice=0.0;
                Double sumRemainVoice=0.0;
                Double sumUsedVoice=0.0;
                for (Uip100007 uip:listVoice){
                    sumTotalVoice+=Double.valueOf(uip.getTotal());
                    sumRemainVoice+=Double.valueOf(uip.getRemain());
                    sumUsedVoice+=Double.valueOf(uip.getUsed());

                }
                MarginVo marginVo1=new MarginVo();
                marginVo1.setSumToal(sumTotalVoice);
                marginVo1.setSumRemain(sumRemainVoice);
                marginVo1.setSumUsed(sumUsedVoice);
                marginVo1.setList(listVoice);

                Double sumTotalFlow=0.0;
                Double sumRemainFlow=0.0;
                Double sumUsedFlow=0.0;
                for (Uip100007 uip:listFlow){
                    sumTotalFlow+=Double.valueOf(uip.getTotal());
                    sumRemainFlow+=Double.valueOf(uip.getRemain());
                    sumUsedFlow+=Double.valueOf(uip.getUsed());

                }
                MarginVo marginVo2=new MarginVo();
                marginVo2.setSumToal(sumTotalVoice);
                marginVo2.setSumRemain(sumRemainVoice);
                marginVo2.setSumUsed(sumUsedVoice);
                marginVo2.setList(listFlow);

                Double sumTotalMessage=0.0;
                Double sumRemainMessage=0.0;
                Double sumUsedMessage=0.0;
                for (Uip100007 uip:listMessage){
                    sumTotalMessage+=Double.valueOf(uip.getTotal());
                    sumRemainMessage+=Double.valueOf(uip.getRemain());
                    sumUsedMessage+=Double.valueOf(uip.getUsed());

                }
                MarginVo marginVo3=new MarginVo();
                marginVo3.setSumToal(sumTotalVoice);
                marginVo3.setSumRemain(sumRemainVoice);
                marginVo3.setSumUsed(sumUsedVoice);
                marginVo3.setList(listMessage);

                Map<String,MarginVo> map= new HashedMap();
                map.put("1",marginVo0);
                map.put("2",marginVo1);
                map.put("4",marginVo2);
                map.put("5",marginVo3);
                loggingService.writeLogInfo(serialNum,"余量查询",globalResponse.getData());
                return  GlobalResponse.success(map);
            }
            return GlobalResponse.fail("查询余量失败");
        }catch(Exception e){
            log.error("findMargin exception",e);
            return GlobalResponse.fail("正在加进对接中。。。。");
        }
    }


    /**
     * 按月份查询缴费记录
     * @param serialNum
     * @param month yyyyMM
     * @return
     */
    public GlobalResponse findPaymentRecByMonth(String serialNum,String month){
        AssertHelper.notBlank(serialNum,"手机号码为空");
        AssertHelper.notBlank(month,"月份为空");
        if(mockHelper.isMock()){
            return GlobalResponse.success("true");
        }
        try{
            int  yyyy=Integer.parseInt(month.substring(0,4));
            int  MM= Integer.parseInt(month.substring(4,5));
            String starttime=month+"01";
            String  maxDay  = DateUtil.getMonthLastDay(yyyy,MM)+"";
            String  endTime = month+maxDay;
            IData param = new DataMap();
            param.put("TELNUM",serialNum);
            param.put("SDATE",starttime);
            param.put("EDATE",endTime);
            GlobalResponse globalResponse = SvcHelper.postSvc("100012", param, "缴费记录查询");
            loggingService.writeLogInfo(serialNum,"缴费记录查询",globalResponse.getData());
            if (globalResponse.isSuccess()){
                 String json=   JsonHelper.toJson(globalResponse.getData());
                 JavaType jt = JsonHelper.constructParametricType(List.class,Uip100012.class);
                 List<Uip100012> list = JsonHelper.fromJson(json,jt);
                 return  GlobalResponse.success(list);
            }
            return globalResponse;
        }catch(Exception e){
            log.error("findPaymentRecByMonth exception",e);
        }
        return GlobalResponse.fail("按月份查询缴费记录失败");

    }


    /**
     * 按月份查询账单记录 no
     * @param serialNum
     * @param month yyyyMM
     * @return
     */
    public GlobalResponse findBillByMonth(String serialNum,String month){
        AssertHelper.notBlank(serialNum,"手机号码为空");
        AssertHelper.notBlank(month,"月份为空");
        /*if(mockHelper.isMock()){
            //模拟调用接口
            //返回Map<String,List>
            Map<String,List> dataMap = new Map<>();

            List<ConsumeColumn> list1 = new LinkedList<>();
            List<ConsumeColumn> list2 = new LinkedList<>();
            List<ConsumeColumn> list3 = new LinkedList<>();
            List<ConsumeColumn> list4 = new LinkedList<>();
            List<ConsumeColumn> list5 = new LinkedList<>();

            list1.add(new ConsumeColumn("增值业务费",1,true));
            list1.add(new ConsumeColumn("手机阅读",1,false));
            list2.add(new ConsumeColumn("上网费",60,true));
            list2.add(new ConsumeColumn("手机上网流量费",60,false));
            list3.add(new ConsumeColumn("消费合计",61,true));
            list4.add(new ConsumeColumn("抵扣额合计",61,true));
            list5.add(new ConsumeColumn("实际应缴合计",0,true));

            dataMap.put("addedServiceConsume",list1);//增值业务费用
            dataMap.put("networkConsume",list2);//上网费用
            dataMap.put("totalConsume",list3);//总消费
            dataMap.put("reduce",list4);//减免费用
            dataMap.put("MoneyShouldPay",list5);//实际应缴费用
            return GlobalResponse.success(dataMap);
        }*/
        try{
            IData param = new DataMap();
            param.put("TELNUM",serialNum);
            param.put("CYCLE",month);
            param.put("TYPE",117);
            GlobalResponse globalResponse = SvcHelper.postSvc("100213", param, "账单查询");
           if (globalResponse.isSuccess()) {
               String json = JsonHelper.toJson(globalResponse.getData());
               JavaType jt = JsonHelper.constructParametricType(List.class,Uip100213.class);
               List<Uip100213> list = JsonHelper.fromJson(json,jt);
               String result=list.get(0).getResult();
               Map<String,Object> map = parserCostInfo(result);
               Map<String, CostInfoItemVo> stringCostInfoItemVoMap = costInfo(map);
               loggingService.writeLogInfo(serialNum, "按月份查询账单记录", globalResponse.getData());
               return GlobalResponse.success(stringCostInfoItemVoMap);
           }
           return GlobalResponse.fail("查询账单记录失败");
        }catch(Exception e){
            log.error("findBillByMonth exception",e);
            return GlobalResponse.fail("正在加进对接中。。。。");
        }
    }

    /**
     * 积分查询
     * @param serialNum
     * @return
     */
    public GlobalResponse findPoings(String serialNum){
        AssertHelper.notBlank(serialNum,"手机号码为空");
        if(mockHelper.isMock()){
            //模拟调接口
            //模拟返回int积分
            List<Score> dataList  = new ArrayList<>();
            dataList.add(new Score(500,450));
            return GlobalResponse.success(dataList);
        }
        try{
            IData param = new DataMap();
            param.put("TELNUM",serialNum);
            GlobalResponse globalResponse = SvcHelper.postSvc("100010",param,"积分查询");
            loggingService.writeLogInfo(serialNum,"积分查询",globalResponse.getData());
            if(globalResponse.isSuccess()){
                String json=JsonHelper.toJson(globalResponse.getData());
                JavaType jt = JsonHelper.constructParametricType(List.class,Uip100010.class);
                List<Uip100010> list = JsonHelper.fromJson(json,jt);
                return GlobalResponse.success(list.get(0));
            }
            return globalResponse;
        }catch(Exception e){
            log.error("findPoings exception",e);
        }
        return GlobalResponse.fail("查询积分失败");
    }

    /**
     * 套餐办理
     * @param serialNum
     * @param mealCode
     * @param efftype
     * @return
     */
    public GlobalResponse addMealByNcode(String serialNum,String mealCode,String efftype){
        AssertHelper.notBlank(serialNum,"手机号码为空");
        AssertHelper.notBlank(mealCode,"套餐编码");
        if(mockHelper.isMock()){
            return GlobalResponse.success("true");
        }
        try{
            IData param = new DataMap();
            param.put("TELNUM",serialNum);
            param.put("NCODE",mealCode);
            param.put("STYPE","ADD");
            param.put("EFFTYPE",efftype);
            param.put("NOTES","套餐办理");
            GlobalResponse globalResponse = SvcHelper.postSvc("100045", param, "新增业务办理");
            loggingService.writeLogInfo(serialNum,"套餐办理",globalResponse.getData());
            if(globalResponse.isSuccess()){
                String json = JsonHelper.toJson(globalResponse.getData());
                JavaType jt = JsonHelper.constructParametricType(List.class,Map.class);
                List<Map> list= JsonHelper.fromJson(json,jt);
                Map hashMap = list.get(0);
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

    public  GlobalResponse changeMeal(String serialNum,String mealCode,String efftype,String cparam){
        AssertHelper.notBlank(serialNum,"手机号码为空");
        AssertHelper.notBlank(mealCode,"套餐编码");
        if(mockHelper.isMock()){
            return GlobalResponse.success("true");
        }
        try{
            IData param = new DataMap();
            param.put("TELNUM",serialNum);
            param.put("NCODE",mealCode);
            param.put("STYPE","ADD");
            param.put("EFFTYPE",efftype);
            param.put("PARM",cparam);
            param.put("NOTES","套餐办理");
            GlobalResponse globalResponse = SvcHelper.postSvc("100115", param, "主体产品转换");
            loggingService.writeLogInfo(serialNum,"主体产品转换",globalResponse.getData());
            if(globalResponse.isSuccess()){
                String json = JsonHelper.toJson(globalResponse.getData());
                JavaType jt = JsonHelper.constructParametricType(List.class,Map.class);
                List<Map> list= JsonHelper.fromJson(json,jt);
                Map hashMap = list.get(0);
                if (hashMap.get("X_RESULTCODE").toString().equals("0")){
                    return GlobalResponse.success("套餐办理成功");
                }else{
                    return GlobalResponse.fail(hashMap.get("X_RESULTINFO").toString());
                }
            }
            return  globalResponse;
        }catch(Exception e){
            log.error("changeMeal exception",e);
        }
        return GlobalResponse.fail("主体产品转移失败");
    }

    /**
     * 获取当前用户的资费
     * @param serialNum
     * @return
     */
    public GlobalResponse findCurrentMeal(String serialNum){
        AssertHelper.notBlank(serialNum,"手机号码为空");
        if(mockHelper.isMock()){
            return GlobalResponse.success("true");
        }
        IData param = new DataMap();
        param.put("TELNUM",serialNum);
        param.put("FLAG","ServiceAndPrivilege");
        param.put("QRYNEXTMONTH","1");
        GlobalResponse globalResponse=SvcHelper.postSvc("100006", param, "业务开通和套餐使用状态查询");
        return  globalResponse;
    }


    /**
     * 增值业务互斥检查
     * @param serialNum
     * @param ncode
     * @param efftype
     * @return
     */
    public GlobalResponse checkZzMutex(String serialNum,String ncode,String efftype){
        AssertHelper.notBlank(serialNum,"手机号码为空");
        AssertHelper.notBlank(ncode,"套餐编码");
        if(mockHelper.isMock()){
            return GlobalResponse.success("true");
        }
        try{
            IData param = new DataMap();
            param.put("TELNUM",serialNum);
            param.put("NCODE",ncode);
            param.put("STYPE","ADD");
            param.put("EFFTYPE",efftype);
            GlobalResponse gr=SvcHelper.postSvc("100226", param, "增值业务互斥检查");
            String json=JsonHelper.toJson(gr.getData());
            JavaType jt=JsonHelper.constructParametricType(List.class,Uip100226.class);
            List<Uip100226> list=JsonHelper.fromJson(json,jt);
/*        IDataset dataset=JsonHelper.fromJson(json,IDataset.class);
       for (int i=0;i<dataset.size();i++){
            ZzMutexVo zzMutexVo=new ZzMutexVo();
            zzMutexVo.setTELNUM(dataset.get(i,"TELNUM").toString());
            zzMutexVo.setMTXENDDATE(dataset.get(i,"MTXENDDATE").toString());
            zzMutexVo.setMTXSTARTDATE(dataset.get(i,"MTXSTARTDATE").toString());
            zzMutexVo.setMTXID(dataset.get(i,"MTXID").toString());
            zzMutexVo.setMTXNAME(dataset.get(i,"MTXNAME").toString());
            zzMutexVo.setRETFLAG(dataset.get(i,"RETFLAG").toString());
            list.add(zzMutexVo);
        }*/
            return  GlobalResponse.success(list.get(0));

        }catch(Exception e){
            log.error("findBillByMonth exception",e);
            return GlobalResponse.fail("增值业务互斥检查失败。。。。");
        }

    }

    /**
     * 校验号码归属地
     * @param serialNum
     * @return
     */
    public GlobalResponse checkIsQcyd(String serialNum){
        AssertHelper.notBlank(serialNum,"手机号码为空");
        if (mockHelper.isMock()){
            return GlobalResponse.success("校验成功");
        }
        IData param = new DataMap();
        param.put("TELNUM",serialNum);
        GlobalResponse globalResponse= SvcHelper.postSvc("100046", param, "号码归属地查询");
        if (globalResponse.isSuccess()){
            JavaType jt = JsonHelper.constructParametricType(List.class,Map.class);
            List<Map> relist = JsonHelper.fromJson(JsonHelper.toJson(globalResponse.getData()), jt);
            Map hashMap = relist.get(0);
            if (Objects.equals(hashMap.get("X_RESULTCODE").toString(),"0")){
                if (hashMap.get("AREAID").toString().contains("CQ.CQ")){
                    return GlobalResponse.success("验证成功");
                }
                return GlobalResponse.fail("非重庆移动手机号码");
            }else{
                return GlobalResponse.fail(hashMap.get("X_RESULTINFO").toString());
            }
        }
        return globalResponse;
    }


    /**
     * 查询用户中心三项基本信息
     * @param serialNum
     * @return
     */
    public  GlobalResponse queryUserCenterBasicInfo(String serialNum,String month){
        AssertHelper.notBlank(serialNum,"手机号码为空");
        if(mockHelper.isMock()){
            //模拟调用接口
            //模拟返回list 包含：本月消费monthConsume、剩余话费remainingCharge、
            //                 可用积分usableIntegral
            UserCenterBasicInfo userCenterBasicInfo = new UserCenterBasicInfo();
            userCenterBasicInfo.setMonthConsume(200.00);
            userCenterBasicInfo.setRemainingCharge(109.30);
            userCenterBasicInfo.setUsableIntegral("23.2");
            return GlobalResponse.success(userCenterBasicInfo);
        }
        try{
            //设置月消费、余额、可用积分
            UserCenterBasicInfo userCenterBasicInfo = new UserCenterBasicInfo();
            //积分列表
            GlobalResponse poings = findPoings(serialNum);
            if (poings.isSuccess()){
                Uip100010 uip100010 = JsonHelper.fromJson(JsonHelper.toJson(poings.getData()), Uip100010.class);
                userCenterBasicInfo.setUsableIntegral(uip100010.getAvailscore());
            }
            //获取当前日期 yyyymmdd 先写死
            //当前账户信息列表
            Date date=DateUtils.parseDate(month,"yyyyMMdd");
            String dateMonth=DateFormatUtils.format(date,"yyyyMM");
            Map<String, CostInfoItemVo> stringCostInfoItemVoMap = (Map<String, CostInfoItemVo>)findBillByMonth(serialNum,dateMonth).getData();

            Uip100041 uip100041 = (Uip100041)queryAccountCharges(serialNum).getData();
            System.out.println("余额："+uip100041.getLeftmoney());
            CurrentCharges currentCharges =new CurrentCharges();
            BeanUtils.copyProperties(uip100041,currentCharges);
            System.out.println("余额："+currentCharges.getLeftmoney());
            Double monthConsume=0.0;
            monthConsume=Double.valueOf(stringCostInfoItemVoMap.get("E").getParent());
            userCenterBasicInfo.setMonthConsume(monthConsume);
            userCenterBasicInfo.setRemainingCharge(Double.valueOf(uip100041.getLeftmoney()));

            loggingService.writeLogInfo(serialNum,"查询用户中心三项基本信息",userCenterBasicInfo);
            return GlobalResponse.success(userCenterBasicInfo);
        }catch(Exception e){
            log.error("queryUserCenterBasicInfo exception",e);
            return GlobalResponse.fail("正在加进对接中。。。。");
        }
    }

    /**
     * 查询账户信息
     * @param serialNum
     * @param month  YYYYMMDD
     * @return
     */
    private GlobalResponse queryAccountInfo(String serialNum,String month){
        AssertHelper.notBlank(serialNum,"手机号码为空");
        AssertHelper.notBlank(month,"月份为空");
        if(mockHelper.isMock()){
            return GlobalResponse.success("success");
        }
        try{
            IData para = new DataMap();
            para.put("TELNUM",serialNum);
            para.put("CYCLE",month);//日期格式为yyyymmdd
            GlobalResponse globalResponse =  SvcHelper.postSvc("100008",para,"查询账户信息");
            loggingService.writeLogInfo(serialNum,"查询账户信息",globalResponse.getData());
            if(globalResponse.isSuccess()) {
                String json = JsonHelper.toJson(globalResponse.getData());
                JavaType javaType = JsonHelper.constructParametricType(List.class, Uip100008.class);
                List<Uip100008> list = JsonHelper.fromJson(json, javaType);
                return GlobalResponse.success(list.get(0));
            }
            return globalResponse;
        }catch(Exception e){
            log.error("queryAccountInfo exception:",e);
        }
        return GlobalResponse.fail("查询账户信息失败");
    }

    /**
     * 查询账户当前余额
     * @param serialNum
     * @return
     */
    private GlobalResponse queryAccountCharges(String serialNum){
        AssertHelper.notBlank(serialNum,"手机号码为空");
        if (mockHelper.isMock()){
            return GlobalResponse.success("success");
        }
        try{
            IData para = new DataMap();
            para.put("TELNUM",serialNum);
            GlobalResponse globalResponse =  SvcHelper.postSvc("100041",para,"查询账户余额");
            if(globalResponse.isSuccess()){
                String json=JsonHelper.toJson(globalResponse.getData())   ;
                JavaType jt = JsonHelper.constructParametricType(List.class,Uip100041.class);
                List<Uip100041> list = JsonHelper.fromJson(json,jt);
                return  GlobalResponse.success(list.get(0));
            }
            return globalResponse;
        }catch(Exception e){
            log.error("queryAccountCharges exception:",e);
        }
        return GlobalResponse.fail("查询账户当前余额失败");
    }

    /**
     * 判断是否主体套餐变更
     * @param serialNum
     * @return
     */
    public GlobalResponse  checkIsMealOnMonth(String serialNum){
        AssertHelper.notBlank(serialNum,"手机号码为空");
        if(mockHelper.isMock()){
            return GlobalResponse.success(false,"success");
        }
        // 判断是否转移资费
        IData param=new DataMap();
        param.put("TELNUM",serialNum);
        GlobalResponse globalResponse = SvcHelper.postSvc("100183", param, "查询用户主体产品变更情况");
        loggingService.writeLogInfo(serialNum,"查询用户主体产品变更情况",globalResponse.getData());
        if (globalResponse.isSuccess()){
            String json = JsonHelper.toJson(globalResponse.getData());
            JavaType jt = JsonHelper.constructParametricType(List.class,Map.class);
            List<Map> list = JsonHelper.fromJson(json,jt);
            Map hashMap = list.get(0);
            if (hashMap.get("RETFLAG") != null && hashMap.get("RETFLAG").toString().equals("0")){
                return GlobalResponse.success(true,"当月办理过主体产品变更");
            }else{
                return GlobalResponse.success(false,"当月未办理过主体产品变更");
            }
        }
        return globalResponse;
    }

    /**
     * 业务开通和套餐使用状态查询
     * 判断是否主题套餐变更
     * @param serialNum
     * @param flag
     * @param qryNextMonth
     * @param privid
     * @return
     */
    public GlobalResponse queryBusinessOpenedAndPackageUsage(String serialNum,String flag,Integer qryNextMonth,String privid){
        AssertHelper.notNull(serialNum,"电话号码不能为空");
        AssertHelper.notNull(flag,"查询标记不能为空");
        if (mockHelper.isMock()){
            return GlobalResponse.success("success");
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
                return GlobalResponse.success(list.get(0));
            }
            return GlobalResponse.fail("查询业务开通和套餐使用状态失败");
        }catch(Exception e){
            log.error("queryBusinessOpenedAndPackageUsage exception:"+e);
            return  GlobalResponse.fail("正在加进对接中。。。。");
        }
    }

    private static Map<String,Object> parserCostInfo(String val){
       String[] items =  StringUtils.split(val,";");
       List<String> itemslist = Lists.newArrayList(items);
       Map<String,Object> map = Maps.newHashMap();
       StringBuffer temstr =new StringBuffer();

       itemslist.stream().forEach(item ->{
           if(!StringUtils.contains(item," ")){
               List<String> subitems = Lists.newArrayList();
               map.put(item,subitems);
               temstr.delete(0,temstr.length());
               temstr.append(item);
           }else{
               ((List<String>)map.get(temstr.toString())).add(item);
           }
       });
       return map;
    }

    private static Map<String,CostInfoItemVo> costInfo(Map<String,Object> map)throws Exception{
        Set<String> keys = map.keySet();
        Map<String,CostInfoItemVo> result = Maps.newHashMap();
        for(String key:keys){
            String[] arr = StringUtils.split(key,"~");
            if(StringUtils.equals(arr[1],"12")||StringUtils.startsWith(arr[2],"套餐及固定费")){
                CostInfoItemVo costInfoItemVo = new CostInfoItemVo();
                costInfoItemVo.setParent(AmountUtils.changeF2Y(arr[3]));
                List<String> values = (List<String>)map.get(key);
                costInfoItemVo.setSubitem(parserList(values));
                result.put("A",costInfoItemVo);

            }else if(StringUtils.equals(arr[1],"15")||StringUtils.startsWith(arr[2],"套餐外")){
                CostInfoItemVo costInfoItemVo = new CostInfoItemVo();
                costInfoItemVo.setParent(AmountUtils.changeF2Y(arr[3]));
                List<String> values = (List<String>)map.get(key);
                costInfoItemVo.setSubitem(parserList(values));
                result.put("B",costInfoItemVo);
            }else if(StringUtils.equals(arr[1],"16")||StringUtils.startsWith(arr[2],"增值业务费")){
                CostInfoItemVo costInfoItemVo = new CostInfoItemVo();
                costInfoItemVo.setParent(AmountUtils.changeF2Y(arr[3]));
                List<String> values = (List<String>)map.get(key);
                costInfoItemVo.setSubitem(parserList(values));
                result.put("C",costInfoItemVo);
            }else if(StringUtils.equals(arr[1],"29")||StringUtils.equals(arr[2],"减免")){
                CostInfoItemVo costInfoItemVo = new CostInfoItemVo();
                costInfoItemVo.setParent(AmountUtils.changeF2Y(arr[3]));
                List<String> values = (List<String>)map.get(key);
                costInfoItemVo.setSubitem(parserList(values));
                result.put("D",costInfoItemVo);
            }else if(StringUtils.equals(arr[1],"XFHJ")||StringUtils.equals(arr[2],"消费合计")){
                CostInfoItemVo costInfoItemVo = new CostInfoItemVo();
                costInfoItemVo.setParent(AmountUtils.changeF2Y(arr[3]));
                List<String> values = (List<String>)map.get(key);
                costInfoItemVo.setSubitem(parserList(values));
                result.put("E",costInfoItemVo);
            }
        }
        return result;
    }

    private static Map<String,String> parserList(List<String> strs)throws Exception{
        Map<String,String> map =Maps.newHashMap();
        for(String str:strs){
          String[] vals =   StringUtils.split(str,"~");
          String[] svals = StringUtils.split(StringUtils.trim(vals[2]),"-");
          if(svals.length>2){
              map.put(svals[2]+svals[0],AmountUtils.changeF2Y(vals[3]));
          }else{
              map.put(svals[0],AmountUtils.changeF2Y(vals[3]));
          }
        }
        return map;

    }

    public static void main(String[] args)throws Exception{
        String val = "0~12~套餐及固定费~18690~2308128950346~唐*~201804;1~12.02.507603~    4G飞享套餐58元档~5800~2308128950346~唐*~201804;2~12.02.641354~    定向流量月包-18元-优酷视频~1800~2308128950346~唐*~201804;3~12.02.641358~    定向流量月包-18元-西瓜视频~1800~2308128950346~唐*~201804;4~12.02.641361~    定向流量月包-18元-咪咕直播~1800~2308128950346~唐*~201804;5~12.02.641362~    定向流量月包-10元-芒果TV~1000~2308128950346~唐*~201804;6~12.02.641366~    定向流量月包-10元-咪咕视频~1000~2308128950346~唐*~201804;7~12.02.641368~    定向流量月包-10元-哔哩哔哩~1000~2308128950346~唐*~201804;8~12.02.641369~    定向流量月包-10元-PPTV~1000~2308128950346~唐*~201804;9~12.02.641400~    流量直充日包15元-2G~1500~2308128950346~唐*~201804;10~12.02.641401~    流量直充日包10元-1G~1000~2308128950346~唐*~201804;11~12.02.680364~    爱奇艺会员组合周包~990~2308128950346~唐*~201804;12~15~套餐外短彩信费~50~2308128950346~唐*~201804;13~15.10~    国内（不含港澳台）短信费~50~2308128950346~唐*~201804;14~16~增值业务费~150~2308128950346~唐*~201804;15~16.297~    手机视频~150~2308128950346~唐*~201804;16~29~减免~-18740~2308128950346~唐*~201804;17~XFHJ~消费合计~150~~~;18~TRDF~他人代付~0~~~;19~JTDF~集团代付~0~~~;20~ZSDK~赠送话费抵扣金额~0~~~;21~BQTF~本期退费~0~~~;";
        Map<String,Object> map = parserCostInfo(val);
        Map<String, CostInfoItemVo> stringCostInfoItemVoMap = costInfo(map);
        String str = JsonHelper.toJson(stringCostInfoItemVoMap);
        System.out.println(str);
//        Set<String> keys = map.keySet();
//
//        for(String key:keys){
//            System.out.print("key value eq :" + key);
//            StringUtils.split("key");
//            List<String> values = (List<String>)map.get(key);
//            System.out.println(" value eq : "+values);
//        }
    }
}
