package com.ai.smart.third.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import com.linkage.appframework.data.DataMap;
import com.linkage.appframework.data.IData;

/**
 *
 * CQBOSSPayParamsHelper
 *
 * @author：yangzy@asiainfo.com
 * @Sep：2015-5-11 上午09:08:59
 * @version 1.0
 */
public class CQBOSSPayParamsHelper {
	private static final String PRINT_VOICE_FLAG = "4";
	private static final String NOT_PRINT_VOICE_FLAG = "2";
	private static final String PRINT_FLAG_KEY = "PRINT_FLAG";
	private static final String OUTPAY_CHANNEL="CQWX";//外围缴费渠道
	public static IData paragraths = new DataMap();

	/**
	 * 数据包包头字段组装
	 * getPackageHead
	 * @param param
	 * @return
	 * @return String返回说明
	 * @Exception 异常说明
	 * @author：yangzy@asiainfo.com
	 * @create：2015-5-11 上午11:30:16
	 * @moduser：
	 * @moddate：
	 * @remark：
	 */
	public static String getPackageHead(JSONObject param, String opcode){
		//交易代码
		String tranType = opcode;
		//交易时间
		String orderTime = param.getString("ORDER_TIME");
		//外围缴费渠道
		String outPayChannel = OUTPAY_CHANNEL;

		/*
		 * 开始获取包头数据
		 */
		//包长度
		String packageLength = "";
		//区号
		String areaCode = "230";
		//渠道类型 0-电子商务中心
		String channelType = "0";
		//缴费网点
		String networkCode = getEmptyString(10);
		//操作员编号
		String operatorCode = getEmptyString(8);
		//有无后续包 0-无 1-有
		String flowFlag = "0";
		//错误代码
		String errorCode = "000";
		//版本号
		String version = "15";
		//MAC校验
		String macCode = getEmptyString(16);

		//包长度获取
		if("3000".equals(tranType)){//缴费查询 70+91
			packageLength = "161" + getEmptyString(1);
		}else if("2000".equals(tranType)){
			//新协议 缴纳话费 70+85
			//老协议 缴纳话费 70+83
			packageLength = (isNew2000Protocol(param) ? "155" : "153") + getEmptyString(1);
		}else if("1000".equals(tranType)){//话费查询 70+25
			packageLength = "95" + getEmptyString(2);
		}else if("2002".equals(tranType)){//国际漫游，宽带预存 70+90
			packageLength = "160" + getEmptyString(1);
		}
		//缴费渠道校验
		if(outPayChannel.length() < 4){
			outPayChannel = outPayChannel + getEmptyString(4-outPayChannel.length());
		}else{
			outPayChannel = outPayChannel.substring(0, 4);
		}
		//交易时间验证
		if(isEmpty(orderTime) || orderTime.length() != 14){
			orderTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		}
		//组装包头
		String packageHead = packageLength + areaCode
				+ channelType + outPayChannel
				+ networkCode + operatorCode
				+ flowFlag + tranType
				+ errorCode + orderTime
				+ version + macCode;

		return packageHead;
	}

