package com.ai.smart.common.helper.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatePartyFee {

/**
 * @auther 彭靖秩
 * @param 根据salary计算党费
 * @return
 */
	public static Integer calculatePartyFeeBySalary(double salary){
		//此工资为税后工资
		Double partyfee = null;
		int sumfee=0;
		if(salary<=3000&&salary>=0){
			partyfee=new BigDecimal(salary*0.005).setScale(0, RoundingMode.HALF_UP).doubleValue();
			sumfee=(int) ((partyfee*10)/10);
		}
		else if(salary>3000&&salary<=5000){
			partyfee=new BigDecimal(salary*0.010).setScale(0, RoundingMode.HALF_UP).doubleValue();
			sumfee=(int) ((partyfee*10)/10);

		}
		else if(salary>5000&&salary<=10000){
			partyfee=new BigDecimal(salary*0.015).setScale(0, RoundingMode.HALF_UP).doubleValue();
			sumfee=(int) ((partyfee*10)/10);

			 
		}
		else if(salary>10000){
			partyfee=new BigDecimal(salary*0.020).setScale(0, RoundingMode.HALF_UP).doubleValue();
			sumfee=(int) ((partyfee*10)/10);

		}
		return sumfee;
}
	/**
	 * 根据退休工资计算党费
	 * @param salary
	 * @return
	 * @author 彭靖秩
	 */
	public  Double calculatePartyFeeByRetireSalary(double salary){
		Double partyfee=0.0;
		if(salary>0&&salary<=5000){
			partyfee=new BigDecimal(salary*0.005).setScale(0, RoundingMode.HALF_UP).doubleValue();
		}
		else if(salary>5000){
			partyfee=new BigDecimal(salary*0.010).setScale(0, RoundingMode.HALF_UP).doubleValue();
		}
		return partyfee;
		
	}
}