	/**
	 * 数据包包体字段组装
	 * getPackageBody
	 * @param param
	 * @return
	 * @return String返回说明
	 * @Exception 异常说明
	 * @author：yangzy@asiainfo.com
	 * @create：2015-5-11 上午11:33:31
	 * @moduser：
	 * @moddate：
	 * @remark：
	 */
	public static String getPackageBody(JSONObject param, String opcode){
		String packageBody = "";
		//交易代码
		String tranType = opcode;
		if("3000".equals(tranType)){//缴费查询
			//缴费流水 22
			String streamingNumber = param.getString("STREAMING_NUM");
			//缴费日期 8
			String orderDate = param.getString("ORDER_DATE");
			//缴费号码 25
			String telNum = param.getString("TEL_NUM");
			//缴费金额（单位元） 9
			String payMent = param.getString("PAY_MENT").trim();
			//移动赠送金额 9
			String giveAmount = param.getString("GIVE_AMOUNT").trim();
			//第三方赠送金额 9
			String thirdGiveAmount = param.getString("THIRD_GIVE_AMOUNT").trim();
			//积分个数 9
			String score = param.getString("SCORE").trim();

			//缴费号码处理
			telNum = telNum + getEmptyString(25-telNum.length());
			//缴费金额处理
			payMent = payMent + getEmptyString(9-payMent.length());
			//移动赠送金额处理
			if(isEmpty(giveAmount) || giveAmount.equals("0") || giveAmount.equals("0.0") || giveAmount.equals("0.00")){
				giveAmount = "0";
			}
			giveAmount = giveAmount + getEmptyString(9-giveAmount.length());
			//第三方赠送金额处理
			if(isEmpty(thirdGiveAmount) || thirdGiveAmount.equals("0") || thirdGiveAmount.equals("0.0") || thirdGiveAmount.equals("0.00")){
				thirdGiveAmount = "0";
			}
			thirdGiveAmount = thirdGiveAmount + getEmptyString(9-thirdGiveAmount.length());
			//积分字段为空处理
			if(isEmpty(score)){
				score = "0";
			}
			score = score + getEmptyString(9-score.length());
			packageBody = streamingNumber + orderDate + telNum + payMent + giveAmount + thirdGiveAmount + score;
		}else if("2000".equals(tranType)){//缴纳话费
			//缴费流水 22
			String streamingNumber = param.getString("STREAMING_NUM");
			//缴费号码 25
			String telNum = param.getString("TEL_NUM");
			//缴费金额（单位元） 9
			String payMent = param.getString("PAY_MENT").trim();
			//移动赠送金额 9
			String giveAmount = param.getString("GIVE_AMOUNT").trim();
			//第三方赠送金额 9
			String thirdGiveAmount = param.getString("THIRD_GIVE_AMOUNT").trim();
			//积分个数 9
			String score = param.getString("SCORE").trim();
			//发票打印方式
			String printFlag = "";
			if (isNew2000Protocol(param)) {
				printFlag = param.getString(PRINT_FLAG_KEY);
				printFlag = printFlag + getEmptyString(2 - printFlag.length());

			}

			//缴费号码处理
			telNum = telNum + getEmptyString(25-telNum.length());
			//缴费金额处理
			payMent = payMent + getEmptyString(9-payMent.length());
			//移动赠送金额处理
			if(isEmpty(giveAmount) || giveAmount.equals("0") || giveAmount.equals("0.0") || giveAmount.equals("0.00")){
				giveAmount = "0";
			}
			giveAmount = giveAmount + getEmptyString(9-giveAmount.length());
			//第三方赠送金额处理
			if(isEmpty(thirdGiveAmount) || thirdGiveAmount.equals("0") || thirdGiveAmount.equals("0.0") || thirdGiveAmount.equals("0.00")){
				thirdGiveAmount = "0";
			}
			thirdGiveAmount = thirdGiveAmount + getEmptyString(9-thirdGiveAmount.length());
			//积分字段为空处理
			if(isEmpty(score)){
				score = "0";
			}
			score = score + getEmptyString(9-score.length());
			packageBody = streamingNumber + telNum + payMent + giveAmount + thirdGiveAmount + score + printFlag;
		}else if("1000".equals(tranType)){//话费查询
			//缴费号码 25
			String telNum = param.getString("TEL_NUM");
			//缴费号码处理
			telNum = telNum + getEmptyString(25-telNum.length());
			packageBody = telNum;
		}else if("2002".equals(tranType)){//宽带缴纳话费
			//缴费流水 22
			String streamingNumber = param.getString("STREAMING_NUM");
			//缴费号码 25
			String telNum = param.getString("TEL_NUM");
			//缴费金额（单位元） 9
			String payMent = param.getString("PAY_MENT").trim();
			//积分个数 9
			String score = param.getString("SCORE").trim();
			//发票打印控制 1  新增字段
			String print = param.getString("PRINT").trim();
			//受理类型 4  新增字段
			String acceptType = param.getString("ACCEPT_TYPE").trim();
			//缴费科目 20 新增字段
			String payType = param.getString("PAY_TYPE").trim();
			//缴费号码处理
			telNum = telNum + getEmptyString(25-telNum.length());
			//缴费金额处理
			payMent = payMent + getEmptyString(9-payMent.length());
			//积分字段为空处理
			if(isEmpty(score)){
				score = "0";
			}
			print = print + getEmptyString(1-print.length());

			acceptType = acceptType + getEmptyString(4-acceptType.length());

			payType = payType + getEmptyString(20-payType.length());

			score = score + getEmptyString(9-score.length());

			packageBody = streamingNumber + telNum + payMent + score + print + acceptType + payType;
		}

		return packageBody;
	}

	/**
	 * 返回字段解析
	 * responseMsgAnalyse
	 * @param response
	 * @return
	 * @return List返回说明
	 * @Exception 异常说明
	 * @author：yangzy@asiainfo.com
	 * @create：2015-5-11 下午07:32:59
	 * @moduser：
	 * @moddate：
	 * @remark：
	 */
	public static Map responseMsgAnalyse(Object response, String opCode){
		Map map = new HashMap();
		if(!isEmpty(response) && !isEmpty(opCode)){
			String backMsg = (String)response;
			//截取包头
			String head = backMsg.substring(0, 70);
			//从包体开始 截取到包结束位置
			String body = backMsg.substring(70, backMsg.length());
			//反馈响应代码定义
			String resultFlag = head.substring(35,38);
			map.put("RESULT_FLAG", resultFlag);
			if(!"000".equals(resultFlag)){
				return map;
			}

			if("3000".equals(opCode)){//缴费查询
				map.put("TEL_NUM", body.substring(0, 25).trim());		//手机号码 25
				map.put("STREAMING_NUM", body.substring(25, 50));		//BOSS侧缴费受理编号 25
				map.put("ORDER_DATE", body.substring(50, 64));			//缴费操作时间（格式:24小时制YYYYMMDDHHMISS） 14
				map.put("PAY_MENT", body.substring(64, 73).trim());		//缴费金额(单位：元) 9
				if(body.length() >= 217){
					map.put("GIVE_NUMBER", body.substring(73, 98).trim());	//BOSS侧移动赠送受理编号 25
					map.put("GIVE_TIME", body.substring(98, 112).trim());	//移动赠送操作时间 14
					map.put("GIVE_MENT", body.substring(112, 121).trim());	//移动赠送金额(单位：元) 9
					map.put("THIRD_GIVE_NUMBER", body.substring(121, 146).trim());	//BOSS侧移动赠送受理编号 25
					map.put("THIRD_GIVE_TIME", body.substring(146, 160).trim());	//第三方赠送操作时间 14
					map.put("THIRD_GIVE_MENT", body.substring(160, 160).trim());	//第三方赠送金额(单位：元) 9
					map.put("SCORE_NUMBER", body.substring(169, 194).trim());//积分支付受理编号  25
					map.put("SCORE_TIME", body.substring(194, 208).trim());	//积分支付操作时间 14
					map.put("SCORES", body.substring(208, 217).trim());		//积分支付个数 9
				}
			}else if("2000".equals(opCode)){//缴纳话费
				map.put("STREAMING_NUM", body.substring(0, 22));		//请求交易流水号 22
				map.put("TEL_NUM", body.substring(22, 47).trim());		//手机号码 25
				if(body.length() >= 156){
					map.put("PAY_NUMBER", body.substring(47, 72).trim());	//BOSS侧移动赠送受理编号 25
					map.put("GIVE_NUMBER", body.substring(72, 97).trim());	//BOSS侧移动赠送受理编号 25
					map.put("THIRD_GIVE_NUM", body.substring(97, 122).trim());//BOSS侧第三方赠送受理编号 25
					map.put("BALANCE", body.substring(122, 131).trim());	//帐户余额 9
					map.put("SCORE_NUMBER", body.substring(131, 156).trim());//积分支付受理编号  25
				}
			}else if("1000".equals(opCode)){//话费查询
				map.put("TEL_NUM", body.substring(0, 25).trim());		//手机号码 25
				map.put("BALANCE", body.substring(25, body.length()).trim());//话费余额 9
			}
		}
		return map;

	}

	/**
	 * 产生流水号
	 * yyyyMMddHHmmss+六位随机数
	 * @return
	 */
	public static String createSerialNo(){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Random rand = new Random();
		int n = rand.nextInt(1000000);
		String randn = "" + n;

		for(int i = 0 ; i < 6 - randn.length() ; i++){
			randn = "0" + randn;
		}

		return format.format(new Date(System.currentTimeMillis())) + randn;
	}

	/**
	 * 产生财付通的订单号：格式如下：
	 *        8位日期（yyyymmdd)和8位流水（发给boss的流水），对账文件取数据的时候，只取后8位
	 * @return
	 * @throws Exception
	 */
	public static String createTenpayBillNo() throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String billNo = format.format(new Date(System.currentTimeMillis())) + getRandomNum(8);
		return billNo;
	}

	/**
	 * 产生工行订单号：格式如下
	 * 　　银行标识 3位　＋ 日期 8 位 + 号码 11 位 +　boss流水号 8 位（2天之内不重复）
	 * @param telNum
	 * @return
	 * @throws Exception
	 */
	public static String createIcbcBillNo(String telNum) throws Exception{
		String a1 = "XXX";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String a2 = format.format(new Date());
		String a3 = telNum;
		String a4 = getRandomNum(8);

		return a1 + a2 + a3 + a4;
	}

	/**
	 * 获取空字符
	 * getEmptyString
	 * @param n
	 * @return
	 * @return String返回说明
	 * @Exception 异常说明
	 * @author：yangzy@asiainfo.com
	 * @create：2015-5-11 上午09:12:00
	 * @moduser：
	 * @moddate：
	 * @remark：
	 */
	public static String getEmptyString(int n){
		String s = "";

		for(int i=0; i<n; i++)
		{
			s += " ";
		}

		return s;
	}

	/**
	 * 获取8位随机数
	 *
	 * @return
	 */
	public static String getRandomNum(int n)
	{
		long begin = 1; // 99999999;

		for(int i=1; i<=n; i++)
		{
			begin =  begin * 10;
		}

		//	System.out.println("begin" + begin);
		long after = begin - 1;

		//	System.out.println("after:" + after);

		long rand = Math.round(Math.random() * begin);

		//	System.out.println("rand:" + rand);

		String num = String.valueOf(rand);

		if(num.length() < n)
		{
			num = String.valueOf(after - rand);
		}
		else
		{
			num = num.substring(0, num.length());
		}

		//	System.out.println(num);

		return num;
	}

	/**
	 * 判空： 空：true  非空：false
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if(null != obj && !"".equals(obj))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public static boolean isNew2000Protocol(JSONObject param) {
		String printFlag = null;
		if (param != null && param.containsKey(PRINT_FLAG_KEY)) {
			printFlag = param.getString(PRINT_FLAG_KEY);
		}

		return NOT_PRINT_VOICE_FLAG.equals(printFlag) || PRINT_VOICE_FLAG.equals(printFlag);
	}
}
